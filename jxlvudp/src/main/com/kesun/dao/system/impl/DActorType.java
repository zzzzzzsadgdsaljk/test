package kesun.dao.system.impl;

import kesun.dao.DaoSupport;
import kesun.dao.system.IActorType;
import org.springframework.stereotype.Repository;

/**
 * Created by wph-pc on 2017/5/30.
 */
@Repository("dActorType")
public class DActorType extends DaoSupport implements IActorType {
    public DActorType() {
        setMapperName("mapper.ActorTypeMapper");
    }
}
