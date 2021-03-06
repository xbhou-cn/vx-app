package com.hz.love.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.hz.love.common.CustomException;
import com.hz.love.em.MsgEnum;
import com.hz.love.result.ResultEntity;
import com.hz.love.service.IFileService;
import com.hz.love.validator.groups.CommonAddGroup;

/**
 * <b>概述</b>：<br>
 * --文件上传下载处理--
 * <p>
 * <b>功能</b>：<br>
 * --文件上传下载处理--
 *
 * @author HZ
 */
@RestController
@RequestMapping("/file")
public class FileController extends BaseController {
    @Autowired
    private IFileService fileService;

    @PostMapping("/uploadFile")
    public ResultEntity uploadFile(@RequestParam("file") MultipartFile file,
            @RequestParam("modularPath") String modularPath, HttpServletRequest request, HttpServletResponse response) {
        logger.info("文件上传开始");
        ResultEntity result;
        try {
            this.validate(file, CommonAddGroup.class);
            result = fileService.uploadFile(file, modularPath);
        } catch (CustomException ex) {
            result = new ResultEntity(false, ex.getErrorMsg());
            logger.error(ex.getMessage());
        } catch (Exception e) {
            String errMsg = "文件上传异常";
            if ("dev".equals(this.edition)) {
                errMsg = e.getMessage();
            }
            result = new ResultEntity(false, errMsg);
            logger.error(e.getMessage());
        }
        logger.info("文件上传结束");
        return result;
    }

    /**
     * @getFileBase64:获取文件的base64编码
     * @param fileId 文件ID
     * @return ResultEntity
     * @date 2020年1月17日 上午11:32:52
     * @author 侯效标
     */
    @GetMapping("/{fileId}")
    public ResultEntity getFileBase64(@PathVariable String fileId) {
        logger.info("文件下载开始");
        ResultEntity result = null;
        try {
            if (StringUtils.isEmpty(fileId)) {
                return new ResultEntity(false, MsgEnum.INF_MSG_01.getCode());
            } else {
                result = fileService.getFileBase64(fileId);
            }
        } catch (Exception e) {
            logger.info("文件下载异常");
            return new ResultEntity(false, MsgEnum.ERR_MSG_99.getCode());
        }
        logger.info("文件下载结束");
        return result;
    }

    /**
     * @downLoadFile:下载文件
     * @param fileId 文件ID
     * @param response 响应
     * @date 2020年1月17日 上午11:36:55
     * @author 侯效标
     */
    @GetMapping("/download/{fileId}")
    public void downLoadFile(@PathVariable String fileId, HttpServletResponse response) {
        logger.info("文件下载开始");
        try {
            if (StringUtils.isEmpty(fileId)) {
                logger.error(MsgEnum.INF_MSG_02.getMsg());
                return;
            } else {
                fileService.downLoadFile(fileId, response);
            }
        } catch (Exception e) {
            logger.error(MsgEnum.ERR_MSG_99.getMsg());
            return;
        }
        logger.info(MsgEnum.SUC_MSG_03.getMsg());
    }

}
