package com.haozi.mongodb.controller;

import com.haozi.mongodb.model.Power;
import com.haozi.mongodb.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hao.yang
 * @date 2019/8/6
 */
@Slf4j
@Controller("/v1")
public class GraphController {

    public static ConcurrentHashMap<String, Object> powerMap = new ConcurrentHashMap<>();

    @ResponseBody
    @RequestMapping(value = "/getGraphInfo", method = RequestMethod.POST)
    public String getGraphInfo() {
        Power power = (Power) powerMap.get("Power");
        Optional<?> powerOptional = Optional.ofNullable(power);
        if (!powerOptional.isPresent()){
            log.warn("invalid power");
            return "invalid power";
        }
        return JsonUtil.bean2Json(power);
    }
}
