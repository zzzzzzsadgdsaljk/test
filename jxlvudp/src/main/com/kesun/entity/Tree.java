package kesun.entity;

import java.util.*;
public class Tree {
	private String name;
    private String id;
    private String alias;
    private String href;
    private String parent;
    private List<Tree> children;

    public Tree getParentNode(List<Tree> trees,Tree tree){
        if (trees.size()==0 || trees==null||tree==null)
            return null;
        for (Tree tempTree:trees){
            if (tree.getParent().equals(tempTree.getId())){
                return tempTree;
            }/*else if(tempTree.getChildren()!=null&&tempTree.getChildren().size()>0){
               Tree tep=getParentNode(trees,tree);
               return tep;
            }*/
        }

        return null;
    }
    public boolean isParent(List<Tree> trees,Tree tree){
    	 if (trees.size()==0 || trees==null||tree==null)
             return false;
    	boolean flag=true;
    	for (Tree tempTree:trees){
    		if (tree.getParent().equals(tree.getId())){
    			flag=true;
    			break;
    		}
    		if (tree.getParent().equals(tempTree.getId())){
    			flag=false;
    			break;
    		
    		}
    	}
    	
    	return flag;
    }
    public List<Tree> convertTreeList(List<Tree> trees){
        if (trees.size()==0 || trees==null)
            return null;
       
        List<Tree> finallyTrees=new ArrayList<Tree>();              //创建最后形成的树
        List<Tree> TempChildrens=new ArrayList<Tree>();             //创建临时的孩子
        for (int i=0;i<trees.size();i++){
        	Tree tree=trees.get(i);
        	
            if (tree.getParent().equals("") || isParent(trees,tree)){
                finallyTrees.add(tree);
               
            }else{
                TempChildrens.add(tree);
            }
        }

        while (TempChildrens.size()>0){
            for (int i=0;i<TempChildrens.size();i++){
                Tree childrens=TempChildrens.get(i);
                Tree parent=getParentNode(finallyTrees,childrens);
                if (parent!=null){
                    if (parent.getChildren()==null)
                        parent.setChildren(new ArrayList<Tree>());
                    parent.getChildren().add(childrens);
                    TempChildrens.remove(childrens);
                }

            }

        }


        return  finallyTrees;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public List<Tree> getChildren() {
        return children;
    }

    public void setChildren(List<Tree> children) {
        this.children = children;
    }


   
}

