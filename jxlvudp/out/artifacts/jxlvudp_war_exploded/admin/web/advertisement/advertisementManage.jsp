<%--
  Created by IntelliJ IDEA.
  User: wph-pc
  Date: 2017/7/29
  Time: 14:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/header/init.jsp"%>
<html>
<head>
    <title>广告综合管理平台</title>
    <link href="<%=basePath%>/plugins/layui/css/layui.css" rel="stylesheet" />
    <script src="<%=basePath%>/plugins/layui/layui.js"></script>
</head>
<body>
<div class="layui-table-tool" id="tb">
    <button class="layui-btn layui-btn-small" id="btnAdd" >
        <i class="layui-icon">&#xe609;</i> 发布广告
    </button>
    <input type="text"  style="width: 300px; height: 28px;" name="name" id="txtName" required  placeholder="请输广告名称" autocomplete="off"/>
    <div class="layui-btn-group">
        <button class="layui-btn layui-btn-small" id="btnFind">
            <i class="layui-icon">&#xe615;</i> 搜索
        </button>
        <button class="layui-btn layui-btn-small layui-btn-warm" id="btnDeleteAll">
            <i class="layui-icon">&#xe640;</i> 批量删除
        </button>
        <button class="layui-btn layui-btn-small" id="btnLoadout">
            <i class="layui-icon">&#xe61e;</i> 导出
        </button>
    </div>
</div>

<table class="easyui-datagrid" title="广告信息管理" style="height:auto" id="dgAdvertisement"
       data-options="rownumbers:true,pagination:true,collapsible:true,striped:true,fit:true,toolbar:'#tb'">
    <thead data-options="frozen:true">
    <tr>
        <th data-options="field:'id',width:80">广告编号</th>
        <th data-options="field:'name',width:200">广告名称</th>
    </tr>
    </thead>
    <thead>
    <tr>
        <th data-options="field:'imgAddress',width:200,formatter:formatImage">广告图片</th>
        <th data-options="field:'linkUrl',width:200">图片链接地址</th>
        <th data-options="field:'homeUrl',width:200">广告所在页面</th>
        <th data-options="field:'type',width:100">广告类别</th>
        <th data-options="field:'posX',width:60">位置X</th>
        <th data-options="field:'posY',width:60">位置Y</th>
        <th data-options="field:'width',width:60">宽度</th>
        <th data-options="field:'height',width:60">高度</th>
        <th data-options="field:'duration',width:60,formatter:formatDuration">显示</th>
        <th data-options="field:'webSiteName',width:200">所属网站</th>
        <th data-options="field:'createDate',width:100,formatter: formatDatebox">创建日期</th>
        <th data-options="field:'status',width:80,formatter:formatStatus">状态</th>
        <th data-options="field:'ids',formatter:jxmstc.formateOperateForEasyui" width="120"
            align="center">操作</th>
    </tr>
    </thead>
</table>
<script src="<%=basePath%>/script/business/web/advertisementManage.js"></script>
</body>
</html>
