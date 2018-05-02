package kesun.bll.system.impl;

import kesun.bll.SuperService;
import kesun.bll.system.ISystemMenu;
import kesun.dao.IDoData;
import kesun.dao.system.impl.DSystemMenu;
import kesun.entity.system.SystemMenu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wph-pc on 2017/9/23.
 */
@Service("bSystemMenu")
public class SystemMenuServiceImpl extends SuperService implements ISystemMenu {
    @Resource(name="dSystemMenu")
    private DSystemMenu dao;
    public SystemMenuServiceImpl(){
        setModel(new SystemMenu());
    }
    public List<Map<String, Object>> getLoadoutExcelColumns() {
        return null;
    }
    @Override
    public  Boolean isDelete()
    {
        Map<String,Object> cons=new HashMap<String, Object>();
        cons.put("parent",getModel().getId());
        List<?> lObjs=find(cons);
        if (lObjs!=null && lObjs.size()>0) return false;//存在子权限

        if (isInUse()) return  false;//当前权限在使用中
        return true;
    }
    public String getLoadoutExcelFileName() {
        return "系统功能权限菜单";
    }

    public IDoData getDAO() {
        return dao;
    }

    public List getSaveAll(String filePath) {
        return null;
    }
}
