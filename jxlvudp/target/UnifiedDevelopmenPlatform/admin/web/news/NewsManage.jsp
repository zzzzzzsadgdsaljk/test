<%--
  Created by IntelliJ IDEA.
  User: 邹智敏
  Date: 2017/10/25
  Time: 14:32
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../../header/init.jsp"%>
<html>
<head>
    <title>新闻管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="<%=basePath%>/plugins/layui/css/layui.css" rel="stylesheet" />
    <script src="<%=basePath%>/plugins/layui/layui.js"></script>
    <style>
        .layui-table-tool{padding: 0px 0px 0px 0px;}
        .layui-btn+.layui-btn{margin-left: 1px;}
        .layui-btn-small{margin: 4px;}
        .datagrid-htable, .datagrid-btable, .datagrid-ftable{text-align:center;}
    </style>
</head>
<body>
<div class="layui-table-tool" id="tb">

    <button class="layui-btn layui-btn-small" id="normalnews" >
        <i class="layui-icon">&#xe705;</i> 普通新闻
    </button>

    <button class="layui-btn layui-btn-normal layui-btn-small" id="videonews" >
        <i class="layui-icon">&#xe660;</i> 视屏新闻
    </button>

    <button class="layui-btn layui-btn-warm layui-btn-small" id="imagenews" >
        <i class="layui-icon">&#xe634;</i> 图片新闻
    </button>

    <input type="text"  style="width: 250px; height:30px;padding-left: 5px;" name="name" id="txtName" required  placeholder="请输新闻名称....."/>
    <button class="layui-btn layui-btn-small" id="Search">
        <i class="layui-icon">&#xe615;</i> 搜索
    </button>

    <button class="layui-btn layui-btn-primary layui-btn-small" id="btnfresh" lay-filter="btnfreshs">
        <i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#x1002;</i> 刷新页面
    </button>

    <button class="layui-btn layui-btn-normal layui-btn-small" id="updataStatus" >
        <i class="layui-icon">&#xe609;</i> 批量审批
    </button>
    <button class="layui-btn layui-btn-warm layui-btn-small" id="plcolos">
        <i class="layui-icon">&#xe609;</i> 批量关闭
    </button>

    <button class="layui-btn layui-btn-small layui-btn-danger" id="btnDeleteAll">
        <i class="layui-icon">&#xe640;</i> 批量删除
    </button>
</div>
<table class="easyui-datagrid" title="新闻管理" style="height:auto" id="dgNews"
       data-options="rownumbers:true,pagination:true,collapsible:true,striped:true,fit:true,toolbar:'#tb'">
    <thead>
    <tr>
        <th data-options="field:'id',width:80">新闻编号</th>
        <th data-options="field:'name',width:300">新闻名称</th>
        <th data-options="field:'type',width:100">新闻类型</th>
        <th data-options="field:'cateName',width:100">新闻所属版块</th>
        <th data-options="field:'webName',width:200">发布网站</th>
        <th data-options="field:'sendDate',width:100,formatter:formatDatebox">发布日期</th>
        <th data-options="field:'status',width:80">状态</th>
        <th data-options="field:'ids',formatter:format" width="120" align="center">操作</th>
    </tr>
    </thead>
</table>
<script src="<%=basePath%>/script/business/web/NewsManage.js"></script>
</body>
</html>
