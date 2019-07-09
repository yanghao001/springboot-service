package com.haozi.cxf.domain.response;


import com.haozi.cxf.domain.RegistrationStatus;
import com.haozi.cxf.utils.JacksonUtil;

import javax.xml.bind.annotation.*;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BootNotificationResponse", namespace = "urn://Ocpp/Cs/2015/10/", propOrder = {
        "status",
        "currentTime",
        "heartbeatInterval"
})
public class BootNotificationResponse {

    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private RegistrationStatus status;
    @XmlSchemaType(name = "dateTime")
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private Date currentTime;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private int heartbeatInterval;

    public RegistrationStatus getStatus() {
        return status;
    }

    public void setStatus(RegistrationStatus status) {
        this.status = status;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

    public int getHeartbeatInterval() {
        return heartbeatInterval;
    }

    public void setHeartbeatInterval(int heartbeatInterval) {
        this.heartbeatInterval = heartbeatInterval;
    }

    @Override
    public String toString() {
        return JacksonUtil.bean2Json(this);
    }
}
