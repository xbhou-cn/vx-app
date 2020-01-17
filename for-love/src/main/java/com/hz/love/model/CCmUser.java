package com.hz.love.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.hz.love.validator.groups.CommonAddGroup;

public class CCmUser {

    private String userId;
    @NotBlank(message = "openid不能为空", groups = { CommonAddGroup.class })
    private String openId;
    @NotBlank(message = "用户名不能为空", groups = { CommonAddGroup.class })
    private String userName;
    private String userFace;

    private Date createTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserFace() {
        return userFace;
    }

    public void setUserFace(String userFace) {
        this.userFace = userFace == null ? null : userFace.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}