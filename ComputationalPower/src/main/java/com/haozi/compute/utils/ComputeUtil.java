package com.haozi.compute.utils;

import com.haozi.compute.config.ConstantConfig;
import com.haozi.compute.model.Device;
import com.haozi.compute.model.DownRange;

import java.util.ArrayList;
import java.util.List;

/**
 * 工具类：计算降幅针对三种类型的桩的停充方式
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
        List<Device> dropAndNoStops = new ArrayList<>(); // 端口个数: 2，可降不可停，额定100 1:120, 2:80
        List<Device> noDropAndNoStops = new ArrayList<>(); // 端口个数: 1，不可降不可停，额定100 1:120
        List<Device> dropStops = new ArrayList<>();
        for (Device device : devices) {
            int type = device.getType();
            switch (type) {
                case ConstantConfig.DROP_STOP:  // 可降可停
                    dropStops.add(device);
                    int overPower = device.getPower() - 100;
                    if (decline > 0) {
                        if (overPower > 0) {
                            decline = decline - device.getPower() / 10;
                            dropAndStops.add(device);
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

        if (decline < 0) { // 不需要停充，只需要降（可降可停端口即可）
            for (Device ds : dropAndNoStops) {
                DownRange downRange = new DownRange();
                downRange.setDeviceNumber(ds.getDeviceNumber());
                downRange.setConnectorId(ds.getConnectorId());
                downRange.setLimit(ds.getPower() * 9 / 10);
                downRanges.add(downRange);
            }
        } else if (decline > 0) { // 需要停充
            // 1. 需要停充可降可停枪口


            // 2. 需要降可降不可停枪口


            // 3. 需要停充可降不可停枪口


            // 4. 需要停充不可降不可停枪口

        } else {
            System.out.println("invalid");
        }

        return downRanges;
    }
}
