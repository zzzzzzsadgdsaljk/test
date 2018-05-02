package kesun.controller.system;


import kesun.controller.IController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户控制层接口
 * Created by wph-pc on 2017/5/29.
 */
public interface IUserController extends IController {
    Object login(HttpServletRequest request, HttpServletResponse response);//用户登录
    void login_ByMobile(HttpServletRequest request, HttpServletResponse response);//移动设备用户登录
    void logout(HttpServletRequest request, HttpServletResponse response);// 用户退出
    void changePwd(HttpServletRequest request, HttpServletResponse response);//用户密码修改
    void initPassword(HttpServletRequest request, HttpServletResponse response);//用户密码修改
    void writeLog(HttpServletRequest request, HttpServletResponse response);//写入用户操作的日志
    void manageUserState(HttpServletRequest request, HttpServletResponse response);//更改成参数新的用户状态
    void setActor(HttpServletRequest request, HttpServletResponse response);//设置用户角色
    void findActor(HttpServletRequest request, HttpServletResponse response);//查找当前用户拥有的角色
    void watchOnline(HttpServletRequest request, HttpServletResponse response);//查找用户的在线或历史状态
}
