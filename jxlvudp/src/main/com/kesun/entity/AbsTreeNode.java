package kesun.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形结构节点持久层类
 * Created by wph-pc on 2017/5/29.
 */
public  class AbsTreeNode extends AbsSuperObject {
    private AbsTreeNode parent=null;//父节点
    private List children=null;//子节点
    private String text="";//仅为支持EasyUI树形结构应用
    private String state="closed";//仅为支持EasyUI树形结构应用
    public AbsTreeNode getParent() {
        return parent;
    }
    public void setParent(AbsTreeNode parent) {
        this.parent = parent;
    }

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        if (children==null || children.size()==0)
            return "";
        else
            return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /*向nodes集合中获取node父级节点*/
    public AbsTreeNode getParentNode(List<AbsTreeNode> nodes,AbsTreeNode node)
    {
        if (nodes==null || nodes.size()==0 || node==null || node.getParent()==null || node.getId().trim().equals(node.getParent().getId().trim())) return null;
        for(AbsTreeNode temp:nodes)
        {
            if (temp.equals(node.getParent()))
                return  temp;
            else
                if (temp.getChildren()!=null && temp.children.size()>0)
                {
                    AbsTreeNode tempNode=getParentNode(temp.getChildren(),node);
                    if (tempNode!=null) return tempNode;
                }

        }
        return  null;
    }
    /*
    * 判断当前source数据源中是否存在obj的父节点；
    * 数据源的类型和obj都必须是AbsTreeNode类型，如果存在，返回true,否则返回false
    * */
    private boolean isExistParentNode(List<Object> source,Object obj)
    {
        if (source==null || source.size()==0 || obj==null) return false;//判断条件是否合法
        if (obj instanceof AbsTreeNode==false) return false;//判断obj类型是否合法
        /*搜索*/
        for (Object temp:source
             ) {
            if (temp instanceof AbsTreeNode==false) continue;

            AbsTreeNode s=(AbsTreeNode)temp;//转成目标类型
            AbsTreeNode t=(AbsTreeNode)obj;//转成目标类型
            if (t.getParent()==null) return false;//没有父节点，直接返回
            if (t.getId().trim().equals(t.getParent().getId().trim())) return false;//自己的父节点不能是自己
            if (t.getParent().getId().trim().equals(s.getId().trim())) return true;//找到父节点

        }
        return  false;
    }
    /*
    * 将非树形结构参数objs转换成符合树形结构的List
    * */
    public List<AbsTreeNode> convertTreeList(List<Object> objs)
    {
        if (objs==null || objs.size()==0) return null;

        /*创建临时数据源*/
        List<Object> tempSource=new ArrayList<Object>();
        tempSource.addAll(objs);

        List<AbsTreeNode> lResult=new ArrayList<AbsTreeNode>();//装换后的数据
        /*处理所有的根节点*/
        for(int i=objs.size()-1;i>=0;i--)
        {
            AbsTreeNode temp= (AbsTreeNode)objs.get(i);
            if (temp.getParent()==null || temp.getParent().getId().trim().equals("") || temp.getParent().getId().trim().equals("0") || isExistParentNode(tempSource,temp)==false)
            {
                lResult.add(temp);
                objs.remove(temp);
            }
        }

        /*处理余下节点*/
        while(objs.size()>0)
        {
         for(int i=objs.size()-1;i>=0;i--)
        {
            AbsTreeNode temp= (AbsTreeNode)objs.get(i);
            AbsTreeNode parent=getParentNode(lResult,temp);//查找当前节点的父节点
            if (parent!=null)
            {
                if (parent.getChildren()==null)
                    parent.setChildren(new ArrayList<AbsTreeNode>());
                parent.getChildren().add(temp);
                objs.remove(temp);//移除掉当前
            }
            else if(isExistParentNode(tempSource,temp)==false) {
                objs.remove(temp);//移除掉当前
            }
        }
        }
        return lResult;
    }
}
