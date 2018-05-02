package kesun.entity.system;

import kesun.entity.AbsSuperObject;

import java.util.List;

/**
 * 用户实体类
 * Created by wph-pc on 2017/5/29.
 */
public class User extends AbsSuperObject {
    private String number="";//账号
    private String nickName="";//昵称
    private String password="";//登录密码
    private List<Actor> actors=null;//用户拥有的角色
    private Organization org=null;//所在的机构
    private String type=UserType.CUSTOMER;//用户类型
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
