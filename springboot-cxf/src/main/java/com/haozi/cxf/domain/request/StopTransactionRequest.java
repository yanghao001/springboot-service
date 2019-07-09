package com.haozi.cxf.domain.request;

import com.haozi.cxf.domain.TransactionData;
import com.haozi.cxf.utils.JacksonUtil;

import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StopTransactionRequest", propOrder = {
        "transactionId",
        "idTag",
        "timestamp",
        "meterStop",
        "transactionData"
})
public class StopTransactionRequest {

    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private int transactionId;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private String idTag;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    @XmlSchemaType(name = "dateTime")
    private Date timestamp;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private int meterStop;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private List<TransactionData> transactionData;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getIdTag() {
        return idTag;
    }

    public void setIdTag(String idTag) {
        this.idTag = idTag;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getMeterStop() {
        return meterStop;
    }

    public void setMeterStop(int meterStop) {
        this.meterStop = meterStop;
    }

    public List<TransactionData> getTransactionData() {
        return transactionData;
    }

    public void setTransactionData(List<TransactionData> transactionData) {
        this.transactionData = transactionData;
    }

    @Override
    public String toString() {
        return JacksonUtil.bean2Json(this);
    }
}
