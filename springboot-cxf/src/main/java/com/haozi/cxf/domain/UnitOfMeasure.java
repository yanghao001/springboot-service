package com.haozi.cxf.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "UnitOfMeasure")
@XmlEnum
public enum UnitOfMeasure {

    @XmlEnumValue("Wh")
    WH("Wh"),
    @XmlEnumValue("kWh")
    K_WH("kWh"),
    @XmlEnumValue("varh")
    VARH("varh"),
    @XmlEnumValue("kvarh")
    KVARH("kvarh"),
    W("W"),
    @XmlEnumValue("kW")
    K_W("kW"),
    @XmlEnumValue("var")
    VAR("var"),
    @XmlEnumValue("kvar")
    KVAR("kvar"),
    @XmlEnumValue("Amp")
    AMP("Amp"),
    @XmlEnumValue("Volt")
    VOLT("Volt"),
    @XmlEnumValue("Celsius")
    CELSIUS("Celsius");
    private final String value;

    UnitOfMeasure(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static UnitOfMeasure fromValue(String v) {
        for (UnitOfMeasure c : UnitOfMeasure.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
