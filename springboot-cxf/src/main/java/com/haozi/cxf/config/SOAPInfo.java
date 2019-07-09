package com.haozi.cxf.config;


import lombok.Data;

/**
 * @author yanghao
 * @Description:
 * @date 2019/1/30 18:21
 */
@Data
public class SOAPInfo {

    private String chargeBoxIdentity;
    private String fromUrl;
    private String toUrl;
    private String namespace;

    public SOAPInfo(String chargeBoxIdentity, String fromUrl, String toUrl, String namespace) {
        this.chargeBoxIdentity = chargeBoxIdentity;
        this.fromUrl = fromUrl;
        this.toUrl = toUrl;
        this.namespace = namespace;
    }

}
