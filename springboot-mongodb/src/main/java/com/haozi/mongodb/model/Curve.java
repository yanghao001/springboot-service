package com.haozi.mongodb.model;

import lombok.Data;

/**
 * @author hao.yang
 * @date 2019/8/2
 */
@Data
public class Curve {
    /**
     * 操作类型
     * */
    private String operationType;

    /**
     * 充电订单
     * */
    private String orderNumber;

    /**
     * 充电订单开始时间
     * */
    private String startTime;

    /**
     *  充电电压
     * */
    private String voltage;

    /**
     * 充电电流
     * */
    private String elecCurrent;

    /**
     *  电池电量
     * */
    private String soc;

    /**
     * 温度
     * */
    private String temperature;

}
