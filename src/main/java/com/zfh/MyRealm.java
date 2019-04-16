package com.zfh;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class MyRealm extends AuthorizingRealm {

//    授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
//        根据主题查询角色  根据角色查权限
//        主题    角色      权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRole("super");
        authorizationInfo.addStringPermission("user:select");
        authorizationInfo.addStringPermission("user:add");
        authorizationInfo.addStringPermission("user:update");
        System.out.println("---------------------------");
        return authorizationInfo;
    }
//    认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String principal = (String) authenticationToken.getPrincipal();
//        调用数据库
//        根据username（principal）查询user对象

        AuthenticationInfo authenticationInfo = null;
        if("zhangsan".equals(principal)){
            authenticationInfo = new SimpleAuthenticationInfo("zhangsan","68609b8b64988c0f4def093eaa025e05",ByteSource.Util.bytes("abcd"),getName());
        }
        return authenticationInfo;
    }
}
