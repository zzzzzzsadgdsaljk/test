<%--
  Created by IntelliJ IDEA.
  User: wph-pc
  Date: 2017/9/23
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/header/init_layui.jsp"%>
<html>
<head>
    <title>系统权限菜单</title>
</head>
<body>
<form class="layui-form" id="ff">
    <input type="hidden" name="oldId"/>
    <input type="hidden" name="parent.id" id="txtParentID"/>
    <div class="layui-form-item">
        <label class="layui-form-label">上级权限： </label>
        <div class="layui-input-block">
            <input type="checkbox" title="是否启用" id="ckUse">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">上级权限： </label>
        <div class="layui-input-block">
            <input type="text" disabled="disabled" id="txtParent" name="parent.name" value="暂无上级权限名称" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">编号：</label>
        <div class="layui-input-block">
            <input type="text" name="id" required  lay-verify="required" placeholder="请输入系统功能权限编号" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">权限名称：</label>
        <div class="layui-input-block">
            <input type="text" name="name" required  lay-verify="required" placeholder="请输入系统功能权限名称" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">权限地址：</label>
        <div class="layui-input-block">
            <input type="text" name="address" required  lay-verify="required" placeholder="请输入系统功能权限地址" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">状态：</label>
        <div class="layui-input-block">
            <input type="radio" name="status" value="正常" title="正常">
            <input type="radio" name="status" value="锁定" title="锁定" checked>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>

<script>
    var kb=new KBLayUI("systemMenu");
    var parentId=getURLParamValue("parentId");
    var type=getURLParamValue("type");//获取操作类型，0表示新增，其他表示修改
    $("#txtParentID").val("");
    $("#txtParent").val("");

    if (parentId!=undefined)
    {
        $("#txtParentID").val(parentId);
        $("#txtParent").val(unescape(getURLParamValue("name")));
    }

    if(type==1)
    kb.getMe(getURLParamValue("id"),function (data) {
        $('#ff').form('load', data);//导入修改的数据，并绑定在界面上
        if (data.parent!=null)
        {
            $("#ckUse").attr("checked",true);
            $("#txtParentID").val(data.parent.id);
            $("#txtParent").val(data.parent.name);
        }
    });
    //Demo
    layui.use(['form','layer'], function(){
        var form = layui.form,layer=layui.layer;

        //监听提交
        form.on('submit(formDemo)', function(data){
            var obj=serializeArrayToObject("ff");
            if ($("#ckUse").attr("checked")==undefined)
                obj.parent=null;
            if (type==0)
            kb.add(obj,function (returnBack) {});
            else
                kb.edit(obj,function (returnBack) { });

            var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
            parent.layer.close(index);
            parent.find();

            return false;
        });
    });
</script>
</body>
</html>
