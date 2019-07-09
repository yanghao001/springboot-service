package com.haozi.cxf.domain.request;


import com.haozi.cxf.domain.FirmwareStatus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FirmwareStatusNotificationRequest", namespace = "urn://Ocpp/Cs/2015/10/", propOrder = {
        "status"
})
public class FirmwareStatusNotificationRequest {

    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    protected FirmwareStatus status;

    public FirmwareStatus getStatus() {
        return status;
    }

    public void setStatus(FirmwareStatus status) {
        this.status = status;
    }
}
