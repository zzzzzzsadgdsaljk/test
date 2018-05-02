<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/11/6
  Time: 21:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/header/init_layui.jsp"%>
<html>
<head>
    <title>多条件新闻搜索</title>
    <style>
        .layui-form-item{margin-top:20px;}
        .layui-form-item .layui-input-inline{width: 260px;}
    </style>
</head>
<body>
<div class="layui-row layui-col-space10">
    <div class="layui-col-md12">
        <form class="layui-form" id="NewsSearch">

            <div class="layui-form-item">
                <label class="layui-form-label">新闻版块</label>
                <div class="layui-input-block">
                    <select name="newsBlock" id="newsBlocks"><option value="" selected=""></option></select>
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">发布网站</label>
                <div class="layui-input-block">
                    <select name="web.id" id="newsUrl"><option value="" selected=""></option></select>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">新闻名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="name" id="newsname" placeholder="请输入新闻名称" autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">创建日期</label>
                    <div class="layui-input-inline">
                        <input type="text" name="createDate" id="date1" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">发布日期</label>
                    <div class="layui-input-inline">
                        <input type="text" name="sendDate" id="date2" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">新闻类型</label>
                <div class="layui-input-block">
                    <input type="checkbox" name="type" title="图片新闻" value="图片新闻">
                    <input type="checkbox" name="type" title="视屏新闻" value="视屏新闻">
                    <input type="checkbox" name="type" title="普通新闻" value="普通新闻">
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit="" lay-filter="StartSelect">开始搜索</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>
    <div class="layui-col-md12">
       <!--这里是存放查询到的数据-->

    </div>
</div>
<script>
    var form;
    layui.use(['form', 'layedit', 'laydate','table'], function(){

        find();

        form = layui.form;

        var layer = layui.layer,laydate = layui.laydate,table = layui.table;

        //日期
        laydate.render({
            elem: '#date2'
        });
        laydate.render({
            elem: '#date1'
        });
        //监听提交
        form.on('submit(StartSelect)', function(data){
            layer.alert(JSON.stringify(data.field), {
                title: '最终的提交信息'
            })
            return false;
        });
    });

    //新闻发布来源下来框的查询
    function find(){
        var obj = new Object();
        obj.id = "";
        obj.Condition = "";
        //获取发布的网站
        doData("webSite/find",obj,function (data) {
            if (data == null && data.obj == undefined && data.obj == null) return false;

            for(var i=0;i<data.obj.length;i++)
            {
                $("#newsUrl").append("<option value='"+data.obj[i].id+"'>"+data.obj[i].name+"</option>");
            }
            //开始渲染select下拉框的值
            getSelect(form);
        });
        //新闻版块的select
        doData("newsCategory/find",obj,function (data) {
            if (data == null && data.obj == undefined && data.obj == null) return false;

            for(var i=0;i<data.obj.length;i++)
            {
                $("#newsBlocks").append("<option value='"+data.obj[i].id+"'>"+data.obj[i].name+"</option>");
            }
            //开始渲染select下拉框的值
            getSelect(form);
        });
    };

    function getSelect(form){
        form.render('select');
    };
</script>
</body>
</html>
