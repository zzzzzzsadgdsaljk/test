package kesun.entity.system;

import kesun.entity.AbsBusinessObject;

import java.util.Date;
import java.util.List;

/**
 * 职员类
 * Created by wph-pc on 2017/5/30.
 */
public class Clerk extends AbsBusinessObject {
    private String gh="";//工号
    private String xm="";//姓名
    private String ywxm="";//英文姓名
    private String xmpy="";//姓名拼音
    private String cym="";//曾用名
    private String xbm="";//性别
    private Date csrq;//出生日期
    private String csdm="";//出生地
    private String jg="";//籍贯
    private String mzm="";//民族
    private String gjdqm="";//国籍或地区码
    private String sfzjlxm="";//身份证件类型
    private String sfzjh="";//身份证件号
    private String hyzkm="";//婚姻状况
    private String zzmmm="";//政治面貌
    private String jkzkm="";//健康状况
    private String xyzjm="";//宗教信仰
    private String xxm="";//血型
    private String zp="";//照片
    private String sfzjyxq="";//身份证件有效期
    private Organization org=null;//所属机构
    private List<ClerkContact> contacts=null;//联系方式
    public String getGh() {
        return gh;
    }

    public void setGh(String gh) {
        this.gh = gh;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getYwxm() {
        return ywxm;
    }

    public void setYwxm(String ywxm) {
        this.ywxm = ywxm;
    }

    public String getXmpy() {
        return xmpy;
    }

    public void setXmpy(String xmpy) {
        this.xmpy = xmpy;
    }

    public String getCym() {
        return cym;
    }

    public void setCym(String cym) {
        this.cym = cym;
    }

    public String getXbm() {
        return xbm;
    }

    public void setXbm(String xbm) {
        this.xbm = xbm;
    }

    public Date getCsrq() {
        return csrq;
    }

    public void setCsrq(Date csrq) {
        this.csrq = csrq;
    }

    public String getCsdm() {
        return csdm;
    }

    public void setCsdm(String csdm) {
        this.csdm = csdm;
    }

    public String getJg() {
        return jg;
    }

    public void setJg(String jg) {
        this.jg = jg;
    }

    public String getMzm() {
        return mzm;
    }

    public void setMzm(String mzm) {
        this.mzm = mzm;
    }

    public String getGjdqm() {
        return gjdqm;
    }

    public void setGjdqm(String gjdqm) {
        this.gjdqm = gjdqm;
    }

    public String getSfzjlxm() {
        return sfzjlxm;
    }

    public void setSfzjlxm(String sfzjlxm) {
        this.sfzjlxm = sfzjlxm;
    }

    public String getSfzjh() {
        return sfzjh;
    }

    public void setSfzjh(String sfzjh) {
        this.sfzjh = sfzjh;
    }

    public String getHyzkm() {
        return hyzkm;
    }

    public void setHyzkm(String hyzkm) {
        this.hyzkm = hyzkm;
    }

    public String getZzmmm() {
        return zzmmm;
    }

    public void setZzmmm(String zzmmm) {
        this.zzmmm = zzmmm;
    }

    public String getJkzkm() {
        return jkzkm;
    }

    public void setJkzkm(String jkzkm) {
        this.jkzkm = jkzkm;
    }

    public String getXyzjm() {
        return xyzjm;
    }

    public void setXyzjm(String xyzjm) {
        this.xyzjm = xyzjm;
    }

    public String getXxm() {
        return xxm;
    }

    public void setXxm(String xxm) {
        this.xxm = xxm;
    }

    public String getZp() {
        return zp;
    }

    public void setZp(String zp) {
        this.zp = zp;
    }

    public String getSfzjyxq() {
        return sfzjyxq;
    }

    public void setSfzjyxq(String sfzjyxq) {
        this.sfzjyxq = sfzjyxq;
    }

    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    public List<ClerkContact> getContacts() {
        return contacts;
    }

    public void setContacts(List<ClerkContact> contacts) {
        this.contacts = contacts;
    }
}
