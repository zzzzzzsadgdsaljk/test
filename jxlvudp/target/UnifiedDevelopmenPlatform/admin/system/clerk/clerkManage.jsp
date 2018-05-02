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
    <title>员工基本信息管理</title>
    <link href="<%=basePath%>/plugins/layui/css/layui.css" rel="stylesheet" />
    <script src="<%=basePath%>/plugins/layui/layui.js"></script>
</head>
<body>

<div class="layui-table-tool" id="tb">
    <button type="button" class="layui-btn layui-btn-small  layui-btn-normal" id="test3"><i class="layui-icon"></i>导入员工</button>
    <button class="layui-btn layui-btn-small" id="btnAdd" >
        <i class="layui-icon">&#xe608;</i> 添加员工
    </button>

    <button type="button" class="layui-btn layui-btn-small  layui-btn-normal" id="test4"><i class="layui-icon"></i>导出员工</button>
    <input type="text"  style="width: 300px; height: 28px;" name="name" id="txtName" required  placeholder="请输站点名称" autocomplete="off"/>

        <button class="layui-btn layui-btn-small" id="btnFind">
            <i class="layui-icon">&#xe615;</i> 搜索
        </button>
        <button class="layui-btn layui-btn-small layui-btn-warm" id="btnDeleteAll">
            <i class="layui-icon">&#xe640;</i> 批量删除
        </button>

</div>
<table class="easyui-datagrid" title="员工基本信息管理" style="height:auto" id="dgClerk"
       data-options="rownumbers:true,pagination:true,collapsible:true,striped:true,fit:true,toolbar:'#tb'">
    <thead data-options="frozen:true">
    <tr>
        <th data-options="field:'id1',checkbox:true"></th>
        <th data-options="field:'id',width:80">工号</th>
        <th data-options="field:'xm',width:100">姓名</th>
        <th data-options="field:'orgName',width:180">所属组织机构</th>
        <th data-options="field:'contacts',width:100">联系方式</th>
        <th data-options="field:'xbm',width:60">性别</th>
    </tr>
    </thead>
    <thead>
    <tr>
        <th data-options="field:'ywxm',width:100">英文姓名</th>
        <th data-options="field:'xmpy',width:100">姓名拼音</th>
        <th data-options="field:'cym',width:100">曾用名</th>
        <th data-options="field:'csrq',width:100, formatter: formatDatebox ">出生日期</th>
        <th data-options="field:'csdm',width:200">出生地</th>
        <th data-options="field:'jg',width:100">籍贯</th>
        <th data-options="field:'mzm',width:80">民族</th>
        <th data-options="field:'gjdqm',width:200">国籍或地区码</th>
        <th data-options="field:'sfzjlxm',width:200">证件类型</th>
        <th data-options="field:'sfzjh',width:200">证件号</th>
        <th data-options="field:'hyzkm',width:100">婚姻状况</th>
        <th data-options="field:'zzmmm',width:100">政治面貌</th>
        <th data-options="field:'jkzkm',width:100">健康状况</th>
        <th data-options="field:'xyzjm',width:100">宗教信仰</th>
        <th data-options="field:'xxm',width:100">血型</th>
        <th data-options="field:'createDate',width:100,formatter: formatDatebox">创建日期</th>
        <th data-options="field:'zp',width:200">照片</th>
        <th data-options="field:'sfzjyxq',width:200">证件有效期</th>


        <th data-options="field:'status',width:60">状态</th>
        <th data-options="field:'ids',formatter:jxmstc.formateOperateForEasyui" width="120"
            align="center">操作</th>
    </tr>
    </thead>
</table>

<!--文件上传-->
<form enctype="multipart/form-data"
      id="frmUpload">
    <input id="Filedata" type="file" name="file" onchange="doUpload()"
           accept=".xls,.xlsx" />
</form>

<!--文件导出-->
<form method="post" id="fLoadout" action="<%=basePath%>/clerk/loadout">
    <input type="hidden" id="txtHCon" name="condition"/>
</form>
<script src="<%=basePath%>/script/business/system/clerk.js"></script>



</body>
</html>
