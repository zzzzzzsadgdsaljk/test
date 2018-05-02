/**
 * 实现各种用户的各种操作
 */
var baseURL = "user";
function format(value, rowData, rowIndex) {
    var str = "<a href='#' type='do'  valueId='" + rowData.sessionID + "' name='" + rowData.loginUser.id +
        "'>强制下线</a>";
    return str;
}
function formatUserNumber(value, rowData, rowIndex) {
    return value.number;
}
function dateFormat(value, rowData, rowIndex) {
    var d=new Date();
    return d.getLongDate(value);
}
function formatUserName(value, rowData, rowIndex) {
    return rowData.loginUser.nickName;
}
function formatType(value,rowData,rowIndex)
{
   if (value==0)
       return "PC设备";
   else if (value==1)
       return "Android移动端";
   else if (value==2)
       return "iOS设备";
   else
       return "其他设备";
}
function doSearch() { 
    find();
}
function find()
{
    var obj = new Object();
    obj.condition = $("#txtName").val();
    findByPage(baseURL+"/watchOnline", "dguser", obj);
}
$(function () {
    find();
    $("a[type=do]").live("click",function () {
        var flag=confirm("强制用户下线后，用户即可终止当前操作，需要重新登录后才能使用系统，确定吗？");
        if (flag==false) return;

        var obj=new Object();
        obj.id=$(this).attr("valueId");//获取在线的用户session的ID
        obj.userId=$(this).attr("name");//用户账号
        doData("user/removeUser",obj,function (data) {
            if (data.code==1)
                find();
            else
                alert("强制用户下线失败，系统返回错误代码："+data.result);
        })

    })
});