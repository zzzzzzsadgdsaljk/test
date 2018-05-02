<%--
  Created by IntelliJ IDEA.
  User: wph-pc
  Date: 2017/10/12
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/header/init_layui.jsp"%>
<html>
<head>
    <title>网站信息维护</title>
</head>
<body style="padding-top: 10px;">
<form class="layui-form" id="ff">
    <input type="hidden" name="oldId"/>
    <div class="layui-form-item">
        <label class="layui-form-label">网站编号：</label>
        <div class="layui-input-block">
            <input name="id" required  lay-verify="required|number" autocomplete="off" placeholder="请输入网站的编号，要求5位以内的数字" class="layui-input" type="text" maxlength="5">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">网站名称：</label>
        <div class="layui-input-block">
            <input name="name" required  lay-verify="required" autocomplete="off" placeholder="请输入网站名称" class="layui-input" type="text">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">首页地址：</label>
        <div class="layui-input-block">
            <input name="indexAddress" required  lay-verify="url" autocomplete="off" placeholder="请输入网站名称" class="layui-input" type="text">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">自动接收:</label>
        <div class="layui-input-block">
            <input name="isAutoAcceptTopMSG" value="true" title="是" checked="" type="radio">
            <input name="isAutoAcceptTopMSG" value="false" title="否" type="radio">
            <span style="color:rgb(62,182,94)">(备注：是否自动接收主站信息)</span>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">自动发送:</label>
        <div class="layui-input-block">
            <input name="isNeedCheckToTopMSG" value="true" title="是" checked="" type="radio">
            <input name="isNeedCheckToTopMSG" value="false" title="否" type="radio">
            <span style="color:rgb(62,182,94)">(备注：是否允许向主站发送信息)</span>
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
    var kb=new KBLayUI("webSite");
    var parentId=getURLParamValue("parentId");
    var type=getURLParamValue("type");//获取操作类型，0表示新增，其他表示修改

    if(type!=0)
        kb.getMe(getURLParamValue("id"),function (data) {
            $('#ff').form('load', data);//导入修改的数据，并绑定在界面上
        });
    if (type==2)//表示浏览
    {
        $("#dButton").hide();
    }

    kb.doFormLayui("formDemo",function () {
        var obj=serializeArrayToObject("ff");

        if (type==0)
            kb.add(obj,function (returnBack) {});
        else
            kb.edit(obj,function (returnBack) { });

        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        parent.layer.close(index);
        parent.find();
    });


</script>
</body>
</html>
