package com.haozi.cxf.domain.request;

import com.haozi.cxf.utils.JacksonUtil;

import javax.xml.bind.annotation.*;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StartTransactionRequest", propOrder = {
        "connectorId",
        "idTag",
        "timestamp",
        "meterStart",
        "reservationId"
})
public class StartTransactionRequest {

    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private int connectorId;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private String idTag;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    @XmlSchemaType(name = "dateTime")
    private Date timestamp;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private int meterStart;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private Integer reservationId;

    public int getConnectorId() {
        return connectorId;
    }

    public void setConnectorId(int connectorId) {
        this.connectorId = connectorId;
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

    public int getMeterStart() {
        return meterStart;
    }

    public void setMeterStart(int meterStart) {
        this.meterStart = meterStart;
    }

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    @Override
    public String toString() {
        return JacksonUtil.bean2Json(this);
    }
}
