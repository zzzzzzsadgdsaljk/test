/**
 * 该类需要依赖public.js,仅为特定项目服务
 * Created by wph-pc on 2017/10/4.
 */

function KBOperate(url) {
    this.baseURL=url;
}
KBOperate.prototype.baseURL="";//后台请求操作的统一首地址
KBOperate.prototype.isAdd=true;//是否新增，true表示新增，false表示修改
KBOperate.prototype.formateOperateForEasyui=function(value, rowData, rowIndex){
    var str = "<i class='layui-icon' style='cursor:pointer;color:rgb(0,150,36)' type='browser' valueId='" + rowData.id + "' name='" + rowData.name+"' title='浏览'>&#xe63c;</i>&nbsp;&nbsp;<i class='layui-icon' style='cursor:pointer;color:rgb(255,184,0)' type='edit' valueId='" + rowData.id + "' name='" + rowData.name+"' title='修改操作'>&#xe642;</i>&nbsp;&nbsp;<i class='layui-icon' style='cursor:pointer;color:rgb(255,87,34)' type='del' valueId='" + rowData.id + "' name='" + rowData.name+"' title='删除'>&#xe640;</i>";
   return str;
}
KBOperate.prototype.findForEasyui=function(datagridId,condition) {
    findByPage((this.baseURL+"/findByPage"), datagridId, condition,function (data) {
        if (data.hasPower!=undefined && data.hasPower!=null && data.hasPower.length>0)
        {
            if (data.hasPower[0]==false)//浏览
            {
                $("a[type=browser]").remove();
                $("i[type=browser]").remove();
            }
            if (data.hasPower[1]==false)//修改
            {
                $("a[type=edit]").remove();
                $("i[type=edit]").remove();
            }
            if (data.hasPower[2]==false)//删除
            {
                $("a[type=del]").remove();
                $("i[type=del]").remove();
            }
        }

    });
}

KBOperate.prototype.browserForEasyui=function(callback){
    var temp=this;
    $("i[type=browser]").live("click",function () {
        var obj=new Object();
        obj.id=$(this).attr("valueId");
        obj.name=$(this).attr("name");
        obj.button=this;
        callback(obj);
    });
}
/************************************************************
 * 功能说明：实现在根据对象主键值获取对象功能，提供向EasyUI的
 *           datagrid的修改列提供操作；
 * 参数callback:回调函数，表示向服务器操作结束后，需要实现
 *              其他的功能，会带回一个对象值；
 *************************************************************/
KBOperate.prototype.editForEasyui=function(callback){
    $("i[type=edit]").live("click",function () {
        var obj=new Object();
        obj.id=$(this).attr("valueId");
        obj.name=$(this).attr("name");
        obj.button=this;
        if (obj.id==null || obj.id.trim()=="")
        {
            callback(null);//参数不符合条件
            return;
        }
        else
            callback(obj);
    });
}
/************************************************************
 * 功能说明：实现在根据对象主键值获取对象功能；
 * 参数objID:表示对象主键；
 * 参数callback:回调函数，表示向服务器操作结束后，需要实现
 *              其他的功能，会带回一个对象值；
 *************************************************************/
KBOperate.prototype.getMe=function(objID,callback){
    var obj=new Object();
    obj.id=objID;
    doData(this.baseURL+"/getMe",obj,function (data) {
        callback(data.obj);//回调
    });
}
/************************************************************
 * 功能说明：实现在datagrid表中删除列中的删除功能；
 * 参数tips:表示删除的提示信息；
 * 参数callback:回调函数，表示删除操作结束后，需要实现其他的功能，
 *              会带回一个参数，服务器操作结果值；
 *************************************************************/
KBOperate.prototype.delForEasyui=function(tips,callback){
    var temp=this;
    $("i[type=del]").live("click",function () {
        var obj=new Object();
        obj.id=$(this).attr("valueId");
        obj.name=$(this).attr("name");
        if (tips==undefined || tips=="")
            tips="删除后数据不可恢复，确定吗？";
        var flag=confirm(tips);
        if (flag)
        {
            doData(temp.baseURL+"/del",obj,function (data) {
                callback(data);
            });
        }
    });
}
/*获取EasyUI中datagrid选中行的所有对象的id,中间以逗号隔开，返回字符串*/
KBOperate.prototype.getEasyuiGridSelectRowsID=function (id) {
    var objs = $("#"+id).datagrid("getSelections");
    if (objs == null || objs.length == 0)
    {
        $.messager.alert("提示", "请选中需要删除的行记录！", "info");
        return "";
    }
    var data = "";
    for (var i = 0; i < objs.length; i++)
    {
        if (data == "")
            data = objs[i].id;
        else
            data = data + "," + objs[i].id;
    }
    return data;
}

KBOperate.prototype.getEasyuiGridSelectRows=function (id) {
    return  $("#"+id).datagrid("getSelections");
}
/*************************************************************
 * 功能说明：根据对象主键值进行数据的删除操作.
 * 参数objID:表示对象主键值;
 * 参数callback:回调函数，表示操作完成后，返回服务器操作结果。
 *************************************************************/
KBOperate.prototype.del=function(objID,callback){
    var obj=new Object();
    obj.id=objID;
    doData(temp.baseURL+"/del",obj,function (data) {
        callback(data);
    });
}

/***********************************************************************************
*功能说明：保存操作分为新增和修改，具体方向有isAdd标志决定，
* 参数checkCallback:表示保存前需要做的一些处理工作，例如验证，返回是boolean值，
 *                   true表示继续，false表示终止操作；
 * 参数obj:表示需要保存的对象；
 * 参数afterCallback：表示保存完成后，需要执行的回调函数，系统会返回服务器执行的结果
************************************************************************************/
KBOperate.prototype.save=function(checkCallback,obj,afterCallback){
    if (checkCallback==undefined || checkCallback==false || obj==undefined || obj==null) return;//不符合条件终止执行
    if (isAdd)
    {
        doData(baseURL+"/add",obj,function (data) {
            afterCallback(data);//回调
        });
    }
    else
    {
        doData(baseURL+"/edit",obj,function (data) {
            afterCallback(data);//回调
        });
    }
}
/*********************************************************************
* 功能说明：将查询的结果导入到EasyUI中的TreeGrid，需要依赖Easyui，
*           该结构仅仅适合于树形结构。
* 参数url：数据查询的子地址，首地址在baseURL中，子地址自己设置；
 * 参数condition：表示查询条件，对象形式表示；
 * 参数domID:表示EasyUI中treeGrid的id；
 * 参数callback:表示回调，回调时会提供表格中的数据，可以选择不操作
* */
KBOperate.prototype.loadTreeGridForEasyui=function (url,condition,domID,callback) {
    $("#"+domID).treegrid("loadData",[]);
    doData(baseURL+"/"+url,condition,function (data) {
        if (data!=null)
            $("#"+domID).treegrid("loadData",data);
        if(callback!=undefined)
          callback(data);//回调
    });
}
/***********************************************************************************
 *功能说明：客户端的通用操作，可以满足客户端的各种请求，使用ajax的Post数据交互方式，
 * 参数url:请求操作子地址；
 * 参数param:表示操作参数的对象；
 * 参数callback：操作完成后需要执行的回调函数，系统会返回服务器执行的结果
 ************************************************************************************/
KBOperate.prototype.do=function(url,param,callback) {
    doData(this.baseURL + "/" + url, param, function (data) {
        if (callback != undefined)
            callback(data);
    });
}
/***********************************************************************************
 *功能说明：客户端的通用操作，可以满足客户端的各种请求，使用ajax的Post数据交互方式，
 * 参数url:请求操作独立地址；
 * 参数param:表示操作参数的对象；
 * 参数callback：操作完成后需要执行的回调函数，系统会返回服务器执行的结果
 ***********************************************************************************/
KBOperate.prototype.doAll=function(url,param,callback){
        doData(url,param,function (data) {
            if (callback!=undefined)
                callback(data);
        });
}





