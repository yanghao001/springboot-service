package com.haozi.mongodb.model;

import lombok.Data;

/**
 * @author hao.yang
 * @date 2019/8/6
 */
@Data
public class Device {
    private String deviceNumber; // 带枪号

    private String type;   // 1:可降可停， 2：可降不可停， 3：不可降不可停

    private Double capacity; // 电力容量

    private Integer controlRatio; // 调控系数

    private Integer reduceRatio; // 功率降幅

    private Integer maxCtrlRatio; // 调控上限

    private Integer minCtrlRatio; // 调控下限

}
