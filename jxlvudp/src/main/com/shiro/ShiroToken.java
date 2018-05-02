package shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by wph-pc on 2017/9/18.
 */
public class ShiroToken extends UsernamePasswordToken implements java.io.Serializable {
    private String pwd ;
    public ShiroToken(String username, String pwd) {
        super(username,pwd);
        this.pwd = pwd ;
    }
    public String getPwd() {
        return pwd;
    }


    public void setPswd(String pswd) {
        this.pwd = pswd;
    }
}
