package com.hz.love.common;

/**
 * <b>概述</b>：<br>
 * --参数校验--
 * <p>
 * <b>功能</b>：<br>
 * --校验参数--
 *
 * @author HZ
 */
public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 3731860511094916796L;

    public CustomException(String code, String errorMsg) {
        this.code = code;
        this.setErrorMsg(errorMsg);
    }

    public CustomException(String code) {
        this.code = code;
    }

    private String code;

    private String errorMsg;

    public String getExceptionCode() {
        return code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
