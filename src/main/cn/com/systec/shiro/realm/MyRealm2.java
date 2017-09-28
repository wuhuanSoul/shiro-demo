package cn.com.systec.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * Created by wh on 9/28/2017.
 */
public class MyRealm2 implements Realm {
    public String getName() {
        return "MyRealm2";
    }

    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken instanceof UsernamePasswordToken;
    }

    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        String password = new String((char[])authenticationToken.getCredentials());
        if(!"james".equals(username)){
            System.out.println("MyRealm2=========用户名错误========");
            throw new UnknownAccountException();
        }
        if(!"456".equals(password)){
            System.out.println("MyRealm2=========密码错误========");
            throw new IncorrectCredentialsException();
        }
        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
