package com.haozi.cxf.domain;

import javax.xml.bind.annotation.*;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdTagInfo", namespace = "urn://Ocpp/Cs/2015/10/", propOrder = {
        "status",
        "expiryDate",
        "parentIdTag"
})
public class IdTagInfo {

    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private AuthorizationStatus status;
    @XmlSchemaType(name = "dateTime")
    @XmlElement(namespace = "urn://Ocpp/Cs/2015/10/")
    private Date expiryDate;
    @XmlElement(namespace = "urn://Ocpp/Cs/2015/10/")
    private String parentIdTag;

    public AuthorizationStatus getStatus() {
        return status;
    }

    public void setStatus(AuthorizationStatus status) {
        this.status = status;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getParentIdTag() {
        return parentIdTag;
    }

    public void setParentIdTag(String parentIdTag) {
        this.parentIdTag = parentIdTag;
    }
}
