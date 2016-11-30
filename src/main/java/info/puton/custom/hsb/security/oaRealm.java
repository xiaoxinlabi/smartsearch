package info.puton.custom.hsb.security;

import info.puton.custom.hsb.mocker.LoginMocker;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.io.IOException;

/**
 * Created by taoyang on 2016/10/27.
 */
public class oaRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission("sysmgr:visit");
        info.addRole("user");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        String username = token.getUsername();

        String password = new String(token.getPassword());

        LoginMocker loginMocker = new LoginMocker();

        try {
            if(loginMocker.valid(username, password)){
                SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password, getName());
                return info;
            } else{
                throw new AuthenticationException("OA用户名或密码错误！");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new AuthenticationException("OA验证服务异常！");
        }
    }
}
