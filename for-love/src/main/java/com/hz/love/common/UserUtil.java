package com.hz.love.common;

import com.hz.love.model.CCmUser;

/**
 * <b>概述</b>：<br>
 * --用户信息--
 * <p>
 * <b>功能</b>：<br>
 * --用户信息--
 *
 * @author HZ
 */
public class UserUtil {
    public static CCmUser getUserInfo() {
        CCmUser cCmUser = new CCmUser();
        cCmUser.setOpenId("admin");
        return cCmUser;
    }
}
