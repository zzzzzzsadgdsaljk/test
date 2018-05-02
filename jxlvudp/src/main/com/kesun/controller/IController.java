package kesun.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wph-pc on 2017/5/27.
 */
public interface IController {
    /*单个对象新增*/
    public void add(HttpServletRequest request, HttpServletResponse response);

    /*单个对象修改操作*/
    public void edit(HttpServletRequest request, HttpServletResponse response);
    /*单个对象删除*/
    public void del(HttpServletRequest request, HttpServletResponse response);
    /*删除符合条件对象操作*/
    public void deleteAll(HttpServletRequest request, HttpServletResponse response);
    /*查询操作*/
    public void find(HttpServletRequest request, HttpServletResponse response);
    /*分页查询操作*/
    public void findByPage(HttpServletRequest request, HttpServletResponse response);
    /*数据导出到Excel操作*/
    public void loadoutData(HttpServletRequest request, HttpServletResponse response);
    /*单个对象打印操作*/
    public void print(HttpServletRequest request, HttpServletResponse response);
    /*打印数据表格操作*/
    public void printDataTable(HttpServletRequest request, HttpServletResponse response);
    /*单个对象以HTML方式显示操作*/
    public void display(HttpServletRequest request, HttpServletResponse response);
    /*获取单个对象，以JSON字符串格式返回*/
    public void getMe(HttpServletRequest request, HttpServletResponse response);
}
