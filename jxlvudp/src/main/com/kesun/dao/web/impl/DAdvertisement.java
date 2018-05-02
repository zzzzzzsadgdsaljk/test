package kesun.dao.web.impl;

import kesun.dao.DaoSupport;
import org.springframework.stereotype.Repository;

/**
 * Created by wph-pc on 2017/5/30.
 */
@Repository("dAdvertisement")
public class DAdvertisement extends DaoSupport {
    public DAdvertisement(){
        setMapperName("mapper.AdvertisementMapper");
    }
}
