package com.haozi.cxf.domain.request;


import com.haozi.cxf.utils.JacksonUtil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BootNotificationRequest", namespace = "urn://Ocpp/Cs/2015/10/",propOrder = {
        "chargePointVendor",
        "chargePointModel",
        "chargePointSerialNumber",
        "chargeBoxSerialNumber",
        "firmwareVersion",
        "iccid",
        "imsi",
        "meterType",
        "meterSerialNumber"
})
public class BootNotificationRequest {

    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    protected String chargePointVendor;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    protected String chargePointModel;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    protected String chargePointSerialNumber;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    protected String chargeBoxSerialNumber;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    protected String firmwareVersion;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    protected String iccid;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    protected String imsi;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    protected String meterType;
    @XmlElement(required = true, namespace = "urn://Ocpp/Cs/2015/10/")
    protected String meterSerialNumber;

    public String getChargePointVendor() {
        return chargePointVendor;
    }

    public void setChargePointVendor(String chargePointVendor) {
        this.chargePointVendor = chargePointVendor;
    }

    public String getChargePointModel() {
        return chargePointModel;
    }

    public void setChargePointModel(String chargePointModel) {
        this.chargePointModel = chargePointModel;
    }

    public String getChargePointSerialNumber() {
        return chargePointSerialNumber;
    }

    public void setChargePointSerialNumber(String chargePointSerialNumber) {
        this.chargePointSerialNumber = chargePointSerialNumber;
    }

    public String getChargeBoxSerialNumber() {
        return chargeBoxSerialNumber;
    }

    public void setChargeBoxSerialNumber(String chargeBoxSerialNumber) {
        this.chargeBoxSerialNumber = chargeBoxSerialNumber;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getMeterType() {
        return meterType;
    }

    public void setMeterType(String meterType) {
        this.meterType = meterType;
    }

    public String getMeterSerialNumber() {
        return meterSerialNumber;
    }

    public void setMeterSerialNumber(String meterSerialNumber) {
        this.meterSerialNumber = meterSerialNumber;
    }

    @Override
    public String toString() {
        return JacksonUtil.bean2Json(this);
    }
}
