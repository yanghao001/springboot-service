package com.haozi.websocket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hao.yang
 * @date 2019/7/4
 */
@Slf4j
@Component
@Controller
public class controller {

    @RequestMapping("view")
    public String mockController(Model model) {
        return "view";
    }

}
