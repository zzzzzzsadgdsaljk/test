<%--
  Created by IntelliJ IDEA.
  User: wph-pc
  Date: 2017/9/26
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/header/init.jsp"%>
<html>
<head>
    <title>组织机构管理</title>
    <link href="<%=basePath%>/plugins/layui/css/layui.css" rel="stylesheet" />
    <script src="<%=basePath%>/plugins/layui/layui.js"></script>
    <script src="<%=basePath%>/script/common/kb.js"></script>
</head>
<body>
<div id="tb" class="layui-fluid" style="height: 40px; line-height:40px;">
    <div class="layui-row">
        <div class="layui-col-md12">
            <shiro:hasPermission name="/org/add">
                <button class="layui-btn layui-btn-small" id="btnAdd" >
                    <i class="layui-icon">&#xe608;</i> 添加
                </button>
            </shiro:hasPermission>
           <shiro:hasPermission name="/org/findTree">
               <input type="text"  style="width: 300px; height: 30px;" name="title" id="txtName" required  lay-verify="required" placeholder="请输权限名称" autocomplete="off"/>
           <div class="layui-btn-group">
                   <button class="layui-btn layui-btn-small" id="btnFind">
                       <i class="layui-icon">&#xe615;</i> 搜索
                   </button>
                <button class="layui-btn layui-btn-small layui-btn-normal" id="btnFresh">
                    <i class="layui-icon">&#x1002;</i> 刷新
                </button>
            </div>
           </shiro:hasPermission>
        </div>
    </div>
</div>
<table class="easyui-treegrid" title="组织机构管理" style="height:auto" id="dgOrg"
       data-options="rownumbers:true,singleSelect:false,checkOnSelect:true,selectOnCheck:true,collapsible:true,striped:true,fit:true,idField: 'id',treeField: 'name',toolbar:'#tb'">
    <thead data-options="frozen:true">
    <tr>
        <th data-options="field:'id1',checkbox:true"></th>
        <th data-options="field:'name'" width="30%">组织机构名称</th>
    </tr>
    </thead>
    <thead>
    <tr>
        <th data-options="field:'id'" width="60">编号</th>
        <th data-options="field:'charger'" width="100">负责人</th>
        <th data-options="field:'type'" width="80">类别</th>
        <th data-options="field:'phone'" width="120">联系电话</th>
        <th data-options="field:'url'" width="200">网址</th>
        <th data-options="field:'address'" width="200">联系地址</th>
        <th data-options="field:'postCode'" width="80">邮政编码</th>
        <th data-options="field:'ids',formatter:format" width="120">操作</th>
    </tr>
    </thead>
</table>
<script src="<%=basePath%>/script/business/system/OrganizationManage.js"></script>
</body>
</html>
