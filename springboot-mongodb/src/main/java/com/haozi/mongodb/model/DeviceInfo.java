package com.haozi.mongodb.model;

import lombok.Data;

/**
 * @author hao.yang
 * @date 2019/8/5
 */
@Data
public class DeviceInfo {

    private String stationName; // 站点名

    private Double capacity; // 电力容量

    private Integer controlRatio; // 调控系数

    private Integer reduceRatio; // 功率降幅

    private Integer maxCtrlRatio; // 调控上限

    private Integer minCtrlRatio; // 调控下限

}
