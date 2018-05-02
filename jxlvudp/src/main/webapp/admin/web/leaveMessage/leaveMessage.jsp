<%--
  Created by IntelliJ IDEA.
  User: wph-pc
  Date: 2017/8/1
  Time: 14:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/header/init_layui.jsp"%>

<html>
<head>
    <title>留言板讯息</title>
</head>
<body style="padding-top:20px;">
<form id="fLM" class="layui-form">
    <div class="layui-form-item">
        <label class="layui-form-label">留言标题：</label>
        <div class="layui-input-block">
            <input type="text" name="name" maxlength="20" required  lay-verify="required" placeholder="请输入标题(5-20个字)" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
           <textarea id="content" name="content"  placeholder="请填写留言信息(500字以内)" style="display: none;" maxlength="500"></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">验证码：</label>
        <div class="layui-input-block">
            <input type="text" maxlength="4" id="txtCode" required  lay-verify="required"   name="ht_dl_yzm" style="width:60px;height: 30px;line-height:30px;"  placeholder="验证码"/>&nbsp;&nbsp;&nbsp;
            <img id="imgCode" border=0 src="<%=basePath%>/imageCode/showcode" width="60" height="30" style="cursor:pointer;line-height: 30px;" onclick="refreshImg()" alt="验证码" title="单击刷新验证码">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" id="btnSave" lay-submit lay-filter="formDemo">提交留言</button>
            <button type="reset" id="btnReset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
<script src="<%=basePath%>/script/common/jquery.form.js"></script>
<script src="<%=basePath%>/script/business/web/leaveMessage.js"></script>

</body>
</html>
