package kesun.bll.system.impl;

import kesun.bll.SuperService;
import kesun.bll.system.IUser;
import kesun.dao.IDoData;
import kesun.dao.system.impl.DUser;
import kesun.entity.system.*;
import kesun.util.ExcelUtil;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;
import shiro.ShiroMD5;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户业务层
 * Created by wph-pc on 2017/5/29.
 */
@Service("bUser")
public class UserServiceImpl extends SuperService implements IUser {
    @Resource(name="dUser")
    private DUser dao;
    @Resource(name="bActor")
    private ActorServiceImpl actorBll;//角色业务对象

    public UserServiceImpl()
    {
        setModel(new User());
    }
    public User login() {

        if (getModel()==null || getModel() instanceof  User==false || dao==null) return null;
        Object obj=getMe();//根据账号获取当前用户的对象信息
        if (obj==null || obj instanceof User==false) return null;//没有查找符合条件的对象
        User temp=(User)obj;//将查找的目标对象转成用户对象
        User u=(User)getModel();

        if (temp.getPassword().equals(ShiroMD5.GetPwd(temp.getNumber(),u.getPassword())))// MD5.GetPwd(u.getPassword())))//密码验证
            return temp;
        else
            return null;
    }
    public int logout() {
        return 0;
    }

    public int changePwd(String newPwd) {
        if (getModel()==null || getModel() instanceof User==false) return 0;//条件不符合
        return dao.changePwd((User)getModel(),newPwd);
    }

    public int initPassword(List<User> users) {
        return dao.initPassword(users);
    }

    public int writeLog(UserLog log) {
        return 0;
    }

    public int manageUserState(String newState) {
        if (getModel()==null || getModel() instanceof User==false) return 0;
        return dao.manageUserState((User)getModel(),newState);
    }

    public int setActor(List<Actor> actors) {
        if (getModel()==null || getModel() instanceof User==false) return 0;
        return dao.setActor((User)getModel(),actors);
    }

    public List<Actor> findActor() {
        if (getModel()==null || getModel() instanceof User==false) return null;
        return  dao.findActor((User)getModel());
    }

    public void findActorAndOrg() {
        if (getModel()==null || getModel() instanceof User==false) return;
        dao.findActorAndOrg((User)getModel());
    }

    /*获取系统功能权限*/
    public List<SystemMenu> findPower()
    {
        List<Actor> actors=findActor();//获取当前用户的角色对象
        return actorBll.getFunctionPower(actors);
    }
    public List<UserOnlineRecord> watchOnline(Map<String, Object> conValues) {
        return null;
    }

    public List<Map<String, Object>> getLoadoutExcelColumns() {
        return null;
    }

    public String getLoadoutExcelFileName() {
        return "用户信息表";
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
                List<User> lTemp=new ArrayList<User>();//创建临时操作对象
                for(Row dataRow:result)
                {
                    if (dataRow.getRowNum()==0) continue;
                    User temp=new User();
                    temp.setId(dataRow.getCell(1).getStringCellValue());
                    temp.setNumber(dataRow.getCell(1).getStringCellValue());
                    temp.setNickName(dataRow.getCell(2).getStringCellValue());
                    temp.setName(dataRow.getCell(3).getStringCellValue());
                    temp.setStatus(dataRow.getCell(4).getStringCellValue());
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
