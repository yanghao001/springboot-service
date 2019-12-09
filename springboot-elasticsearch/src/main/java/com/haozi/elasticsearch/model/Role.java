package com.haozi.elasticsearch.model;

import java.util.Date;

/**
 * @author hao.yang
 * @date 2019/9/18
 */
public class Role {
    private String id;

    private String name;

    private Date createTime;

    private String description;

    public Role() { }

    public Role(String id, String name, Date createTime, String description) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
