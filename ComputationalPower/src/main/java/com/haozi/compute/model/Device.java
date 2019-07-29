package com.haozi.compute.model;

import lombok.Data;

/**
 * @author hao.yang
 * @date 2019/7/24
 */
@Data
public class Device {

    private String deviceNumber;

    private Integer connectorId;

    private Integer type;   // 1:可降可停， 2：可降不可停， 3：不可降不可停

    private Integer power; // 当前功率

}
