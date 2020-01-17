package com.hz.love.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hz.love.dao.CCmUserMapper;
import com.hz.love.em.MsgEnum;
import com.hz.love.model.CCmUser;
import com.hz.love.result.ResultEntity;
import com.hz.love.service.IUserService;

/**
 * <b>概述</b>：<br>
 * --用户管理--
 * <p>
 * <b>功能</b>：<br>
 * --用户管理service--
 *
 * @author HZ
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private CCmUserMapper userMapper;

    @Override
    public ResultEntity saveUser(Map<String, Object> user) {
        String openId = (String) user.get("openId");
        CCmUser cmUser = userMapper.findByOpenId(openId);
        if (cmUser == null) {
            cmUser = new CCmUser();
            try {
                BeanUtils.populate(cmUser, user);
                cmUser.setUserId(UUID.randomUUID().toString());
                cmUser.setCreateTime(new Date());
                userMapper.insert(cmUser);
            } catch (Exception e) {
                return new ResultEntity(false, MsgEnum.ERR_MSG_01.getMsg());
            }
        }
        return new ResultEntity(cmUser, true, MsgEnum.SUC_MSG_01.getMsg());
    }

}
