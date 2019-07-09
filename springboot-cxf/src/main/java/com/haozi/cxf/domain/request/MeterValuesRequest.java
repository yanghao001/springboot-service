package com.haozi.cxf.domain.request;

import com.haozi.cxf.domain.MeterValue;
import com.haozi.cxf.utils.JacksonUtil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MeterValuesRequest", propOrder = {
        "connectorId",
        "transactionId",
        "values"
})
public class MeterValuesRequest {
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private int connectorId;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private Integer transactionId;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private List<MeterValue> values;

    public int getConnectorId() {
        return connectorId;
    }

    public void setConnectorId(int connectorId) {
        this.connectorId = connectorId;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public List<MeterValue> getValues() {
        return values;
    }

    public void setValues(List<MeterValue> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return JacksonUtil.bean2Json(this);
    }
}
