package com.hz.love.common;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;

import com.hz.love.model.CCmFile;

/**
 * <b>概述</b>：<br>
 * --共通--
 * <p>
 * <b>功能</b>：<br>
 * --共通--
 *
 * @author HZ
 */
public class Common {
    @Value("${file.path}")
    private static String FILE_PATH;

    public static boolean uploadFile(InputStream in, CCmFile file) {
        
        return false;
    }
}
