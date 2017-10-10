package cn.com.systec.shiro.chapter4;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by wh on 10/9/2017.
 */
public class ConfigurationCreateTest {

    @Test
    public void configCreateTest() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-config.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        subject.login(token);
        System.out.println("========登录成功=========");
        Assert.assertTrue(subject.isAuthenticated());
    }

}
