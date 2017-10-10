package cn.com.systec.shiro.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * Created by wh on 10/9/2017.
 */
public class BitAndWildPermissionResolver implements PermissionResolver{


    public Permission resolvePermission(String permissionString) {
       if(permissionString.startsWith("+")){
           return new BitPermission(permissionString);
       }
       return new WildcardPermission(permissionString);
    }
}
