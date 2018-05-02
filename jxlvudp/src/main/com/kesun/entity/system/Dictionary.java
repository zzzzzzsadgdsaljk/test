package kesun.entity.system;

import kesun.entity.AbsTreeNode;

/**
 * Created by wph-pc on 2017/5/29.
 */
public class Dictionary extends AbsTreeNode {
  private String key="";//键名称
  private String value="";//键值

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
