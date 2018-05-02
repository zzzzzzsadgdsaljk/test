<%--
  Created by IntelliJ IDEA.
  User: wph-pc
  Date: 2017/5/26
  Time: 22:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/header/init.jsp"%>
<html>
<head>
    <title>角色类型管理</title>
</head>
<body>
<table class="easyui-datagrid" title="角色类型信息管理" style="height:auto" id="dgActorType"
       data-options="rownumbers:true,pagination:true,collapsible:true,striped:true,fit:true,toolbar:'#tb'">
    <thead>
    <tr>
        <th data-options="field:'id1',checkbox:true"></th>
        <th data-options="field:'id',width:80">分类代码</th>
        <th data-options="field:'name',width:200">角色类型名称</th>
        <th data-options="field:'createDate',width:100,formatter: formatDatebox">创建日期</th>
        <th data-options="field:'type',width:60">分类</th>
        <th data-options="field:'orgName',width:160">组织机构</th>
        <th data-options="field:'status',width:60">状态</th>
        <th data-options="field:'ids',formatter:format" width="120"
            align="center">操作</th>
    </tr>
    </thead>
</table>
<div id="tb" style="padding:2px 5px;">
    <shiro:hasPermission name="/actorType/add">
    <a href="#" class="easyui-linkbutton" id="btnAdd" data-options="plain:true,iconCls: 'icon-add'">新增</a>
    </shiro:hasPermission>
    <input class="easyui-searchbox" id="txtName" data-options="prompt:'请输角色类型名称',searcher:doSearch" style="width:300px">
    <a href="#" class="easyui-linkbutton" id="btnLoadin" data-options="plain:true,iconCls: 'icon-loadin'">导入</a>|
    <a href="#" class="easyui-linkbutton" id="btnDel" data-options="plain:true,iconCls: 'icon-remove'">批量删除</a>|
    <a href="#" class="easyui-linkbutton" id="btnLoadout" data-options="plain:true,iconCls: 'icon-loadout'">导出</a>|
    <a href="#" class="easyui-linkbutton" id="btnPrint" data-options="plain:true,iconCls: 'icon-print'">打印</a>
</div>

<!--表单-->
<div id="w" class="easyui-window" title="角色分类操作" data-options="modal:true,maximizable:false,closed:true,iconCls:'icon-filter',footer:'#footer'" style="width:550px;height:300px;padding:10px;">
    <form id="ff" class="easyui-form">
        <input type="hidden" id="txtOldID" name="oldId" />
        <table cellpadding="5">
            <tr>
                <td>角色分类代码:</td>
                <td><input class="easyui-textbox" id="txtID" type="text" name="id" data-options="required:true"></input></td>
            </tr>
            <tr>
                <td>角色分类名称:</td>
                <td><input class="easyui-textbox" type="text" name="name" data-options="required:true"></input></td>
            </tr>
            <tr>
                <td>组织机构：</td>
                <td><input id="org" class="easyui-combotree" data-options="required:true" style="width:200px;"></td>
            </tr>
            <tr>
                <td>类别:</td>
                <td><input checked="checked" type="radio" name="type" value="个人"/>个人<input type="radio" name="type" value="集体"/>集体</td>
            </tr>
            <tr>
                <td>状态:</td>
                <td><input checked="checked" type="radio" name="status" value="正常"/>正常<input type="radio" name="status" value="锁定"/>锁定</td>
            </tr>
        </table>
    </form>
</div>
<div id="footer"
     style="padding:5px;margin-bottom:10px;text-align:center;">
    <a href="#" class="easyui-linkbutton"
       data-options="iconCls:'icon-save'" style="width:60px;height:30px;"
       id="btnSave">保存</a> <a href="#" class="easyui-linkbutton"
                              data-options="iconCls:'icon-set'" style="width:60px;height:30px;"
                              id="btnReset" type="reset">重置</a>
</div>

<!--文件上传-->
<form enctype="multipart/form-data"
      id="frmUpload">
    <input id="Filedata" type="file" name="file" onchange="doUpload()"
           accept=".xls,.xlsx" />
</form>
<!--文件导出-->
<form method="post" id="fLoadout" action="<%=basePath%>/actorType/loadout">
    <input type="hidden" id="txtHCon" name="condition"/>
</form>

<script src="<%=basePath%>/script/common/jquery.form.js"></script>
<script src="<%=basePath%>/script/business/system/actorTypeManage.js"></script>
</body>
</html>
