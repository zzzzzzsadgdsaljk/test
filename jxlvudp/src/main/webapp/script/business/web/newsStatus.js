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
    obj.status=getCheckboxValue("status");
    return obj;
}
function find() {
    jxmstc.findForEasyui("dgNews",getCondition());
}

function changeStatus(status) {
    var ids=jxmstc.getEasyuiGridSelectRowsID("dgNews");
    if (ids=="")
    {
        return;
    }
    var obj=new Object();
    obj.status=status;
    obj.id=ids;
    jxmstc.do("changeStatus",obj,function (data) {
        if (data.code>0)
        {
            layer.msg("您选中的新闻数据行【"+status+"】变更成功！")
            find();
        }
        else
            layer.msg("您选中的新闻数据行【"+status+"】状态转换失败！，错误代码："+data.code);
    });

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

    $("#btnSetNormal").click(function () {
        changeStatus("正常");
    });
    $("#btnSetClose").click(function () {
        changeStatus("关闭");
    });
    $("#btnFind").click(function () {
        find();
    });
});

