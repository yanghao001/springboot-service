package com.haozi.cxf.config;

/**
 * @author yanghao
 * @Description:
 * @date 2019/2/12 14:25
 */
public class MessageConstant {

    /**
     * header config
     */
    public static final String HEADER_ACTION = "Action";
    public static final String HEADER_MESSAGEID = "MessageID";
    public static final String HEADER_RELATESTO = "RelatesTo";
    public static final String HEADER_FROM = "From";
    public static final String HEADER_REPLYTO = "ReplyTo";
    public static final String HEADER_REPLYTO_ADDRESS = "Address";
    public static final String HEADER_TO = "To";
    public static final String HEADER_CHARGEBOXIDENTITY = "chargeBoxIdentity";

    /**
     * interface address config
     */
    public static final String ADDRESS = "http://127.0.0.1:8181/CentralSystemService";

    /*
     * kafka topic server to device
     *
     * */
    public static final String OCPP16_S2D_TOPIC = "S2D-OCPP16-REQ-TOPIC";
    public static final String OCPP16_D2S_TOPIC = "D2S-OCPP16-REQ-TOPIC";

    /*
     * operatorType server to device
     *
     * */
    public static final String REMOTE_START_TRANSACTION = "RemoteStartTransaction";
    public static final String REMOTE_STOP_TRANSACTION = "RemoteStopTransaction";
    public static final String CHANGE_CONFIG = "ChangeConfiguration";
    public static final String RESET = "Reset";

    public static final String D2S_AUTHORIZE_REQ = "Authorize";
    public static final String D2S_BOOT_NOTIFICATION_REQ = "BootNotification";
    public static final String D2S_STATUS_NOTIFICATION_REQ = "StatusNotification";
    public static final String D2S_START_TRANSACTION_REQ = "StartTransaction";
    public static final String D2S_STOP_TRANSACTION_REQ = "StopTransaction";
    public static final String D2S_METER_VALUES_REQ = "MeterValues";

    /*
     * nameSpace
     *
     * */
    public static final String NAMESPACE_CENTRALSYSTEM = "urn://Ocpp/Cs/2015/10/";


}

