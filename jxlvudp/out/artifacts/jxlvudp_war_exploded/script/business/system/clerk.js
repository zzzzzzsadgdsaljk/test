/**
 * Created by wph-pc on 2017/10/10.
 */

var jxmstc=new KBOperate("clerk");
layui.use(['layer'],function () {
    var layer=layui.layer;
});
function getCondition() {
    var obj = new Object();
    obj.condition = $("#txtName").val();
    return obj;
}
function find() {
    jxmstc.findForEasyui("dgClerk",getCondition());
}

$(function () {

    find();
    jxmstc.browserForEasyui(function (obj) {
        var toUrl=getRootPath()+"/admin/system/clerk/clerk.jsp?type=2&id="+obj.id;
        layer.open({
            title:'员工信息浏览',
            type: 2,
            area: ['100%', '100%'],
            content: toUrl
        });
    });

    jxmstc.editForEasyui(function (obj) {
        var toUrl=getRootPath()+"/admin/system/clerk/clerk.jsp?type=1&id="+obj.id;
        layer.open({
            title:'员工信息修改',
            type: 2,
            area: ['100%', '100%'],
            content: toUrl
        });
    });
    jxmstc.delForEasyui("您确定删除当前选中的员工信息吗？",function (data) {
        if (data.code>0){
            layer.msg("删除成功！");
            jxmstc.findForEasyui("dgClerk",getCondition());
        }else
        {
            layer.msg("删除失败，数据在使用中...！");
        }
    });

    $("#btnAdd").click(function () {
        var toUrl=getRootPath()+"/admin/system/clerk/clerk.jsp?type=0";
        layer.open({
            title:'员工信息新增',
            type: 2,
            area: ['100%', '100%'],
            content: toUrl
        });
    });

    $("#btnDeleteAll").click(function () {
        var ids=jxmstc.getEasyuiGridSelectRowsID("dgClerk");
        if (ids=="") return;
        var obj=new Object();
        obj.ids=ids;
        jxmstc.do("deleteAll",obj,function (data) {
          if (data.code>0)
          {
             layer.msg("您选中的员工信息已经删除！");
             find();
          }
          else
          {
              layer.msg("您选中的员工信息删除失败，错误代码："+data.code);
          }
        });
    });



    $("#btnFind").click(function () {
        find();
    });
});
/*批量导入*/
$("#test3").click(function () {
    $("#Filedata").click();
});

/*批量导出*/
$("#test4").click(function () {
    $("#txtHCon").val($("#txtName").val());
    $("#fLoadout").form().submit();
});



function doUpload() {
    doUploadFile("uploadfile","frmUpload",null,function (data) {
        if (data!=undefined && data!=null && data.length>0)
        {

            jxmstc.do("/loadData",data[0],function (data) {
                if (data.obj>0)
                    $.messager.alert("提示","员工类型批量导入成功！");
                else
                    $.messager.alert("提示","员工类型批量导入失败！");
            });
        }
        else
            $.messager.alert("提示","导入文件上传失败！");

    });
}
