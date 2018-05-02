/**
 * Created by wph-pc on 2017/10/10.
 */

var kb=new KBLayUI("newsCategory");
function format(value, rowData, rowIndex) {
    var str = "<i class='layui-icon' style='cursor:pointer;color:rgb(0,150,36)' type='browser' valueId='" + rowData.id + "' name='" + rowData.name+"' title='浏览'>&#xe63c;</i>&nbsp;|&nbsp;<i class='layui-icon' style='cursor:pointer;color:rgb(255,184,0)' type='edit' valueId='" + rowData.id + "' name='" + rowData.name+"' title='修改操作'>&#xe642;</i>&nbsp;|&nbsp;<i class='layui-icon' style='cursor:pointer;color:rgb(255,87,34)' type='del' valueId='" + rowData.id + "' name='" + rowData.name+"' title='删除'>&#xe640;</i>";

    return str;
}
function formatWeb(value, rowData, rowIndex) {
    return value.name;
}

layui.use(['layer'],function () {
    var layer=layui.layer;
});
function getCondition() {
    var obj = new Object();
    obj.condition = $("#txtName").val();
    return obj;
}
function find(condition) {
    kb.loadTreeGrid("findTree",condition,"dgNewsCategory",function () {

    });
}

$(function () {
    layui.use("layer",function () {
        var layer=layui.layer;
    });
    find(getCondition());//数据初始加载

    $("#btnFresh").click(function () {
        find(null);
    });
    $("#btnFind").click(function () {
        find(getCondition());
    });
    $("#btnAdd").click(function () {
        var row=$("#dgNewsCategory").treegrid("getSelected");//获取当前选中的行

        var toUrl=getRootPath()+"/admin/web/newscategory/newscategory.jsp";
        if (row!=null)
            toUrl+='?type=0&parentId='+row.id+'&id='+row.id+'&name='+escape(row.name);
        layer.open({
            title:'新闻分类信息新增',
            type: 2,
            area:[600,400],
            content: toUrl
        });
    });


    $("i[type=browser]").live("click",function () {
        var toUrl=getRootPath()+"/admin/web/newscategory/newscategory.jsp?type=2&id="+$(this).attr("valueId");
        layer.open({
            title:'新闻分类信息浏览',
            type: 2,
            area:[600,350],
            content: toUrl
        });
    });
    $("i[type=edit]").live("click",function () {
        var toUrl=getRootPath()+"/admin/web/newscategory/newscategory.jsp?type=1&id="+$(this).attr("valueId");
        layer.open({
            title:'新闻分类信息修改',
            type: 2,
            area:[600,350],
            content: toUrl
        });
    });
    $("i[type=del]").live("click",function () {
        var flag=confirm("只有当前新闻分类没有子类，而且没有被使用才可以删除，删除后数据不可以恢复，确定吗？");
        if (flag)
        {
            var obj=new Object();
            obj.id=$(this).attr("valueId");
            obj.name=$(this).attr("name");
            kb.del(obj,function (data) {
                if (data.code>0)
                    find(null);
            });
        }
    });

});

