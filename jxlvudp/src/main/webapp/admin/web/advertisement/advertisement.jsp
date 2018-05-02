<%--
  Created by IntelliJ IDEA.
  User: wph-pc
  Date: 2017/7/27
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/header/init_layui.jsp"%>
<html>
<head>
    <title>广告维护</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width; initial-scale=1.0">


    <script type="text/javascript" charset="utf-8" src="<%=basePath %>/plugins/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>/plugins/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript"src="<%=basePath %>/static/js/html5media.min.js"></script>
</head>
<body style="padding-top: 15px;">
<form class="layui-form" id="fAdvertisement">
    <div class="layui-form-item">
        <label class="layui-form-label">发布网站：</label>
        <div class="layui-input-block">
            <select id="sltWeb" name="web.id" style="width:120px;" data-options="valueField:'id',textField:'name'"></select>
        </div>
    </div>
    <input type="hidden" name="oldId"/>
    <div class="layui-form-item">
        <label class="layui-form-label">广告编号：</label>
        <div class="layui-input-block">
            <input name="id" required  lay-verify="required" autocomplete="off" placeholder="请输入网站的编号，要求20位以内的字符" class="layui-input" type="text" maxlength="20">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">广告名称：</label>
        <div class="layui-input-block">
            <input name="name" required  lay-verify="required" autocomplete="off" placeholder="请输入网站的名称，要求20位以内的字符" class="layui-input" type="text" maxlength="20">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">广告图片：</label>
        <div class="layui-input-block">
            <img id="imgImageAddress" alt="暂无图片" src="<%=basePath %>/static/images/zanwu.jpg" style="width:100px;height:100px;cursor:pointer;" title="双击图片，完成新图片上传"/>
            <input id="imgAddress" name="imgAddress" type="hidden"/>

        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">图片链接地址：</label>
        <div class="layui-input-block">
            <input name="linkUrl" required  lay-verify="required|url" autocomplete="off" placeholder="图片点击时的链接地址" class="layui-input" type="text">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">视频链接地址：</label>
        <div class="layui-input-block">
            <div class="layui-inline">
                <div class="layui-input-inline">
                 <input id="videoAddress" name="videoAddress" type="hidden"/>
                 <button class="layui-btn" type="button" id="btnVideoAddress" title="仅支持MP4(H264编码格式)">上传广告视频</button>
                </div>
             </div>
            <div class="layui-inline">
              <div id="dVideoTips"></div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">预览</label>
                <div class="layui-input-inline">
                    <video id="vVideoAddress" controls="controls" width="200px;" height="100px;" controls preload>
                        您的浏览器不支持 video 标签。
                    </video>
                </div>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">广告嵌入页面地址：</label>
        <div class="layui-input-block">
            <input name="homeUrl" required  lay-verify="required|url" autocomplete="off" placeholder="图片点击时的链接地址" class="layui-input" type="text">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">广告类型：</label>
        <div class="layui-input-block">
            <input name="type" value="位置固定广告" title="位置固定广告" checked="" type="radio">
            <input name="type" value="移动广告" title="移动广告" type="radio">
            <input name="type" value="右底部悬浮广告" title="右底部悬浮广告" type="radio">
            <input name="type" value="右底部固定广告" title="右底部固定广告" type="radio">
            <input name="type" value="整页面浮动广告" title="整页面浮动广告" type="radio">
            <input name="type" value="中间固定广告" title="中间固定广告" type="radio">
            <input name="type" value="中间浮动广告" title="中间浮动广告" type="radio">

        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">广告位置：</label>
        <div class="layui-input-block">
            <div class="layui-inline">
                <label class="layui-form-label">X坐标：</label>
                <div class="layui-input-inline">
                    <input name="posX" lay-verify="number" autocomplete="off" class="layui-input" type="text">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">Y坐标：</label>
                <div class="layui-input-inline">
                    <input name="posY"  lay-verify="number" autocomplete="off" class="layui-input" type="text">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">宽度：</label>
                <div class="layui-input-inline">
                    <input name="width" lay-verify="number" autocomplete="off" class="layui-input" type="text">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">高度：</label>
                <div class="layui-input-inline">
                    <input name="height" lay-verify="number" autocomplete="off" class="layui-input" type="text">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">显示时间(单位：秒)：</label>
                <div class="layui-input-inline">
                    <input name="duration" lay-verify="number" autocomplete="off" class="layui-input" type="text">
                </div>
            </div>

        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">广告内容：</label>
        <div class="layui-input-block">
            <div id="pContent" title="" style="width:100%;padding:10px;">
                <!--style给定宽度可以影响编辑器的最终宽度-->
                <textarea id="myEditor" style="width:95%;height:240px;">
                   </textarea>
            </div>
        </div>
    </div>
    <div class="layui-form-item" id="dButton">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="formDemo">提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>

<form id="frmUploadImg" style="display:none;">
    <input type="file" name="file" id="btnLoadFile" onchange="doUploadImage()" accept="image/png,image/gif"/>
</form>
<form id="frmUploadVideo" style="display:none;">
    <input type="file" name="file" id="btnLoadVideoFile" onchange="doUploadVideo()" accept=".mp4,.ogg,.webm"/>
</form>
     <script type="text/javascript">
               //实例化编辑器
               var um = UE.getEditor('myEditor');
     layui.use('form',function () {
         var form=layui.form;
     });
    </script>
   <script src="<%=basePath%>/static/js/jquery.form.js"></script>
    <script src="<%=basePath%>/static/js/business/soa/advertisement.js"></script>
</body>
</html>
