package kesun.entity.system;

/**
 * 类名称：用户类型
 * 说明：系统中用户类别顶级分类，固化
 * Created by wph-pc on 2017/10/27.
 */
public class UserType {
    public static final String COLLEGE="COLLEGE";//学校用户
    public static final String CORPORATION="CORPORATION";//公司用户
    public static final String MANAGER="MANAGER";//管理单位用户
    public static final String STUDENT="STUDENT";//学生用户，有归属学校
    public static final String TEACHER="TEACHER";//教师用户，有归属学校
    public static final String FAMILY="FAMILY";//家长用户，有对应学生
    public static final String CUSTOMER="CUSTOMER";//游客用户，没有单位，独立自由者
}
