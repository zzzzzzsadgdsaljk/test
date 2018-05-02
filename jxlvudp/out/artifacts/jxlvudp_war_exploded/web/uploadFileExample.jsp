<%--
  Created by IntelliJ IDEA.
  User: wph-pc
  Date: 2017/10/21
  Time: 22:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/header/init.jsp"%>
<html>
<head>
    <title>多个文件上传案例</title>
</head>
<body>
<form

        id="frmUpload">
    <input type="file" name="file"
           accept=".png" multiple="multiple"/>
    <input type="file" name="file"
           accept=".png"/>
    <input type="file" name="file"
           accept=".png"/>
    <input id="txtDoType" type="button" name="doType" value="上传"/>
</form>
<div id="dTips"></div>
<div id="dDisplay"></div>
<script>
    $(function () {
        $("#txtDoType").click(function () {
            doUploadFile("uploadfile","frmUpload",function () {
                $("#dTips").text("请稍后，文件正在上传中......");
            },function (data) {
                $("#dTips").text("");
                if (data!=null && data.length>0)
                {
                    for(var i=0;i<data.length;i++)
                    {
                        $("#dDisplay").append("<img src='"+getRootPath()+"/uploadfiles/"+data[i]+"' width='100' height='100'/>");
                    }
                }

            });
        });
    });
</script>
</body>
</html>
