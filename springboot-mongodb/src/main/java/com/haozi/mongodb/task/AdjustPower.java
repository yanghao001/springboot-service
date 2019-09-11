package com.haozi.mongodb.task;

import com.haozi.mongodb.model.Device;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 调节功率
 *
 * @author hao.yang
 * @date 2019/8/6
 */
@Slf4j
public class AdjustPower {


    /**
     * 下调功率
     *
     * @Param deviceNumber 桩号（带枪）
     * @Param type 站点类型
     * @Param deviceRatePower 桩额定功率
     * @Param stationRatePower 站点额定功率
     * */
    public static void downPower(List<Device> deviceList) {
        log.info("device need down power");
        for (Device device: deviceList) {
            String deviceNumebr = device.getDeviceNumber();

        }

    }

    /**
     * 上调功率
     *
     * @Param deviceNumber 桩号（带枪）
     * @Param type 站点类型
     * @Param deviceRatePower 桩额定功率
     * @Param stationRatePower 站点额定功率
     * */
    public static void upPower(List<Device> deviceList) {
        log.info("device need up power");

    }

}
