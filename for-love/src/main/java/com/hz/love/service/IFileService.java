package com.hz.love.service;

import org.springframework.web.multipart.MultipartFile;

import com.hz.love.result.ResultEntity;

/**
 * <b>概述</b>：<br>
 * --文件上传下载--
 * <p>
 * <b>功能</b>：<br>
 * --文件上传下载--
 *
 * @author 侯效标
 */
public interface IFileService {

    /**
     * @uploadFile:文件上传
     * @param file 文件
     * @param modularPath 所属画面模块
     * @return ResultEntity
     * @date 2020年1月10日 下午4:55:04
     * @author 侯效标
     */
    ResultEntity uploadFile(MultipartFile file, String modularPath);

    /**
     * @getDownloadFile:获得下载文件
     * @param fileId 文件ID
     * @return ResultEntity
     * @date 2020年1月15日 下午3:41:53
     * @author 侯效标
     * @param response 响应
     * @param request 请求
     * @throws Exception
     */
    ResultEntity getDownloadFile(String fileId);

}
