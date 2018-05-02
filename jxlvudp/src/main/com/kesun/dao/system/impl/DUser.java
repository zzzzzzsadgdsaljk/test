package kesun.dao.system.impl;


import kesun.dao.DaoSupport;
import kesun.dao.system.IUser;
import kesun.entity.system.*;
import kesun.util.Tool;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;
import shiro.ShiroMD5;

import java.util.*;

/**
 * 用户数据访问层类
 * Created by wph-pc on 2017/5/30.
 */
@Repository("dUser")
public class DUser extends DaoSupport implements IUser {

    public DUser() {
        setMapperName("mapper.UsersMapper");
    }

    public User login(UserOnlineRecord record) {
        return null;
    }

    public int logout(UserOnlineRecord record) {
        return 0;
    }

    public int changePwd(User u, String newPwd) {
        if (u==null) return 0;//条件为空
        Map<String,Object> values=new HashMap<String, Object>();
        values.put("id",u.getId());
        values.put("password",newPwd);
        return getSqlSessionTemplate().update("mapper.UsersMapper.changePwd",values);

    }
    /*密码重置，将密码与用户账号一致*/
    public int initPassword(List<User> users) {
        if (users==null || users.size()==0) return 0;
        SqlSessionFactory sqlSessionFactory =getSqlSessionTemplate().getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);

        try
        {
            for (int i = 0; i < users.size(); i++) {
                Map<String, Object> values = new HashMap<String, Object>();
                values.put("id", users.get(i).getId());
                values.put("password", ShiroMD5.GetPwd(users.get(i).getId(),users.get(i).getId()));// MD5.GetPwd(users.get(i).getId()));
                sqlSession.insert("mapper.UsersMapper.changePwd", values);
            }
            sqlSession.commit();
        }catch (Exception e)
        {
            sqlSession.flushStatements();
            sqlSession.rollback();//回滚
            e.printStackTrace();
            return -1;
        }finally {
            sqlSession.close();
        }
        return users.size();
    }

    public int writeLog(UserLog log) {
        return 0;
    }

    public int manageUserState(User u, String newState) {
        if (u==null) return 0;//条件为空
        Map<String,Object> values=new HashMap<String, Object>();
        values.put("id",u.getId());
        values.put("status",newState);
        return getSqlSessionTemplate().update("mapper.UsersMapper.changeState",values);

    }

    public int setActor(User u, List<Actor> actors) {
        if (u==null) return 0;//条件为空
        SqlSessionFactory sqlSessionFactory =getSqlSessionTemplate().getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);

        int result=0;//返回结果
        try {
            /*删除已经存在的角色分配数据*/
            List<String> lCons=new ArrayList<String>();
            lCons.add(u.getId());
            sqlSession.delete("mapper.UsersMapper.delUserMapActor",lCons);
            result=1;
            if  (actors!=null && actors.size()>0) //开始分配角色
            { /*重新分配*/
                for (int i = 0; i < actors.size(); i++) {
                Map<String, Object> values = new HashMap<String, Object>();
                values.put("id", Tool.CreateID());
                values.put("name", "用户【" + u.getName() + "】分配角色【" + actors.get(i).getName() + "】");
                values.put("createDate", new Date());
                values.put("userID", u.getId());
                values.put("actorID", actors.get(i).getId());
                sqlSession.insert("mapper.UsersMapper.setActor", values);
            }
                result=actors.size();
            }
                sqlSession.commit();
        }catch (Exception e){
            sqlSession.flushStatements();
            sqlSession.rollback();//回滚
            e.printStackTrace();
            return -1;
        }finally{
            sqlSession.close();//关闭资源
        }
        return result;
    }

    public List<Actor> findActor(User u) {
        if (u==null) return null;//条件为空
        Map<String,Object> values=new HashMap<String, Object>();
        values.put("userID",u.getId());
        return getSqlSessionTemplate().selectList("mapper.UsersMapper.queryActorByUserID",values);
    }

    public void findActorAndOrg(User u) {
        if(u==null) return;
        try {
            List<?> lResult=doFind("findActorAndOrg",u);
            if (lResult!=null && lResult.size()>0)
            {
                if (u.getOrg()==null) u.setOrg(new Organization());
                if (u.getActors()==null) u.setActors(new ArrayList<Actor>());
                u.getActors().clear();//清空原有的角色对象
                /*数据分解到User对象各个属性中*/
                for (Object obj:lResult
                     ) {
                    Map<String,Object> temp=(Map<String,Object>)obj;
                    /*设置组织机构*/
                    if(u.getOrg().getId().equals(""))
                    {
                        u.getOrg().setId(temp.get("org").toString().trim());
                        u.getOrg().setName(temp.get("orgName").toString());
                    }
                    /*处理角色*/
                    Actor actor=new Actor();
                    actor.setId(temp.get("id").toString());
                    actor.setName(temp.get("name").toString());
                    /*设置角色类型*/
                    actor.setType(new ActorType());
                    actor.getType().setId(temp.get("type").toString());
                    actor.getType().setName(temp.get("typeName").toString());
                    u.getActors().add(actor);//添加角色
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<UserOnlineRecord> watchOnline(Map<String, Object> conValues) {
        return null;
    }
}
