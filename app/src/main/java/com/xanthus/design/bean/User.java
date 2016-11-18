package com.xanthus.design.bean;

/**
 * Created by liyiheng on 16/11/9.
 */

public class User {
    /*
    ID       int64  `orm:"auto;pk" json:"id"`
	Username string `orm:"size(50);unique" json:"username"`
	Password string `orm:"size(20)" json:"password"`
	Nickname string `orm:"size(50)" json:"nickname"`
	Avatar   string `orm:"size(100)" json:"avatar"`
	Token    string `json:"token"`
	Phone    string `orm:"size(20)" json:"phone"`
	Gender   int    `json:"gender"`
	Role     int    `json:"role"`
     */
    private long id;
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private String token;
    private String phone;
    private int gender;
    private int role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
