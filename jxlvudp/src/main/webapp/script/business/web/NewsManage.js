/**
 * 实现新闻管理操作
 */
var jxmstc = new KBOperate("news");
layui.use(['layer'],function () {
    var layer=layui.layer;
 /*  //示范一个公告层
    layer.open({
        type: 1
        ,title: "公告：" //显示标题栏
        ,closeBtn: false
        ,area: '300px;'
        ,shade: 0.8
        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
        ,resize: false
        ,btn: ['行了，朕知道了']
        ,btnAlign: 'c'
        ,moveType: 1 //拖拽模式，0或者1
        ,content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">温馨提示：<br>请测试人员尽快测试：邹智敏的新闻管理和资源管理两个模块，认真测试,明天星期五把测试报告交给我！！</div>'
    });*/
});
//自动生成按钮
function format(value, rowData, rowIndex) {
    var str =
        "<a class='layui-btn layui-btn-normal layui-btn-mini' href='#' type='browser' valueId='" + rowData.id + "' name='" + rowData.name +"' newtype='"+rowData.type+"'>浏览</a>" +
        "<a class='layui-btn layui-btn-warm layui-btn-mini' href='#' type='edit' valueId='" + rowData.id + "' name='" + rowData.name +"' newtype='"+rowData.type+"'>修改</a>" +
        "<a class='layui-btn layui-btn-danger layui-btn-mini' href='#' type='del' valueId='" + rowData.id + "' name='" + rowData.name + "' newtype='"+rowData.type+"'>删除</a>";

    return str;
};
//弹窗
function tanchuang(message,url){
    var toUrl=getRootPath()+url;
    layer.open({
        title:message,
        type: 2,
        area:["100%","100%"],
        content: toUrl
    });
}
//后台条件传值
function getCondition() {
    var obj = new Object();
    obj.condition = $("#txtName").val();
    return obj;
};
//查找
function find() {
    jxmstc.findForEasyui("dgNews",getCondition());
};
//信息提示
function tips(id,msg,color){
    //鼠标移动到按钮上的提示框
    $(id).hover(function(){
        //页面一加载刷新按钮就显示提示
        layer.tips(msg, id, {tips: [1, color]});
    },function(){
        layer.closeAll("tips");//关闭提示框
    });
};
//根据新闻类型，判断进入那个页面
function type(obj,videourl,imgurl,normalurl){
//判断新闻的类型是什么？
    var type = obj.attr("newtype");
    if(type == "视屏新闻") tanchuang("视屏新闻浏览/修改",videourl+"?type=1&id="+obj.attr("valueId"));
    if(type == "图片新闻") tanchuang("图片新闻浏览/修改",imgurl+"?type=1&id="+obj.attr("valueId"));
    if(type == "普通新闻") tanchuang("普通新闻浏览/修改",normalurl+"?type=1&id="+obj.attr("valueId"));
};
//拿到删除的数据
function getDel(obj,url,success,erro){
    doData("news"+url,obj,function (result) {
        if (result.code > 0)
        {
            layer.msg(success);
            find();
        }
        else{
            layer.msg(erro);
        }
    });
};

//修改状态
function Getstatus(obj,url){
    doData("news"+url,obj,function (result) {
        if (result.code > 0)
        {
            find();
            layer.msg(result.message);
        }
        else{
            layer.msg(result.message);
        }
    });
};
//拿到所有的id
function getIDs(){
    var objs = $("#dgNews").datagrid("getSelections");
    if (objs == null || objs.length == 0)
    {
        layer.alert("<i class=\"layui-icon\">&#xe6af;</i> 请选择数据.....");
        return false;
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
//批量修改
function plupdate(status){
    var obj=new Object();
    if(getIDs()==false) return false;
    obj.id=getIDs();
    obj.status=status;
    Getstatus(obj,'/updateStatus');
}
$(function () {

    find();

    $("a[type=edit]").live("click",function () {
        type($(this),
            "/admin/web/news/videoNews/VideoNewsAdds.jsp",
            "/admin/web/news/imageNew/ImageNewsAdd.jsp",
            "/admin/web/news/normalNews/normalNewsAdds.jsp");
    });

    //浏览
    $("a[type=browser]").live("click",function () {
        type($(this),
            "/admin/web/news/videoNews/ViewVideoNews.jsp",
            "/admin/web/news/imageNew/ViewImageNews.jsp",
            "/admin/web/news/normalNews/ViewnormalNews.jsp");
    });

    //修改新闻状态
    $("#updataStatus").on("click",function(){
        plupdate("正常");
    });
    //修改新闻状态
    $("#plcolos").on("click",function(){
        plupdate("关闭");
    });

    //视屏新闻新增页面
    $("#videonews").click(function () {
        var toUrl = "/admin/web/news/videoNews/VideoNewsAdds.jsp?type=0";
        tanchuang("视屏新闻信息新增",toUrl);
    });

    //图片新闻新增页面
    $("#imagenews").click(function () {
        var toUrl = "/admin/web/news/imageNew/ImageNewsAdd.jsp?type=0";
        tanchuang("图片新闻信息新增",toUrl);
    });

    //普通新闻新增页面
    $("#normalnews").click(function () {
        var toUrl = "/admin/web/news/normalNews/normalNewsAdds.jsp?type=0";
        tanchuang("普通新闻信息新增",toUrl);
    });

    // 刷新页面
    $("#btnfresh").click(function(){
        find();
    });

    //删除
    $("a[type=del]").live("click", function () {
        var obj = new Object();
        obj.id = $(this).attr("valueId");
        layer.confirm("确定要删除这条数据吗？", {
            btn: ['确定','取消'] //按钮
        }, function(){
            //点击确定按钮
            getDel(obj,'/del',"删除成功！！","删除失败！！");
        }, function(){
            //点击取消按钮

        });
    });

    //批量删除操作
    $("#btnDeleteAll").click(function () {

        if(getIDs()==false) return false;
        layer.confirm("确定要删除这条数据吗？", {
            btn: ['确定','取消'] //按钮
        }, function(){
            //点击确定按钮
            var obj = new Object();
            obj.ids = getIDs();
            getDel(obj,'/deleteAll',"批量删除成功！","批量删除失败");
        }, function(){
            //点击取消按钮

        });
    });

    $("#Search").on("click",function(){
        var text = $("#txtName").val();
        find();
    });

    //搜索
    tips("#Search",'温馨提示：输入框中输入新闻名称可点击搜索按钮，如需要其他搜索，请先清空输入框中的值，在点击搜索按钮！！',"#5fb878");
    //刷新按钮显示提示框
    tips("#btnfresh",'刷新当前页',"#5fb878");
    //视屏新增按钮显示提示框
    tips("#videonews",'视屏新闻新增',"#1e9fff");
    //图片新增按钮显示提示框
    tips("#imagenews",'图片新闻新增',"#ffb800");
    //普通新闻按钮显示提示框
    tips("#normalnews",'普通新闻新增',"#009688");
    //批量发布显示提示框
    tips("#updataStatus",'提示：批量发布就是修改新闻的状态，新闻新增默认是关闭的，要把新闻发布出去，但一条一条发布效率太低，此按钮可以完成批量发布功能',"#009688");
    //普通新闻按钮显示提示框
    tips("#plcolos",'提示：批量关闭就是修改新闻的状态，新闻发布后，一条一条去关闭新闻，效率太低，故此按钮可以完成批量取消新闻发布功能',"#009688");
    //批量删除按钮显示提示框
    tips("#btnDeleteAll",'温馨提示：批量删除前，请选择需要删除的数据，删除操作才能正常工作 ，删除后数据将不能恢复！！',"#FF5722");
});

