package com.haozi.cxf.domain.response;

import com.haozi.cxf.domain.DataTransferStatus;
import com.haozi.cxf.utils.JacksonUtil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataTransferResponse", namespace = "urn://Ocpp/Cs/2015/10/", propOrder = {
        "status",
        "data"
})
public class DataTransferResponse {

    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private DataTransferStatus status;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private String data;

    public DataTransferStatus getStatus() {
        return status;
    }

    public void setStatus(DataTransferStatus status) {
        this.status = status;
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
