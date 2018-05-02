/**
 * Created by wph-pc on 2017/10/10.
 */

var jxmstc=new KBOperate("webSite");
layui.use(['layer'],function () {
    var layer=layui.layer;
});
function getCondition() {
    var obj = new Object();
    obj.condition = $("#txtName").val();
    return obj;
}
function find() {
    jxmstc.findForEasyui("dgWebSite",getCondition());
}

$(function () {

    find();
    jxmstc.browserForEasyui(function (obj) {
        var toUrl=getRootPath()+"/admin/web/website/website.jsp?type=2&id="+obj.id;
        layer.open({
            title:'网站信息浏览',
            type: 2,
            area:[600,350],
            content: toUrl
        });
    });

    jxmstc.editForEasyui(function (obj) {
        var toUrl=getRootPath()+"/admin/web/website/website.jsp?type=1&id="+obj.id;
        layer.open({
            title:'网站信息修改',
            type: 2,
            area:[600,350],
            content: toUrl
        });
    });
    jxmstc.delForEasyui("您确定删除当前选中的站点吗？",function (data) {
        if (data.code>0){
            layer.msg("删除成功！");
            jxmstc.findForEasyui("dgWebSite",getCondition());
        }else
        {
            layer.msg("删除失败，数据在使用中...！");
        }
    });

    $("#btnAdd").click(function () {
        var toUrl=getRootPath()+"/admin/web/website/website.jsp?type=0";
        layer.open({
            title:'网站信息新增',
            type: 2,
            area:[600,350],
            content: toUrl
        });
    });

    $("#btnDeleteAll").click(function () {
        var ids=jxmstc.getEasyuiGridSelectRowsID("dgWebSite");
        if (ids=="") return;
        var obj=new Object();
        obj.ids=ids;
        jxmstc.do("deleteAll",obj,function (data) {
          if (data.code>0)
          {
             layer.msg("您选中的站点已经删除！");
             find();
          }
          else
          {
              layer.msg("您选中的站点删除失败，错误代码："+data.code);
          }
        });
    });
    $("#btnFind").click(function () {
        find();
    });
});

