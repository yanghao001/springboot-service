package com.haozi.cxf.handler;


import com.haozi.cxf.config.MessageConstant;
import com.haozi.cxf.config.SOAPInfo;
import org.springframework.stereotype.Component;
import javax.xml.soap.*;

/**
 * @author yanghao
 * @Description:
 * @date 2019/1/30 11:56
 */
@Component
public class WebServiceWrapper {

    private SOAPInfo soapInfo;

    // 包装header(命名空间/前缀)
    public void wrapper(String operatorType, String messageId, SOAPMessage soapMessage) throws SOAPException {

        String prefix = "wsa5";
        String addNamespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing";
        SOAPFactory soapFactory = SOAPFactory.newInstance();

        // 1. 创建soapPart, 得到信封
        SOAPPart soapPart = soapMessage.getSOAPPart();
        SOAPEnvelope soapEnvelope = soapPart.getEnvelope();

        // 2. soap头部header
        SOAPHeader soapHeader = soapEnvelope.getHeader();

        // 3. 包装header
        // Set chargeBoxIdentity
        SOAPHeaderElement soapHeaderElement = soapHeader.addHeaderElement(soapFactory.createName(MessageConstant.HEADER_CHARGEBOXIDENTITY, "cs", soapInfo.getNamespace()));
        soapHeaderElement.setMustUnderstand(true);
        soapHeaderElement.setValue(soapInfo.getChargeBoxIdentity());

        // Set Action
        SOAPHeaderElement actionElement = soapHeader.addHeaderElement(soapFactory.createName(MessageConstant.HEADER_ACTION, prefix, addNamespace));
        actionElement.setMustUnderstand(true);
        actionElement.setValue(String.format("/%s", operatorType));

        // Set MessageID
        SOAPHeaderElement messageIDElement = soapHeader.addHeaderElement(soapFactory.createName(MessageConstant.HEADER_MESSAGEID, prefix, addNamespace));
        messageIDElement.setMustUnderstand(true);
        messageIDElement.setValue(messageId);

        // Set RelatesTo
        SOAPHeaderElement relatesToElement = soapHeader.addHeaderElement(soapFactory.createName(MessageConstant.HEADER_RELATESTO, prefix, addNamespace));
        relatesToElement.setValue(messageId);

        // Set From
        SOAPHeaderElement fromElement = soapHeader.addHeaderElement(soapFactory.createName(MessageConstant.HEADER_FROM, prefix, addNamespace));
        fromElement.setValue(soapInfo.getFromUrl());

        // Set ReplyTo
        SOAPHeaderElement replyToElement = soapHeader.addHeaderElement(soapFactory.createName(MessageConstant.HEADER_REPLYTO, prefix, addNamespace));
        replyToElement.setMustUnderstand(true);
        SOAPElement addressElement = replyToElement.addChildElement(soapFactory.createName(MessageConstant.HEADER_REPLYTO_ADDRESS, prefix, addNamespace));
        addressElement.setValue("http://www.w3.org/2005/08/addressing/anonymous");

        // Set To
        SOAPHeaderElement toElement = soapHeader.addHeaderElement(soapFactory.createName(MessageConstant.HEADER_TO, prefix, addNamespace));
        toElement.setMustUnderstand(true);
        toElement.setValue(soapInfo.getToUrl());

    }
}

