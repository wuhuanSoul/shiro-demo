package cn.com.systec.shiro.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by wh on 10/9/2017.
 */
public class MyRolePermissionResolver implements RolePermissionResolver {
    public Collection<Permission> resolvePermissionsInRole(String roleString) {
        if("role1".equals(roleString)){
            return Arrays.asList((Permission) new WildcardPermission("menu:*"));
        }
        return null;
    }
}
