package com.haozi.cxf.interceptor;



import com.haozi.cxf.config.MessageConstant;
import com.haozi.cxf.config.SOAPInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.List;
import java.util.Objects;

/**
 * @author yanghao
 * @Description:
 * @date 2019/2/18 16:47
 */
@Slf4j
@Component
public class AuthInInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    public AuthInInterceptor() {
        super(Phase.PRE_INVOKE); // 调用方法前拦截
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        log.info("AuthInInterceptor handleMessage");

        List<Header> headerList = message.getHeaders();
        if (headerList == null || headerList.size() == 0) {
            throw new Fault(new IllegalArgumentException("没有Header,拦截器实施拦截"));
        }

        Header header = headerList.get(0);
        Element element = (Element) header.getObject();
        NodeList chargeBoxIdentityNode = element.getElementsByTagName("ChargeBoxIdentity");
        NodeList actionNode = element.getElementsByTagName("Action");
        NodeList messageIdNode = element.getElementsByTagName("MessageId");
        NodeList fromNode = element.getElementsByTagName("From");
        NodeList replyToNode = element.getElementsByTagName("ReplyTo");
        NodeList toNode = element.getElementsByTagName("To");

        if (messageIdNode == null) {
            log.info("messageId invalid");
            return;
        }
        if (Objects.isNull(chargeBoxIdentityNode)) {
            log.info("chargeBoxIdentityNode invalid");
            return;
        }
        String chargeBoxIdentity = chargeBoxIdentityNode.item(0).getTextContent();
        if (fromNode == null) {
            log.info("fromNode invalid");
            return;
        }
        String fromUrl = fromNode.item(0).getTextContent();
        if (toNode == null) {
            log.info("toNode invalid");
            return;
        }
        String toUrl = toNode.item(0).getTextContent();

        new SOAPInfo(chargeBoxIdentity, fromUrl, toUrl, MessageConstant.NAMESPACE_CENTRALSYSTEM);

    }

}

