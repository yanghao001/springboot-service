package com.haozi.cxf.domain.request;

import com.haozi.cxf.utils.JacksonUtil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataTransferRequest", propOrder = {
        "vendorId",
        "messageId",
        "data"
})
public class DataTransferRequest {

    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private String vendorId;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private String messageId;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private String data;

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JacksonUtil.bean2Json(this);
    }
}
