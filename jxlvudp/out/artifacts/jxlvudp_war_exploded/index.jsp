<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/header/init.jsp"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>江西联微软件技术有限公司统一开发平台</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/css/index.css">
</head>
<body class="easyui-layout">
<!--rgb(47,64,86)藏青色-->
<div data-options="region:'north',border:false"
     style="height:60px; line-height:60px; overflow:hidden;background-color:rgb(0,129,194);padding-left: 10px;padding-right: 10px;">
    <!--<iframe src="top.jsp" width="100%" height="99%;" frameborder="0"> </iframe> -->
    <span style="color:white;font-size: 25px;">江西联微软件技术有限公司统一开发平台</span>
    <!-- 顶部状态栏-->
    <div style="float:right; text-align:right; color:white">
        <span id="sUser" style="color:white">欢迎【admin】访问系统</span>&nbsp;&nbsp;
        <a href="#" class="eayui-linkbutton" id="aUser" title="点此修改密码"><span style="color:white;">密码修改</span></a>&nbsp;&nbsp;&nbsp;
        <a href="javascript:void(0)" class="eayui-linkbutton" id="btnLogout"><span style="color:white;">退出系统</span></a>

    </div>
</div>
<!--头部-->

<div data-options="region:'west',split:false,collapsible:true,title:'导航菜单'"
     style="width:200px;padding:1px;" id="dMenu">
    <div id="dMyMenu" class="easyui-accordion"  style="width:196px;">
        <div title="辅助系统" style="width:195px;" class="nav_fl">
            <a href="#"><li class="li_bian" id="dictionary">数据词典管理</li></a>
            <shiro:hasPermission name="/actorType/actorType">
                <a href="#"><li class="li_bian" id="actorType">角色类型管理</li></a>
            </shiro:hasPermission>
            <a href="#"><li class="li_bian"  id="actor">角色管理</li></a>
            <a href="#"><li class="li_bian" id="user">用户管理</li></a>
            <a href="#"><li class="li_bian" id="userOnline">用户在线管理</li></a>
            <a href="#"><li class="li_bian" id="systemMenu">系统菜单管理</li></a>
            <a href="#"><li class="li_bian" id="organization">组织机构管理</li></a>
            <a href="#"><li class="li_bian" id="clerk">员工管理</li></a>
            <a href="text1.html" target="content"><li class="li_bian">数据管理</li></a>

        </div>
        <div title="通用网站后台" style="width:195px;" class="nav_gw">
            <a href="#"><li class="li_bian" id="website">子网站或频道管理</li></a>
            <a href="#"><li class="li_bian" id="newsCategory">新闻分类管理</li></a>
            <a href="#"><li class="li_bian" id="notice">通知管理</li></a>
            <a href="#"><li class="li_bian" id="annoucement">公告管理</li></a>
            <a href="#"><li class="li_bian" id="news">新闻管理</li></a>
            <a href="#"><li class="li_bian" id="newsCheck">新闻审核管理</li></a>
            <a href="#"><li class="li_bian" id="advertisement">广告管理</li></a>
            <a href="#"><li class="li_bian" id="leaveMSG">留言板留言</li></a>
            <a href="#"><li class="li_bian" id="leaveMessage">留言板管理</li></a>
        </div>
        <div title="工作流程管理" style="width:195px;" class="nav_wj">

        </div>


    </div>
</div>


<div data-options="region:'south',border:false,height:30" style="padding:6px 0;text-align: center;color:white;background-color:rgb(0,129,194)">
    Copy Right &copy 2017 江西联微软件技术有限公司 版权所有 </div>
<div data-options="region:'center'" style="z-index:-1;">
    <div class="easyui-tabs" id="tWork"
         data-options="fit:true,border:false,plain:true">
        <div title="首页">
            <iframe src="home.jsp" width="100%" height="99%;" frameborder="0" wmode="window"> </iframe>
        </div>
    </div>
</div>


<div id="winpwd" class="easyui-window" title="修改密码"
     data-options="modal:true,closed:true,iconCls:'icon-save',collapsible:false,minimizable:false,maximizable:false"
     style="width:350px;height:250px;padding:20px;">
    <form id="formchangepwd" class="easyui-form" method="post"
          data-options="novalidate:true">
        <table>
            <tr>
                <td>请输入原密码：</td>
                <td><input class="easyui-textbox" style="height:30px;"
                           name="password" type="password" id="txtprepassword"
                           data-options="required:true,validType:'length[6,20]'"></input>
                </td>
            </tr>
            <tr>
                <td>请输入新密码：</td>
                <td><input class="easyui-textbox" style="height:30px;"
                           name="password" type="password" id="txtnewpassword"
                           data-options="required:true,validType:'length[6,20]'"></input>
                </td>
            </tr>
            <tr>
                <td>请再次输入新密码：</td>
                <td><input class="easyui-textbox" style="height:30px;"
                           name="password" type="password" id="txtconfirmpassword"
                           data-options="required:true,validType:'length[6,20]'"></input>
                </td>
            </tr>
        </table>
        <div id="dFooter"
             style="padding:10px;margin-bottom:10px;text-align:center;">
            <a href="#" class="easyui-linkbutton"
               data-options="iconCls:'icon-save'" style="width:100px;height:40px;"
               id="btnSavePwd">更新</a> <a href="#" class="easyui-linkbutton"
                                         data-options="iconCls:'icon-set'" style="width:100px;height:40px;"
                                         id="btnReset" type="reset">重置</a>
        </div>
    </form>
</div>

</body>
<script type="text/javascript">
    function addTab(title, url) {
        if ($('#tWork').tabs('exists', title)) {
            $('#tWork').tabs('select', title);
        } else {
            var content = '<iframe scrolling="auto" frameborder="0"  src="'+ url + '" style="width:100%;height:98%;"></iframe>';

            $('#tWork').tabs('add', {
                title : title,
                content : content,
                closable : true,
                fit:true
            });
        }
    }


    $(function() {
        doData("user/loginUser",null,function (data) {
            if (data!=null && data!=undefined)
             $("#sUser").text("欢迎您，"+data.nickName+"【"+data.number+"】");
            else
                $("#sUser").text("");
        });
        $(document).on("click","#actorType",function() {
            addTab("角色类型管理", getRootPath()+"/actorType/actorType");
        });
        $(document).on("click","#actor",function() {
            addTab("角色管理", getRootPath()+"/actor/index");
        });
        $(document).on("click","#anncoucement",function() {
            addTab("公告管理", getRootPath()+"/announcement/index");
        });
        $(document).on("click","#clerk",function() {
            addTab("职员管理", getRootPath()+"/clerk/index");
        });
        $(document).on("click","#personal",function() {
            addTab("职员管理", getRootPath()+"/clerk/index");
        });
        $(document).on("click","#honorCredentials",function() {
            addTab("荣誉证书", getRootPath()+"/honorCertification/index");
        });
        $(document).on("click","#projectProduction",function() {
            addTab("项目产品", getRootPath()+"/production/index");
        });
        $(document).on("click","#college",function() {
            addTab("高校管理", getRootPath()+"/college/index");
        });
        $(document).on("click","#special",function() {
            addTab("专业管理", getRootPath()+"/special/index");
        });
        $(document).on("click","#teacher",function() {
            addTab("教师管理", getRootPath()+"/teacher/index");
        });
        $(document).on("click","#department",function() {
            addTab("部门管理", getRootPath()+"/dep/index");
        });
        $(document).on("click","#collegeClass",function() {
            addTab("班级管理", getRootPath()+"/collegeClass/index");
        });
        $(document).on("click","#student",function() {
            addTab("学生管理", getRootPath()+"/student/index");
        });
        $(document).on("click","#dictionary",function() {
            addTab("数据词典管理",getRootPath()+ "/dictionary/index");
        });
        $(document).on("click","#news",function() {
            addTab("新闻管理", getRootPath()+"/news/index");
        });
        $(document).on("click","#advertisement",function() {
            addTab("广告管理", getRootPath()+"/advertisement/index");
        });
        $(document).on("click","#leaveMessage",function() {
            addTab("留言管理", getRootPath()+"/leaveMessage/index");
        });
        $(document).on("click","#leaveMSG",function() {
            addTab("留言", getRootPath()+"/leaveMessage/writeMsg");
        });
        $(document).on("click","#notice",function() {
            addTab("通知管理", getRootPath()+"/notice/index");
        });
        $(document).on("click","#annoucement",function() {
            addTab("公告管理", getRootPath()+"/announcement/index");
        });
        $(document).on("click","#website",function() {
            addTab("网站设置管理", getRootPath()+"/webSite/index");
        });
        $(document).on("click","#newsCategory",function() {
            addTab("新闻分类管理", getRootPath()+"/newsCategory/index");
        });
        $(document).on("click","#news",function() {
            addTab("新闻管理", getRootPath()+"/news/index");
        });
        $(document).on("click","#newsCheck",function() {
            addTab("新闻审核管理", getRootPath()+"/news/index");
        });
        $(document).on("click","#organization",function() {
            addTab("组织机构管理", getRootPath()+"/org/index");
        });

        $(document).on("click","#systemMenu",function() {
            addTab("系统菜单管理",getRootPath()+ "/systemMenu/index");
        });
        $(document).on("click","#user",function() {
            addTab("用户管理", getRootPath()+"/user/index");
        });
        $(document).on("click","#userOnline",function() {
            addTab("用户在线管理", getRootPath()+"/user/userOnline");
        });

        $(document).on("click","#asset",function() {
            addTab("资产存放地点管理", getRootPath()+"/asset/index");
        });
        $(document).on("click","#supplier",function() {
            addTab("供应商管理", getRootPath()+"/supplier/index");
        });
        $(document).on("click","#techResource",function() {
            addTab("资源管理", getRootPath()+"/techResource/index");
        });
        $(document).on("click","#techIndex",function() {
            addTab("指标管理", getRootPath()+"/techIndex/index");
        });
        $(document).on("click","#position",function() {
            addTab("岗位管理", getRootPath()+"/position/index");
        });
        $(document).on("click","#setPosTechIndex",function() {
            addTab("岗位指标分配", getRootPath()+"/positionTechIndex/index");
        });

        $(document).on("click","#techCase",function() {
            addTab("教案管理", getRootPath()+"/techCase/index");
        });

        $(document).on("click","#testStore",function() {
            addTab("题库管理", getRootPath()+"/fillBlankTest/index");
        });
        $("#aUser").click(function(){
            $("#winpwd").window('open');
        });
        $("#btnSavePwd").click(function(){
            if($("#txtnewpassword").val()!=$("#txtconfirmpassword").val()){
                $.messager.show({
                    title : "提示",
                    msg : "两次密码输入不一致"
                });
                return;
            }
            var obj = new Object();
            obj.password = $("#txtprepassword").val();
            obj.newPwd=$("#txtnewpassword").val();

            doData("user/changePwd", obj, function(data) {
                switch(data.result)
                {
                    case -100:
                        alert("您还没有登录，请先登录，后进行密码修改！");
                        location.href="login2.jsp";
                        break;
                    case -200:
                        alert("原密码验证错误，请重新输入！");
                        break;
                    case 1:
                        $("#winpwd").window("close");
                        alert("密码修改成功，请重新登录！");
                        location.href="login2.jsp";
                        break;
                    default:
                        alert("密码修改失败，错误代码："+data.result);
                        break;
                }
            });

        });

        $("#btnLogout").click(function () {
            var flag=confirm("确定退出系统吗？");
            if (flag)
            {
                doData("user/logout",null,function () {
                    location.href="login.jsp";
                });
            }
        });
    });
</script>

</html>
