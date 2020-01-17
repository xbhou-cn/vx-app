package com.hz.love.validator;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.stereotype.Component;

import com.hz.love.em.MsgEnum;

/**
 * @author shun
 * @date 2019/4/17 13:39
 */
@Component
public class DataValidator {

    private Validator validator;

    public DataValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @SuppressWarnings("rawtypes")
    public <T> DataValidatorResult valid(T model, Class... groups) {
        if (model == null) {
            return fail(MsgEnum.ERR_MSG_02.getCode(), MsgEnum.ERR_MSG_02.getMsg());
        }
        Set<ConstraintViolation<T>> violations = validator.validate(model, groups);
        if (violations != null) {
            for (ConstraintViolation<T> violation : violations) {
                return fail("", violation.getMessageTemplate());
            }
        }
        return success();
    }

    @SuppressWarnings("rawtypes")
    public <T> DataValidatorResult valid(List<T> models, Class... groups) {
        if (models == null || models.size() <= 0) {
            return fail(MsgEnum.ERR_MSG_05.getCode(), MsgEnum.ERR_MSG_05.getMsg());
        }
        for (T t : models) {
            if (t == null) {
                return fail(MsgEnum.ERR_MSG_04.getCode(), MsgEnum.ERR_MSG_04.getMsg());
            }
            Set<ConstraintViolation<T>> violations = validator.validate(t, groups);
            if (violations != null) {
                for (ConstraintViolation<T> violation : violations) {
                    return fail("", violation.getMessageTemplate());
                }
            }
        }
        return success();
    }

    @SuppressWarnings("rawtypes")
    public <T> DataValidatorResult valid(Map<String, Object> models, Class<T> t, Class... groups) {
        if (models == null || models.isEmpty()) {
            return fail(MsgEnum.ERR_MSG_02.getCode(), MsgEnum.ERR_MSG_02.getMsg());
        }
        Set<Entry<String, Object>> entrySet = models.entrySet();
        for (Entry<String, Object> entry : entrySet) {
            String key = entry.getKey();
            Object value = entry.getValue();
            Set<ConstraintViolation<T>> validateValue = validator.validateValue(t, key, value, groups);
            for (ConstraintViolation<T> violation : validateValue) {
                return fail("", violation.getMessageTemplate());
            }
        }
        return success();
    }

    private DataValidatorResult success() {
        return new DataValidatorResult(true, null, null);
    }

    private DataValidatorResult fail(String errorCode, String errorMessage) {
        return new DataValidatorResult(false, errorCode, errorMessage);
    }
}
