package com.haozi.statemachine.Message.request;

import com.haozi.statemachine.model.ChargeProfile;

/**
 * @author hao.yang
 * @date 2019/7/18
 */
public class SetChargeProfile {

    private int connectorId;

    private ChargeProfile chargeProfile;

    public int getConnectorId() {
        return connectorId;
    }

    public void setConnectorId(int connectorId) {
        this.connectorId = connectorId;
    }

    public ChargeProfile getChargeProfile() {
        return chargeProfile;
    }

    public void setChargeProfile(ChargeProfile chargeProfile) {
        this.chargeProfile = chargeProfile;
    }
}
