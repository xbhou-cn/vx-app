package com.hz.love.service;

import java.util.Map;

import com.hz.love.result.ResultEntity;

/**
 * <b>概述</b>：<br>
 * 用户管理
 * <p>
 * <b>功能</b>：<br>
 * 用户管理Service
 *
 * @author HZ
 */
public interface IUserService {

    /**
     * @saveUser:用户登陆
     * @param user 用户信息
     * @return ResultEntity
     * @date 2019年12月15日 下午11:45:56
     * @author 侯效标
     */
    ResultEntity saveUser(Map<String, Object> user);

}
