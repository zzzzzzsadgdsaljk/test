/**
 * 实现各种用户的各种操作
 */
var baseURL = "user";
var isAdd = true;//新增吗，true表示新增，false表示修改
var statusSource=[{"id":"正常","name":"正常"},{"id":"锁定","name":"锁定"},{"id":"注销","name":"注销"}];
function format(value, rowData, rowIndex) {
    var str = "<a href='#' type='browser' valueId='" + rowData.id + "' name='" + rowData.name +
        "'>浏览</a>&nbsp;|&nbsp;<a href='#' type='edit' valueId='" + rowData.id + "' name='" + rowData.name +
        "'>修改</a>&nbsp;|&nbsp;<a href='#' type='del' valueId='" + rowData.id + "' name='" + rowData.name + "'>删除</a>";
                
    return str;
}
/*处理角色信息*/
function formatDoActor(value, rowData, rowIndex) {
    if (value==null || value.length==0)
        return "未分配【<a href='#' userName='"+rowData.name+"' userID='"+rowData.id+"' type='doActor'>分配</a>】";
    else
    {
        var temp="";
        for(var i=0;i<value.length;i++)
        {
           if (temp=="")
               temp=value[i].name;
           else
               temp+=","+value[i].name;
        }
        return temp+"【<a href='#' userName='"+rowData.name+"' userID='"+rowData.id+"' type='doActor'>变更</a>】";
    }
}
function formatStatus(value,rowData,rowIndex)
{
    if (value=="正常") return "<select type='user' userID='"+rowData.id+"' oldState='"+value+"'><option value='正常' selected='selected'>正常</option><option value='锁定'>锁定</option><option value='注销'>注销</option></select>";
    if (value=="锁定") return "<select type='user' userID='"+rowData.id+"' oldState='"+value+"'><option value='正常'>正常</option><option value='锁定' selected='selected'>锁定</option><option value='注销'>注销</option></select>";
    if (value=="注销") return "<select type='user' userID='"+rowData.id+"' oldState='"+value+"'><option value='正常'>正常</option><option value='锁定'>锁定</option><option value='注销' selected='selected'>注销</option></select>";
    return "<select userID='"+rowData.id+"' oldState='"+value+"'><option value='正常'>正常</option><option value='锁定'>锁定</option><option value='注销'>注销</option><option value='"+value+"' selected='selected'>"+value+"</option></select>";
}
function doSearch() { 
    find();
}
function find()
{
    var obj = new Object();
    obj.condition = $("#txtName").val();
    findByPage(baseURL+"/findByPage", "dguser", obj);
}
/*清空easyui tree树形结构选中*/
function treeClearCheck(treeID) {
    var nodes = $('#'+treeID+'').tree('getChecked');
    for(var i=0; i<nodes.length; i++){
        $('#'+treeID+'').tree('uncheck', nodes[i].target);
    }
}
$(function () {
    $("#btnAdd").click(function () {
        $('#ff').form('clear');//清空表单内容
        $("#btnSave").show();
        $("#btnReset").show();
        $("#w").panel({ title: "用户信息新增" });//设置新增窗体的标题为“角色类型信息新增”
        isAdd = true;//设置当前操作为“新增”
        $("#btnSave").val("保存");//设置保存按钮显示内容为“保存”
        $("#w").window("open");
    });
    $("#btnSave").click(function () {


        if ($("#ff").form('enableValidation').form('validate') == false)
            return;
        var obj = serializeArrayToObject("ff");//将表单数据进行对象化

        if (isAdd)
        {
            doData(baseURL+"/add", obj, function (data) {
                returnCodeUI(data,function (code) {
                    if (code>0 && code<10)
                        find();
                })

                $("#w").window("close");

            });
        }
        else
            {

                doData(baseURL+"/edit", obj, function (data) {
                    if (data.result>0)
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
                        $.messager.alert("提示", "修改失败，错误代码：" + data, "error");
                    }
                });

            }

    });

    /*load actor data binding tree*/
    var objActor=new Object();
    objActor.name="";
    doData("actor/findTree",objActor,function (data) {
        $("#tActor").tree("loadData",data);
    });

    $("#btnSetActor").click(function () {
        /*判断是否有选中的用户*/
        var rows = $('#dguser').datagrid('getSelections');
        if (rows==null || rows.length==0)
        {
            alert("请选中需要分配的角色的用户行！");
            return;
        }
        var flag=confirm("当前的操作，将会实现所用选中的用户设置相同的【角色】，原来已设置的【角色】全部取消，确定吗？");
        if (flag)
        {
            var userName="";//用户名
            var userID="";//用户的ID
            for(var i=0;i<rows.length;i++)
            {
                if (userName=="")
                {
                    userName=rows[i].name;
                    userID=rows[i].id;
                }
                else
                {
                    userName+=","+rows[i].name;
                    userID+=","+rows[i].id;
                }
            }
            $("#dUsers").text(userName);
            $("#dUserIDs").text(userID);
            treeClearCheck("tActor");//清空角色树节点选项
            $("#wSetActor").window("open");
        }

    });

    $("#btnSetInitPWD").click(function () {
        /*判断是否有选中的用户*/
        var rows = $('#dguser').datagrid('getSelections');
        if (rows==null || rows.length==0)
        {
            alert("请选中需要密码初始化的用户行！");
            return;
        }
        var flag=confirm("当前的操作，将会实现所用选中的用户进行密码初始化，初始化后的密码与账号相同，确定吗？");
        if (flag) {
            var ids = "";//用户的ID
            for (var i = 0; i < rows.length; i++) {
                if (ids == "") {
                    ids = rows[i].id;
                }
                else {
                    ids += "," + rows[i].id;
                }
            }
            var obj=new Object();
            obj.ids=ids;
            doData(baseURL+"/initPassword",obj,function (data) {
                if (data.result>0)
                {
                    alert("密码初始化成功！");
                }
                else
                    alert("密码初始化失败，错误代码："+data.result);
            })
        }
    });
    $("#btnReset").click(function () {
        $('#ff').form('clear');
    });

    find();
    $("a[type=del]").live("click", function () {
        var id = $(this).attr("valueId");
        var name = $(this).attr("name");
        $.messager.confirm('删除操作', "您正在操作--【删除】用户为：【" + name + "】的记录，删除后收据无法还原，确定吗？", function (r) {
            if (r) {
                var obj = new Object();
                obj.id = id;
                doData(baseURL+"/del", obj, function (data) {
                    if (data.result >= 1)
                    {
                        find();
                        $.messager.show({
                            title: "提示",
                            msg: "您已经成功删除【"+name+"】记录！"
                        });

                    } else
                        $.messager.alert("提示", "删除失败，错误代码：" + data, "error");

                });
            }
        });
    });
    $("a[type=browser]").live("click", function () {
        var obj = new Object();
        obj.id= $(this).attr("valueId");
        doData(baseURL+"/getMe", obj, function (data) {
            $('#ff').form('load', data);//导入修改的数据，并绑定在界面上
            isAdd = false;//表示当前操作是修改
            $("#btnSave").hide();//修改界面“保存”按钮的信息为“更新”
            $("#btnReset").hide();
            $("#w").panel({title: "用户信息浏览"});//重新设置窗体标题信息
            $("#w").window("open");
        });

    });
    $("a[type=edit]").live("click", function () {
        var obj = new Object();
        obj.id= $(this).attr("valueId");
        doData(baseURL+"/getMe", obj, function (data) {
            $('#ff').form('load', data);//导入修改的数据，并绑定在界面上

            isAdd = false;//表示当前操作是修改
            $("#btnSave").show();
            $("#btnReset").show();
            $("#btnSave").val("更新");//修改界面“保存”按钮的信息为“更新”
            $("#w").panel({title: "用户信息修改"});//重新设置窗体标题信息
            $("#w").window("open");
        });

    });

    $("a[type=doActor]").live("click",function () {
        $("#dUsers").text($(this).attr("userName"));
        $("#dUserIDs").text($(this).attr("userID"));
        /*获取当前用户已经分配的角色*/
        var u=new Object();
        u.id=$(this).attr("userID");

        treeClearCheck("tActor");//清空角色树节点选项

        doData(baseURL+"/findActor",u,function (data) {
            if (data!=null)
            {
                var nodes = $('#tActor').tree('getChildren');
                for(var i=0; i<nodes.length; i++){
                    for(var j=0;j<data.length;j++)
                    {
                        if (data[j].id==nodes[i].id)
                            $('#tActor').tree('check', nodes[i].target);
                    }
                }
            }
            $("#wSetActor").window("open");
        })

    });
    $("select[type='user']").live("change",function () {
        confirm("用户状态只有在【正常】才能操作，【锁定】和【注销】状态的用户是无法进入系统，确定继续操作吗？")
        if ($(this).attr("oldState")==$(this).val()) return;//状态没有变化
        var obj=new Object();
        obj.id=$(this).attr("userID");
        obj.newStatus=$(this).val();
        doData(baseURL+"/setUserState",obj,function (data) {
            if (data.result>0)
                find();
            else
                alert("当前用户的状态更新失败，错误码："+data.result);
        });
    });
    $("#btnSaveActor").click(function () {
        var obj=new Object();
        obj.actors=new Array();
        obj.users=$("#dUserIDs").text();//获取操作被分配用户的id
        var nodes = $('#tActor').tree('getChecked');
        for(var i=0; i<nodes.length; i++)
        {
            var temp=new Object();
            temp.id=nodes[i].id;
            temp.name=nodes[i].text;
            obj.actors.push(temp);
        }
        if (nodes==null || nodes.length==0)
        {
            if (confirm("当前您没有设置任何的角色，如果当前用户存在已经设置的角色，系统将做角色清空操作，请确认是否继续？")==false)
                return;
        }
        doData(baseURL+"/setActor",obj,function (data) {
            if (data.result>0)
            {
                find();
                $.messager.show({
                    title: "提示",
                    msg: "您当前角色设置操作成功！"
                });
                $("#wSetActor").window("close");
            }
            else
               alert("当前角色分配失败，错误代码："+data.result);
        })
    });
    /*批量删除操作*/
    $("#btnDel").click(function () {
        var objs = $("#dguser").datagrid("getSelections");
        if (objs == null || objs.length == 0)
        {
            $.messager.alert("提示", "请选中需要删除的用户记录！", "info");
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

        $.messager.confirm('删除操作', "您正在执行【" + objs.length + "】条用户信息【删除】操作，删除后收据无法还原，确定吗？", function (r) {
            if (r) {
                var obj = new Object();
                obj.ids = data;

                doData(baseURL+"/deleteAll", obj, function (data) {
                    if (data.result >= 1) {
                        find();
                        $.messager.show({
                            title: "提示",
                            msg: "您已经成功删除选中的记录！"
                        });

                    } else
                        $.messager.alert("提示", "删除失败，错误代码：" + data, "error");

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
    $("#frmUpload").ajaxForm(function(msg) {
                    $.messager
                        .alert("提示", msg);
            });
});

function doUpload() {
    $("#frmUpload").submit();
}
