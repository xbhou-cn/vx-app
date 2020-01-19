package com.hz.love.service;

import javax.servlet.http.HttpServletResponse;

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
     * @getFileBase64:获取文件base64
     * @param fileId 文件ID
     * @return ResultEntity
     * @date 2020年1月17日 上午11:26:03
     * @author 侯效标
     */
    ResultEntity getFileBase64(String fileId);

    /**
     * @downLoadFile:文件下载
     * @param fileId 文件ID
     * @param response 响应
     * @date 2020年1月17日 上午11:36:43
     * @author 侯效标
     */
    void downLoadFile(String fileId, HttpServletResponse response);

}
