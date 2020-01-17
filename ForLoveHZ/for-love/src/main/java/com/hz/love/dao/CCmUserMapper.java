package com.hz.love.dao;

import org.apache.ibatis.annotations.Mapper;

import com.hz.love.model.CCmUser;

@Mapper
public interface CCmUserMapper {
    int deleteByPrimaryKey(String userId);

    /**
     * @insert:插入数据
     * @param record 参数
     * @return int
     * @date 2019年12月17日 下午5:56:20
     * @author 侯效标
     */
    int insert(CCmUser record);

    int insertSelective(CCmUser record);

    CCmUser selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(CCmUser record);

    int updateByPrimaryKey(CCmUser record);

    /**
     * @findByOpenId:通过openId查询用户
     * @param openId
     * @return CCmUser
     * @date 2019年12月17日 下午5:36:03
     * @author 侯效标
     */
    CCmUser findByOpenId(String openId);
}