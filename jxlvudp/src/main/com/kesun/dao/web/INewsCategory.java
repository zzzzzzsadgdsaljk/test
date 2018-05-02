package kesun.dao.web;


import kesun.entity.web.NewsCategory;

/**
 * Created by wph-pc on 2017/10/16.
 */
public interface INewsCategory {
    /*更新当前数据词典的父级节点*/
    int updateParent(NewsCategory node);
}
