package cn.com.systec.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by wh on 9/27/2017.
 */
public class TestShiro {

    @Test
    public void userLoginTest() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("tom", "123");
        try {
            subject.login(token);
            System.out.println("登录成功");
            if(subject.hasRole("admin")){
                System.out.println("=======有权限=======");
            }else {
                System.out.println("=======无权限======");
            }
            System.out.println("=======检查有无 create 权限========");
            subject.checkPermission("user:create");
        }catch (AuthenticationException e){
            System.out.println("================用户登录验证失败=======");
            e.printStackTrace();
        }
        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录
        subject.logout();
    }

    @Test
    public void realmsTest() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realms.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("james", "456");
        try {
            subject.login(token);
            System.out.println("==========登录成功==========");
        }catch (AuthenticationException e){
            System.out.println("=============用户登录验证失败==========");
            e.printStackTrace();
        }
        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录
        subject.logout();
    }

    @Test
    public void jdbcRealmsTest(){
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-jdbc-realm.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("lisi","456");
        try {
            subject.login(token);
            System.out.println("==========登录成功==========");
        }catch (AuthenticationException e){
            System.out.println("=============用户登录验证失败==========");
            e.printStackTrace();
        }
        Assert.assertEquals(true, subject.isAuthenticated());
        subject.logout();

    }


}
