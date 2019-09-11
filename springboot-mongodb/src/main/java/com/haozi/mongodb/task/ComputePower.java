package com.haozi.mongodb.task;

import com.haozi.mongodb.config.ConstantConfig;
import com.haozi.mongodb.controller.GraphController;
import com.haozi.mongodb.model.ChargeInfo;
import com.haozi.mongodb.model.Device;
import com.haozi.mongodb.model.DeviceInfo;
import com.haozi.mongodb.model.Power;
import com.haozi.mongodb.utils.JacksonUtil;
import com.haozi.mongodb.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 计算功率
 *
 * @author hao.yang
 * @date 2019/8/5
 */
@Slf4j
@Component
public class ComputePower {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 分类计算
     */
    public void sort(Map<String, Object> map) {
        List<Device> devices = new ArrayList<>();
        for (String deviceNumber : map.keySet()) {
            String chargingList = stringRedisTemplate.opsForValue().get(deviceNumber);
            List<ChargeInfo> chargingLists = JsonUtil.json2List(chargingList, ChargeInfo.class);
            computeDevicePower(deviceNumber, chargingLists);
            computeStationPower(deviceNumber);
            String stationName = stringRedisTemplate.opsForValue().get(deviceNumber + ":stationName");
            String info = stringRedisTemplate.opsForValue().get(stationName + ":deviceInfo");
            DeviceInfo deviceInfo = JacksonUtil.json2Bean(info, DeviceInfo.class);
            if (deviceInfo != null) {
                Device device = new Device();
                device.setType(deviceInfo.getStationName());
                device.setCapacity(deviceInfo.getCapacity());
                device.setDeviceNumber(deviceNumber);
                device.setControlRatio(deviceInfo.getControlRatio());
                device.setMaxCtrlRatio(deviceInfo.getMaxCtrlRatio());
                device.setMinCtrlRatio(deviceInfo.getMinCtrlRatio());
                device.setReduceRatio(deviceInfo.getReduceRatio());
                devices.add(device);
            }
        }
        map.clear();

        // 计算站点功率
        Double dropStopPower = convert(stringRedisTemplate.opsForValue().get(ConstantConfig.DROP_AND_STOP));
        Double dropNoStopPower = convert(stringRedisTemplate.opsForValue().get(ConstantConfig.DROP_AND_NO_STOP));
        Double noDropNoStopPower = convert(stringRedisTemplate.opsForValue().get(ConstantConfig.NO_DROP_AND_NO_STOP));
        Double sumPower = dropStopPower + dropNoStopPower + noDropNoStopPower;
        log.info("drop and stop stationPower:{}", dropStopPower);
        log.info("drop and no stop stationPower:{}", dropNoStopPower);
        log.info("no drop and no stop stationPower:{}", noDropNoStopPower);
        log.info("the power of 3 stations:{}", sumPower);
        Power power = new Power();
        power.setDropStopPower(dropStopPower);
        power.setDropNoStopPower(dropNoStopPower);
        power.setNoDropNoStopPower(noDropNoStopPower);
        power.setSumPower(sumPower);
        GraphController.powerMap.put("Power", JsonUtil.bean2Json(power));

        // 调节功率
    }

    /**
     * 计算设备平均功率
     */
    public void computeDevicePower(String deviceNumber, List<ChargeInfo> lists) {
        int size = lists.size();
        Double sum = 0.0;
        for (ChargeInfo chargeInfo : lists) {
            Double voltage = convert(chargeInfo.getVoltage());
            Double current = convert(chargeInfo.getElecCurrent());
            sum += voltage * current;
        }
        Double devicePower = sum / size;
        stringRedisTemplate.opsForValue().set(deviceNumber + ":devicePower", String.valueOf(devicePower));
        log.info("deviceNumber:[{}] compute devicePower:[{}]", deviceNumber, devicePower);
    }

    /**
     * 计算站点平均功率
     */
    public void computeStationPower(String deviceNumber) {
        String dPower = stringRedisTemplate.opsForValue().get(deviceNumber + ":devicePower");
        Double devicePower = convert(dPower);
        String stationName = stringRedisTemplate.opsForValue().get(deviceNumber + ":stationName");
        String sPower = stringRedisTemplate.opsForValue().get(stationName);
        Double stationPower = convert(sPower);
        stationPower += devicePower;
        stringRedisTemplate.opsForValue().set(stationName, String.valueOf(stationPower));
    }

    public Double convert(String power) {
        if (power == null) {
            power = "0.0";
        }
        return Double.parseDouble(power);
    }
}
