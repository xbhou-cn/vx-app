package com.hz.love.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.hz.love.common.CustomException;
import com.hz.love.em.MsgEnum;
import com.hz.love.validator.DataValidator;
import com.hz.love.validator.DataValidatorResult;

/**
 * <b>概述</b>：<br>
 * --controller--
 * <p>
 * <b>功能</b>：<br>
 * --BaseController--
 *
 * @author 侯效标
 */
public class BaseController {
    protected final static Logger logger = LoggerFactory.getLogger(BaseController.class);
    @Value("${edition}")
    protected String edition;
    @Autowired
    private DataValidator dataValidator;

    @SuppressWarnings("rawtypes")
    protected <T> void validate(T model, Class... groups) {
        DataValidatorResult validatorResult = dataValidator.valid(model, groups);
        if (!validatorResult.getValidatorIsSuccess()) {
            throw new CustomException(MsgEnum.ERR_MSG_03.getCode(), validatorResult.getErrorMessage());
        }
    }

    @SuppressWarnings("rawtypes")
    protected <T> void validate(Map<String, Object> model, Class<T> t, Class... groups) {
        DataValidatorResult validatorResult = dataValidator.valid(model, t, groups);
        if (!validatorResult.getValidatorIsSuccess()) {
            throw new CustomException(MsgEnum.ERR_MSG_03.getCode(), validatorResult.getErrorMessage());
        }
    }
}
