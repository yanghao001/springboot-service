package com.haozi.elasticsearch.model;

import java.util.Date;
import java.util.List;

/**
 * @author hao.yang
 * @date 2019/9/18
 */
public class User {
    private String id;

    private String userName;

    private Integer age;

    private Date birthday;

    private String description;

    /**
     * 1对多在spring-data-elasticsearch 统一为nested类型
     **/
    private List<Role> roles;

    public User() {}

    public User(String id, String userName, Integer age, Date birthday, String description, List<Role> roles) {
        this.id = id;
        this.userName = userName;
        this.age = age;
        this.birthday = birthday;
        this.description = description;
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
