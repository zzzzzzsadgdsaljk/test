/**
 * Created by wph-pc on 2017/10/10.
 */

var jxmstc=new KBOperate("news");
layui.use(['layer'],function () {
    var layer=layui.layer;
});
function getCondition() {
    var obj = new Object();
    obj.condition = $("#txtName").val();
    return obj;
}
function find() {
    jxmstc.findForEasyui("dgNews",getCondition());
}

$(function () {

    find();
    jxmstc.browserForEasyui(function (obj) {
        var row=$(obj.button).parent().parent().parent();
        var toUrl="";

        if ($(row).find("td:eq(5)").text()==undefined || $(row).find("td:eq(5)").text()=="") return;

        if ($(row).find("td:eq(5)").text()=="图片新闻")
           toUrl=getRootPath()+"/admin/web/news/imageNews.jsp?type=2&id="+obj.id;
        else
            toUrl=getRootPath()+"/admin/web/news/news.jsp?type=2&id="+obj.id;
        layer.open({
            title:'新闻信息浏览',
            type: 2,
            area:["100%","100%"],
            content: toUrl
        });
    });

    jxmstc.editForEasyui(function (obj) {
        var row=$(obj.button).parent().parent().parent();
        var toUrl="";
        if ($(row).find("td:eq(5)").text()==undefined || $(row).find("td:eq(5)").text()=="") return;

        if ($(row).find("td:eq(5)").text()=="图片新闻")
           toUrl=getRootPath()+"/admin/web/news/imageNews.jsp?type=1&id="+obj.id;
        else
            toUrl=getRootPath()+"/admin/web/news/news.jsp?type=1&id="+obj.id;
        layer.open({
            title:'新闻信息修改',
            type: 2,
            area:["100%","100%"],
            content: toUrl
        });
    });
    jxmstc.delForEasyui("您确定删除当前选中的新闻吗？",function (data) {
        if (data.code>0){
            layer.msg("删除成功！");
            jxmstc.findForEasyui("dgNews",getCondition());
        }else
        {
            layer.msg("删除失败，数据在使用中...！");
        }
    });

    $("#btnAdd").click(function () {
        var toUrl=getRootPath()+"/admin/web/news/news.jsp?type=0";
        layer.open({
            title:'新闻信息新增',
            type: 2,
            area:["100%","100%"],
            content: toUrl
        });
    });
    $("#btnAddImageNews").click(function () {
        var toUrl=getRootPath()+"/admin/web/news/imageNews.jsp?type=0";
        layer.open({
            title:'图片新闻新增',
            type: 2,
            area:["100%","100%"],
            content: toUrl
        });
    });
    $("#btnDeleteAll").click(function () {
        var ids=jxmstc.getEasyuiGridSelectRowsID("dgNews");
        if (ids=="") return;
        var obj=new Object();
        obj.ids=ids;
        jxmstc.do("deleteAll",obj,function (data) {
          if (data.code>0)
          {
             layer.msg("您选中的新闻信息已经删除！");
             find();
          }
          else
          {
              layer.msg("您选中的新闻删除失败，错误代码："+data.code);
          }
        });
    });
    $("#btnFind").click(function () {
        find();
    });
});

