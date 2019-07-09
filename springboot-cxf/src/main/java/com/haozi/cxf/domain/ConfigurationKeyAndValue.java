package com.haozi.cxf.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ConfigurationKeyAndValue")
@XmlEnum
public enum ConfigurationKeyAndValue {

    @XmlEnumValue("HeartBeatInterval")
    HEARTBEAT_INTERVAL("HeartBeatInterval"),
    @XmlEnumValue("ConnectionTimeOut")
    CONNECTION_TIMEOUT("ConnectionTimeOut"),
    @XmlEnumValue("ResetRetries")
    RESET_RETRIES("ResetRetries"),
    @XmlEnumValue("BlinkRepeat")
    BLINK_REPEAT("BlinkRepeat"),
    @XmlEnumValue("LightIntensity")
    LIGHT_INTENSITY("LightIntensity"),
    @XmlEnumValue("MeterValueSampleInterval")
    METERVALUE_SAMPLE_INTERVAL("MeterValueSampleInterval"),
    @XmlEnumValue("ClockAlignedDataInterval")
    CLOCK_ALIGNED_DATA_INTERVAL("ClockAlignedDataInterval"),
    @XmlEnumValue("MeterValuesSampledData")
    METERVALUES_SAMPLED_DATA("MeterValuesSampledData"),
    @XmlEnumValue("StopTxnSampledData")
    STOP_TXN_SAMPLED_DATA("StopTxnSampledData"),
    @XmlEnumValue("StopTxnAlignedData")
    STOP_TXN_ALIGNED_DATA("StopTxnAlignedData");

    private final String value;

    ConfigurationKeyAndValue(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static String fromValue(String v) {
        for (ConfigurationKeyAndValue c : ConfigurationKeyAndValue.values()) {
            if (c.value.equals(v)) {
                return String.valueOf(c);
            }
        }
        throw new IllegalArgumentException(v);
    }

}

