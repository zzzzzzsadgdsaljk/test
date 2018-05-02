var isAdd=true;//是否新增操作，true表示是，false表示修改；
function format(value, rowData, rowIndex) {
    var str = "<i class='layui-icon' style='cursor:pointer;color:rgb(0,150,36)' type='browser' valueId='" + rowData.id + "' name='" + rowData.name+"' title='浏览'>&#xe63c;</i>&nbsp;|&nbsp;<i class='layui-icon' style='cursor:pointer;color:rgb(255,184,0)' type='edit' valueId='" + rowData.id + "' name='" + rowData.name+"' title='修改操作'>&#xe642;</i>&nbsp;|&nbsp;<i class='layui-icon' style='cursor:pointer;color:rgb(255,87,34)' type='del' valueId='" + rowData.id + "' name='" + rowData.name+"' title='删除'>&#xe640;</i>&nbsp;|&nbsp;<i class='layui-icon' style='cursor:pointer;color:rgb(255,87,34)' type='power' valueId='" + rowData.id + "' name='" + rowData.name+"' title='查看授权和重新设置权限'>&#xe612;</i>";

    return str;
}
function powerFormat(value, rowData, rowIndex) {
    if (value==true)
     return "<span style='color:rgb(0,150,36)'>已授权</span>";
   else
    return "<span style='color:rgb(255,184,0)'>未授权</span>";
}
function formatType(value,rowData,rowIndex)
{
    return value.name;
}
function  find() {
    $("#dgActor").treegrid("loadData",[]);
    var objActor=new Object();
    objActor.condition=$("#txtName").val();
    doData("actor/findTree",objActor,function (data) {
        $("#dgActor").treegrid("loadData",data);
    });
}

layui.use("layer",function () {
    var layer=layui.layer;
})
$(function(){

    var obj=new Object();
    obj.condition="";
    doData("actorType/find",obj,function (data) {
        $("#actorType").combobox('loadData',data.obj);
    });


    $("#btnFind").click(function () {
        find();
    });
    $("#btnFresh").click(function () {
        $("#dgActor").treegrid("loadData",[]);
        var objActor=new Object();
        objActor.condition="";
        doData("actor/findTree",objActor,function (data) {
            $("#dgActor").treegrid("loadData",data);
        });
    });
    $("#btnSetPower").click(function () {
        var rows=$("#dgActor").treegrid("getSelections");
        if(rows==null || rows.length==0)
        {
            layer.msg("请选中授权的角色！");
            return;
        }
        /*形成授权的角色组*/
        var ids = "";//用户的ID
        for (var i = 0; i < rows.length; i++) {
            if (ids == "") {
                ids = rows[i].id;
            }
            else {
                ids += "," + rows[i].id;
            }
        }
        layer.open({
            title:'角色授权',
            type: 2,
            area:[600,400],
            content: getRootPath()+'/admin/system/actor/actorMapPower.jsp?id='+ids
        });
    });
    /*load actor data binding tree*/
    find();

    $("#dgActor").treegrid({
        onClickCell:function (rowIndex, rowData) {
            $("#txtParent").val(rowData.name);
            $("#txtParentID").val(rowData.id);
        }
    });

    $("#cbRoot").change(function () {
        if ($(this).attr("checked"))
        {
            $("#txtParent").val("");
            $("#txtParentID").val("");
        }
        else
        {
            if ($("#tActor").tree('getSelected')!=null)
            {
                $("#txtParent").val($("#tActor").tree('getSelected').text);
                $("#txtParentID").val($("#tActor").tree('getSelected').id);
            }
        }

    });
    $("#btnAdd").click(function () {
        isAdd=true;
        $('#ff').form('clear');
        $("#w").panel({title: "角色信息新增"});//重新设置窗体标题信息
        $("#w").window("open");
        $("#txtParent").val($("#dgActor").treegrid('getSelected').name);
        $("#txtParentID").val($("#dgActor").treegrid('getSelected').id);
    });
    $("#btnSave").click(function () {
        if ($("#ff").form('enableValidation').form('validate') == false)
            return;

        var obj=serializeArrayToObject("ff");
        if (isAdd)
            doData("actor/add",obj,function (data) {
                if (data.code>0)
                {
                    $("#w").window("close");
                    $.messager.show({
                        title: "提示",
                        msg: "您已经成功新增【" + obj.name + "】记录！"
                    });
                }
                else
                    $.messager.alert("提示", "新增失败，错误代码：" + data.code, "error");
            });
        else
            doData("actor/edit",obj,function (data) {
                if (data.code>0)
                {
                    $("#w").window("close");
                    $.messager.show({
                        title: "提示",
                        msg: "您已经成功修改角色【" + obj.name + "】记录！"
                    });
                }
                else
                    $.messager.alert("提示", "修改失败，错误代码：" + data.code, "error");
            });
    });

    $("i[type=browser]").live("click",function () {
        alert("ok");
    });
    $("i[type=edit]").live("click",function () {
        $('#ff').form('clear');
        isAdd=false;//修改标记
        /*获取修改角色对象*/
        var obj=new Object();
        obj.id=$(this).attr("valueId");
        doData("actor/getMe",obj,function (model) {
            $('#ff').form('load', model.obj);//导入修改的数据，并绑定在界面上
            if (model.obj.parent!=null)
                $("#txtParentID").val(model.obj.parent.id);
            $("#actorType").combobox("setValue",model.obj.type.id);
            isAdd = false;//表示当前操作是修改
            $("#btnSave").show();
            $("#btnSave").val("更新");//修改界面“保存”按钮的信息为“更新”
            $("#w").panel({title: "角色信息修改"});//重新设置窗体标题信息
            $("#w").window("open");
        })

    });
    $("i[type=del]").live("click",function () {
        var obj=new Object();
        obj.id=$(this).attr("valueId");
        if (obj.id.trim()=="")
        {
            layer.msg("抱歉，系统没有获取到您要删除角色的信息！");
            return;
        }
        layer.confirm('只有没有子角色，而且当前角色没有被使用才可以删除，删除后数据不可以恢复，确定吗？', {
            btn: ['删除确认', '取消'], //可以无限个按钮
            btn1: function(index, layero){
                doData("actor/del",obj,function (data) {
                    if (data.code>0)
                    {
                        layer.msg("您选中的角色已经删除!");
                        find();//刷新
                    }
                    else
                        layer.msg("删除失败，原因角色正在使用中!");
                });
            }
        });
    });
    $("i[type=power]").live("click",function () {
        /*获取角色对象信息*/
        var actorId=$(this).attr("valueId");//获取角色编号
        if (actorId.trim()=="")
        {
            layer.msg("抱歉，系统没有获取到您要授权角色的信息！");
            return;
        }
        layer.open({
            title:'角色授权',
            type: 2,
            area:[600,400],
            content: getRootPath()+'/admin/system/actor/actorMapPower.jsp?id='+actorId
        });
    });
});