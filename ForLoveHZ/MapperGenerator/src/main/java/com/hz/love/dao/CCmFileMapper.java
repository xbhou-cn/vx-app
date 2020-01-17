package com.hz.love.dao;

import com.hz.love.model.CCmFile;

public interface CCmFileMapper {
    int deleteByPrimaryKey(String fileId);

    int insert(CCmFile record);

    int insertSelective(CCmFile record);

    CCmFile selectByPrimaryKey(String fileId);

    int updateByPrimaryKeySelective(CCmFile record);

    int updateByPrimaryKey(CCmFile record);
}