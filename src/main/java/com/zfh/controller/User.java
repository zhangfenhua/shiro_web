package com.zfh.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class User {
    /**
     * 2019.4.16
     * shiro测试不能用测试类，因为有方法重写，测试有可能扫描不到重写后的方法
     * 必须启动web服务器
     * @param name  用户名
     * @param pwd   密码
     * @return  返回的json  测试用
     */
    @RequestMapping("login")
    public String login(String name, String pwd) {
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(name,pwd);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(usernamePasswordToken);
            System.out.println("验证完成");
            return "ok";
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("异常");
            return "用户名异常";
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            return "密码异常";
        }
    }
}
