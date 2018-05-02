package kesun.dao.system.impl;

import kesun.dao.DaoSupport;
import kesun.dao.system.IActor;
import kesun.entity.AbsSuperObject;
import kesun.entity.system.Actor;
import kesun.entity.system.SystemMenu;
import kesun.util.Tool;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by wph-pc on 2017/5/30.
 */
@Repository("dActor")
public class DActor extends DaoSupport implements IActor {
    public DActor() {
        setMapperName("mapper.ActorMapper");
    }

    public boolean isUse(AbsSuperObject actor) {
        if (actor==null) return false;//条件不符合
        List<Map<String,Object>> lResult= getSqlSessionTemplate().selectList(getMapperName()+".findActorInUse",actor.getId());
        if (lResult!=null && lResult.size()>0)
            return true;
        else
           return false;
    }

    public int setFunctionPower(List<Actor> actors, List<SystemMenu> powers) {
        if (actors==null || actors.size()==0) return 0;//没有合适的授权角色
        SqlSessionFactory sqlSessionFactory = getSqlSessionTemplate().getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        try {
            /*清空原来的授权*/
            List<String> lIDs=new ArrayList<String>();
            for(int i=0;i<actors.size();i++)
            {
                lIDs.add(actors.get(i).getId());
            }
            Map<String,Object> tempActors=new HashMap<String, Object>();
            tempActors.put("ids",lIDs);
            sqlSession.delete(getMapperName()+".removePower", tempActors);
            /*授权*/
            if (powers != null && powers.size()>0) {
                for (int i = 0; i < actors.size(); i++) {
                    Map<String,Object> params=new HashMap<String, Object>();
                    params.put("name","角色授权");
                    params.put("createDate",new Date());
                    params.put("actorID",actors.get(i).getId());
                    for(int j=0;j<powers.size();j++)
                    {
                        params.put("id", Tool.CreateID());
                        params.put("systemMenuID",powers.get(j).getId());
                        sqlSession.insert(getMapperName()+".setPower", params);
                    }
                }
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
        return 1;
    }

    public List<SystemMenu> getFunctionPower(List<Actor> actors) {
        if (actors==null || actors.size()==0) return null;//没有合适的授权角色
        /*清空原来的授权*/
        List<String> lIDs=new ArrayList<String>();
        for(int i=0;i<actors.size();i++)
        {
            lIDs.add(actors.get(i).getId());
        }
        Map<String,Object> tempActors=new HashMap<String, Object>();
        tempActors.put("ids",lIDs);
        return getSqlSessionTemplate().selectList(getMapperName()+".getPower",tempActors);
    }
}
