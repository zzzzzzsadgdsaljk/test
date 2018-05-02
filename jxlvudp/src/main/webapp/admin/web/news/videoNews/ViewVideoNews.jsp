<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../../../header/init_layui.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>新闻展示</title>
    <style type="text/css">
        span{
                font-size:20px;
                margin:20px;
                display: block;
            }
    </style>
</head>
<body>
<ul class="layui-nav">
    <li class="layui-nav-item"><a href="">视屏新闻</a></li>
</ul>
<div class="layui-container" style="margin-top:50px;padding:5px;">
    <div class="layui-row">
        <div class="layui-col-md12" id="content"></div>
    </div>
</div>
<script>
    var kb=new KBLayUI("news");
    var id=getURLParamValue("id");
    kb.getMe(id,function (data) {
        if(data==null || data=="") {
            alert("没有新闻内容，无法进行播放，请换一个进行浏览吧！！")
            return false;
        }
        $("#content").append("<video id='video' src="+getRootPath()+"/uploadfiles/"+data.videoAddress+" width='100%' height='450' style='background: rgba(0,0,0,0.5);' controls='controls'>"+
            "                                您的浏览器不支持html5,请换一个支持html5的浏览器;如：火狐,谷歌"+
            "                 </video>"+
            "                 <span>"+data.name+"</span>"+
            "                 <span style='font-size: 12px;'>发布日期："+formatDatebox(data.sendDate)+"&nbsp;&nbsp;&nbsp;&nbsp;来源：<a href='#'>"+data.web.name+"</a></span>"+
            "                 <div>"+data.content+"</div>"
            );
    });
</script>
</body>
</html>
