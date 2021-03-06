package com.hz.love.em;

/**
 * <b>概述</b>：<br>
 * --信息--
 * <p>
 * <b>功能</b>：<br>
 * --信息枚举--
 *
 * @author 侯效标
 */
public enum MsgEnum {
    /**
     * @describe 参数转换异常
     */
    ERR_MSG_01("err_msg_01", "参数转换异常"),
    /**
     * @describe 接受对象为空
     */
    ERR_MSG_02("err_msg_02", "接受对象为空"),
    /**
     * @describe 参数校验失败
     */
    ERR_MSG_03("err_msg_03", "参数校验失败"),
    /**
     * @describe 接受对象为空
     */
    ERR_MSG_04("err_msg_04", "接受对象为空"),
    /**
     * @describe 接受集合为空
     */
    ERR_MSG_05("err_msg_05", "接受集合为空"),
    /**
     * @describe 文件上传服务器失败
     */
    ERR_MSG_06("err_msg_06", "文件上传服务器失败"),
    /**
     * @describe 文件不存在
     */
    ERR_MSG_07("err_msg_07", "文件不存在"),
    /**
     * @describe 文件下载失败
     */
    ERR_MSG_08("err_msg_08", "文件下载失败"),
    /**
     * @describe 系统异常
     */
    ERR_MSG_99("err_msg_99", "系统异常"),
    /**
     * @describe 登陆成功
     */
    SUC_MSG_01("suc_msg_01", "登陆成功"),
    /**
     * @describe 文件上传成功
     */
    SUC_MSG_02("suc_msg_02", "文件上传成功"),
    /**
     * @describe 文件下载成功
     */
    SUC_MSG_03("suc_msg_03", "文件下载成功"),
    /**
     * @describe 文件不存在，请刷新重试
     */
    INF_MSG_01("inf_msg_01", "文件不存在，请刷新重试"),
    /**
     * @describe 请选择要下载的文件
     */
    INF_MSG_02("inf_msg_02", "请选择要下载的文件");

    /**
     * @describe 编码
     */
    private String code;
    /**
     * @describe 信息
     */
    private String msg;

    private MsgEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
