package com.haozi.cxf.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "DiagnosticsStatus")
@XmlEnum
public enum DiagnosticsStatus {

    @XmlEnumValue("Uploaded")
    UPLOADED("Uploaded"),
    @XmlEnumValue("UploadFailed")
    UPLOAD_FAILED("UploadFailed");
    private final String value;

    DiagnosticsStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DiagnosticsStatus fromValue(String v) {
        for (DiagnosticsStatus c : DiagnosticsStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
