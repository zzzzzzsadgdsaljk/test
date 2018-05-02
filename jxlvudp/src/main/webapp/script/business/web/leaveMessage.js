/**
 * Created by wph-pc on 2017/8/1.
 */
var baseURL = "leaveMessage";
function  refreshImg() {
    document.getElementById("imgCode").src=getRootPath()+"/imageCode/showcode?timestamp="+(new Date()).getTime();
}

function doSave(obj) {
    /*验证验证码*/
    doData("/imageCode/getCode",null,function (data) {
        if ($("#txtCode").val()!=data.result)
        {
            layer.msg("验证码错误！");
            refreshImg();
            return;
        }

        doData(baseURL+"/add",obj,function (data) {
            if (data.result>0)
            {
                refreshImg();
                layer.msg("已经留言好了！");
                $("#content").val("");
                $("#btnReset").click();

            }
            else
                layer.msg("抱歉，留言失败，错误代码:"+data.result);
        });
    });
}
$(function () {
    layui.use(['layedit','layer','form'], function(){
        var layedit = layui.layedit,layer=layui.layer,form=layui.form;
        layedit.set({
            uploadImage: {
                url: getRootPath()+'/uploadfileForLayUI' //接口url
                , type: 'post' //默认post
            }
        });
        var index=layedit.build('content'); //建立编辑器
        //监听提交
        form.on('submit(formDemo)', function(data){
            var obj = serializeArrayToObject("fLM");//将表单数据进行对象化

            obj.content=layedit.getContent(index);
            if (layedit.getText(index).trim()=="")
            {
                layer.msg("编辑留言内容不能为空！");
                return false;
            }
            doSave(obj);
            return false;
        });
    });

});
