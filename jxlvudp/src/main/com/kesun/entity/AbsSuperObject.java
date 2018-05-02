package kesun.entity;

import java.util.Date;

/**
 * Created by wph-pc on 2017/5/26.
 */
public abstract class AbsSuperObject extends  Object implements java.io.Serializable{
    private String id="";//机内码，对应数据库主键
    private String oldId="";//修改前机内码值
    private String name="";//对象名称
    private Date createDate=new Date();//对象创建时间
    private String status="";//对象状态

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description="";//对象描述
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOldId() {
        return oldId;
    }

    public void setOldId(String oldId) {
        this.oldId = oldId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    /*重写判断两个对象是否相等方法*/
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof AbsSuperObject==false) return false;//判断当前对象是否规定类型
        AbsSuperObject temp=(AbsSuperObject)obj;
        if (this.getClass()==obj.getClass() && getId().trim().equals(temp.getId().trim()))
            return  true;
        else
            return false;
    }
}
