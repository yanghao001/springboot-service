package com.haozi.cxf.service;

import com.haozi.cxf.domain.request.*;
import com.haozi.cxf.domain.response.*;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Action;
import javax.xml.ws.soap.Addressing;
import java.text.ParseException;

/**
 * @author yanghao
 * @Description:
 * @date 2019/2/18 16:56
 */
@WebService(name = "CentralSystemService",
        targetNamespace = "urn://Ocpp/Cs/2015/10/"
)
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@Addressing(enabled = true)
public interface CentralSystemService {

    @WebMethod(operationName = "Authorize", action = "/Authorize")
    @Action(input = "/Authorize", output = "/AuthorizeResponse")
    @WebResult(name = "authorizeResponse", /*targetNamespace = "urn://Ocpp/Cs/2015/10/",*/ partName = "parameters")
    AuthorizeResponse authorize(
            @WebParam(partName = "parameters", name = "authorizeRequest", targetNamespace = "urn://Ocpp/Cs/2015/10/")
                    AuthorizeRequest parameters,
            @WebParam(partName = "chargeBoxIdentity", name = "chargeBoxIdentity", header = true, targetNamespace = "urn://Ocpp/Cs/2015/10/")
                    String chargeBoxIdentity,
            @WebParam(partName = "Action", name = "Action", header = true, targetNamespace = "urn://Ocpp/Cs/2015/10/")
                    String action,
            @WebParam(partName = "MessageID", name = "MessageID", header = true, targetNamespace = "urn://Ocpp/Cs/2015/10/")
                    String messageID,
            @WebParam(partName = "From", name = "From", header = true, targetNamespace = "urn://Ocpp/Cs/2015/10/")
                    String from,
            @WebParam(partName = "ReplyTo", name = "ReplyTo", header = true, targetNamespace = "urn://Ocpp/Cs/2015/10/")
                    String replyTo,
            @WebParam(partName = "To", name = "To", header = true, targetNamespace = "urn://Ocpp/Cs/2015/10/")
                    String to
    );

    @WebMethod(operationName = "BootNotification", action = "/BootNotification")
    @Action(input = "/BootNotification", output = "/BootNotificationResponse")
    @WebResult(name = "bootNotificationResponse", partName = "parameters", targetNamespace = "urn://Ocpp/Cs/2015/10/")
    BootNotificationResponse bootNotification(
            @WebParam(partName = "parameters", name = "bootNotificationRequest", targetNamespace = "urn://Ocpp/Cs/2015/10/")
                    BootNotificationRequest parameters,
            @WebParam(partName = "chargeBoxIdentity", name = "chargeBoxIdentity", header = true, targetNamespace = "urn://Ocpp/Cs/2015/10/")
                    String chargeBoxIdentity
    );

    @WebMethod(operationName = "DataTransfer", action = "/DataTransfer")
    @Action(input = "/DataTransfer", output = "/DataTransferResponse")
    @WebResult(name = "dataTransferResponse", partName = "parameters", targetNamespace = "urn://Ocpp/Cs/2015/10/")
    DataTransferResponse dataTransfer(
            @WebParam(partName = "parameters", name = "dataTransferRequest", targetNamespace = "urn://Ocpp/Cs/2015/10/")
                    DataTransferRequest parameters,
            @WebParam(partName = "chargeBoxIdentity", name = "chargeBoxIdentity", header = true, targetNamespace = "urn://Ocpp/Cs/2015/10/")
                    String chargeBoxIdentity
    );

    @WebMethod(operationName = "DiagnosticsStatusNotification", action = "/DiagnosticsStatusNotification")
    @Action(input = "/DiagnosticsStatusNotification", output = "/DiagnosticsStatusNotificationResponse")
    @WebResult(name = "diagnosticsStatusNotificationResponse", partName = "parameters", targetNamespace = "urn://Ocpp/Cs/2015/10/")
    DiagnosticsStatusNotificationResponse diagnosticsStatusNotification(
            @WebParam(partName = "parameters", name = "diagnosticsStatusNotificationRequest", targetNamespace = "urn://Ocpp/Cs/2015/10/")
                    DiagnosticsStatusNotificationRequest parameters,
            @WebParam(partName = "chargeBoxIdentity", name = "chargeBoxIdentity", header = true, targetNamespace = "urn://Ocpp/Cs/2015/10/")
                    String chargeBoxIdentity
    );

    @WebMethod(operationName = "FirmwareStatusNotification", action = "/FirmwareStatusNotification")
    @Action(input = "/FirmwareStatusNotification", output = "/FirmwareStatusNotificationResponse")
    @WebResult(name = "firmwareStatusNotificationResponse", partName = "parameters", targetNamespace = "urn://Ocpp/Cs/2015/10/")
    FirmwareStatusNotificationResponse firmwareStatusNotification(
            @WebParam(partName = "parameters", name = "firmwareStatusNotificationRequest", targetNamespace = "urn://Ocpp/Cs/2015/10/")
                    FirmwareStatusNotificationRequest parameters,
            @WebParam(partName = "chargeBoxIdentity", name = "chargeBoxIdentity", header = true, targetNamespace = "urn://Ocpp/Cs/2015/10/")
                    String chargeBoxIdentity
    );

    @WebMethod(operationName = "Heartbeat", action = "/Heartbeat")
    @Action(input = "/Heartbeat", output = "/HeartbeatResponse")
    @WebResult(name = "heartbeatResponse", partName = "parameters", targetNamespace = "urn://Ocpp/Cs/2015/10/")
    HeartbeatResponse heartbeat(
            @WebParam(partName = "parameters", name = "heartbeatRequest", targetNamespace = "urn://Ocpp/Cs/2015/10/")
                    HeartbeatRequest parameters,
            @WebParam(partName = "chargeBoxIdentity", name = "chargeBoxIdentity", header = true, targetNamespace = "urn://Ocpp/Cs/2015/10/")
                    String chargeBoxIdentity
    ) throws ParseException;

    @WebMethod(operationName = "MeterValues", action = "/MeterValues")
    @Action(input = "/MeterValues", output = "/MeterValuesResponse")
    @WebResult(name = "meterValuesResponse", partName = "parameters", targetNamespace = "urn://Ocpp/Cs/2015/10/")
    MeterValuesResponse meterValues(
            @WebParam(partName = "parameters", name = "meterValuesRequest", targetNamespace = "urn://Ocpp/Cs/2015/10/")
                    MeterValuesRequest parameters,
            @WebParam(partName = "chargeBoxIdentity", name = "chargeBoxIdentity", header = true, targetNamespace = "urn://Ocpp/Cs/2015/10/")
                    String chargeBoxIdentity
    );

    @WebMethod(operationName = "StartTransaction", action = "/StartTransaction")
    @Action(input = "/StartTransaction", output = "/StartTransactionResponse")
    @WebResult(name = "startTransactionResponse", partName = "parameters", targetNamespace = "urn://Ocpp/Cs/2015/10/")
    StartTransactionResponse startTransaction(
            @WebParam(partName = "parameters", name = "startTransactionRequest", targetNamespace = "urn://Ocpp/Cs/2015/10/")
                    StartTransactionRequest parameters,
            @WebParam(partName = "chargeBoxIdentity", name = "chargeBoxIdentity", header = true, targetNamespace = "urn://Ocpp/Cs/2015/10/")
                    String chargeBoxIdentity
    );

    @WebMethod(operationName = "StatusNotification", action = "/StatusNotification")
    @Action(input = "/StatusNotification", output = "/StatusNotificationResponse")
    @WebResult(name = "statusNotificationResponse", partName = "parameters", targetNamespace = "urn://Ocpp/Cs/2015/10/")
    StatusNotificationResponse statusNotification(
            @WebParam(partName = "parameters", name = "statusNotificationRequest", targetNamespace = "urn://Ocpp/Cs/2015/10/")
                    StatusNotificationRequest parameters,
            @WebParam(partName = "chargeBoxIdentity", name = "chargeBoxIdentity", header = true, targetNamespace = "urn://Ocpp/Cs/2015/10/")
                    String chargeBoxIdentity
    );

    @WebMethod(operationName = "StopTransaction", action = "/StopTransaction")
    @Action(input = "/StopTransaction", output = "/StopTransactionResponse")
    @WebResult(name = "stopTransactionResponse", partName = "parameters", targetNamespace = "urn://Ocpp/Cs/2015/10/")
    StopTransactionResponse stopTransaction(
            @WebParam(partName = "parameters", name = "stopTransactionRequest", targetNamespace = "urn://Ocpp/Cs/2015/10/")
                    StopTransactionRequest parameters,
            @WebParam(partName = "chargeBoxIdentity", name = "chargeBoxIdentity", header = true, targetNamespace = "urn://Ocpp/Cs/2015/10/")
                    String chargeBoxIdentity
    );

}

