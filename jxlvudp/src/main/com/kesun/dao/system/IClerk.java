package kesun.dao.system;

import kesun.entity.system.Clerk;
import kesun.entity.system.Organization;

/**
 * Created by wph-pc on 2017/10/28.
 */
public interface IClerk {
    Clerk findClerk(Organization org);
}
