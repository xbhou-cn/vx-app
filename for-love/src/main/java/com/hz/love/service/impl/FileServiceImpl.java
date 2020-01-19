package com.hz.love.service.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hz.love.common.FtpUtil;
import com.hz.love.dao.CCmFileMapper;
import com.hz.love.em.MsgEnum;
import com.hz.love.model.CCmFile;
import com.hz.love.result.ResultEntity;
import com.hz.love.service.IFileService;

/**
 * <b>概述</b>：<br>
 * --文件上传下载--
 * <p>
 * <b>功能</b>：<br>
 * --文件上传下载--
 *
 * @author 侯效标
 */
@Service
public class FileServiceImpl implements IFileService {
    @Autowired
    private CCmFileMapper fileMapper;
    private final static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    /**
     * @describe 文件保存根目录
     */
    @Value("${file.upload_path}")
    private String path;

    @Override
    public ResultEntity uploadFile(MultipartFile file, String modularPath) {
        String remoteFilePath = this.path + "/" + modularPath;
        CCmFile cCmFile = FtpUtil.uploadFile2Ftp(file, remoteFilePath);
        if (cCmFile != null) {
            int insert = fileMapper.insert(cCmFile);
            if (insert == 1) {
                return new ResultEntity(true, MsgEnum.SUC_MSG_02.getCode());
            }
        }
        return new ResultEntity(false, MsgEnum.ERR_MSG_06.getCode());
    }

    @Override
    public ResultEntity getFileBase64(String fileId) {
        CCmFile cCmFile = fileMapper.selectByPrimaryKey(fileId);
        if (cCmFile == null) {
            return new ResultEntity(false, MsgEnum.ERR_MSG_07.getCode());
        }
        // 定义则下载文件
        ResultEntity downloadFile = FtpUtil.getFileBase64(cCmFile);
        return downloadFile;
    }

    @Override
    public void downLoadFile(String fileId, HttpServletResponse response) {
        CCmFile cCmFile = fileMapper.selectByPrimaryKey(fileId);
        if (cCmFile == null) {
            logger.error(MsgEnum.ERR_MSG_07.getMsg());
            return;
        }
        FtpUtil.downLoadFile(cCmFile, response);
    }
}
