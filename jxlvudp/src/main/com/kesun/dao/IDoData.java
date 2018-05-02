package kesun.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 常规数据操作接口
 * Created by wph-pc on 2017/10/9.
 */
public interface IDoData {
    /*新增操作*/
    int save(Object obj);
    /*批量新增操作*/
    int batchSave(List objs);
    /*批量修改操作*/
    int batchUpdate(List objs);
    /*更新操作*/
    int update(Object obj);
    /*删除操作*/
    int delete(Object obj);
    /*批量删除操作*/
    int batchDelete(List objs);
    /*查找当前对象*/
    Object getMe(Object obj);
    /*查找对象ID的数据是否在使用中*/
    Boolean objectInUse(String id);
    /*查找符合条件values的对象,以List的Map形式返回*/
    List<Map<String,Object>> findForMap(Map values);
    /*查找符合条件values的对象,以List的对象形式返回*/
    List<?> find(Map values);
    /*查找符合条件values的数据*/
    List<Map<String,Object>> findByPage(Map conValues);
    /*查找符合条件values的行数*/
    int getRowsCount(Map values);
}
