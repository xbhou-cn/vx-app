package com.hz.love.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <b>概述</b>：<br>
 * 第三方接口调用
 * <p>
 * <b>功能</b>：<br>
 * 第三方接口service
 *
 * @author HZ
 */
@FeignClient(name = "userClient", url = "${feign.user-client}")
public interface UserClient {
    /**
     * @getUserInfo:
     * @param appid 小程序 appId
     * @param secret 小程序 appSecret
     * @param js_code 登录时获取的 code
     * @param grant_type 授权类型，此处只需填写 authorization_code
     * @return ResultEntity
     * @date 2019年12月15日 上午1:26:40
     * @author HZ
     */
    @GetMapping(value = "/sns/jscode2session")
    String getUserInfo(@RequestParam("appid") String appid, @RequestParam("secret") String secret,
            @RequestParam("js_code") String js_code, @RequestParam("grant_type") String grant_type);

}
