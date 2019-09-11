package com.haozi.mongodb.task;

import com.haozi.mongodb.model.ChargeInfo;
import com.haozi.mongodb.utils.JacksonUtil;
import com.haozi.mongodb.utils.JsonUtil;
import com.mongodb.*;
import com.mongodb.util.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hao.yang
 * @date 2019/8/1
 */
@Slf4j
@Component
public class ScheduleTask {

    @Autowired
    private ComputePower computePower;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;
    /**
     * 每间隔十分钟查询一次mongo数据
     */
    @Scheduled(initialDelay = 0, fixedRate = 600000)
    public void queryData() {
        log.info("query data for every 10 min");
        int endTime = (int) System.currentTimeMillis();
        int startTime = (int) (System.currentTimeMillis() - 600000);
        log.info("startTime={} endTime={} ", startTime, endTime);

        // 1. 查询10分钟以内数据
        Query query = new Query();
        query.addCriteria(Criteria.where("timestamp").gte(startTime).lte(endTime));
        List<ChargeInfo> chargeInfos = mongoTemplate.find(query, ChargeInfo.class, "chargeGraph");
        log.info("chargeInfo:{}", chargeInfos.isEmpty() ? "no data" : chargeInfos);

        // 2. 按桩号存入map
        Map<String, Object> keyMap = new HashMap<>();
        for (ChargeInfo chargeInfo : chargeInfos) {
            String deviceNumber = chargeInfo.getDeviceNumber();
            String info = stringRedisTemplate.opsForValue().get(deviceNumber);
            List<ChargeInfo> lists = JsonUtil.json2List(info, ChargeInfo.class);
            if (lists == null) {
                lists = new ArrayList<>();
            }
            if (!keyMap.containsKey(deviceNumber)) {
                keyMap.put(deviceNumber, null);
            }
            lists.add(chargeInfo);
            stringRedisTemplate.opsForValue().set(deviceNumber, JacksonUtil.list2Json(lists));
        }

        // 3. 计算平均功率
        computePower.sort(keyMap);
    }
}
