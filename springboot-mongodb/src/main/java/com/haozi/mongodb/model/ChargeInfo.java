package com.haozi.mongodb.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class ChargeInfo {

    /**
     * 操作类型
     * */
    private String operationType;

    /**
     * 充电订单开始时间
     * */
    private int startTime;

    /**
     *  电池电量
     * */
    private String soc;

    /**
     * 充电量
     * */
    private Double totalPower;

    /**
     * 桩号
     * */
    private String deviceNumber;
}
