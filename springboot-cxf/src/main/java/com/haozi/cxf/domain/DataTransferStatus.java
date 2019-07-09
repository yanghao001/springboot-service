package com.haozi.cxf.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "DataTransferStatus")
@XmlEnum
public enum DataTransferStatus {

    @XmlEnumValue("Accepted")
    ACCEPTED("Accepted"),
    @XmlEnumValue("Rejected")
    REJECTED("Rejected"),
    @XmlEnumValue("UnknownMessageId")
    UNKNOWN_MESSAGE_ID("UnknownMessageId"),
    @XmlEnumValue("UnknownVendorId")
    UNKNOWN_VENDOR_ID("UnknownVendorId");
    private final String value;

    DataTransferStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DataTransferStatus fromValue(String v) {
        for (DataTransferStatus c : DataTransferStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
