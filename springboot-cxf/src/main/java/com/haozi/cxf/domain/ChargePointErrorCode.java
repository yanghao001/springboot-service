package com.haozi.cxf.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ChargePointErrorCode")
@XmlEnum
public enum ChargePointErrorCode {

    @XmlEnumValue("ConnectorLockFailure")
    CONNECTOR_LOCK_FAILURE("ConnectorLockFailure"),
    @XmlEnumValue("HighTemperature")
    HIGH_TEMPERATURE("HighTemperature"),
    @XmlEnumValue("Mode3Error")
    MODE_3_ERROR("Mode3Error"),
    @XmlEnumValue("NoError")
    NO_ERROR("NoError"),
    @XmlEnumValue("PowerMeterFailure")
    POWER_METER_FAILURE("PowerMeterFailure"),
    @XmlEnumValue("PowerSwitchFailure")
    POWER_SWITCH_FAILURE("PowerSwitchFailure"),
    @XmlEnumValue("ReaderFailure")
    READER_FAILURE("ReaderFailure"),
    @XmlEnumValue("ResetFailure")
    RESET_FAILURE("ResetFailure"),
    @XmlEnumValue("GroundFailure")
    GROUND_FAILURE("GroundFailure"),
    @XmlEnumValue("OverCurrentFailure")
    OVER_CURRENT_FAILURE("OverCurrentFailure"),
    @XmlEnumValue("UnderVoltage")
    UNDER_VOLTAGE("UnderVoltage"),
    @XmlEnumValue("WeakSignal")
    WEAK_SIGNAL("WeakSignal"),
    @XmlEnumValue("OtherError")
    OTHER_ERROR("OtherError");
    private final String value;

    ChargePointErrorCode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ChargePointErrorCode fromValue(String v) {
        for (ChargePointErrorCode c : ChargePointErrorCode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
