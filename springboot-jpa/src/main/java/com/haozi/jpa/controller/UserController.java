package com.haozi.jpa.controller;

import com.haozi.jpa.entity.UserEntity;
import com.haozi.jpa.repository.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author hao.yang
 * @date 2019/7/26
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserJPA userJPA;

    /**
     * 查询用户列表
     * */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<UserEntity> list() {
        return userJPA.findAll();
    }

    /**
     * 添加，更新用户
     * */
    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public void save(UserEntity userEntity) {
        userJPA.save(userEntity);
        System.out.println("添加用户成功！");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void delete(Long id) {
        userJPA.deleteById(id);
        System.out.println("删除用户成功！");
    }
}
