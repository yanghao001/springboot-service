package com.haozi.mongodb.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ChargeInfo {

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
