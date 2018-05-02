/**
 * description:执行常规的数据对象的增删改查操作；这里的操作需要依赖与JQuery和Layui的支持；
 * 配合控制层SuperController实现常规操作；
 * Created by wph-pc on 2017/9/23.
 */

function KBLayUI(url){
    this.baseURL=url;
    layui.use("layer",function () {
        var layer=layui.layer;
    });
}
KBLayUI.prototype.baseURL="";


/*实现对象的新增操作*/
KBLayUI.prototype.add=function (model,callback) {
    if (model==null)
    {
        if (parent==null)
          layer.msg("系统检测到当前操作的数据为空！");
        else
            parent.layer.msg("系统检测到当前操作的数据为空！");
        return;
    }
    doData(this.baseURL+"/add",model,function (data) {
        if (data.code>0)
        {
            if (parent==null)
                layer.msg("您已经成功新增【" + model.name + "】记录！");
            else
                parent.layer.msg("您已经成功新增【" + model.name + "】记录！");
        }

        else
        {
            if (parent==null)
                layer.msg("新增失败，错误代码：" + data.code);
            else
                parent.layer.msg("新增失败，错误代码：" + data.code);
        }

        callback(data);//回调
    });
}

/*实现对象的修改操作*/
KBLayUI.prototype.edit=function (model,callback) {
    if (model==null)
    {
        if (parent==null)
          layer.msg("系统检测到当前操作的数据为空！");
        else
            parent.layer.msg("系统检测到当前操作的数据为空！");
        return;
    }
    doData(this.baseURL+"/edit",model,function (data) {
        if (data.code > 0)
        {
            if (parent==null)
               layer.msg("您已经成功修改【" + model.name + "】记录！");
            else
               parent.layer.msg("您已经成功修改【" + model.name + "】记录！");
        }
        else
        {
            if (parent==null)
              layer.msg("修改失败，错误代码：" + data.code);
           else
              parent.layer.msg("修改失败，错误代码：" + data.code);
        }
        callback(data);//回调
    });
}

/*实现对象的修改操作*/
KBLayUI.prototype.getMe=function (id,callback) {
    if (id==null || id.trim()=="")
    {
        if (parent==null)
          layer.msg("系统检测到获取数据的条件为空！");
        else
            parent.layer.msg("系统检测到获取数据的条件为空！");
        return;
    }
    var model=new Object();
    model.id=id;
    doData(this.baseURL+"/getMe",model,function (data) {
        callback(data.obj);//回调
    });
}

/*删除操作*/
KBLayUI.prototype.del=function (model,callback) {
    if (model==null)
    {
        layer.msg("系统无法检测到要删除的数据！");
        return;
    }
    doData(this.baseURL+"/del",model,function (data) {
        if (data.code>0)
            layer.msg("您已经删除【" + model.name + "】记录！");
        else
            layer.msg("删除失败，原因当前数据正在使用中，错误代码：" + data.code);
        callback(data);//回调
    });
}
/*树形结构查询操作*/
KBLayUI.prototype.loadTreeGrid=function (url,condition,domID,callback) {
    $("#"+domID).treegrid("loadData",[]);
    doData(this.baseURL+"/"+url,condition,function (data) {
        if (data!=null)
            $("#"+domID).treegrid("loadData",data);
    });
}
/*对象查询*/
KBLayUI.prototype.find=function (condition,callback) {
    doData(this.baseURL+"/find",condition,callback);
}
/*对象查询*/
KBLayUI.prototype.findByPage=function (condition,callback) {
    doData(this.baseURL+"/findByPage",condition,callback);
}
/*分页查询，并绑定easyui的datagrid*/
KBLayUI.prototype.findByPageForDataGrid=function(tableId,condition)
{
    findByPage(this.baseURL+"/findByPage",tableId,condition,condition);
}

/*分页查询，并绑定layui的table*/
KBLayUI.prototype.findByPageForLayuiTable=function(tableId,condition,cols,callback)
{
    var temp=this;
    layui.use('table', function(){
        var table = layui.table;
        table.render({
            elem:"#"+tableId,
            url:temp.baseURL+"/findByPageForLayui",
            method:"post",
            id:tableId,
            cols:[cols],
            page:true,
            where:condition
        });
        callback(table);
    })

}
/*layui的表单数据载入*/
KBLayUI.prototype.loadFormData=function (formId,objID,callback) {
    if (objID==null || objID.trim()=="")
    {
        layer.msg("系统检测到获取数据的条件为空！");
        return;
    }
    var model=new Object();
    model.id=objID;
    doData(this.baseURL+"/getMe",model,function (data) {
        $('#'+formId).form('load', data.obj);//导入修改的数据，并绑定在界面上
        if (callback!=undefined)
            callback(data.obj);//回调
    });
}
/*layui的表单提交，其中id是指submit按钮指定lay-filter的属性值，即lay-filter=id*/
KBLayUI.prototype.doFormLayui=function (id,callback) {
    layui.use(['form','layer'],function () {
        var form=layui.form,layer=layui.layer;
        form.on('submit('+id+')', function(data){
            callback(data);
            return false;
        });
    });
}
/*Layui简单的文本编辑器操作*/
KBLayUI.prototype.doLayuiEditor=function (domId,returnEditorIndex) {
    layui.use(['layedit'], function() {
        var layedit = layui.layedit;
        layedit.set({
            uploadImage: {
                url: getRootPath() + '/uploadfileForLayUI' //接口url
                , type: 'post' //默认post
            }
        });
        returnEditorIndex=layedit.build(domId); //建立编辑器
    });
}
/**********************************************
 * ajax数据处理
 * params:参数对象
 * type:数据处理类别
 * callback:回调函数
 *********************************************/
KBLayUI.prototype.do=function (url,param,callback) {
    if (url==undefined || url==null || url=="")
    {
        layer.msg("请求地址不能为空！");
        return;
    }
    doData(this.baseURL+"/"+url,param,callback);
}

/**********************************************
 * ajax数据处理
 * url:请求独立地址
 * params:参数对象
 * callback:回调函数
 *********************************************/
KBLayUI.prototype.doALL=function (url,param,callback) {
    if (url==undefined || url==null || url=="")
    {
        layer.msg("请求地址不能为空！");
        return;
    }
    doData(url,param,callback);
}



