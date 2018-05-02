<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header/init_layui.jsp"%>
<!DOCTYPE html>
<html lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<title>江西联微软件技术有限公司统一开发平台</title>
	<meta name="keywords" content="联微软件">
    <meta name="description" content="统一开发平台">
    <meta name="renderer" content="webkit">	
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">	
	<meta name="Author" content="larry">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">	
	<meta name="apple-mobile-web-app-capable" content="yes">	
	<meta name="format-detection" content="telephone=no">
	<link rel="stylesheet" type="text/css" href="css/animate.css" media="all">
	<link rel="stylesheet" type="text/css" href="css/login.css" media="all">
	<style>#larry_code .code-img img{width:111%;}</style>
	<script>
		var layer;
        layui.use('layer', function(){
            layer = layui.layer;
        });
        function  refreshImg() {
            document.getElementById("imgCode").src="<%=basePath%>/imageCode/showcode?timestamp="+(new Date()).getTime();
        }
        $(function () {
            if(window.parent.length>0)
                window.parent.location=location;

            $("#btnLogin").click(function () {
                if ($("#txtNumber").val()=="" || $("#txtPwd").val()=="" || $("#txtCode").val()=="")
                {
                    layer.msg("账号、密码及验证码都不能为空！",{offset: 'c',icon: 6,anim: 6});
                    return;
                }
                /*验证验证码*/
                doData("imageCode/getCode",null,function (data) {
                    if ($("#txtCode").val()!=data.result)
                    {
                        refreshImg();
                        layer.msg("验证码错误！",{offset: 't',icon: 5,anim: 6});
                        return false;
                    }
                    else
                    {
                        var user=new Object();
                        user.id=$("#txtNumber").val();
                        user.password=$("#txtPwd").val();
                        user.deviceType=0;//登录设备类型
                        doData("user/login",user,function (result) {
                            switch (parseInt(result.code))
                            {
                                case 100:
                                    layer.msg(result.message,{offset: 't',icon: 5,anim: 6});
                                    break;
                                case 600:
                                    layer.msg("账号或密码有误！",{offset: 't',icon: 5,anim: 6});
                                    break;
                                case 500:
                                    layer.msg("当前账号已经锁定，无法使用！",{offset: 't',icon: 5,anim: 6});
                                    break;
                                case 200:
                                    location.href="index";
                                    break;
                                case 700:
                                    layer.msg(result.message,{offset: 't',icon: 5,anim: 6});
                                    break;
                                default:
                                    layer.msg("系统登录异常,错误代码："+result.code,{offset: 't',icon: 5,anim: 6});
                                    break;
                            }
                            refreshImg();
                            $("#txtNumber").val("");
                            $("#txtPwd").val("");
                        });
                    }
                });
            });
        });
	</script>
</head>
<body>
<div class="larry-main layui-layout animated shake larry-delay2" id="larry_login" style="margin-top: 94px;">
	<div class="title">江西联微软件统一开发平台</div>
	<p class="info">后台登录中心</p>
	<div class="user-info">
		<div class="avatar"><img src="images/admin.png" alt=""></div>
		<form class="layui-form" id="larry_form" method="post">
			 <div class="layui-form-item">
			 	  <label class="layui-form-label">用户名:</label>
			 	  <input type="text" id="txtNumber" name="ht_dl_name" style="ime-mode:disabled" required="" lay-verify="required" aautocomplete="off" class="layui-input larry-input" placeholder="请输入您的用户名" autocomplete="off">
			 </div>
			 <div class="layui-form-item" id="password">
			 	  <label class="layui-form-label">密码:</label>
			 	  <input type="password" id="txtPwd" name="ht_dl_pass" style="ime-mode:disabled" required="" lay-verify="required|password" aautocomplete="off" class="layui-input larry-input" placeholder="请输入您的密码" autocomplete="off">
			 </div>
			 <div class="layui-form-item larry-verfiy-code" id="larry_code">
			 	   <input type="text" maxlength="4" name="ht_dl_yzm" id="txtCode" class="layui-input larry-input" placeholder="输入验证码">
			 	   <div class="code">
			 	   	   <div class="arrow"></div>
			 	   	   <div class="code-img"><img src="<%=basePath%>/imageCode/showcode" alt="点击切换" id="imgCode" onclick="refreshImg()"></div>
			 	   </div>
			 </div>
			 <div class="layui-form-item">
			 	 <input type="button" class="layui-btn larry-btn" value="立即登录" id="btnLogin" name="ht_dl_sub">
			 </div>
		</form>
	</div>
	<div class="copy-right"><span style="color:white">&copy;2017 江西联微软件技术有限公司</span></div>
</div>
<div class="layui-layer-move"></div>
	<ul id="supersized" class="quality" style="visibility: visible;">
		<li class="slide-1 activeslide" style="visibility: visible; opacity: 1;">
			<a target="_blank">
				<img src="./images/login_bg.jpg" style="width: 100%; left: 0px; top: 0px; height: 100%;">
			</a>
		</li>
	</ul>
</div>
</body>
</html>