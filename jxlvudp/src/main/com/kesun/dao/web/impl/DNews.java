package kesun.dao.web.impl;

import kesun.dao.DaoSupport;
import kesun.dao.web.INews;
import kesun.entity.web.ImageNews;
import kesun.entity.web.News;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wph-pc on 2017/11/3.
 */
@Repository("dNews")
public class DNews extends DaoSupport implements INews {
    public DNews()
    {
        setMapperName("mapper.DnewsMapper");
    }
    //修改状态
    public int changeStatus(String status, List<String> ids) {
        Map<String,Object> values=new HashMap<String,Object>();
        values.put("status",status);
        values.put("sendDate",new java.util.Date());
        values.put("ids",ids);
        return getSqlSessionTemplate().update(getMapperName()+".updateStatus",values);
    }
    //删除图片
    public int delImg(String imageAddress) {
        return getSqlSessionTemplate().delete(getMapperName() + ".delImage", imageAddress);
    }
    //批量查询新闻图片
    public List<ImageNews> imageNews(String id) {
        return getSqlSessionTemplate().selectList(getMapperName() + ".selectImageNews", id);
    }
}
