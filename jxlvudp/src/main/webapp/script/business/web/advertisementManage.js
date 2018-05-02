/**
 * 实现角色类型各种操作
 */

var jxmstc=new KBOperate("advertisement");
layui.use(['layer'],function () {
    var layer=layui.layer;
});
function getCondition() {
    var obj = new Object();
    obj.condition = $("#txtName").val();
    return obj;
}

function formatImage(value, rowData, rowIndex) {
    if (value!=null && value!="")
        return "<img src='"+getRootPath()+"/uploadfiles/"+value+"' width='60px' height='40px'/>";
    else
       return "";
}
function formatDuration(value, rowData, rowIndex) {
    if (value==-1)
        return "<span style='color:red;'>永久显示</span>";
    else
        return value+"(秒)";
}

function formatStatus(value, rowData, rowIndex) {
    if (value=="正常")
        return "<input type='switchbutton' id='"+rowData.id+"' adType='"+rowData.type+"' class='easyui-switchbutton' checked>";
    else
        return "<input type='switchbutton' id='"+rowData.id+"' adType='"+rowData.type+"' class='easyui-switchbutton'>";
}
function doSearch() { 
    find();
}

function find()
{
    var obj = new Object();
    obj.condition = $("#txtName").val();
    findByPage(jxmstc.baseURL+"/findByPage", "dgAdvertisement", obj,function () {
        $("input[type=switchbutton]").switchbutton({onChange: function(checked){
            var temp=$(this);
            if (checked == true){//选中
                var obj=new Object();
                obj.id=$(temp).attr("id");
                obj.type=$(temp).attr("adType");
                obj.status="正常";
                doData(jxmstc.baseURL+"/edit",obj,function (result) {
                    if (result.result<0)
                    {
                        $(temp).attr("checked",false);
                        alert("系统开启失败！");
                    }
                });

            }
            if (checked == false){
                var obj=new Object();
                obj.id=$(temp).attr("id");
                obj.type=$(temp).attr("adType");
                obj.status="关闭";
                doData(jxmstc.baseURL+"/edit",obj,function (result) {
                    if (result.result<0)
                    {
                        $(temp).attr("checked",true);
                        alert("系统关闭失败！");
                    }
                });
            }}
        });
    });

}

$(function () {

    find();
    jxmstc.browserForEasyui(function (obj) {
        var toUrl=getRootPath()+"/admin/web/advertisement/advertisement.jsp?type=2&id="+obj.id;
        layer.open({
            title:'广告信息浏览',
            maxmin: true,
            type: 2,
            area:[750,450],
            content: toUrl
        });
    });

    jxmstc.editForEasyui(function (obj) {
        var toUrl=getRootPath()+"/admin/web/advertisement/advertisement.jsp?type=1&id="+obj.id;
        layer.open({
            title:'广告信息修改',
            maxmin: true,
            type: 2,
            area:[750,450],
            content: toUrl
        });
    });
    jxmstc.delForEasyui("您确定删除当前选中的广告吗？",function (data) {
        if (data.code>0){
            find();
            layer.msg("删除成功！");
        }else
        {
            layer.msg("删除失败，数据在使用中...！");
        }
    });

    $("#btnAdd").click(function () {
        var toUrl=getRootPath()+"/admin/web/advertisement/advertisement.jsp?type=0";
        layer.open({
            title:'广告信息发布',
            maxmin: true,
            type: 2,
            area:[750,450],
            content: toUrl
        });
    });

    $("#btnDeleteAll").click(function () {
        var ids=jxmstc.getEasyuiGridSelectRowsID("dgAdvertisement");
        if (ids=="") return;
        var obj=new Object();
        obj.ids=ids;
        jxmstc.do("deleteAll",obj,function (data) {
            if (data.code>0)
            {
                layer.msg("您选中的广告已经删除！");
                find();
            }
            else
            {
                layer.msg("您选中的广告删除失败，错误代码："+data.code);
            }
        });
    });
    $("#btnFind").click(function () {
        find();
    });
});


 