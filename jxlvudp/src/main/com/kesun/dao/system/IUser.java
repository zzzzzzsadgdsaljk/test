package kesun.dao.system;

import kesun.entity.system.Actor;
import kesun.entity.system.User;
import kesun.entity.system.UserLog;
import kesun.entity.system.UserOnlineRecord;

import java.util.List;
import java.util.Map;

/**
 * 用户数据访问层接口
 * Created by wph-pc on 2017/5/30.
 */
public interface IUser{
    public User login(UserOnlineRecord record);//用户登录，如果成功，并写入用户日志，返回登录完整用户信息，否则返回null表示失败
    public int logout(UserOnlineRecord record);// 用户退出，退出后，写入退出的用户日志和在线日志
    public int changePwd(User u, String newPwd);//用户密码修改，大于0表示成功，其他表示失败
    public int initPassword(List<User> users);//密码初始化，与账号一致
    public int writeLog(UserLog log);//写入用户操作的日志
    public int manageUserState(User u, String newState);//更改成参数新的用户状态；大于0表示成功，其他表示失败
    /*分配用户角色，成功返回大于0的整数，其他表示失败*/
    public int setActor(User u, List<Actor> actors);
    List<Actor> findActor(User u);//查找当前用户拥有的角色
    void findActorAndOrg(User u);//查找用户角色和组织机构,数据写入User参数中
    public List<UserOnlineRecord> watchOnline(Map<String, Object> conValues);//查找用户的在线或历史状态
}
