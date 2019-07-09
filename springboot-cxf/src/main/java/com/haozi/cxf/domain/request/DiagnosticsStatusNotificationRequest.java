package com.haozi.cxf.domain.request;

import com.haozi.cxf.domain.DiagnosticsStatus;
import com.haozi.cxf.utils.JacksonUtil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DiagnosticsStatusNotificationRequest", propOrder = {
        "status"
})
public class DiagnosticsStatusNotificationRequest {

    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private DiagnosticsStatus status;

    public DiagnosticsStatus getStatus() {
        return status;
    }

    public void setStatus(DiagnosticsStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return JacksonUtil.bean2Json(this);
    }
}
