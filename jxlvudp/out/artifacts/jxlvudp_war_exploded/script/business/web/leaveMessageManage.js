/**
 * Created by wph-pc on 2017/8/2.
 */
var baseURL = "leaveMessage";
jQuery.fn.pager =function (pageCount,callback,previewCallback,nextCallback) {
    var currentIndex=-1;//当前页码索引值；
    var page=$(this).find(" .pagination");//获取bootstrap结构的翻页控件
    $(page).empty();
    /*初始化翻页控件*/
    if (pageCount<=5)
    {
        for(var i=0;i<pageCount;i++)
           $(page).append("<li><a href='javascript:void(0)'>"+(i+1)+"</a></li>");
    }
    else
    {
        $(page).append("<li class='disabled'><a type='preview' href='javascript:void(0)' aria-label='Previous'><span aria-hidden='true'>«</span></a></li>");//第一个向前按钮
        /*前5个有数字按钮*/
        for(var i=0;i<5;i++)
            $(page).append("<li><a href='javascript:void(0)'>"+(i+1)+"</a></li>");
        /*中间省略号*/
        $(page).append("<li class='disabled'><a href='javascript:void(0)'>...</a></li>");

        $(page).append("<li><a type='next' href='javascript:void(0)' aria-label='Next'><span aria-hidden='true'>»</span></a></li>");

    }
    $($(page).find("a[aria-label!=Previous][aria-label!=Next]")[0]).parent().addClass("active");//默认第一个选中

    $(page).on("click","a[aria-label!=Previous][aria-label!=Next]",function () {
        $(page).find("li").removeClass("active");
        $(this).parent().addClass("active");
        currentIndex=$(this).text();
        callback(currentIndex);
    });

    $(page).on("click","a[aria-label=Previous]",function () {
        //获取第一个按钮的数字
        if ( $($("a[aria-label!=Previous][aria-label!=Next]")[0]).text()!="1")
        {

        }
        previewCallback();
    });

    $(page).on("click","a[aria-label=Next]",function () {
        nextCallback();
    });

}
    function createItem(item) {
    var content="<div class='row'>"+
            "<div class='col-md-12'>"+
            "<div class='page-header'>"+
            "<div class='row'>"+
            "<div class='col-sm-8'><h3>"+item.name+ "<small>&nbsp;&nbsp;"+(new Date()).getLongDate(item.createDate) +"</small></h3></div>"+
            "<div class='col-sm-4' style='text-align: right;'><div style='line-height: 56px;'>"+
            "<button type='button' action='del' delObjName='"+item.name+"' delObjID='"+item.id+"' class='btn btn-warning btn-xs' data-toggle='modal' data-target='#myModal'><span class='glyphicon glyphicon-remove' aria-hidden='true'></span>删除</button>";
    if (item.response==undefined || item.response==null || item.response=="") {
        content += "&nbsp;<button type='button' action='response' rObjName='" + item.name + "' rObjID='" + item.id + "' class='btn btn-info btn-xs' data-toggle='modal' data-target='#responseModal'><span class='glyphicon glyphicon-comment' aria-hidden='true'></span> 回复</button>";
        content+="</div></div></div></div></div>";
        content+="<div class='row'><div class='col-md-12'> <p>"+item.content+"</p></div></div>";
    }
    else
    {
        content+="</div></div></div></div></div>";
        content+="<div class='row'><div class='col-md-12'><p>"+item.content+"</p></div></div>";
        /*已经回复显示*/
        content+="<div class='row'><div class='col-md-12'><div class='page-header'><div class='row'><div class='col-sm-12'><h3><span class='glyphicon glyphicon-comment text-info' aria-hidden='true'></span><small class='text-info'>&nbsp;&nbsp;"+(new Date()).getLongDate(item.responseDate) +"</small></h3></div></div></div></div></div>";
        content+="<div class='row'><div class='col-md-12'><p>"+item.response+"</p></div></div>";

    }


    return content+"<hr style='background-color:#0b7eff;height:1px;border:none;''>";
}

var recordTotal=0;
function find(pageNumber,rowsCount)
{
    var obj = new Object();
    obj.condition = $("#txtName").val();
    findDataByPageAndSync(baseURL+"/findByPage",pageNumber,rowsCount, obj,function (data) {

        if (data!=null && data.rows.length>0)
        {
            $("#dContent").html("");
            for(var i=0;i<data.rows.length;i++)
            {
                $("#dContent").append(createItem(data.rows[i]));
            }
            recordTotal=data.total;
        }
        else
        {
            $("#dContent").html("抱歉，没有符合您要求的数据!");
            recordTotal=0;
        }
    });
}

$(function () {
    find(1,1);
    $("#btnFind").click(function () {
        find(1,1);
    });

    $("#dContent").on("click","button[action=del]",function() {
        $("#dDelMsg").text("您正在准备删除留言信息【"+$(this).attr("delObjName")+"】，删除后数据不可回复，确定吗？");
        $("#txtLM").val($(this).attr("delObjID"));
    });
    $("#dContent").on("click","button[action=response]",function() {
        $("#txtRLM").val($(this).attr("rObjID"));
        $("#lblResponse").text("回复【"+$(this).attr("rObjName")+"】留言");
        $('#responseModal').on('show.bs.modal', function () {
            $("#txtResponse").val("");
        });
    });
    $("#btnDel").on("click",function() {
        var obj=new Object();
        obj.id= $("#txtLM").val();
        doData(baseURL+"/del",obj,function (data) {
            $('#myModal').modal('hide');
            if (data.result>0)
            {
                find();
                $('#alert-warning').removeClass("alert-warning");
                $('#alert-warning').addClass("alert-success");
                $("#sTitle").text("提示");
                $("#pTips").text("您选中的【留言】信息已经删除！");
                $('#alert-warning').removeClass("hide");
                window.setTimeout(function(){
                    $('#alert-warning').addClass("hide");
                },2000);
            }
            else
            {
                $("#sTitle").text("警告");
                $("#pTips").text("当前删除失败，错误码："+data.result);
                $('#alert-warning').removeClass("hide");
                window.setTimeout(function(){
                    $('#alert-warning').addClass("hide");
                },2000);
            }
        });
    });

    $("#btnReponse").on("click",function () {
        if ($("#txtResponse").val().trim().length==0)
        {
            $('#alert').removeClass("hide");
            window.setTimeout(function(){
                $('#alert').addClass("hide");
            },2000);
            return;
        }
        var obj=new Object();
        obj.id= $("#txtRLM").val();
        obj.response=$("#txtResponse").val();
        doData(baseURL+"/edit",obj,function (data) {
            $('#responseModal').modal('hide');
            if (data.result>0)
            {
                find();
                $('#alert-warning').removeClass("alert-warning");
                $('#alert-warning').addClass("alert-success");
                $("#sTitle").text("提示");
                $("#pTips").text("回复成功！");
                $('#alert-warning').removeClass("hide");
                window.setTimeout(function(){
                    $('#alert-warning').addClass("hide");
                },2000);
            }
            else
            {
                $("#sTitle").text("警告");
                $("#pTips").text("回复失败，错误码："+data.result);
                $('#alert-warning').removeClass("hide");
                window.setTimeout(function(){
                    $('#alert-warning').addClass("hide");
                },2000);
            }
        });
    });

    $("#pager").pager(recordTotal/1,function (pageNumber) {
        find(pageNumber,1);
    },function () {

    },function () {

    });
})