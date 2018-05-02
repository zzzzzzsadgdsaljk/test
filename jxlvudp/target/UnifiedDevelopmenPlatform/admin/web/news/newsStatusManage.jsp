<%--
  Created by IntelliJ IDEA.
  User: wph-pc
  Date: 2017/10/9
  Time: 21:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/header/init.jsp"%>
<html>
<head>
    <title>新闻审核状态管理</title>
    <link href="<%=basePath%>/plugins/layui/css/layui.css" rel="stylesheet" />
    <script src="<%=basePath%>/plugins/layui/layui.js"></script>
</head>
<body>
<div class="layui-table-tool" id="tb" style="padding-left:10px;">

    <input name="status" value="正常"  type="checkbox">正常
    <input name="status" value="关闭" type="checkbox">关闭
    <input type="text"  style="width: 300px; height: 28px;" name="name" id="txtName" required  placeholder="请输新闻标题" autocomplete="off"/>
    <div class="layui-btn-group">
        <button class="layui-btn layui-btn-small" id="btnFind">
            <i class="layui-icon">&#xe615;</i> 搜索
        </button>
        <button class="layui-btn layui-btn-small layui-btn-normal" id="btnSetNormal">
            <i class="layui-icon">&#xe614;</i> 设置正常
        </button>
        <button class="layui-btn layui-btn-small layui-btn-danger" id="btnSetClose">
            <i class="layui-icon">&#xe620;</i> 设置关闭
        </button>
    </div>
</div>
<table class="easyui-datagrid" title="新闻消息状态审核管理" style="height:auto" id="dgNews"
       data-options="rownumbers:true,pagination:true,collapsible:true,striped:true,fit:true,toolbar:'#tb'">
    <thead>
    <tr>
        <th data-options="field:'id1',checkbox:true"></th>
        <th data-options="field:'id',width:80">编号</th>
        <th data-options="field:'name',width:280">标题</th>
        <th data-options="field:'createDate',width:100,formatter: formatDatebox">发布日期</th>
        <th data-options="field:'cateName',width:60">消息分类</th>
        <th data-options="field:'type',width:60" >新闻类别</th>
        <th data-options="field:'status',width:60">状态</th>
        <th data-options="field:'ids',formatter:jxmstc.formateBrowerOperateForEasyui" width="120"
            align="center">操作</th>
    </tr>
    </thead>
</table>

<script src="<%=basePath%>/static/js/business/web/newsStatus.js"></script>

</body>
</html>
