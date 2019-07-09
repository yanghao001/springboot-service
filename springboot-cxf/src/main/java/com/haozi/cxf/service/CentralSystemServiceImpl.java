package com.haozi.cxf.service;

import com.haozi.cxf.config.MessageConstant;
import com.haozi.cxf.domain.AuthorizationStatus;
import com.haozi.cxf.domain.DiagnosticsStatus;
import com.haozi.cxf.domain.IdTagInfo;
import com.haozi.cxf.domain.RegistrationStatus;
import com.haozi.cxf.domain.request.*;
import com.haozi.cxf.domain.response.*;
import com.haozi.cxf.message.KafkaConsumer;
import com.haozi.cxf.message.KafkaProducer;
import com.haozi.cxf.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.InInterceptors;
import org.apache.cxf.interceptor.OutInterceptors;
import org.apache.cxf.jaxws.context.WrappedMessageContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.soap.SOAPBinding;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author yanghao
 * @Description:
 * @date 2019/2/18 16:55
 */
@WebService(serviceName = "CentralSystemService",
        targetNamespace = "urn://Ocpp/Cs/2015/10/",
        endpointInterface = "com.haozi.cxf.service.CentralSystemService"
)
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@Slf4j
@Component
@InInterceptors
@OutInterceptors
public class CentralSystemServiceImpl implements CentralSystemService {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private KafkaProducer kafkaProducer;
    @Autowired
    private KafkaConsumer kafkaConsumer;
    @Resource
    private WebServiceContext webServiceContext;

    @Override
    public AuthorizeResponse authorize(AuthorizeRequest authorizeRequest, String chargeBoxIdentity, String action,
                                       String messageID, String from, String replyTo, String to) {
        log.info("[chargeBoxIdentity]:{} [AuthorizeRequest]:{}", chargeBoxIdentity, JacksonUtil.bean2Json(authorizeRequest));

        // 1. 构造响应
        AuthorizeResponse response = new AuthorizeResponse();
        IdTagInfo idTagInfo = new IdTagInfo();
        idTagInfo.setExpiryDate(null);
        idTagInfo.setParentIdTag("");

        // 2. 封装传递centralService参数
        HashMap<String, Object> map = new HashMap<>();
        String messageId = getMessageId();
        map.put("MessageId", messageId);
        map.put("OperatorType", MessageConstant.D2S_AUTHORIZE_REQ);
        map.put("IdTag", authorizeRequest.getIdTag());
        log.info("[ChargeBoxIdentity]:{} [MessageId]:{} [IdTag]:{}", chargeBoxIdentity, messageId, authorizeRequest.getIdTag());

        // 3. kafka send to centralService
        kafkaProducer.send(MessageConstant.OCPP16_D2S_TOPIC, chargeBoxIdentity, JacksonUtil.map2Json(map));

        // 4. 根据centralService回复，应答chargePoint
        if (Objects.nonNull(kafkaConsumer.hashMap.get(messageId))) {
            AuthorizationStatus status = (AuthorizationStatus) kafkaConsumer.hashMap.get(messageId);
            log.info("状态status: {}", status);
            idTagInfo.setStatus(status);
        }
        response.setIdTagInfo(idTagInfo);
        return response;

    }

    @Override
    public BootNotificationResponse bootNotification(BootNotificationRequest bootificationRequest, String chargeBoxIdentity) {
        log.info("[chargeBoxIdentity]:{} [BootNotificationRequest]:{}", chargeBoxIdentity, JacksonUtil.bean2Json(bootificationRequest));

        // 1. 构造响应
        BootNotificationResponse bootNotificationResponse = new BootNotificationResponse();

        // 2. 封装传递centralService参数
        HashMap<String, Object> map = new HashMap<>();
        String messageId = getMessageId();
        map.put("MessageId", messageId);
        map.put("OperatorType", MessageConstant.D2S_BOOT_NOTIFICATION_REQ);
        map.put("ChargePointModel", bootificationRequest.getChargePointModel());
        map.put("ChargePointVendor", bootificationRequest.getChargePointVendor());
        map.put("FirmwareVersion", bootificationRequest.getFirmwareVersion());
        map.put("MeterSerialNumber", bootificationRequest.getMeterSerialNumber());
        map.put("Iccid", bootificationRequest.getIccid());
        map.put("Imsi", bootificationRequest.getImsi());
        map.put("MeterType", bootificationRequest.getMeterType());
        log.info("ChargeBoxIdentity:{} MessageId:{}", chargeBoxIdentity, messageId);

        // 3. kafka send to centralService
        kafkaProducer.send(MessageConstant.OCPP16_D2S_TOPIC, chargeBoxIdentity, JacksonUtil.map2Json(map));

        // 4. 根据centralService应答，下发chargePoint
        if (Objects.nonNull(kafkaConsumer.hashMap.get(messageId))) {
            RegistrationStatus status = (RegistrationStatus) kafkaConsumer.hashMap.get(messageId);
            log.info("状态status: {}", status);
            bootNotificationResponse.setStatus(status);
            bootNotificationResponse.setCurrentTime(new Date());
            bootNotificationResponse.setHeartbeatInterval(60);
        }
        return bootNotificationResponse;

    }

    @Override
    public DataTransferResponse dataTransfer(DataTransferRequest dataTransferRequest, String chargeBoxIdentity) {
        log.info("[chargeBoxIdentity]:{} [DataTransferRequest]:{}", chargeBoxIdentity, JacksonUtil.bean2Json(dataTransferRequest));
        return null;
    }

    @Override
    public DiagnosticsStatusNotificationResponse diagnosticsStatusNotification(DiagnosticsStatusNotificationRequest diagnosticsStatusNotificationRequest, String chargeBoxIdentity) {
        log.info("[chargeBoxIdentity]:{} [DiagnosticsStatusNotificationRequest]:{}", chargeBoxIdentity, JacksonUtil.bean2Json(diagnosticsStatusNotificationRequest));

        // 1. 构造响应
        DiagnosticsStatusNotificationResponse diagnosticsStatusNotificationResponse = new DiagnosticsStatusNotificationResponse();

        // 2. 封装传递centralService参数
        HashMap<String, Object> map = new HashMap<>();
        String messageId = getMessageId();
        map.put("MessageId", messageId);
        map.put("OperatorType", MessageConstant.D2S_BOOT_NOTIFICATION_REQ);
        map.put("Status", diagnosticsStatusNotificationRequest.getStatus());

        // 3. kafka send to centralService
        kafkaProducer.send(MessageConstant.OCPP16_D2S_TOPIC, chargeBoxIdentity, JacksonUtil.map2Json(map));

        // 4. 根据centralService应答，下发chargePoint
        if (Objects.nonNull(kafkaConsumer.hashMap.get(messageId))) {
            DiagnosticsStatus status = (DiagnosticsStatus) kafkaConsumer.hashMap.get(messageId);
            log.info("状态status: {}", status);
        }

        return diagnosticsStatusNotificationResponse;
    }

    @Override
    public FirmwareStatusNotificationResponse firmwareStatusNotification(FirmwareStatusNotificationRequest parameters, String chargeBoxIdentity) {
        FirmwareStatusNotificationResponse firmwareStatusNotificationResponse = new FirmwareStatusNotificationResponse();
        return firmwareStatusNotificationResponse;
    }

    @Override
    public HeartbeatResponse heartbeat(HeartbeatRequest heartbeatRequest, String chargeBoxIdentity) throws ParseException {
        log.info("[chargeBoxIdentity]:{} [HeartbeatRequest]:{}", chargeBoxIdentity, JacksonUtil.bean2Json(heartbeatRequest));
        HeartbeatResponse heartbeatResponse = new HeartbeatResponse();
        heartbeatResponse.setCurrentTime(new Date());
        return heartbeatResponse;
    }

    @Override
    public MeterValuesResponse meterValues(MeterValuesRequest meterValuesRequest, String chargeBoxIdentity) {
        log.info("[chargeBoxIdentity]:{} [MeterValuesRequest]:{}", chargeBoxIdentity, JacksonUtil.bean2Json(meterValuesRequest));

        // 1. 构造响应
        MeterValuesResponse meterValuesResponse = new MeterValuesResponse();

        // 2. 封装参数
        HashMap<String, Object> map = new HashMap<>();
        String messageId = getMessageId();
        map.put("MessageId", messageId);
        map.put("OperationType", MessageConstant.D2S_METER_VALUES_REQ);
        map.put("TransactionId", meterValuesRequest.getTransactionId());
        map.put("ConnectorId", meterValuesRequest.getConnectorId());
        map.put("Electricity", meterValuesRequest.getValues());
        map.put("Timestamp", sdf.format(new Date()));

        // 3. send CentralService
        kafkaProducer.send(MessageConstant.OCPP16_D2S_TOPIC, chargeBoxIdentity, JacksonUtil.map2Json(map));

        // 4. 根据centralService应答，下发chargePoint
        if (Objects.nonNull(kafkaConsumer.hashMap.get(messageId))) {
            String status = (String) kafkaConsumer.hashMap.get(messageId);
            log.info("状态status: {}", status);
        }

        return meterValuesResponse;
    }

    @Override
    public StartTransactionResponse startTransaction(StartTransactionRequest startTransactionRequest, String chargeBoxIdentity) {
        log.info("[chargeBoxIdentity]:{} [StartTransactionRequest]:{}", chargeBoxIdentity, JacksonUtil.bean2Json(startTransactionRequest));

        // 1. response
        StartTransactionResponse startTransactionResponse = new StartTransactionResponse();
        IdTagInfo idTagInfo = new IdTagInfo();

        // 2. 封装参数
        HashMap<String, Object> map = new HashMap<>();
        String messageId = getMessageId();
        map.put("MessageId", messageId);
        map.put("OperationType", MessageConstant.D2S_START_TRANSACTION_REQ);
        map.put("ConnectorId", startTransactionRequest.getConnectorId());
        map.put("IdTag", startTransactionRequest.getIdTag());
        map.put("Timestamp", sdf.format(new Date()));
        map.put("MeterStart", startTransactionRequest.getMeterStart());

        // 3. send to centralService
        kafkaProducer.send(MessageConstant.OCPP16_D2S_TOPIC, chargeBoxIdentity, JacksonUtil.map2Json(map));

        // 4. response to chargePoint
        if (Objects.nonNull(kafkaConsumer.hashMap.get(messageId))) {
            AuthorizationStatus status = (AuthorizationStatus) kafkaConsumer.hashMap.get(messageId);
            idTagInfo.setStatus(status);
            log.info("状态status: {}", status);
        }
        startTransactionResponse.setIdTagInfo(idTagInfo);

        return startTransactionResponse;
    }

    @Override
    public StatusNotificationResponse statusNotification(StatusNotificationRequest statusNotificationRequest, String chargeBoxIdentity) {
        log.info("[chargeBoxIdentity]:{} [StatusNotificationRequest]:{}", chargeBoxIdentity, JacksonUtil.bean2Json(statusNotificationRequest));

        // 1. response
        StatusNotificationResponse statusNotificationResponse = new StatusNotificationResponse();

        // 2. 封装参数
        HashMap<String, Object> map = new HashMap<>();
        map.put("MessageId", getMessageId());
        map.put("OperationType", MessageConstant.D2S_STATUS_NOTIFICATION_REQ);
        map.put("ConnectorId", statusNotificationRequest.getConnectorId());
        map.put("Status", statusNotificationRequest.getStatus());
        map.put("ErrorCode", statusNotificationRequest.getErrorCode());

        // 3. send to centralService
        kafkaProducer.send(MessageConstant.OCPP16_D2S_TOPIC, chargeBoxIdentity, JacksonUtil.map2Json(map));

        return statusNotificationResponse;
    }

    @Override
    public StopTransactionResponse stopTransaction(StopTransactionRequest stopTransactionRequest, String chargeBoxIdentity) {
        log.info("[chargeBoxIdentity]:{} [StopTransactionRequest]:{}", chargeBoxIdentity, JacksonUtil.bean2Json(stopTransactionRequest));

        // 1. response
        StopTransactionResponse stopTransactionResponse = new StopTransactionResponse();
        IdTagInfo idTagInfo = new IdTagInfo();

        // 2. package
        HashMap<String, Object> map = new HashMap<>();
        String messageId = getMessageId();
        map.put("MessageId", messageId);
        map.put("OperationType", MessageConstant.D2S_STOP_TRANSACTION_REQ);
        map.put("TransactionId", stopTransactionRequest.getTransactionId());
        map.put("IdTag", stopTransactionRequest.getIdTag());
        map.put("Timestamp", sdf.format(new Date()));
        map.put("MeterStop", stopTransactionRequest.getMeterStop());
        map.put("Reason", stopTransactionRequest.getTransactionData());

        // 3. kafka send to centralService
        kafkaProducer.send(MessageConstant.OCPP16_D2S_TOPIC, chargeBoxIdentity, JacksonUtil.map2Json(map));

        // 4. response to chargePoint
        if (Objects.nonNull(kafkaConsumer.hashMap.get(messageId))) {
            AuthorizationStatus status = (AuthorizationStatus) kafkaConsumer.hashMap.get(messageId);
            idTagInfo.setStatus(status);
            log.info("状态status: {}", status);
        }
        stopTransactionResponse.setIdTagInfo(idTagInfo);

        return stopTransactionResponse;
    }

    public String getMessageId() {
        System.out.println("getMessageId");

        String messageId = null;
        Header header = null;
        SoapMessage soapMessage = null;
        WrappedMessageContext msgContext = (WrappedMessageContext) webServiceContext.getMessageContext();
        soapMessage = (SoapMessage) msgContext.getWrappedMessage();
        if (soapMessage != null) {
            List<Header> headers = soapMessage.getHeaders();

            if (headers == null || headers.size() == 0) {
                throw new Fault(new Exception("没有检查到请求头"));
            } else {
                header = headers.get(0);
            }

            Element element = (Element) header.getObject();
            NodeList messageIdNode = element.getElementsByTagName("MessageId");
            if (messageIdNode == null) {
                throw new Fault(new Exception("MessageId is not exist"));
            }
            messageId = messageIdNode.item(0).getTextContent();
        } else {
            throw new Fault(new IllegalArgumentException("soapMessage is not exist"));
        }
        return messageId;
    }
}

