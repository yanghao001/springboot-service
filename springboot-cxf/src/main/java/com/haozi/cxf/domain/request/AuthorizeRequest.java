package com.haozi.cxf.domain.request;


import com.haozi.cxf.utils.JacksonUtil;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @Author：caoj
 * @Description：
 * @Date：Created in 2018/9/5
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthorizeRequest", namespace = "urn://Ocpp/Cs/2015/10/", propOrder = {
        "idTag"
})
public class AuthorizeRequest {

    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private String idTag;

    public AuthorizeRequest() {
    }

    public AuthorizeRequest(String idTag) {
        this.idTag = idTag;
    }

    public String getIdTag() {
        return idTag;
    }

    public void setIdTag(String idTag) {
        this.idTag = idTag;
    }

    @Override
    public String toString() {
        return JacksonUtil.bean2Json(this);
    }
}
