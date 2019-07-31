package com.haozi.mongodb.config;

public class ConstantConfig {
    /**
     *   kafka topic
     */
    public static final String D2S_TOPIC = "d-request";
    public static final String D2S_REQ_TOPIC = "D_SERVER_REQ";

    /**
     *  电池上报类型
     */
    public static final String D_RREPORT_BATTERY_STATUS = "DReportBatteryStatusRequest";
    /**
     * 充电过程
     */
    public static final String D_REPORT_CHARGE_PROGRESS = "DReportChargeProgressRequest";
    /**
     *  请求操作类型
     */
    public static final String  REQ_OPERATION_TYPE_VOLTAGE = "voltage";
    public static final String  REQ_OPERATION_TYPE_CURRENT = "elecCurrent";
    public static final String  REQ_OPERATION_TYPE_SOC = "soc";
    public static final String  REQ_OPERATION_TYPE_TEMPERATURE = "temperature";


    /**
     *  Mongodb collection
     */
    public static final String MONGO_CHARGE_GRAPE_DATA = "chargeGraph";


    public static final Integer BASE_PARAMS_CODE = 1;
    public static final String  BASE_REQ_PRRAMS_MSG = "Invaid params";
    public static final Integer  BASE_RES_CODE=-1;
    public static final String BASE_REQ_MSG = "System busy, please try again later";

}
