package kesun.bll;


import kesun.entity.AbsTreeNode;

import java.util.List;

/**
 * Created by wph-pc on 2017/5/29.
 */
public interface ITreeNode {
    /*获取当前节点的父节点*/
    public AbsTreeNode getParent();
    /*获取当前节点的子节点*/
    public List getSons();
}
