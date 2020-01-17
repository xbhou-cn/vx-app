package com.hz.love.service.impl;

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
//    private final static Logger logger = LoggerFactory.getLogger(BaseController.class);
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
    public ResultEntity getDownloadFile(String fileId) {
        CCmFile cCmFile = fileMapper.selectByPrimaryKey(fileId);
        if (cCmFile == null) {
            return new ResultEntity(false, MsgEnum.ERR_MSG_07.getCode());
        }
        // 定义则下载文件
        // response.setHeader("Content-disposition", "attachment; filename=" + resultFileName);// 设定输出文件头
        ResultEntity downloadFile = FtpUtil.downloadFile(cCmFile);
        return downloadFile;
    }
}