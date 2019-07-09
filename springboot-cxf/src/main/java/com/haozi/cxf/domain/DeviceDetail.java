package com.haozi.cxf.domain;

import java.util.HashMap;
import java.util.Map;

public class DeviceDetail {


    public final static String ORDER_NONE = "";
    public final static String ORDER_CHARGE = "充电";
    public final static String ORDER_TIME = "定时";
    public final static String ORDER_BOOK = "预约";
    public final static String START_NONE = "";

    private int userId;
    private volatile String orderType;
    private String orderNumber;
    private String sequenceNumber;
    private String serialNumber;
    private String startType;
    private String startDetail;
    private String startedAt;
    private String finishedAt;
    private int voltage;
    private int current;
    private String finishChargeReason;
    private Map<String, Integer> elecQuantityDetail = new HashMap<String, Integer>();
    private Map<String, String> bmsStopReason = new HashMap<String, String>();
    private Map<String, String> bmsFaultReason = new HashMap<String, String>();
    private int elecQuantity;
    private int duration;
    private String faultType;
    private String dcModleFaultType;
    private String faultReason;
    private String chargeType;
    private int ramainingTime;
    private int socStatus;
    private int ionTemperature;
    private int singMaxVoltage;
    private String battryType;
    private int maxTemperature;
    private int bmsMaxVoltage;
    private int moMaxVoltage;
    private int maxCurrent;
    private int totalVoltage;
    private int currentVoltage;
    private int ratedCapacity;
    private int nomEnergy;

    public DeviceDetail() {
        init();
    }
    public void init() {
        userId = 0;
        orderType = ORDER_NONE;
        orderNumber = "";
        sequenceNumber = "";
        serialNumber = "";
        startType = START_NONE;
        startDetail = "";
        startedAt = "";
        finishedAt = "";
        voltage = 0;
        current = 0;
        finishChargeReason = "";
        elecQuantityDetail.clear();
        bmsStopReason.clear();
        bmsFaultReason.clear();
        elecQuantity = 0;
        duration = 0;
        faultType = "";
        faultReason = "";
        dcModleFaultType = "";
        chargeType = "";
        ramainingTime = 0;
        socStatus = 0;
        ionTemperature = 0;
        singMaxVoltage = 0;
        battryType = "";
        maxTemperature = 0;
        bmsMaxVoltage = 0;
        moMaxVoltage = 0;
        maxCurrent = 0;
        totalVoltage = 0;
        currentVoltage = 0;
        ratedCapacity = 0;
        nomEnergy = 0;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getStartType() {
        return startType;
    }

    public void setStartType(String startType) {
        this.startType = startType;
    }

    public String getStartDetail() {
        return startDetail;
    }

    public void setStartDetail(String startDetail) {
        this.startDetail = startDetail;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(String finishedAt) {
        this.finishedAt = finishedAt;
    }

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public String getFinishChargeReason() {
        return finishChargeReason;
    }

    public void setFinishChargeReason(String finishChargeReason) {
        this.finishChargeReason = finishChargeReason;
    }

    public Map<String, Integer> getElecQuantityDetail() {
        return elecQuantityDetail;
    }

    public void setElecQuantityDetail(Map<String, Integer> elecQuantityDetail) {
        this.elecQuantityDetail = elecQuantityDetail;
    }

    public Map<String, String> getBmsStopReason() {
        return bmsStopReason;
    }

    public void setBmsStopReason(Map<String, String> bmsStopReason) {
        this.bmsStopReason = bmsStopReason;
    }

    public Map<String, String> getBmsFaultReason() {
        return bmsFaultReason;
    }

    public void setBmsFaultReason(Map<String, String> bmsFaultReason) {
        this.bmsFaultReason = bmsFaultReason;
    }

    public int getElecQuantity() {
        return elecQuantity;
    }

    public void setElecQuantity(int elecQuantity) {
        this.elecQuantity = elecQuantity;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getFaultType() {
        return faultType;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }

    public String getDcModleFaultType() {
        return dcModleFaultType;
    }

    public void setDcModleFaultType(String dcModleFaultType) {
        this.dcModleFaultType = dcModleFaultType;
    }

    public String getFaultReason() {
        return faultReason;
    }

    public void setFaultReason(String faultReason) {
        this.faultReason = faultReason;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public int getRamainingTime() {
        return ramainingTime;
    }

    public void setRamainingTime(int ramainingTime) {
        this.ramainingTime = ramainingTime;
    }

    public int getSocStatus() {
        return socStatus;
    }

    public void setSocStatus(int socStatus) {
        this.socStatus = socStatus;
    }

    public int getIonTemperature() {
        return ionTemperature;
    }

    public void setIonTemperature(int ionTemperature) {
        this.ionTemperature = ionTemperature;
    }

    public int getSingMaxVoltage() {
        return singMaxVoltage;
    }

    public void setSingMaxVoltage(int singMaxVoltage) {
        this.singMaxVoltage = singMaxVoltage;
    }

    public String getBattryType() {
        return battryType;
    }

    public void setBattryType(String battryType) {
        this.battryType = battryType;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(int maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public int getBmsMaxVoltage() {
        return bmsMaxVoltage;
    }

    public void setBmsMaxVoltage(int bmsMaxVoltage) {
        this.bmsMaxVoltage = bmsMaxVoltage;
    }

    public int getMoMaxVoltage() {
        return moMaxVoltage;
    }

    public void setMoMaxVoltage(int moMaxVoltage) {
        this.moMaxVoltage = moMaxVoltage;
    }

    public int getMaxCurrent() {
        return maxCurrent;
    }

    public void setMaxCurrent(int maxCurrent) {
        this.maxCurrent = maxCurrent;
    }

    public int getTotalVoltage() {
        return totalVoltage;
    }

    public void setTotalVoltage(int totalVoltage) {
        this.totalVoltage = totalVoltage;
    }

    public int getCurrentVoltage() {
        return currentVoltage;
    }

    public void setCurrentVoltage(int currentVoltage) {
        this.currentVoltage = currentVoltage;
    }

    public int getRatedCapacity() {
        return ratedCapacity;
    }

    public void setRatedCapacity(int ratedCapacity) {
        this.ratedCapacity = ratedCapacity;
    }

    public int getNomEnergy() {
        return nomEnergy;
    }

    public void setNomEnergy(int nomEnergy) {
        this.nomEnergy = nomEnergy;
    }
}
