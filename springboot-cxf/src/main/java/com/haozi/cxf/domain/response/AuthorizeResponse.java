package com.haozi.cxf.domain.response;


import com.haozi.cxf.domain.IdTagInfo;
import com.haozi.cxf.utils.JacksonUtil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthorizeResponse", namespace = "urn://Ocpp/Cs/2015/10/", propOrder = {
        "idTagInfo"
})
public class AuthorizeResponse {

    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private IdTagInfo idTagInfo;

    public IdTagInfo getIdTagInfo() {
        return idTagInfo;
    }

    public void setIdTagInfo(IdTagInfo idTagInfo) {
        this.idTagInfo = idTagInfo;
    }

    @Override
    public String toString() {
        return JacksonUtil.bean2Json(this);
    }
}
