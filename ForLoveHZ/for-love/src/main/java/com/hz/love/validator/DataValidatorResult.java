package com.hz.love.validator;

/**
 * @author shun
 * @date 2019/4/17 13:44
 */
public class DataValidatorResult {
    private Boolean validatorIsSuccess = false;

    private String errorCode;

    private String errorMessage;
    public DataValidatorResult(Boolean validatorIsSuccess,String errorCode,String errorMessage){
        super();
        this.validatorIsSuccess = validatorIsSuccess;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public Boolean getValidatorIsSuccess() {
        return validatorIsSuccess;
    }

    public void setValidatorIsSuccess(Boolean validatorIsSuccess) {
        this.validatorIsSuccess = validatorIsSuccess;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
