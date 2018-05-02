package kesun.entity.system;

import kesun.entity.AbsTreeNode;

/**
 * 系统菜单实体类
 * Created by wph-pc on 2017/5/30.
 */
public class SystemMenu extends AbsTreeNode {
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String address;
}

