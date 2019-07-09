package com.haozi.cxf.handler;

/**
 * @author yanghao
 * @Description:
 * @date 2019/2/14 15:07
 */
//@Slf4j
//@Component
public class MessageIdUtil {

//    @Resource
//    private WebServiceContext webServiceContext;
//
//    public String getMessageId() {
//
//        System.out.println("getMessageId");
//
//        SOAPMessageContext msgContext = (SOAPMessageContext) webServiceContext.getMessageContext();
//        SOAPMessage soapMessage = msgContext.getMessage();
//
//        String messageId = null;
//        SOAPHeader header = null;
//        try {
//            header = soapMessage.getSOAPPart().getEnvelope().getHeader();
//        } catch (SOAPException e) {
//            log.info("soapException: {}", e);
//        }
//
//        if (header != null) {
//            Node messageNode = header.getElementsByTagName("MessageId").item(1);
//            if (messageNode != null) {
//                log.info("MessageId is not exist");
//            }
//            messageId = messageNode.getTextContent();
//            if (messageId == null) {
//                log.warn("messageId is not exist");
//            }
//            log.info("MessageId:{}", messageId);
//        }
//
//        return messageId;
//    }
}

