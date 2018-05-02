<%--
  Created by IntelliJ IDEA.
  User: wph-pc
  Date: 2017/8/2
  Time: 9:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/header/init_bootstrap.jsp"%>
<html>
<head>
    <title>留言信息综合管理</title>
</head>
<body style="padding-top: 10px;">
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="input-group">
                <input type="text" id="txtName" class="form-control" placeholder="输入留言的标题关键字">
                <span class="input-group-btn">
               <button class="btn btn-primary" type="button" id="btnFind">查找</button>
               </span>
            </div>
        </div>
    </div>
    <div id="dContent" style="padding-top: 20px;">

    </div>

    <div class="rows">
        <div class="col-lg-12">
            <nav id="pager" aria-label="...">
                <ul class="pagination">
                    <li class="disabled"><a href="javascript:void(0)" aria-label="Previous"><span aria-hidden="true">«</span></a></li>
                    <li class="active"><a href="javascript:void(0)">1 <span class="sr-only">(current)</span></a></li>
                    <li><a href="javascript:void(0)">2</a></li>
                    <li><a href="javascript:void(0)">3</a></li>
                    <li><a href="javascript:void(0)">4</a></li>
                    <li class="disabled"><a href="javascript:void(0)">...</a></li>
                    <li><a href="javascript:void(0)">5</a></li>
                    <li><a href="javascript:void(0)" aria-label="Next"><span aria-hidden="true">»</span></a></li>
                </ul>
            </nav>
        </div>
    </div>
</div>

<!--delete Modal dialog-->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">留言信息删除警告提示</h4>
            </div>
            <input type="hidden" id="txtLM"/>
            <div class="modal-body" id="dDelMsg">
                ...
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="btnDel">确定</button>
            </div>
        </div>
    </div>
</div>

<!--response Modal dialog-->
<div class="modal fade" id="responseModal" tabindex="-2" role="dialog" aria-labelledby="lblResponse">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="lblResponse">留言信息回复</h4>
            </div>
            <input type="hidden" id="txtRLM"/>
            <div class="modal-body">
                <form role="form">
                    <div class="form-group">
                        <textarea id="txtResponse" class="form-control" rows="3" placeholder="请填写回复内容，200字以内......" maxlength="200"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="btnReponse">回复</button>
            </div>
        </div>
    </div>
    <!--警告条-->
    <div id="alert" class="alert alert-info hide fade in" role="alert">
        <strong>提示</strong>
        <p id="alert-info"> 不能为空，请输入200字以内的回复内容. </p>
    </div>
</div>
<!--警告条-->
<div id="alert-warning" class="alert alert-warning hide fade in" role="alert">
    <strong id="sTitle">Warning!</strong>
    <p id="pTips"> Better check yourself, you're not looking too good. </p>
</div>
<script src="<%=basePath%>/script/business/web/leaveMessageManage.js"></script>
</body>
</html>
