package com.haozi.cxf.config;


import com.chargedot.ocpp.Interceptor.AuthInInterceptor;
import com.chargedot.ocpp.Interceptor.AuthOutInterceptor;
import com.chargedot.ocpp.service.CentralSystemService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxb.JAXBDataBinding;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.ws.addressing.AddressingProperties;
import org.apache.cxf.ws.addressing.WSAddressingFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.xml.ws.Endpoint;
import javax.xml.ws.EndpointContext;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.spi.http.HttpContext;
import java.util.List;
import java.util.Set;

/**
 * @author yanghao
 * @Description:
 * @date 2019/1/31 10:39
 */
@Configuration
public class CxfConfig {

    @Autowired
    private Bus bus;
    @Autowired
    private CentralSystemService centralSystemService;
    @Autowired
    private AuthInInterceptor authInInterceptor;
    @Autowired
    private AuthOutInterceptor authOutInterceptor;

    @Bean
    public Endpoint endpoint(){
        EndpointImpl endpoint = new EndpointImpl(bus, centralSystemService);
//        endpoint.getFeatures().add(new WSAddressingFeature());
//        endpoint.getInInterceptors().add(authInInterceptor);
        endpoint.getOutInterceptors().add(authOutInterceptor);
        endpoint.publish("/CentralSystemService");
        System.out.println("success!");
        return endpoint;
    }


}

