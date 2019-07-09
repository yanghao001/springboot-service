package com.haozi.cxf.domain;

import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MeterValue", propOrder = {
        "timestamp",
        "value"
})
public class MeterValue {

    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    @XmlSchemaType(name = "dateTime")
    protected Date timestamp;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    protected List<Value> value;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public List<Value> getValue() {
        return value;
    }

    public void setValue(List<Value> value) {
        this.value = value;
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    public static class Value {
        @XmlValue
        private String value;
        @XmlAttribute(name = "context")
        private ReadingContext context;
        @XmlAttribute(name = "format")
        private ValueFormat format;
        @XmlAttribute(name = "measurand")
        private Measurand measurand;
        @XmlAttribute(name = "location")
        private Location location;
        @XmlAttribute(name = "unit")
        private UnitOfMeasure unit;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public ReadingContext getContext() {
            return context;
        }

        public void setContext(ReadingContext context) {
            this.context = context;
        }

        public ValueFormat getFormat() {
            return format;
        }

        public void setFormat(ValueFormat format) {
            this.format = format;
        }

        public Measurand getMeasurand() {
            return measurand;
        }

        public void setMeasurand(Measurand measurand) {
            this.measurand = measurand;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public UnitOfMeasure getUnit() {
            return unit;
        }

        public void setUnit(UnitOfMeasure unit) {
            this.unit = unit;
        }
    }
}
