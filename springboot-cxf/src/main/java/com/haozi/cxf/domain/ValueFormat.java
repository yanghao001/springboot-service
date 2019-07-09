package com.haozi.cxf.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ValueFormat")
@XmlEnum
public enum ValueFormat {

    @XmlEnumValue("Raw")
    RAW("Raw"),
    @XmlEnumValue("SignedData")
    SIGNED_DATA("SignedData");
    private final String value;

    ValueFormat(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ValueFormat fromValue(String v) {
        for (ValueFormat c : ValueFormat.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
