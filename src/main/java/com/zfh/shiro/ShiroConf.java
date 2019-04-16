package com.zfh.shiro;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConf {
    /**
     * @param securityManager 安全管理  这个对象可以创建，但是可以交由工厂管理 写在形参里边  工厂自动注入
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
//      设置登录跳转的路径  shiroFilterFactoryBean.setLoginUrl();
        Map<String, String> map = new HashMap<String, String>();
        // AnonymousFilter 匿名过滤器 anon
        // FormAuthenticationFilter 认证过滤器 authc
        map.put("/**", "authc");
        map.put("/index.jsp", "anon");
        map.put("/user/login", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager getSecurityManager(MyReadm myReadm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myReadm);
        return securityManager;
    }

    @Bean
    public MyReadm getMyRealm(CredentialsMatcher credentialsMatcher, CacheManager cacheManager) {
        MyReadm myRealm = new MyReadm();
        myRealm.setCredentialsMatcher(credentialsMatcher);
        myRealm.setCacheManager(cacheManager);
        return myRealm;
    }

    /**
     * 凭据匹配器 也就是密码加密方式，和加密的次数
     *
     * @return
     */
    @Bean
    public CredentialsMatcher getCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(1024);
        return credentialsMatcher;
    }

    /**
     * 开启shiro缓存，减少对数据库的访问
     *
     * @return
     */
    @Bean
    public CacheManager getCacheManager() {
        CacheManager cacheManager = new EhCacheManager();
        return cacheManager;
    }

}
