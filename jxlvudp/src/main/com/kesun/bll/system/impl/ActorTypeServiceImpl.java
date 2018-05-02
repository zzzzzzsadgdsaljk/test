package kesun.bll.system.impl;

import kesun.bll.SuperService;
import kesun.bll.system.IActorType;
import kesun.dao.IDoData;
import kesun.dao.system.impl.DActorType;
import kesun.entity.system.ActorType;
import kesun.entity.system.Organization;
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
 * Created by wph-pc on 2017/5/26.
 */
@Service("bActorType")
public class ActorTypeServiceImpl extends SuperService implements IActorType {
    @Resource(name="dActorType")
    private DActorType dao;

    public ActorTypeServiceImpl()
    {
        setModel(new ActorType());
    }

    @Override
    public Boolean isDelete()
    {
        if (isInUse())
            return false;
        else
            return true;
    }
    public List<Map<String, Object>> getLoadoutExcelColumns() {

        List<Map<String,Object>> lObjs=new ArrayList<Map<String, Object>>();
        Map<String,Object> cols=new HashMap<String, Object>();
        cols.put("id","角色类型代号");
        Map<String,Object> cols1=new HashMap<String, Object>();
        cols1.put("name","角色名称");
        lObjs.add(cols);
        lObjs.add(cols1);
        return lObjs;
    }

    public String getLoadoutExcelFileName() {
        return "角色类型表";
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
                List<ActorType> lTemp=new ArrayList<ActorType>();//创建临时操作对象
                for(Row dataRow:result)
                {
                    if (dataRow.getRowNum()==0) continue;
                    ActorType temp=new ActorType();
                    temp.setId(Tool.CreateID());
                    temp.setName(dataRow.getCell(0).getStringCellValue());
                    temp.setOrg(new Organization());
                    temp.getOrg().setId(dataRow.getCell(1).getStringCellValue());
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
}
