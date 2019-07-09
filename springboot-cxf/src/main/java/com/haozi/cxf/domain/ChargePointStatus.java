package com.haozi.cxf.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ChargePointStatus")
@XmlEnum
public enum ChargePointStatus {

    @XmlEnumValue("Available")
    AVAILABLE("Available"),
    @XmlEnumValue("Occupied")
    OCCUPIED("Occupied"),
    @XmlEnumValue("Faulted")
    FAULTED("Faulted"),
    @XmlEnumValue("Unavailable")
    UNAVAILABLE("Unavailable"),
    @XmlEnumValue("Reserved")
    RESERVED("Reserved");
    private final String value;

    ChargePointStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ChargePointStatus fromValue(String v) {
        for (ChargePointStatus c : ChargePointStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
