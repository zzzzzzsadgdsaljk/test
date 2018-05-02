package kesun.bll.system.impl;

import kesun.bll.SuperService;
import kesun.bll.system.IClerk;
import kesun.dao.IDoData;
import kesun.dao.system.impl.DClerk;
import kesun.entity.system.Clerk;
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
 * Created by wph-pc on 2017/10/28.
 */
@Service("bClerk")
public class ClerkServiceImpl extends SuperService implements IClerk {
    @Resource(name="dClerk")
    private DClerk dao;
    public ClerkServiceImpl()
    {
        setModel(new Clerk());
    }
    public List<Map<String, Object>> getLoadoutExcelColumns() {
        List<Map<String,Object>> lObjs=new ArrayList<Map<String, Object>>();
        Map<String,Object> cols=new HashMap<String, Object>();
        cols.put("id","员工工号");
        Map<String,Object> cols1=new HashMap<String, Object>();
        cols1.put("xm","姓名");
        Map<String,Object> cols2=new HashMap<String, Object>();
        cols2.put("xbm","性别");
        Map<String,Object> cols3=new HashMap<String, Object>();
        cols3.put("csrq","出生日期");
        Map<String,Object> cols4=new HashMap<String, Object>();
        cols4.put("csdm","出生地");
        Map<String,Object> cols5=new HashMap<String, Object>();
        cols5.put("sfzjlxm","证件类型");
        Map<String,Object> cols6=new HashMap<String, Object>();
        cols6.put("sfzjh","证件号");
        Map<String,Object> cols7=new HashMap<String, Object>();
        cols7.put("jg","籍贯");
        Map<String,Object> cols8=new HashMap<String, Object>();
        cols8.put("orgName","组织机构");
        Map<String,Object> cols9=new HashMap<String, Object>();
        cols9.put("status","状态");

        lObjs.add(cols);
        lObjs.add(cols1);
        lObjs.add(cols2);
        lObjs.add(cols3);
        lObjs.add(cols4);
        lObjs.add(cols5);
        lObjs.add(cols6);
        lObjs.add(cols7);
        lObjs.add(cols8);
        lObjs.add(cols9);

        return lObjs;
    }

    public String getLoadoutExcelFileName() {
        return "公司职员基本信息表";
    }



    public IDoData getDAO() {
        return dao;
    }

    public List getSaveAll(String filePath) {
        File file=new File(filePath);


        ExcelUtil excel=new ExcelUtil();
        try {
            List<Row> result=excel.readExcel(file.getPath());//读取Excel内容
            if (result!=null && result.size()>0)
            {
                List<Clerk> lTemp= new ArrayList<Clerk>();//创建临时操作对象
                for(Row dataRow:result)
                {
                    if (dataRow.getRowNum()==0) continue;
                    Clerk temp=new  Clerk();
                    Organization org=new Organization();
                    org.setId(dataRow.getCell(3).getStringCellValue());

                    temp.setId(dataRow.getCell(1).getStringCellValue());

                    temp.setXm(dataRow.getCell(2).getStringCellValue());
                    temp.setOrg(org);
                    temp.setXbm(dataRow.getCell(4).getStringCellValue());
                    temp.setYwxm(dataRow.getCell(5).getStringCellValue());
                    temp.setXmpy(dataRow.getCell(6).getStringCellValue());
                    temp.setCym(dataRow.getCell(7).getStringCellValue());
                    temp.setCsrq(dataRow.getCell(8).getDateCellValue());
                    temp.setCsdm(dataRow.getCell(9).getStringCellValue());
                    temp.setJg(dataRow.getCell(10).getStringCellValue());
                    temp.setMzm(dataRow.getCell(11).getStringCellValue());
                    temp.setGjdqm(dataRow.getCell(12).getStringCellValue());
                    temp.setSfzjlxm(dataRow.getCell(13).getStringCellValue());
                    temp.setSfzjh(dataRow.getCell(14).getStringCellValue());
                    temp.setHyzkm(dataRow.getCell(15).getStringCellValue());
                    temp.setZzmmm(dataRow.getCell(16).getStringCellValue());
                    temp.setJkzkm(dataRow.getCell(17).getStringCellValue());
                    temp.setXyzjm(dataRow.getCell(18).getStringCellValue());
                    temp.setXxm(dataRow.getCell(19).getStringCellValue());
                    temp.setZp(dataRow.getCell(20).getStringCellValue());
                    temp.setSfzjyxq(dataRow.getCell(21).getStringCellValue());
                    temp.setStatus(dataRow.getCell(22).getStringCellValue());
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

    public Clerk findClerk(Organization org) {
        return dao.findClerk(org);
    }
}
