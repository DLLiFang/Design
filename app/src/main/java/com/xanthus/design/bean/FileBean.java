package com.xanthus.design.bean;

/**
 * ID         int64  `orm:"auto;pk" json:"id"`
 * Name       string `orm:"size(100)" json:"name"`
 * User       *User  `orm:"rel(fk)" json:"user"`
 * Path       string `orm:"size(150)" json:"path"`
 * CreateTime int64  `json:"createtime"`
 * Count      int    `json:"count"`
 * Created by liyiheng on 16/11/9.
 */

public class FileBean {
    private long id;
    private String name;
    private User user;
    private String path;
    private long createtime;
    private int count;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
