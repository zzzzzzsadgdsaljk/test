/**
 * 实现角色类型各种操作
 */
var baseURL = "actorType";
var isAdd = true;//新增吗，true表示新增，false表示修改

function format(value, rowData, rowIndex) {
    var str = "<a href='#' type='browser' valueId='" + rowData.id + "' name='" + rowData.name +
        "'>浏览</a>&nbsp;|&nbsp;<a href='#' type='edit' valueId='" + rowData.id + "' name='" + rowData.name +
        "'>修改</a>&nbsp;|&nbsp;<a href='#' type='del' valueId='" + rowData.id + "' name='" + rowData.name + "'>删除</a>";
                
    return str;
}
function doSearch() { 
    find();
}
function find()
{
    var obj = new Object();
    obj.condition = $("#txtName").val();
    findByPage(baseURL+"/findByPage", "dgActorType", obj,function (data) {
        if (data.hasPower!=undefined && data.hasPower!=null && data.hasPower.length>0)
        {
            if (data.hasPower[0]==false)//浏览
            {
                $("a[type=browser]").remove();
            }
            if (data.hasPower[1]==false)//修改
            {
                $("a[type=edit]").remove();
            }
            if (data.hasPower[2]==false)//删除
            {
                $("a[type=del]").remove();
            }
        }

    });

}


$(function () {

    doData("org/findTree","{}",function (data) {
        $("#org").combotree("loadData",data);
    });

    $("#btnAdd").click(function () {
        $('#ff').form('clear');//清空表单内容
        $("#w").panel({ title: "角色类型信息新增" });//设置新增窗体的标题为“角色类型信息新增”
        isAdd = true;//设置当前操作为“新增”
        $("#btnSave").show();
        $("#btnSave").val("保存");//设置保存按钮显示内容为“保存”
        $("#w").window("open");
    });

    $("#btnSave").click(function () {
       
        if ($("#ff").form('enableValidation').form('validate') == false)
            return;
        var obj = serializeArrayToObject("ff");//将表单数据进行对象化
        obj.org=new Object();
        obj.org.id=$('#org').combotree('getValue');
        if (isAdd)
        {
            doData(baseURL+"/add", obj, function (data) {
                find();
                $("#w").window("close");
                if (data.code>0)
                    $.messager.show({
                        title: "提示",
                        msg: "您已经成功新增【" + obj.name + "】记录！"
                    });
                else
                    $.messager.alert("提示", "新增失败，错误代码：" + data.code, "error");
            });
        }
        else
            {
                doData(baseURL+"/edit", obj, function (data) {
                    if (data.code>0)
                    {
                        find();
                        $("#w").window("close");
                        $.messager.show({
                            title: "提示",
                            msg: "您已经成功修改【" + obj.name + "】记录！"
                        });
                    }
                    else
                    {
                        $.messager.alert("提示", "修改失败，错误代码：" + data.code, "error");
                    }
                });
           
            }

    });

    $("#btnReset").click(function () {
        $('#ff').form('clear');
    });

    find();
    $("a[type=browser]").live("click", function () {
        var obj = new Object();
        obj.id= $(this).attr("valueId");
        doData(baseURL+"/getMe", obj, function (data) {
            $('#ff').form('load', data.obj);//导入修改的数据，并绑定在界面上
            $('#cc').combotree('setValue',data.obj.org.id);
            $("#btnSave").hide();//修改界面“保存”按钮的信息为“更新”
            $("#w").panel({title: "角色类型信息浏览"});//重新设置窗体标题信息
            $("#w").window("open");
        });


    });
    $("a[type=del]").live("click", function () {
        var id = $(this).attr("valueId");
        var name = $(this).attr("name");
        $.messager.confirm('删除操作', "您正在操作--【删除】角色类型为：【" + name + "】的记录，删除后收据无法还原，确定吗？", function (r) {
            if (r) {
                var obj = new Object();
                obj.id = id;
                doData(baseURL+"/del", obj, function (data) {
                    if (data.code >0)
                    {
                        find();
                        $.messager.show({
                            title: "提示",
                            msg: "您已经成功删除【"+name+"】记录！" 
                        });
                                    
                    } else
                        $.messager.alert("提示", "删除失败，可能被【角色】使用了，需要先撤销使用；错误代码：" + data.code, "error");
                                    
                });
            }
        }); 
    });
    $("a[type=edit]").live("click", function () {
        $("#btnSave").show();

        var obj = new Object();
        obj.id= $(this).attr("valueId");
        doData(baseURL+"/getMe", obj, function (data) {
            $('#ff').form('load', data.obj);//导入修改的数据，并绑定在界面上
            $('#org').combotree('setValue',data.obj.org.id);
            isAdd = false;//表示当前操作是修改
            $("#btnSave").val("更新");//修改界面“保存”按钮的信息为“更新”
            $("#w").panel({title: "角色类型信息修改"});//重新设置窗体标题信息
            $("#w").window("open");
        });
    });
    /*批量删除操作*/
    $("#btnDel").click(function () {
        var objs = $("#dgActorType").datagrid("getSelections");
        if (objs == null || objs.length == 0)
        {
            $.messager.alert("提示", "请选中需要删除的角色类型记录！", "info");
            return;
        }
        var data = "";
        for (var i = 0; i < objs.length; i++)
        {
            if (data == "")
                data = objs[i].id;
            else
                data = data + "," + objs[i].id;
        }

        $.messager.confirm('删除操作', "您正在执行【" + objs.length + "】条角色信息【删除】操作，删除后收据无法还原，确定吗？", function (r) {
            if (r) {
                var obj = new Object();
                obj.ids = data;

                doData(baseURL+"/deleteAll", obj, function (data) {
                    if (data.code >0) {
                        find();
                        $.messager.show({
                            title: "提示",
                            msg: "您已经成功删除选中的记录！"
                        });

                    } else
                        $.messager.alert("提示", "删除失败，可能被【角色】使用了，需要先撤销使用；错误代码：" + data.code, "error");

                });
            }
        }); 
    });

    /*批量导入*/
    $("#btnLoadin").click(function () {
        $("#Filedata").click();
    });
    /*批量导出*/
    $("#btnLoadout").click(function () {
       $("#txtHCon").val($("#txtName").val());
        $("#fLoadout").form().submit();
    });
});

function doUpload() {
    doUploadFile("uploadfile","frmUpload",null,function (data) {
        if (data!=undefined && data!=null && data.length>0)
        {
            doData(baseURL+"/loadData",data[0],function (data) {
                if (data.obj>0)
                    $.messager.alert("提示","角色类型批量导入成功！");
                else
                    $.messager.alert("提示","角色类型批量导入失败！");
            });
        }
        else
            $.messager.alert("提示","导入文件上传失败！");

    });
}
 