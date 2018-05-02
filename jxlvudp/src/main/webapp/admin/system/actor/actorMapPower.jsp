<%--
  Created by IntelliJ IDEA.
  User: wph-pc
  Date: 2017/9/24
  Time: 12:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/header/init.jsp"%>
<html>
<head>
    <title>角色功能权限分配</title>
    <link href="<%=basePath%>/plugins/layui/css/layui.css" rel="stylesheet" />
    <script src="<%=basePath%>/plugins/layui/layui.js"></script>
    <script src="<%=basePath%>/script/common/kb.js"></script>
</head>
<body>
<form class="layui-form" id="ff">
    <div class="layui-form-item">
        <label class="layui-form-label">分配角色： </label>
        <div class="layui-input-block">
            <input type="text" id="txtActors" disabled="disabled" id="txtActors" name="actors" value="暂无授权角色信息" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">功能权限：</label>
        <div class="layui-input-block">
            <ul id="powers" class="easyui-tree" data-options="animate:true,checkbox:true"></ul>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="formDemo">立即分配</button>

        </div>
    </div>
    <script>
        var kb=new KBLayUI("actor");

        kb.doFormLayui("formDemo",function (data) {
            /*获取选中的权限集合*/
            var nodes = $('#powers').tree('getChecked');
            if (nodes!=null && nodes.length>0)
            for(var i=0;i<nodes.length;i++)
            {
                if (powers=="")
                    powers=nodes[i].id;
                else
                   powers+=","+nodes[i].id;
            }
            var obj=new Object();
            obj.actors=actorIds;
            obj.power=powers;
            kb.do("setPower",obj,function (r) {
                if (r.code>0)
                {
                    parent.layer.msg("授权成功！");
                }
                else
                    parent.layer.msg("角色授权失败，错误代码："+r.code);
            });

        });

        function loadTree() {
            var obj=new Object();
            obj.condition="";
            doData("systemMenu/findTree",obj,function (data) {
                $("#powers").tree("loadData",data);
                setActorAndPower();
            });
        }

        function setActorAndPower() {
            /*获取角色对象*/
            var actorId=getURLParamValue("id");
            var con=new Object();
            con.ids=actorId;
            if (actorId!=undefined && actorId!=null)
                kb.find(con,function (data) {
                    if (data.obj!=undefined && data.obj!=null)
                    {
                        var actorNames="";
                        for(var i=0;i<data.obj.length;i++)
                        {
                            if (actorIds=="")
                                actorIds=data.obj[i].id;
                            else
                                actorIds+=","+data.obj[i].id;

                            if (actorNames=="")
                                actorNames=data.obj[i].name;
                            else
                                actorNames+=","+data.obj[i].name;

                        }
                        $("#txtActors").val(actorNames);
                        /*查找当前角色权限*/
                        con=new Object();
                        con.actors=actorIds;
                        kb.do("getPower",con,function (data) {
                            if (data.obj!=null && data.obj.length>0)
                            for(var i=0;i<data.obj.length;i++)
                            {
                                var node = $("#powers").tree("find",data.obj[i].id);
                                if (node!=null)
                                   $("#powers").tree("check",node.target);
                            }

                        });
                    }
                } );
        }
        var actorIds="";//待收取角色ids
        var powers="";//选中的权限
        $(function () {
            loadTree();
        });
    </script>
</form>
</body>
</html>
