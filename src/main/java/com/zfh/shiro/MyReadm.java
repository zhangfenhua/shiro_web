package com.zfh.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class MyReadm extends AuthorizingRealm {
    /**
     * 认证
     *
     * @param authenticationToken 令牌，相当于一个user用户
     * @return
     * @throws AuthenticationException
     */
    @Override
    public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String tokenPrincipal = (String) authenticationToken.getPrincipal();
//        然后调用数据库通过username查询出用户对象

        AuthenticationInfo authenticationInfo = null;
        if ("zfh".equals(tokenPrincipal)) {
//          验证            username    加密后数据库查询的密码   盐                        类名
            authenticationInfo = new SimpleAuthenticationInfo(
//                        username    加密后数据库查询的密码         盐                        类名
                    "zfh", "e60ccf161d6bb276a8d0adcb8920be78", ByteSource.Util.bytes("abcd"), getName());
        }
        System.out.println("认证");
        return authenticationInfo;
    }

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权");
        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        /**
         * 然后根据主体查询角色，根据角色查询权限
         * 主体   角色   权限
         */
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole("super");
        simpleAuthorizationInfo.addStringPermission("user:select");
        simpleAuthorizationInfo.addStringPermission("user:add");
        simpleAuthorizationInfo.addStringPermission("user:update");
        System.out.println("-------------数据库查询的次数，通过shiro标签，--------------");
        return simpleAuthorizationInfo;
    }


}
