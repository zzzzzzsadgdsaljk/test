package kesun.controller.system;


/**
 * Created by wph-pc on 2017/5/30.
 */
public interface IActor{
    /*授权*/
    Object setPower(String data);
    /*获取指定角色权限*/
    Object getPower(String actors);
}
