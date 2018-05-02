package shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;

/**
 * Shiro处理MD5加密
 * Created by wph-pc on 2017/10/4.
 */
public class ShiroMD5 {
    /*number表示用户账号，代表加密盐值，加密old字符串，返回加密后的数据*/
    public static String GetPwd(String number,String old)
    {
        // 从shiro的session中取activeUser
        Subject subject = SecurityUtils.getSubject();
        // 取身份信息

        // 生成salt,随机生成
        SecureRandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();
        //String salt = secureRandomNumberGenerator.nextBytes().toHex();//盐值确保相同的密码，显示结果不一样
        Md5Hash md5 = new Md5Hash(old, number, 1024);
        String newMd5Password = md5.toHex();
        // 设置新密码
        return newMd5Password;
    }
}
