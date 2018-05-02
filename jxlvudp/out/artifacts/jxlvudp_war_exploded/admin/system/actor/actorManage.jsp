<%--
  Created by IntelliJ IDEA.
  User: wph-pc
  Date: 2017/6/5
  Time: 21:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/header/init.jsp"%>
<html>
<head>
    <title>角色综合管理平台</title>
    <link href="<%=basePath%>/plugins/layui/css/layui.css" rel="stylesheet" />
    <script src="<%=basePath%>/plugins/layui/layui.js"></script>
</head>
<body>
<div id="tb" class="layui-fluid" style="height: 40px; line-height:40px;">
    <div class="layui-row">
        <div class="layui-col-md12">
            <button class="layui-btn layui-btn-small"  id="btnAdd">
                <i class="layui-icon">&#xe608;</i> 添加
            </button>
            <input type="text"  style="width: 300px; height: 30px;" name="title" id="txtName" required  lay-verify="required" placeholder="请输角色名称" autocomplete="off"/>
            <div class="layui-btn-group">
            <button class="layui-btn layui-btn-small" id="btnFind">
                <i class="layui-icon">&#xe615;</i> 搜索
            </button>

            <button class="layui-btn layui-btn-small layui-btn layui-btn-warm" id="btnSetPower">
                <i class="layui-icon">&#xe612;</i> 授权
            </button>
            <button class="layui-btn layui-btn-small layui-btn-normal" id="btnFresh">
                <i class="layui-icon">&#x1002;</i> 刷新
            </button>
            </div>
        </div>
    </div>
</div>
<table class="easyui-treegrid" title="角色信息管理" style="height:auto" id="dgActor"
       data-options="rownumbers:true,singleSelect:false,initialState:'collapsed',checkOnSelect:true,selectOnCheck:true,collapsible:true,striped:true,fit:true,idField: 'id',treeField: 'name',toolbar:'#tb'">
    <thead>
    <tr>
        <th data-options="field:'id1',checkbox:true"></th>
        <th data-options="field:'name'" width="50%">角色名称</th>
        <th data-options="field:'type',formatter:formatType" width="150">角色类型</th>
        <th data-options="field:'power',formatter:powerFormat" width="60">授权状态</th>
        <th data-options="field:'status'" width="80">状态</th>
        <th data-options="field:'ids',formatter:format" width="120">操作</th>
    </tr>
    </thead>
</table>

<!--表单-->
<div id="w" class="easyui-window" title="角色操作" data-options="modal:true,maximizable:false,closed:true,iconCls:'icon-filter',footer:'#footer'" style="width:550px;height:330px;padding:10px;">

    <form class="easyui-form" id="ff" method="post" data-options="novalidate:true">
        <input type="hidden" name="parent.id" id="txtParentID"/>
        <input type="hidden" name="oldId"/>
        <table cellpadding="5px;">
            <tr><td style="padding:5px;">上级角色:</td><td><input type="text" id="txtParent" disabled="disabled" /><input type="checkbox" id="cbRoot">根节点</td></tr>
            <tr><td style="padding:5px;">角色名称:</td><td><input class="easyui-textbox" required="true" name="name"/></td></tr>
            <tr><td style="padding:5px;">角色类型:</td><td><select id="actorType" name="type.id" required="true" class="easyui-combobox" style="width:200px;" data-options="valueField:'id',textField:'name'"></select></td></tr>
            <tr><td style="padding:5px;">描述:</td><td>
                <input class="easyui-textbox" name="description" data-options="multiline:true" style="height:60px"/>
            </td></tr>
            <tr>
                <td style="padding:5px;">状态:</td>
                <td><input checked="checked" type="radio" name="status" value="正常"/>正常<input type="radio" name="status" value="锁定"/>锁定</td>
            </tr>
            <tr><td colspan="2" style="text-align:center;"> </td></tr>
        </table>
    </form>

</div>
<div id="footer" style="padding:10px;margin-bottom:10px;text-align:center;">
    <button id="btnSave" class="layui-btn">
        <i class="layui-icon">&#xe605;</i> 保存
    </button>
</div>
<script src="<%=basePath%>/script/business/system/ActorManage.js"></script>
</body>
</html>
