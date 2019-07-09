package com.haozi.cxf.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yanghao
 * @Description:
 * @date 2019/2/13 14:09
 */
@Slf4j
public class SoapMessageUtil {

//    @Resource(name = "wsContext")
//    private WebServiceContext wsContext;
//
//    public String getMessageId() {
//        SOAPMessageContext msgContext = (SOAPMessageContext) wsContext.getMessageContext();
//        SOAPMessage soapMessage = msgContext.getMessage();
//
//        String messageId = null;
//        SOAPHeader header = null;
//        try {
//            header = soapMessage.getSOAPPart().getEnvelope().getHeader();
//        } catch (SOAPException e) {
//            e.printStackTrace();
//        }
//
//        if (header != null) {
//            Node messageNode = header.getElementsByTagName("MessageId").item(1);
//            if (messageNode != null) {
//                log.info("MessageId invalid");
//            }
//            messageId = messageNode.getTextContent();
//
//            log.info("MessageId:{}", messageId);
//        }
//
//        return messageId;
//    }
}

