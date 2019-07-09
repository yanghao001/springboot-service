package com.haozi.cxf.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "Location")
@XmlEnum
public enum Location {
    @XmlEnumValue("Inlet")
    INLET("Inlet"),
    @XmlEnumValue("Outlet")
    OUTLET("Outlet"),
    @XmlEnumValue("Body")
    BODY("Body");
    private final String value;

    Location(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Location fromValue(String v) {
        for (Location c : Location.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }


}
