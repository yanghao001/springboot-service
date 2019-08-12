package com.haozi.mongodb.config;

public class ConstantConfig {
    /**
     *   kafka topic
     */
    public static final String D2S_TOPIC = "d-request";
    public static final String D2S_REQ_TOPIC = "D_SERVER_REQ";
    public static final String PUSH_NOTIFICATION = "push-notification";

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
    public static final String MONGO_CHARGE_POWER_DATA = "chargePower";


    public static final Integer BASE_PARAMS_CODE = 1;
    public static final String  BASE_REQ_PRRAMS_MSG = "Invaid params";
    public static final Integer  BASE_RES_CODE=-1;
    public static final String BASE_REQ_MSG = "System busy, please try again later";


    /**
     * station type
     * */
    public static final String DROP_AND_STOP = "可降可停";
    public static final String DROP_AND_NO_STOP = "可降不可停";
    public static final String NO_DROP_AND_NO_STOP = "不可降不可停";
    public static final String SUM_POWER = "总功率";


    /**
     * device port status
     *
     * */
    public static final int UNCONNECTED = 1;
    public static final int AVAILABLE = 2;
    public static final int OCCUPY = 3;
    public static final int TRY_TO_OCCUPY_CHARGE = 4;
    public static final int WAIT_CHAGE = 5;
    public static final int CHARGING = 6;
    public static final int TRY_TO_FINISH_CHARGE = 7;
    public static final int FINISH_CHARGE = 8;
    public static final int LOST = 9;
    public static final int FAULT = 10;


}
