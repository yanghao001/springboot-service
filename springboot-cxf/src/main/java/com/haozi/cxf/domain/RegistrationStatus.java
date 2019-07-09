package com.haozi.cxf.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "RegistrationStatus")
@XmlEnum
public enum RegistrationStatus {

    @XmlEnumValue("Accepted")
    ACCEPTED("Accepted"),
    @XmlEnumValue("Rejected")
    REJECTED("Rejected");
    private final String value;

    RegistrationStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RegistrationStatus fromValue(String v) {
        for (RegistrationStatus c: RegistrationStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
