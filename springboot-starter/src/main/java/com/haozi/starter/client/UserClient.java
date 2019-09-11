package com.haozi.starter.client;

import com.haozi.starter.config.UserProperties;

/**
 *  相当于mongoTemplate,其类似
 *  写好后就可以像mongo加入依赖，注入使用，可以直接调用userClient方法
 * @author hao.yang
 * @date 2019/9/11
 */
public class UserClient {

    private UserProperties userProperties;

    public UserClient() {

    }

    public UserClient(UserProperties p) {
        this.userProperties = p;
    }

    public String getName() {
        return userProperties.getName();
    }

}
