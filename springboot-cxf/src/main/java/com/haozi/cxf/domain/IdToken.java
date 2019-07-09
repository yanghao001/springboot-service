package com.haozi.cxf.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdToken", propOrder = {
        "IdToken"
})
public class IdToken {

    private String[] IdToken;

    public IdToken() {
    }

    public IdToken(String[] idToken) {
        IdToken = idToken;
    }

    public String[] getIdToken() {
        return IdToken;
    }

    public void setIdToken(String[] idToken) {
        IdToken = idToken;
    }
}
