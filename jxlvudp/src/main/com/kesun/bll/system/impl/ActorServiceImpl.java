package kesun.bll.system.impl;

import kesun.bll.SuperService;
import kesun.bll.system.IActor;
import kesun.dao.IDoData;
import kesun.dao.system.impl.DActor;
import kesun.entity.AbsTreeNode;
import kesun.entity.system.Actor;
import kesun.entity.system.SystemMenu;
import kesun.util.ExcelUtil;
import kesun.util.Tool;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wph-pc on 2017/5/29.
 */
@Service("bActor")
public class ActorServiceImpl extends SuperService implements IActor {
    @Resource(name="dActor")
    private DActor dao;

    public ActorServiceImpl()
    {
        setModel(new Actor());
    }
    public AbsTreeNode getParent() {
        return null;
    }

    @Override
    public  Boolean isDelete()
    {
        Map<String,Object> cons=new HashMap<String, Object>();
        cons.put("parent",getModel().getId());
        List<?> lObjs=getModel().getId().trim().equals("")?null:find(cons);
        if (lObjs!=null && lObjs.size()>0) return false;//存在子角色

        if (isUse()) return  false;//当前角色在使用中
        return true;
    }
    public List getSons() {
        if (dao==null) return  null;
        Map<String,Object> values=new HashMap<String, Object>();
        values.put("parent",getModel().getId());
        return find(values);
    }

    public List<Map<String, Object>> getLoadoutExcelColumns() {
        return null;
    }

    public String getLoadoutExcelFileName() {
        return "系统角色信息表";
    }

    public IDoData getDAO() {
        return dao;
    }

    public List getSaveAll(String filePath) {

        File file=new File(filePath);
        if (file.exists()==false) return  null;
        ExcelUtil excel=new ExcelUtil();
        try {
            List<Row> result=excel.readExcel(file.getPath());//读取Excel内容
            if (result!=null && result.size()>0)
            {
                List<Actor> lTemp=new ArrayList<Actor>();//创建临时操作对象
                for(Row dataRow:result)
                {
                    if (dataRow.getRowNum()==0) continue;
                    Actor temp=new Actor();
                    temp.setId(Tool.CreateID());
                    temp.setName(dataRow.getCell(1).getStringCellValue());
                    temp.setStatus(dataRow.getCell(2).getStringCellValue());
                    temp.setCreateDate(new java.util.Date());
                    lTemp.add(temp);
                }
                return lTemp;
            }
            else
                return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isUse() {
        return dao.isUse(getModel());
    }

    public int setFunctionPower(List<Actor> actors, List<SystemMenu> powers) {
        return dao.setFunctionPower(actors,powers);
    }

    public List<SystemMenu> getFunctionPower(List<Actor> actors) {
        return dao.getFunctionPower(actors);
    }
}
