package com.loto.mall.user.controller;

import com.loto.mall.api.user.model.UserInfo;
import com.loto.mall.user.service.UserInfoService;
import com.loto.mall.util.common.RespResult;
import com.loto.mall.util.security.JwtToken;
import com.loto.mall.util.security.MD5;
import com.loto.mall.utils.IPUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-30 19:23<p>
 * PageName：UserInfoController.java<p>
 * Function：
 */

@RestController
@RequestMapping(value = "/user/info")
@Api(value = "UserInfoController", tags = "用户信息")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public RespResult<String> login(@RequestParam(value = "userName") String userName, @RequestParam(value = "pwd") String pwd, HttpServletRequest request) throws Exception {
        // 查询用户
        UserInfo userinfo = userInfoService.getById(userName);

        // 匹配
        if (userinfo != null && pwd.equals(userinfo.getPassword())) {
            // 封装令牌
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("user_name", userinfo.getUserName());
            dataMap.put("name", userinfo.getName());
            dataMap.put("roles_id", userinfo.getRolesId());

            // 获取IP
            String ip = IPUtils.getIpAddr(request);
            dataMap.put("ip", MD5.md5(ip));

            // 创建令牌
            String token = JwtToken.createToken(dataMap);
            return RespResult.ok(token);
        }
        return RespResult.error("账号或者密码不对");
    }

}
