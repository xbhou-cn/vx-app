package com.hz.love.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hz.love.common.CustomException;
import com.hz.love.feign.UserClient;
import com.hz.love.model.CCmUser;
import com.hz.love.result.ResultEntity;
import com.hz.love.service.IUserService;
import com.hz.love.validator.groups.CommonAddGroup;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    /**
     * @describe 用户管理第三方接口
     */
    @Autowired
    private UserClient userClient;
    /**
     * @describe 用户管理service
     */
    @Autowired
    private IUserService userService;
    /**
     * @describe appId
     */
    @Value("${feign.appid}")
    private String appid;
    /**
     * @describe 小程序 appSecret
     */
    @Value("${feign.app-secret}")
    private String appSecret;
    /**
     * @describe 授权类型
     */
    private static final String GRANT_TYPE = "authorization_code";

    /**
     * @getUserInfo:获取微信用户
     * @param code 登录时获取的 code
     * @return ResultEntity
     * @date 2020年1月10日 下午4:23:25
     * @author HZ
     */
    @GetMapping("/getUserInfo")
    public ResultEntity getUserInfo(String code) {
        if (StringUtils.isEmpty(code)) {
            return new ResultEntity(false, "未获取到微信用户");
        }
        logger.info("获取用户信息开始");
        ResultEntity result;
        try {
            String obj = userClient.getUserInfo(appid, appSecret, code, GRANT_TYPE);
            result = new ResultEntity(obj);
        } catch (Exception e) {
            String errMsg = "获取用户信息异常";
            if ("dev".equals(this.edition)) {
                errMsg = e.getMessage();
            }
            result = new ResultEntity(false, errMsg);
            logger.error(e.getMessage());
        }
        logger.info("获取用户信息结束");
        return result;
    }

    /**
     * @saveUser:用户登陆
     * @param user 用户信息
     * @return ResultEntity
     * @date 2020年1月10日 下午4:24:30
     * @author HZ
     */
    @PostMapping("/saveUser")
    public ResultEntity saveUser(@RequestBody Map<String, Object> user) {
        logger.info("用户登陆开始");
        ResultEntity result;
        try {
            this.validate(user, CCmUser.class, CommonAddGroup.class);
            result = userService.saveUser(user);
        } catch (CustomException ex) {
            result = new ResultEntity(false, ex.getErrorMsg());
            logger.error(ex.getMessage());
        } catch (Exception e) {
            String errMsg = "用户登陆异常";
            if ("dev".equals(this.edition)) {
                errMsg = e.getMessage();
            }
            result = new ResultEntity(false, errMsg);
            logger.error(e.getMessage());
        }
        logger.info("用户登陆结束");
        return result;
    }
}
