package com.haozi.cxf.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ReadingContext")
@XmlEnum
public enum ReadingContext {

    @XmlEnumValue("Interruption.Begin")
    INTERRUPTION_BEGIN("Interruption.Begin"),
    @XmlEnumValue("Interruption.End")
    INTERRUPTION_END("Interruption.End"),
    @XmlEnumValue("Sample.Clock")
    SAMPLE_CLOCK("Sample.Clock"),
    @XmlEnumValue("Sample.Periodic")
    SAMPLE_PERIODIC("Sample.Periodic"),
    @XmlEnumValue("Transaction.Begin")
    TRANSACTION_BEGIN("Transaction.Begin"),
    @XmlEnumValue("Transaction.End")
    TRANSACTION_END("Transaction.End");
    private final String value;

    ReadingContext(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ReadingContext fromValue(String v) {
        for (ReadingContext c: ReadingContext.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
