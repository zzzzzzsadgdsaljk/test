<%--
  Created by IntelliJ IDEA.
  User: 邹智敏
  Date: 2017/10/29
  Time: 11:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/header/init_layui.jsp"%>
<html>
<head>
    <title>图片新闻浏览</title>
    <style type="text/css">
        .title{width:100%;height: 100px;margin-top:50px;text-align: center;}
        .title span{font-size: 24px;display: block;}
        .content{width: 100%;text-indent: 2em}
    </style>
</head>
<body>
<ul class="layui-nav">
    <li class="layui-nav-item"><a href="">普通新闻</a></li>
</ul>
<div class="layui-container">
    <div class="layui-row">
        <div class="layui-col-md12" id="content">

        </div>
    </div>
</div>
<script>
    var kb=new KBLayUI("news");
    var id=getURLParamValue("id");
    kb.getMe(id,function (data) {
        console.log(data);
        if(data==null || data=="") {
            alert("没有新闻内容，无法进行浏览，请换一个进行浏览吧！！");
            return false;
        }
        $("#content").append("<div class='title'>"+
            "                <span style='margin:20px;'>"+data.name+"</span>"+
            "                <span style='font-size: 12px;'>发布日期："+formatDatebox(data.sendDate)+"；发布来源：<a href='#'>"+data.web.name+"</a></span>"+
            "            </div>"+
            "            <div class='content'>"+data.content+"</div>");
    });
</script>
</body>
</html>
