package shiro;


import kesun.bll.system.impl.UserServiceImpl;
import kesun.entity.system.Actor;
import kesun.entity.system.SystemMenu;
import kesun.entity.system.User;
import kesun.util.SpringContextUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by wph-pc on 2017/9/18.
 */
public class HandleRealm extends AuthorizingRealm {

    private List<String> getPowers(String userID)
    {
        UserServiceImpl userBll=(UserServiceImpl) SpringContextUtil.getBean("bUser");

        User temp=new User();
        temp.setId(userID);;
        temp.setNumber(userID);
        userBll.setModel(temp);

        List<SystemMenu> lPowers=userBll.findPower();//获取权限；
        if (lPowers==null || lPowers.size()==0) return  null;
        /*权限转换*/
        List<String> powers=new ArrayList<String>();
        for(SystemMenu sm:lPowers) {
            powers.add(sm.getAddress());
        }
        return powers;
    }
    /*获取当前用户的角色*/
    private Set<String> getActors(String userID)
    {
        UserServiceImpl userBll=(UserServiceImpl)SpringContextUtil.getBean("bUser");

        User temp=new User();
        temp.setId(userID);;
        temp.setNumber(userID);
        userBll.setModel(temp);

        List<Actor> lActors=userBll.findActor();//获取权限；
        if (lActors==null || lActors.size()==0) return  null;
        /*权限转换*/
        Set<String> actors=new HashSet<String>();
        for(Actor a:lActors) {
            actors.add(a.getId());
        }
        return actors;
    }

    public HandleRealm()
    {
        super();
    }
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //根据自己系统规则的需要编写获取授权信息，这里为了快速入门只获取了用户对应角色的资源url信息
        String username = (String) principalCollection.fromRealm(getName()).iterator().next();
        if (username != null) {
            List<String> powers =getPowers(username);//应业务层读取权限
            if (powers != null && !powers.isEmpty()) {
                SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
                info.addStringPermissions(powers);//加入权限
                /*设置角色，从数据库中读取，案例是静态数据*/
                Set<String> roles=getActors(username);
                info.setRoles(roles);
                return info;
            }
        }
        return null;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        ShiroToken token = (ShiroToken) authenticationToken;
        // 通过表单接收的用户名
        String username = token.getUsername();
        UserServiceImpl userBll=(UserServiceImpl)SpringContextUtil.getBean("bUser");
        User user=new User();
        user.setId(token.getUsername());
        user.setNumber(token.getUsername());
        user.setPassword(token.getPwd());
        userBll.setModel(user);
        user=userBll.login();

        if(null == user){
            throw new AccountException("帐号或密码不正确！");
        }else if(user.getStatus().trim().equals("注销")){
            throw new DisabledAccountException("帐号已经禁止登录！");
        }else{
            //更新登录时间 last login time
            //user.(new Date());
            //userService.updateByPrimaryKeySelective(user);

        }
        SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(token.getUsername(),token.getPwd(), getName());
        return info;
    }

    /**
     * 清空当前用户权限信息
     */
    public  void clearCachedAuthorizationInfo() {
        PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
        SimplePrincipalCollection principals = new SimplePrincipalCollection(
                principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }
    /**
     * 指定principalCollection 清除
     */
    public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(
                principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }
}
