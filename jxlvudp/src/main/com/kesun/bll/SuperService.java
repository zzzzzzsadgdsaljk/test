package kesun.bll;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import kesun.dao.IDoData;
import kesun.entity.AbsSuperObject;
import kesun.entity.Page;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by wph-pc on 2017/5/26.
 */
public abstract class SuperService{
    public AbsSuperObject getModel() {
        return model;
    }
    public void setModel(AbsSuperObject model) {
        this.model = model;
    }
    private AbsSuperObject model=null;

    public abstract List<Map<String,Object>> getLoadoutExcelColumns();//获取导出的表格的列名称,key-value结构形式表示：key列名称，value中文显示的值
    public abstract String getLoadoutExcelFileName();//获取导出Excel数据文件名称

    /*获取数据访问层对象*/
    public abstract IDoData getDAO();

    /*
    *是否新增控制条件，true表示新增，false表示不新增
    * */
    public  Boolean isAdd()
    {
        return true;
    }
    /*
        *是否修改控制条件，true表示新增，false表示不新增
        * */
    public  Boolean isEdit()
    {
        return true;
    }
    /*
    是否删除控制条件，true表示新增，false表示不新增
      * */
    public  Boolean isDelete()
    {
        if (isInUse())
            return false;
        else
           return true;
    }
    /*判断当前实体对象是否在使用中*/
    public Boolean isInUse()
    {
        if (getDAO()==null || getModel()==null) return false;
        return getDAO().objectInUse(getModel().getId());
    }
    public int del()
    {
        try {
            return (Integer)getDAO().delete(model.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 全查
     * @return
     * @throws Exception
     */
    public List<?> find(Map values){
        try {
            return getDAO().find(values);
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }


    public List<Map<String,Object>> findForMap(Map values) {
        try {
            return getDAO().findForMap(values);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getRowsCount(Map values) {
        try {
            return getDAO().getRowsCount(values);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    public Page findByPage(Map values, int page, int rowsCount) {
        if (values==null || values.size()==0) return  null;
       Page temp=new Page();
        try
        {
            temp.setTotal(getRowsCount(values));

            values.put("start",page*rowsCount);
            values.put("limit",rowsCount);
            List<Map<String,Object>> list = getDAO().findByPage(values);
            temp.setPage(page);
            temp.setRowsCount(rowsCount);
            temp.setRows(list);

        }catch (Exception e)
        {
            temp.setPage(page);
            temp.setRowsCount(rowsCount);
            temp.setTotal(0);
            temp.setRows(null);
        }
        return temp;
    }

    public Page findByPage_Oracle(Map values,int page,int rowsCount) {
        if (values==null || values.size()==0) return  null;
        Page temp=new  Page();
        try
        {
            temp.setTotal(getRowsCount(values));
             page++;
            values.put("rowsStart",page*rowsCount);
            values.put("rowsEnd",page*rowsCount+rowsCount);
            List<Map<String,Object>> list = getDAO().findByPage(values);

            temp.setPage(page);
            temp.setRowsCount(rowsCount);
            temp.setRows(list);

        }catch (Exception e)
        {
            temp.setPage(page);
            temp.setRowsCount(rowsCount);
            temp.setRows(null);
        }
        return temp;
    }

    public int add() {
        try {
            return (Integer) getDAO().save(model);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    /*获取批量保存的对象*/
    public abstract List getSaveAll(String filePath);
    /**
     * 批量新增
     * @return 受影响的行数
     */
    public int loadinData(String filePath) {
        List objs=getSaveAll(filePath);
        if (objs==null || objs.size()==0) return 0;//没有获取到符合条件数据
        return getDAO().batchSave(objs);
    }

    public Object getMe(){
        try {
            return  getDAO().getMe(model.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //批量更新
    public int editAll(List objs){
        try {
            return getDAO().batchUpdate(objs);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    //批量删除的方法
    public int deleteAll(List ids){
        int result = 0;
        try {
            result = (Integer) getDAO().batchDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return result;
    }


    /*
    多个id数组转集合的方法
     */
    public List<String> getList(String ids){
        List<String> list = new ArrayList<String>();
        String[] str = ids.split(",");
        for (int i = 0; i < str.length; i++) {
            list.add(str[i]);
        }
        return list;
    }

    public int edit() {

        int result = 0;
        try {
            result = (Integer) getDAO().update(model);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return result;
    }

    /*
    物理分页方法，调用当前类的findAll方法
    @param：pageNum 页码数
    @param：pageSize 每页数据条目数
     */
    public PageInfo<Object> find(int pageNum, int pageSize,Map values) {
        PageHelper.startPage(pageNum,pageSize);//开始分页
        List list = this.find(values);
        PageInfo<Object> pageInfo = new PageInfo<Object>(list);
        return pageInfo;
    }
}
