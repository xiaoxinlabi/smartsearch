package info.puton.product.smartsearch.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Created by taoyang on 2016/10/27.
 */
public class myRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String username = String.valueOf(principalCollection.getPrimaryPrincipal());
        if(username.equals("admin")){
            info.addStringPermission("sysmgr:visit");
            info.addStringPermission("index:delete");
            info.addStringPermission("file:manage");
            info.addStringPermission("address:manage");
            info.addStringPermission("website:manage");
            info.addStringPermission("index:manage");
            info.addStringPermission("cache:manage");
            info.addRole("admin");
        } else {
            info.addStringPermission("sysmgr:visit");
            info.addStringPermission("file:manage");
            info.addRole("user");
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        String username = token.getUsername();

        String password = new String(token.getPassword());

        if(username.equals("admin") && password.equals("admin")){
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password, getName());
            return info;
        } else if(username.equals("user1") && password.equals("user1")){
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password, getName());
            return info;
        } else if(username.equals("user2") && password.equals("user2")){
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password, getName());
            return info;
        } else{
            throw new AuthenticationException("智搜用户名或密码错误！");
        }
    }
}
