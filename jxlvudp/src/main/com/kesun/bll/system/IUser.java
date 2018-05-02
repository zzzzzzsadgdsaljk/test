package kesun.bll.system;


import kesun.entity.system.*;

import java.util.List;
import java.util.Map;

/**
 * 用户操作业务层接口
 * Created by wph-pc on 2017/5/29.
 */
public interface IUser {
    User login();//用户登录，如果成功，返回登录完整用户信息，否则返回null表示失败
    int logout();// 用户退出，退出后，写入退出的用户日志
    List<SystemMenu> findPower();//获取用户的系统功能权限
    int changePwd(String newPwd);//用户密码修改，大于0表示成功，其他表示失败
    int initPassword(List<User> users);//密码初始化，与账号一致
    int writeLog(UserLog log);//写入用户操作的日志
    int manageUserState(String newState);//更改成参数新的用户状态；大于0表示成功，其他表示失败
    int setActor(List<Actor> actors);//设置用户角色，首先清空原来的所有角色，在设置参数中新的角色
    List<Actor> findActor();//查找当前用户拥有的角色
    void findActorAndOrg();//获取用户的角色，角色类型和机构
    List<UserOnlineRecord> watchOnline(Map<String, Object> conValues);//查找用户的在线或历史状态
}
