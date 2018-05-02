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
    <style>
        img{width:100%;height:93%;}
    </style>
</head>
<body>
<div class="layui-carousel" id="benner">
    <div carousel-item="" id="bennerImg"></div>
</div>
<script>
    var options ={
        elem: '#benner'
        ,width: '100%'
        ,height: '100%'
        ,interval: 5000
        ,anim:"fade"
        ,indicator:"none"
    };
    layui.use(['carousel'], function(){
        var carousel = layui.carousel;
        //图片轮播
        var ins = carousel.render(options);
        dodata(ins);
    });
    function dodata(ins){
        var obj = new Object();
        obj.id = getURLParamValue("id");
        //开始遍历图片新闻
        doData("news/SelectImageNews",obj,function (result) {
            if (result == null || result.length <= 0) return false;
            var html = "";
            for (var i = 0; i < result.obj.length; i++) {
                var img_num = result.obj.length;
                html += "<div><img src='"+getRootPath()+"/uploadfiles/"+result.obj[i].imageAddress+"'/><span>"+result.obj[i].description+"&nbsp;&nbsp;"+(i+1)+"/"+img_num+"</span></div>";
            }
            $("#benner #bennerImg").append(html);
            //轮播图重置
            ins.reload(options);
        });
    };
</script>
</body>
</html>
