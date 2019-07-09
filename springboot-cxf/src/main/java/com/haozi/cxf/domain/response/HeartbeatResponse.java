package com.haozi.cxf.domain.response;


import com.haozi.cxf.utils.JacksonUtil;

import javax.xml.bind.annotation.*;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeartbeatResponse", propOrder = {
        "currentTime"
})
public class HeartbeatResponse {

    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    @XmlSchemaType(name = "dateTime")
    protected Date currentTime;

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

    @Override
    public String toString() {
        return JacksonUtil.bean2Json(this);
    }
}
