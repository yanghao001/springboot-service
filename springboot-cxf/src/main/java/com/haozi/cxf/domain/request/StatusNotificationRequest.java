package com.haozi.cxf.domain.request;


import com.haozi.cxf.domain.ChargePointErrorCode;
import com.haozi.cxf.domain.ChargePointStatus;
import com.haozi.cxf.utils.JacksonUtil;

import javax.xml.bind.annotation.*;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatusNotificationRequest", propOrder = {
        "connectorId",
        "status",
        "errorCode",
        "info",
        "timestamp",
        "vendorId",
        "vendorErrorCode"
})
public class StatusNotificationRequest {

    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private int connectorId;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private ChargePointStatus status;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private ChargePointErrorCode errorCode;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private String info;
    @XmlSchemaType(name = "dateTime")
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private Date timestamp;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private String vendorId;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private String vendorErrorCode;

    public int getConnectorId() {
        return connectorId;
    }

    public void setConnectorId(int connectorId) {
        this.connectorId = connectorId;
    }

    public ChargePointStatus getStatus() {
        return status;
    }

    public void setStatus(ChargePointStatus status) {
        this.status = status;
    }

    public ChargePointErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ChargePointErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorErrorCode() {
        return vendorErrorCode;
    }

    public void setVendorErrorCode(String vendorErrorCode) {
        this.vendorErrorCode = vendorErrorCode;
    }

    @Override
    public String toString() {
        return JacksonUtil.bean2Json(this);
    }
}
