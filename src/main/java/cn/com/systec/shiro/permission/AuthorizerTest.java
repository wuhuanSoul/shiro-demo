package cn.com.systec.shiro.permission;

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
public class AuthorizerTest {
    private void login(String configFile, String username, String password){
        // 1、获取SecurityManager工厂,此处使用Init配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);
        //2、    得到SecurityManager实例 并绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        //2、得到Subject及创建用户名、密码身份验证（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        subject.login(token);
    }

    private Subject subject(){
       return SecurityUtils.getSubject();
    }

    @Test
    public void testPrimitted(){
        login("classpath:shiro-authorizer.ini","zhang", "123");
        //判断拥有权限
        Assert.assertTrue(subject().isPermitted("user1:update"));
        Assert.assertTrue(subject().isPermitted("user1:update"));
        //通过二进制位的方式表示权限
        Assert.assertTrue(subject().isPermitted("+user1+2"));   //新增权限
        Assert.assertTrue(subject().isPermitted("+user1+8"));   //查看权限
        Assert.assertTrue(subject().isPermitted("user1+10"));   //新增及查看
        Assert.assertTrue(subject().isPermitted("user1+4"));    //没有删除权限
        Assert.assertTrue(subject().isPermitted("menu:view"));  //通过MyRolePermissionResolver解析得到的权限
    }
}
