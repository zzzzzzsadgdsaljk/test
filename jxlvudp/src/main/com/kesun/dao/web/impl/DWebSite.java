package kesun.dao.web.impl;


import kesun.dao.DaoSupport;
import kesun.dao.web.IWebSite;
import org.springframework.stereotype.Repository;

/**
 * Created by wph-pc on 2017/5/30.
 */
@Repository("dWebSite")
public class DWebSite extends DaoSupport implements IWebSite {
    public DWebSite(){
        setMapperName("mapper.WebSiteMapper");
    }
}
