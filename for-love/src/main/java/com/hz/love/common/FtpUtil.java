package com.hz.love.common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.util.Base64;
import org.apache.commons.pool2.ObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.hz.love.em.MsgEnum;
import com.hz.love.model.CCmFile;
import com.hz.love.result.ResultEntity;

/**
 * Ftp工具类
 * 
 * @author wxyh
 */
public class FtpUtil {

    /**
     * ftpClient连接池初始化标志
     */
    private static volatile boolean hasInit = false;
    /**
     * ftpClient连接池
     */
    private static ObjectPool<FTPClient> ftpClientPool;

    private final static Logger logger = LoggerFactory.getLogger(FtpUtil.class);

    /**
     * 初始化ftpClientPool
     *
     * @param ftpClientPool
     */
    public static void init(ObjectPool<FTPClient> ftpClientPool) {
        if (!hasInit) {
            synchronized (FtpUtil.class) {
                if (!hasInit) {
                    FtpUtil.ftpClientPool = ftpClientPool;
                    hasInit = true;
                }
            }
        }
    }

    /**
     * 读取csv文件
     *
     * @param remoteFilePath 文件路径（path+fileName）
     * @param header 列头
     * @return
     * @throws IOException
     */
    // public static List<CSVRecord> readCsvFile(String remoteFilePath,
    // String... headers) throws IOException {
    // FTPClient ftpClient = getFtpClient();
    // try (InputStream in =
    // ftpClient.retrieveFileStream(encodingPath(remoteFilePath))) {
    // return CSVFormat.EXCEL.withHeader(headers).withSkipHeaderRecord(false)
    // .withIgnoreSurroundingSpaces().withIgnoreEmptyLines()
    // .parse(new InputStreamReader(in, "utf-8")).getRecords();
    // } finally {
    // ftpClient.completePendingCommand();
    // releaseFtpClient(ftpClient);
    // }
    // }

    /**
     * 按行读取FTP文件
     *
     * @param remoteFilePath 文件路径（path+fileName）
     * @return
     * @throws IOException
     */
    public static List<String> readFileByLine(String remoteFilePath) throws IOException {
        FTPClient ftpClient = getFtpClient();
        try (InputStream in = ftpClient.retrieveFileStream(encodingPath(remoteFilePath));
                BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            return br.lines().map(line -> line == null ? null : line.trim())
                    .filter(line -> (line != null && line.length() > 0)).collect(Collectors.toList());
        } finally {
            ftpClient.completePendingCommand();
            releaseFtpClient(ftpClient);
        }
    }

    /**
     * 获取指定路径下FTP文件
     *
     * @param remotePath 路径
     * @return FTPFile数组
     * @throws IOException
     */
    public static FTPFile[] retrieveFTPFiles(String remotePath) throws IOException {
        FTPClient ftpClient = getFtpClient();
        try {
            return ftpClient.listFiles(encodingPath(remotePath + "/"), file -> file != null && file.getSize() > 0);
        } finally {
            releaseFtpClient(ftpClient);
        }
    }

    /**
     * 获取指定路径下FTP文件名称
     *
     * @param remotePath 路径
     * @return ftp文件名称列表
     * @throws IOException
     */
    public static List<String> retrieveFileNames(String remotePath) throws IOException {
        FTPFile[] ftpFiles = retrieveFTPFiles(remotePath);
        if (ftpFiles == null || ftpFiles.length == 0) {
            return new ArrayList<>();
        }
        return Arrays.stream(ftpFiles).filter(Objects::nonNull).map(FTPFile::getName).collect(Collectors.toList());
    }

    /**
     * 编码文件路径
     */
    private static String encodingPath(String path) throws UnsupportedEncodingException {
        // FTP协议里面，规定文件名编码为iso-8859-1，所以目录名或文件名需要转码
        return new String(path.replaceAll("//", "/").getBytes("GBK"), "iso-8859-1");
    }

    /**
     * 获取ftpClient
     *
     * @return
     */
    private static FTPClient getFtpClient() {
        checkFtpClientPoolAvailable();
        FTPClient ftpClient = null;
        Exception ex = null;
        // 获取连接最多尝试3次
        for (int i = 0; i < 3; i++) {
            try {
                ftpClient = ftpClientPool.borrowObject();
                break;
            } catch (Exception e) {
                ex = e;
            }
        }
        if (ftpClient == null) {
            throw new RuntimeException("Could not get a ftpClient from the pool", ex);
        }
        return ftpClient;
    }

    /**
     * 释放ftpClient
     */
    private static void releaseFtpClient(FTPClient ftpClient) {
        if (ftpClient == null) {
            return;
        }
        try {
            ftpClientPool.returnObject(ftpClient);
        } catch (Exception e) {
            // log.error("Could not return the ftpClient to the pool", e);
            // destoryFtpClient
            if (ftpClient.isAvailable()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException io) {
                }
            }
        }
    }

    /**
     * 检查ftpClientPool是否可用
     */
    private static void checkFtpClientPoolAvailable() {
        Assert.state(hasInit, "FTP未启用或连接失败！");
    }

    // /**
    // * 上传Excel文件到FTP
    // *
    // * @param workbook
    // * @param remoteFilePath
    // * @throws IOException
    // */
    // public static boolean uploadExcel2Ftp(Workbook workbook, String
    // remoteFilePath) throws IOException {
    // Assert.notNull(workbook, "workbook cannot be null.");
    // Assert.hasText(remoteFilePath, "remoteFilePath cannot be null or
    // blank.");
    // FTPClient ftpClient = getFtpClient();
    // try (OutputStream out =
    // ftpClient.storeFileStream(encodingPath(remoteFilePath))) {
    // workbook.write(out);
    // workbook.close();
    // return true;
    // } finally {
    // ftpClient.completePendingCommand();
    // releaseFtpClient(ftpClient);
    // }
    // }

    /**
     * -- 上传文件到FTP
     * 
     * @param workbook
     * @param remoteFilePath
     * @throws IOException
     */
    public static CCmFile uploadFile2Ftp(MultipartFile in, String remoteFilePath) {
        Assert.notNull(in, "file cannot be null.");
        Assert.hasText(remoteFilePath, "remoteFilePath cannot be null or blank.");
        CCmFile cmFile = null;
        InputStream inputStream = null;
        FTPClient ftpClient = getFtpClient();
        // ftpClient.enterLocalPassiveMode();
        try {
            String fileName = in.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String newFileName = uuid + suffix;
            remoteFilePath += "/" + UserUtil.getUserInfo().getOpenId() + "/"
                    + new SimpleDateFormat(Constant.YYYYMMDD).format(new Date());
            if (!makeAndChangeDir(ftpClient, remoteFilePath)) {
                logger.error("创建目录失败");
                return null;
            }
            inputStream = in.getInputStream();
            ftpClient.storeFile(newFileName, inputStream);
            cmFile = new CCmFile();
            cmFile.setFileId(uuid);
            cmFile.setFileDes(suffix);
            cmFile.setFileName(fileName);
            cmFile.setFilePath(remoteFilePath);
            cmFile.setCreateName(UserUtil.getUserInfo().getOpenId());
            cmFile.setUpdateName(UserUtil.getUserInfo().getOpenId());
        } catch (IOException e) {
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e1) {
                    return null;
                }
            }
            releaseFtpClient(ftpClient);
        }
        return cmFile;
    }

    /**
     * @getFileBase64:获取文件base64编码
     * @param cCmFile 文件信息
     * @return ResultEntity
     * @date 2020年1月17日 上午11:26:55
     * @author 侯效标
     */
    public static ResultEntity getFileBase64(CCmFile cCmFile) {
        FTPClient ftpClient = getFtpClient();
        if (!changeDir(ftpClient, cCmFile.getFilePath())) {
            logger.error("切换目录失败");
            return new ResultEntity(false, MsgEnum.ERR_MSG_07.getCode());
        }
        Map<String, Object> rs = new HashMap<>();
        String result = null;
        InputStream in = null;
        String fileName = cCmFile.getFileId() + cCmFile.getFileDes();
        try {
            in = ftpClient.retrieveFileStream(fileName);
            if (in == null) {
                return new ResultEntity(false, MsgEnum.ERR_MSG_07.getCode());
            }
            byte[] bt = input2byte(in);
            result = Base64.encodeBase64String(bt);
            rs.put("FILE_INFO", cCmFile);
            rs.put("FILE", result);
        } catch (IOException e) {
            return new ResultEntity(false, MsgEnum.ERR_MSG_99.getCode());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                // 防止第二次访问in为null
                ftpClient.completePendingCommand();
            } catch (IOException e) {
                return new ResultEntity(false, MsgEnum.ERR_MSG_99.getCode());
            }
            releaseFtpClient(ftpClient);
        }
        return new ResultEntity(rs, true, MsgEnum.SUC_MSG_03.getCode());
    }

    /**
     * @downLoadFile:下载文件
     * @param cCmFile 要下载的文件信息
     * @param response 响应
     * @date 2020年1月17日 上午11:48:08
     * @author 侯效标
     */
    public static void downLoadFile(CCmFile cCmFile, HttpServletResponse response) {
        String fileName = cCmFile.getFileId() + cCmFile.getFileDes();
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        FTPClient ftpClient = getFtpClient();
        if (!changeDir(ftpClient, cCmFile.getFilePath())) {
            logger.error(MsgEnum.ERR_MSG_07.getMsg());
            return;
        }
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            ftpClient.retrieveFile(fileName, out);
        } catch (IOException e) {
            logger.error("输出流获取异常");
            return;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error("输出流关闭异常");
                    return;
                }
            }
            releaseFtpClient(ftpClient);
        }
    }

    /**
     * @input2byte:将输入流的数据转换成数组
     * @param inStream 输入流
     * @throws IOException
     * @return byte[] 数组
     * @date 2020年1月16日 下午5:09:34
     * @author 侯效标
     */
    public static byte[] input2byte(InputStream inStream) throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[inStream.available()];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        swapStream.close();
        return in2b;
    }

    /**
     * @makeAndChangeDir:创建并切换目录
     * @param ftpClient ftp客户端
     * @param path 目录 （格式：/xxxx/xxxx）
     * @return boolean
     * @date 2020年1月15日 下午2:44:52
     * @author 侯效标
     */
    public static boolean makeAndChangeDir(FTPClient ftpClient, String path) {
        /* 该部分为逐级创建 */
        String[] split = path.split("/");
        try {
            ftpClient.changeWorkingDirectory("/");
            for (String mkDir : split) {
                if (StringUtils.isEmpty(mkDir)) {
                    continue;
                }
                if (!ftpClient.changeWorkingDirectory(mkDir)) {
                    ftpClient.makeDirectory(mkDir);
                    ftpClient.changeWorkingDirectory(mkDir);
                }
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * @changeDir:切换目录
     * @param ftpClient ftp客户端
     * @param path 目录 （格式：/xxxx/xxxx）
     * @return boolean
     * @date 2020年1月17日 上午11:28:53
     * @author 侯效标
     */
    public static boolean changeDir(FTPClient ftpClient, String path) {
        /* 该部分为逐级创建 */
        String[] split = path.split("/");
        try {
            ftpClient.changeWorkingDirectory("/");
            for (String mkDir : split) {
                if (StringUtils.isEmpty(mkDir)) {
                    continue;
                }
                if (!ftpClient.changeWorkingDirectory(mkDir)) {
                    ftpClient.changeWorkingDirectory(mkDir);
                }
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * 从ftp下载excel文件
     * 
     * @param remoteFilePath
     * @param response
     * @throws IOException
     */
    public static void downloadExcel(String remoteFilePath, HttpServletResponse response) throws IOException {
        String fileName = remoteFilePath.substring(remoteFilePath.lastIndexOf("/") + 1);
        fileName = new String(fileName.getBytes("GBK"), "iso-8859-1");
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        FTPClient ftpClient = getFtpClient();
        try (InputStream in = ftpClient.retrieveFileStream(encodingPath(remoteFilePath));
                OutputStream out = response.getOutputStream()) {
            int size = 0;
            byte[] buf = new byte[10240];
            while ((size = in.read(buf)) > 0) {
                out.write(buf, 0, size);
                out.flush();
            }
        } finally {
            ftpClient.completePendingCommand();
            releaseFtpClient(ftpClient);
        }
    }

}
