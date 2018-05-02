package kesun.dao.system;


import kesun.entity.system.Dictionary;

/**
 * Created by wph-pc on 2017/9/26.
 */
public interface IDictionary {
    /*更新当前数据词典的父级节点*/
    int updateParent(Dictionary node);
}
