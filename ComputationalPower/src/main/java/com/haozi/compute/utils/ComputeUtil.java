package com.haozi.compute.utils;

import com.haozi.compute.config.ConstantConfig;
import com.haozi.compute.model.Device;
import com.haozi.compute.model.DownRange;

import java.util.ArrayList;
import java.util.List;

/**
 * 工具类：计算降幅针对三种类型的桩的停充方式
 * 两种方式降：（1）针对所有超出上限一起降，一次降到什么程度？ （2）针对功率大小排序，从高到低降功率，对着一根桩降，直到停充
 *
 * @author hao.yang
 * @date 2019/7/24
 */
public class ComputeUtil {

    /**
     * 同一站点下，decline: 降幅
     * 每个桩号deviceNumber枪
     */
    private static List<DownRange> compute(int decline, List<Device> devices) {
        List<DownRange> downRanges = new ArrayList<>();
        List<Device> dropAndStops = new ArrayList<>(); // 端口个数: 3，可降可停，额定100，1：110, 2:120, 3:90
        List<Device> dropAndNoStops = new ArrayList<>(); // 端口个数: 2，可降不可停，额定150 1:170, 2:180
        List<Device> noDropAndNoStops = new ArrayList<>(); // 端口个数: 1，不可降不可停，额定200 1:220
        List<Device> dropStops = new ArrayList<>();
        for (Device device : devices) {
            int type = device.getType();
            switch (type) {
                case ConstantConfig.DROP_STOP:  // 可降可停
                    dropStops.add(device);
                    int upPower = device.getPower() - 150; // 可降可停上限150
                    if (upPower > 0) {
                        int overPower = device.getPower() - 100;
                        if (decline > 0) {
                            if (overPower > 0) {
                                decline = decline - overPower;
                                dropAndStops.add(device);
                            }
                        }
                    }
                    break;
                case ConstantConfig.DROP_NO_STOP:  // 可降不可停
                    dropAndNoStops.add(device);
                    break;
                case ConstantConfig.NO_DROP_NO_STOP:  // 不可降不可停
                    noDropAndNoStops.add(device);
                    break;
                default:
                    break;
            }
        }

        if (decline <= 0) { // 1. 不需要停充，只需要降可降可停端口即可
            for (Device ds : dropAndStops) {
                DownRange downRange = new DownRange();
                downRange.setDeviceNumber(ds.getDeviceNumber());
                downRange.setConnectorId(ds.getConnectorId());
                downRange.setLimit(100);
                downRanges.add(downRange);
            }
        } else { // 2. 需要停充
            DownRange downRange = new DownRange();
            // 2.1 只需要停充可降可停枪口
            // 2.1.1 超出上限的端口停充
            for (int i = 0; i < dropAndStops.size(); i++) {
                if (decline >= 0) {
                    decline = decline - 100;
                    System.out.println("前" + i + "个可降可停端口停充即可");
                    if (dropAndStops.size() == dropStops.size()) {
                        System.out.println("可降可停端口全部停充");
                    }
                    downRange.setDeviceNumber(dropAndStops.get(i).getDeviceNumber());
                    downRange.setConnectorId(dropAndStops.get(i).getConnectorId());
                    downRange.setLimit(0);
                    downRanges.add(downRange);
                } else {
                    downRange.setDeviceNumber(dropAndStops.get(i).getDeviceNumber());
                    downRange.setConnectorId(dropAndStops.get(i).getConnectorId());
                    downRange.setLimit(100);
                    downRanges.add(downRange);
                }
            }

            // 2.1.2 可降可停未超出上限，端口停充
            for (int t = 0; t < dropStops.size(); t++) {
//                dropStops.get(t) !=
            }


            // 2.2 当可降可停端口全部停充了，还不满足要求，则需要降可降不可停枪口
            if (decline > 0) {
                List<Device> dropNoStops = new ArrayList<>();

                // 超出可降不可停端口上限(200)
                for (int j = 0; j < dropAndNoStops.size(); j++) {
                    int upPower = 200 - dropAndNoStops.get(j).getPower();
                    if (upPower > 0) {
                        int overPower = dropAndNoStops.get(j).getPower() - 150;
                        if (overPower > 0) {
                            decline = decline - overPower;
                            dropNoStops.add(dropAndNoStops.get(j));
                        }
                    }
                }

                // 2.2.1 仅需要降可降不可停端口即可
                if (decline < 0) {
                    for (Device ds : dropNoStops) {
                        downRange.setDeviceNumber(ds.getDeviceNumber());
                        downRange.setConnectorId(ds.getConnectorId());
                        downRange.setLimit(150);
                        downRanges.add(downRange);
                    }
                }

                // 2.2.2 当可降不停端口全部降完了，则需要停充可降不可停枪口
                if (decline > 0) {
                    for (int x = 0; x < dropNoStops.size(); x++) {
                        if (decline >= 0) {
                            decline = decline - 150;
                            System.out.println("前" + x + "个可降不可停端口停充即可");
                            if (dropAndStops.size() == dropStops.size()) {
                                System.out.println("可降不可停端口全部停充");
                            }
                            downRange.setDeviceNumber(dropAndStops.get(x).getDeviceNumber());
                            downRange.setConnectorId(dropAndStops.get(x).getConnectorId());
                            downRange.setLimit(0);
                            downRanges.add(downRange);
                        } else {
                            downRange.setDeviceNumber(dropAndStops.get(x).getDeviceNumber());
                            downRange.setConnectorId(dropAndStops.get(x).getConnectorId());
                            downRange.setLimit(150);
                            downRanges.add(downRange);
                        }
                    }
                }

                // 2.2.3 当可降不可停端口全部停充了，则需要停充不可降不可停枪口
                if (dropAndNoStops.size() == dropNoStops.size()) {
                    for (int y = 0; y < noDropAndNoStops.size(); y++) {
                        if (decline > 0) {
                            int upPower = noDropAndNoStops.get(y).getPower() - 250;
                            if (upPower > 0) {
                                decline = decline - noDropAndNoStops.get(y).getPower();
                                downRange.setDeviceNumber(dropAndStops.get(y).getDeviceNumber());
                                downRange.setConnectorId(dropAndStops.get(y).getConnectorId());
                                downRange.setLimit(0);
                                downRanges.add(downRange);
                            }
                        }
                    }

                    if (decline > 0) {
                        System.out.println("报警：全部端口停充依然不符合要求！！！！");
                    }
                }
            }
        }
        return downRanges;
    }
}
