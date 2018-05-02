var isAdd=true;//是否新增操作，true表示是，false表示修改；
var kb=new KBLayUI("systemMenu");

function format(value, rowData, rowIndex) {
    var str = "<i class='layui-icon' style='cursor:pointer;color:rgb(0,150,36)' type='browser' valueId='" + rowData.id + "' name='" + rowData.name+"' title='浏览'>&#xe63c;</i>&nbsp;|&nbsp;<i class='layui-icon' style='cursor:pointer;color:rgb(255,184,0)' type='edit' valueId='" + rowData.id + "' name='" + rowData.name+"' title='修改操作'>&#xe642;</i>&nbsp;|&nbsp;<i class='layui-icon' style='cursor:pointer;color:rgb(255,87,34)' type='del' valueId='" + rowData.id + "' name='" + rowData.name+"' title='删除'>&#xe640;</i>";

    return str;
}

function find() {
    var con=new Object();
    con.condition=$("#txtName").val();
    kb.loadTreeGrid("findTree",con,"dgPower",function () {

    });
}

$(function(){
    layui.use("layer",function () {
        var layer=layui.layer;
    });
    find();//数据初始加载

    $("#btnFresh").click(function () {
        var con=new Object();
        con.condition="";
        kb.loadTreeGrid("findTree",con,"dgPower",function () {

        });
    });
    $("#btnFind").click(function () {
       find();
    });
    $("#btnAdd").click(function () {
        var row=$("#dgPower").treegrid("getSelected");//获取当前选中的行
        var toUrl=getRootPath()+"/admin/system/systemMenu/systemMenu.jsp";
        if (row!=null)
            toUrl+='?type=0&parentId='+row.id+'&id='+row.id+'&name='+escape(row.name);
        layer.open({
            title:'权限信息新增',
            type: 2,
            area:[600,400],
            content: toUrl
        });
    });


    $("i[type=browser]").live("click",function () {
        alert("ok");
    });
    $("i[type=edit]").live("click",function () {
        var toUrl=getRootPath()+"/admin/system/systemMenu/systemMenu.jsp?type=1&id="+$(this).attr("valueId");
        layer.open({
            title:'新权限信息修改',
            type: 2,
            area:[600,350],
            content: toUrl
        });
    });
    $("i[type=del]").live("click",function () {
        var flag=confirm("只有当前权限没有子权限，而且当前权限没有被使用才可以删除，删除后数据不可以恢复，确定吗？");
        if (flag)
        {
            var obj=new Object();
            obj.id=$(this).attr("valueId");
            obj.name=$(this).attr("name");
            kb.del(obj,function (data) {
                if (data.code>0)
                     find();
            });
        }
    });
});