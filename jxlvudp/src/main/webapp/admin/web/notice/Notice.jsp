<%--
  Created by IntelliJ IDEA.
  User: heying
  Date: 2017/10/29
  Time: 19:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/header/init_layui.jsp"%>
<html>
<head>
    <title>通知信息维护</title>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>/plugins/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>/plugins/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript"src="<%=basePath%>/static/js/html5media.min.js"></script>

</head>
<body style="padding-top: 10px">
<form class="layui-form" id="ff">
    <input type="hidden" name="oldId"/>
    <div class="layui-form-item">
        <label class="layui-form-label">通知编号：</label>
        <div class="layui-input-block">
            <input id="id" name="id" required style="width:500px;" lay-verify="required" autocomplete="off" placeholder="请输入通知的编号" class="layui-input" type="text" >
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">通知名称：</label>
        <div class="layui-input-block">
            <input name="name" required style="width:500px;" lay-verify="required" autocomplete="off" placeholder="请输入通知名称" class="layui-input" type="text">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">所属网站：</label>
        <div class="layui-input-block"style="width:500px;">
            <select name="web.id" id="sltWeb"></select>
        </div>
    </div>
    <br/>
    <br/>
    <div class="layui-form-item">
        <label class="layui-form-label">通知内容：</label>
        <div class="layui-input-block" id="pContent" >
            <textarea placeholder="请输入内容"id="myEditor" required name="content" lay-verify="required" autocomplete="off" style="width:97%;height:240px; padding: 20px"type="text" class="layui-textarea"></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">状态:</label>
        <div class="layui-input-block">
            <input name="status" value="正常" title="正常" checked="checked" type="radio">
            <input name="status" value="关闭" title="关闭" type="radio">
        </div>
    </div>
    <div class="layui-form-item" id="dButton">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="formDemo">提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>


<script>
    //实例化编辑器
    var um = UE.getEditor('myEditor');
    var kb=new KBLayUI("notice");
    var isOk =false;
    var type=getURLParamValue("type");//获取操作类型，0表示新增，其他表示修改
    if(type!=0)
        kb.getMe(getURLParamValue("id"),function (data) {
            $('#ff').form('load', data);//导入修改的数据，并绑定在界面上
            um.addListener('ready', function (editor) {
                um.setContent(data.content);
            });

        });
    if (type==2)//表示浏览
    {
        $("#dButton").hide();
    }

    //鼠标离开
    $("#id").blur(function(){
        var id =  $("#id").val();

        if(!(/^[0-9]\d*$/.test(id))){
            layer.msg("编号只能位数字！");
            $("#id").focus();
            $("#id").addClass("layui-form-danger")
            return ;
        }
        kb.getMe(id ,function (data) {
            console.log(data);
            if(data.name!=""){
                layer.msg("当前编号的已存在请重新输入");
                $("#id").focus();
                $("#id").addClass("layui-form-danger");
                isOk = false;
            }else{
                isOk = true;
            }
        });

    })

    kb.doFormLayui("formDemo",function () {
        var obj=serializeArrayToObject("ff");
        obj.content=um.getContent();
        if((/^[0-9]\d*$/.test(obj.id))){

            if (type==0)
                kb.add(obj,function (returnBack) {
                    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                    parent.layer.close(index);
                    parent.find();
                });
            else
                kb.edit(obj,function (returnBack) {
                    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                    parent.layer.close(index);
                    parent.find();
                });

        }else{
            layer.msg("编号只能位数字！");
            $("#id").focus();
            $("#id").addClass("layui-form-danger")
            return ;
        }


    });

    $(function () {
        /*完成站点加载，如果是不是新增，要实现数据选中*/
        doData("webSite/find",null,function (data) {
            if (data!=null && data.obj!=undefined && data.obj!=null)
                for(var i=0;i<data.obj.length;i++)
                {
                    $("#sltWeb").append("<option value='"+data.obj[i].id+"'>"+data.obj[i].name+"</option>");
                }
            if(type!=0)
                kb.getMe(getURLParamValue("id"),function (data) {
                    console.log(data);
                    $("#sltWeb").find("option[value='"+data.web.id+"']").attr("selected","selected");

                    layui.use(['form'],function () {
                        var form=layui.form;
                        form.render('select'); //刷新select选择框渲染
                    });
                });
            layui.use(['form'],function () {
                var form=layui.form;
                form.render('select'); //刷新select选择框渲染
            });
        });
    });



</script>
</body>
</html>
