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
    <title>用户管理</title>
</head>
<body>
<table class="easyui-datagrid" title="用户信息管理" style="height:auto" id="dguser"
       data-options="rownumbers:true,pagination:true,collapsible:true,striped:true,fit:true,toolbar:'#tb'">
    <thead>
    <tr>
        <th data-options="field:'id',checkbox:true"></th>
        <th data-options="field:'number',width:100">账号</th>
        <th data-options="field:'nickName',width:200">昵称</th>
        <th data-options="field:'name',width:200">名称</th>
        <th data-options="field:'createDate',width:100,formatter: formatDatebox">创建日期</th>
         <th data-options="field:'actors',width:220,formatter:formatDoActor">角色</th>
        <th data-options="field:'type',width:100">类别</th>
        <th data-options="field:'status',width:60,formatter:formatStatus">状态</th>
        <th data-options="field:'ids',formatter:format" width="120"
            align="center">操作</th>
    </tr>
    </thead>
</table>
<div id="tb" style="padding:2px 5px;">
    <a href="#" class="easyui-linkbutton" id="btnAdd" data-options="plain:true,iconCls: 'icon-add'">新增</a>
    <input class="easyui-searchbox" id="txtName" data-options="prompt:'请输用户名称',searcher:doSearch" style="width:300px">
    <a href="#" class="easyui-linkbutton" id="btnLoadin" data-options="plain:true,iconCls: 'icon-loadin'">导入</a>|
    <a href="#" class="easyui-linkbutton" id="btnDel" data-options="plain:true,iconCls: 'icon-remove'">批量删除</a>|
    <a href="#" class="easyui-linkbutton" id="btnSetActor" data-options="plain:true,iconCls: 'icon-filter'">角色分配</a>|
    <a href="#" class="easyui-linkbutton" id="btnSetInitPWD" data-options="plain:true,iconCls: 'icon-lock'">密码重置</a>|
    <a href="#" class="easyui-linkbutton" id="btnLoadout" data-options="plain:true,iconCls: 'icon-loadout'">导出</a>|
    <a href="#" class="easyui-linkbutton" id="btnPrint" data-options="plain:true,iconCls: 'icon-print'">打印</a>
</div>

<!--表单-->
<div id="w" class="easyui-window" title="用户" data-options="modal:true,maximizable:false,closed:true,iconCls:'icon-save',footer:'#footer',collapsible:false" style="width:500px;height:310px;padding:10px;">
    <form id="ff" class="easyui-form">
        <input type="hidden" id="txtID" name="id" />
        <table cellpadding="5">
            <tr>
                <td>账户:</td>
                <td><input class="easyui-textbox" type="text" name="number" data-options="required:true" style="ime-mode: disabled"></input><br>【建议由数字、字母和下划线组成，长度在6-12之间】</td>
            </tr>
            <tr>
                <td>昵称:</td>
                <td><input class="easyui-textbox" type="text" name="nickName" data-options="required:true"></input></td>
            </tr>
            <tr>
                <td>名称:</td>
                <td><input class="easyui-textbox" type="text" name="name" data-options="required:true"></input></td>
            </tr>
            <tr>
                <td>分类:</td>
                <td>
                  <select name="type" class="easyui-combobox">
                      <option value="COLLEGE">学校用户</option>
                      <option value="CORPORATION">公司用户</option>
                      <option value="MANAGER">管理用户</option>
                      <option value="STUDENT">学生用户</option>
                      <option value="TEACHER">教师用户</option>
                      <option value="FAMILY">家长用户</option>
                      <option value="CUSTOMER">游客用户</option>
                  </select>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align: center;">说明：初始密码与账号一致，登录后进行修改！</td>

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

<!--角色分配窗体-->
<div id="wSetActor" class="easyui-window" title="用户角色分配" data-options="modal:true,maximizable:false,closed:true,iconCls:'icon-filter',footer:'#dActorfooter'" style="width:500px;height:300px;padding:10px;">
    <form id="fActors">
        <table cellpadding="5">
            <tr>
                <td>被分配用户:</td>
                <td>
                   <div id="dUsers" style="border:1px solid gray;width:330px;"></div>
                   <div id="dUserIDs" style="display: none;"></div>
                </td>
            </tr>
            <tr>
                <td>请选择角色:</td>
                <td>
                    <div style="border:1px solid gray;width:330px;">
                    <ul class="easyui-tree" id="tActor" data-options="animate:true,checkbox:true"></ul>
                    </div>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="dActorfooter" style="padding:5px;text-align: center;"><a href="#" class="easyui-linkbutton"
                                               data-options="iconCls:'icon-save'" style="width:90px;height:30px;"
                                               id="btnSaveActor">分配角色</a>
</div>
<!--文件上传-->
<form  style="display: none;" enctype="multipart/form-data"
      action="<%=basePath%>/uploadfile"
      method="post" id="frmUpload">
    <input id="Filedata" type="file" name="file" onchange="doUpload()"
           accept=".xls,.xlsx" multiple="multiple" />
    <input id="txtDoType" type="hidden" name="doType" value="actorType"/>
</form>
<!--文件导出-->
<form method="post" id="fLoadout" action="<%=basePath%>/user/loadout">
    <input type="hidden" id="txtHCon" name="condition"/>
</form>

<script src="<%=basePath%>/script/common/jquery.form.js"></script>
<script src="<%=basePath%>/script/business/system/UserManage.js"></script>
</body>
</html>
