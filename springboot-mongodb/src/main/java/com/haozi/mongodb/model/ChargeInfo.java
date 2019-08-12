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
     *  时间戳
     * */
    private int timestamp;

    /**
     * 电压
     * */
    private String voltage;

    /**
     * 电流
     * */
    private String elecCurrent;

    /**
     * 订单号
     * */
    private int transactionId;

    /**
     * 桩号
     * */
    private String deviceNumber;
}
