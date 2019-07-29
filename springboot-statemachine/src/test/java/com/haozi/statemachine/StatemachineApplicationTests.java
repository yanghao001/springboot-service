package com.haozi.statemachine;

import com.haozi.statemachine.Message.request.SetChargeProfile;
import com.haozi.statemachine.model.ChargeProfile;
import com.haozi.statemachine.model.ChargingProfileKind;
import com.haozi.statemachine.model.ChargingProfilePurpose;
import com.haozi.statemachine.utils.JsonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatemachineApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void ObjectToJson() {
        SetChargeProfile setChargeProfile = new SetChargeProfile();
        ChargeProfile chargeProfile = new ChargeProfile();
        chargeProfile.setChargeProfileId(1);
        chargeProfile.setStackLevel(0);
        chargeProfile.setTransactionId(12345);
        chargeProfile.setChargingProfilePurpose(ChargingProfilePurpose.chargePointMaxProfile.toString());
        chargeProfile.setChargingProfileKind(ChargingProfileKind.absoulte.toString());
        setChargeProfile.setChargeProfile(chargeProfile);
        setChargeProfile.setConnectorId(1);
        String str = JsonUtils.bean2Json(setChargeProfile);
        System.out.println("json 数据格式： " + str);
    }

}
