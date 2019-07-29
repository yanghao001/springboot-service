package com.haozi.statemachine.model;

/**
 * @author hao.yang
 * @date 2019/7/18
 */
public class ChargeProfile {

    private int chargeProfileId;

    private int transactionId;

    private int stackLevel;

    private String chargingProfilePurpose;

    private String chargingProfileKind;

    public int getChargeProfileId() {
        return chargeProfileId;
    }

    public void setChargeProfileId(int chargeProfileId) {
        this.chargeProfileId = chargeProfileId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getStackLevel() {
        return stackLevel;
    }

    public void setStackLevel(int stackLevel) {
        this.stackLevel = stackLevel;
    }

    public String getChargingProfilePurpose() {
        return chargingProfilePurpose;
    }

    public void setChargingProfilePurpose(String chargingProfilePurpose) {
        this.chargingProfilePurpose = chargingProfilePurpose;
    }

    public String getChargingProfileKind() {
        return chargingProfileKind;
    }

    public void setChargingProfileKind(String chargingProfileKind) {
        this.chargingProfileKind = chargingProfileKind;
    }
}
