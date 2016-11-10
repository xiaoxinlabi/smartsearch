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
        //TODO
        if(username.equals("admin")){
            info.addStringPermission("sysmgr:visit");
            info.addStringPermission("index:delete");
            info.addRole("admin");
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        String username = token.getUsername();

        String password = new String(token.getPassword());

        if(username.equals("admin") && password.equals("teradata")){
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password, getName());
            return info;
        } else{
            throw new AuthenticationException("用户名或密码错误.");
        }
    }
}
