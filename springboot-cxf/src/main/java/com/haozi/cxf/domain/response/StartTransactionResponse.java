package com.haozi.cxf.domain.response;


import com.haozi.cxf.domain.IdTagInfo;
import com.haozi.cxf.utils.JacksonUtil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StartTransactionResponse", propOrder = {
        "transactionId",
        "idTagInfo"
})
public class StartTransactionResponse {

    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private int transactionId;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    protected IdTagInfo idTagInfo;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

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
