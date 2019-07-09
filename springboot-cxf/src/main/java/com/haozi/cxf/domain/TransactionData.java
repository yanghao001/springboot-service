package com.haozi.cxf.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionData", propOrder = {
        "values"
})
public class TransactionData {

    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    private List<MeterValue> values;

    public List<MeterValue> getValues() {
        return values;
    }

    public void setValues(List<MeterValue> values) {
        this.values = values;
    }
}
