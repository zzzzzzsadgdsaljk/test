package kesun.dao.web.impl;


import kesun.dao.DaoSupport;
import kesun.dao.web.IAnnouncement;
import org.springframework.stereotype.Repository;

/**
 * Created by heying on 2017/10/25.
 */
@Repository("dAnnouncement")
public class DAnnouncement extends DaoSupport implements IAnnouncement {
 public DAnnouncement(){
     setMapperName("mapper.AnnouncementMapper");
 }
    public Boolean objectInUse(String id)
    {
        return false;
    }
}
