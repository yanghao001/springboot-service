package com.haozi.cxf.interceptor;

import com.chargedot.ocpp.handler.WebServiceWrapper;
import com.chargedot.ocpp.message.KafkaConsumer;
import com.chargedot.ocpp.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;

import javax.xml.soap.*;
import java.io.IOException;

/**
 * @author yanghao
 * @Description:
 * @date 2019/2/18 16:47
 */
@Component
@Slf4j
public class AuthOutInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    @Autowired
    private KafkaConsumer kafkaConsumer;
    @Autowired
    private WebServiceWrapper webServiceWrapper;

    public AuthOutInterceptor() {
        super(Phase.PRE_PROTOCOL);
    }

    @Override
    public void handleMessage(SoapMessage soapMessage) {
        log.info("AuthOutInterceptor handleMessage");

        SOAPEnvelope envelope = null;
        try {
            SOAPMessage message = soapMessage.getContent(SOAPMessage.class);
            envelope = message.getSOAPPart().getEnvelope();
            SOAPHeader header = envelope.getHeader();
            if (header != null) {
                Node node = header.getElementsByTagName("MessageId").item(0);
                String messageId = node.getTextContent();
                if (messageId == null) {
                    log.info("OutAccessHandler messageId invalid");
                    return;
                }
                String operatorType = null;
                if (kafkaConsumer.hashMap.get("OperatorType" + messageId) == null) {
                    log.info("operatorType invaild");
                    return;
                }
                operatorType = (String) kafkaConsumer.hashMap.get("OperatorType" + messageId);
                webServiceWrapper.wrapper(operatorType, messageId, message);
            }

            message.writeTo(System.out);

        } catch (SOAPException e) {
            log.info("SOAPException: {}", e);
        } catch (IOException e) {
            log.info("IOException: {}", e);
        }
    }

}

