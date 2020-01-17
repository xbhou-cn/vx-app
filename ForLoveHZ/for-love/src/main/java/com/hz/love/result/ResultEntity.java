package com.hz.love.result;

import java.io.Serializable;

public class ResultEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * @实体对象
     */
    private Object obj;
    /**
     * @是否异常
     */
    private boolean successful = true;
    /**
     * @错误信息
     */
    private String msg;

    public ResultEntity() {
        super();
    }

    public ResultEntity(Object obj) {
        super();
        this.obj = obj;
    }

    public ResultEntity(boolean successful, String msg) {
        super();
        this.successful = successful;
        this.msg = msg;
    }

    public ResultEntity(Object obj, boolean successful, String msg) {
        super();
        this.obj = obj;
        this.successful = successful;
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
