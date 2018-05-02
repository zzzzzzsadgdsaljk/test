package kesun.dao.system.impl;

import kesun.dao.DaoSupport;
import kesun.dao.system.IClerk;
import kesun.entity.system.Clerk;
import kesun.entity.system.Organization;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wph-pc on 2017/10/28.
 */
@Repository("dClerk")
public class DClerk extends DaoSupport implements IClerk {
    public DClerk()
    {
        setMapperName("mapper.ClerkMapper");
    }
    @Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;




    public int batchSave(List objs) {
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        try {
            if (objs != null) {
                for (int i = 0; i < objs.size(); i++) {
                    sqlSession.insert("mapper.ClerkMapper.insert", objs.get(i));
                }
                sqlSession.commit();
            }
        }catch (Exception e){
            sqlSession.flushStatements();
            sqlSession.rollback();//回滚
            e.printStackTrace();
            return -1;
        }finally{
            sqlSession.close();//关闭资源
        }
        return objs.size();
    }


    public Clerk findClerk(Organization org) {
        try {
            Object obj=doFindObject("findClerk",org);
            if (obj!=null || obj instanceof Clerk)
                return (Clerk) obj;
            else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
