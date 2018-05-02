<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../../../header/init_layui.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>视屏新闻新增页面</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0;">
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>/plugins/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>/plugins/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript"src="<%=basePath %>/static/js/html5media.min.js"></script>
    <style>
        body{overflow-x: hidden;}
        .layui-form-item{width: 98%;}
        .VideoAddress{width:100%;height:300px;}
        #video{width:100%;height: 100%;position: absolute;z-index:5000;left:0;top:0px;background: #fafafa;}
        #btnVideoAddress{position: absolute;z-index:50000;left: 46%;top: 89px;background: #FFEB3B;border: 0px;border-radius: 5px;}
        .layui-upload-drag .layui-icon{color:rgba(255, 255, 255, 0.8);}
    </style>
</head>
<body>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
    <legend>视屏新闻的新增/修改</legend>
</fieldset>
<div class="layui-row">
    <div class="layui-col-md12">
        <form id="news" class="layui-form">

            <div class="layui-form-item layui-anim layui-anim-scale">
                <div class="layui-input-block">
                    <div class="VideoAddress">
                        <video id="video" src="" autoplay></video>
                        <div class="layui-upload-drag" id="btnVideoAddress"><i class="layui-icon">&#xe660;</i></div>
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">发布网站:</label>
                <div class="layui-input-block" >
                    <select name="web.id" id="newsUrl"></select>
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">新闻版块</label>
                <div class="layui-input-block">
                    <select name="newsBlock.id" id="newsBlocks"></select>
                </div>
            </div>

            <div class="layui-form-item" id="videourl" style="display: none;">
                <label class="layui-form-label">视频链接:</label>
                <div class="layui-input-block">
                    <input type="url" name="videoAddress"  id="videoAddress" placeholder="视屏路径......" lay-verify="title" autocomplete="off" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item layui-anim layui-anim-scale" id="xwbh">
                <label class="layui-form-label">新闻编号</label>
                <div class="layui-input-block">
                    <input type="text" name="id" lay-verify="number" autocomplete="off" placeholder="请输入编号....." class="layui-input" maxlength="40">
                </div>
            </div>

            <div class="layui-form-item layui-anim layui-anim-scale">
                <label class="layui-form-label">新闻标题</label>
                <div class="layui-input-block">
                    <input type="text" name="name" autocomplete="off" lay-verify="title" placeholder="请输入标题....." class="layui-input" maxlength="50">
                </div>
            </div>

            <div class="layui-form-item layui-anim layui-anim-scale">
                <label class="layui-form-label">新闻同步</label>
                <div class="layui-input-block">
                    <input type="checkbox" checked="" name="isShare" lay-skin="switch" lay-filter="switchTest" lay-text="同步|不同步" value="true">
                </div>
            </div>

            <div class="layui-form-item layui-anim layui-anim-scale"style="height:500px;overflow-x:hidden;">
                <div class="layui-input-block">
                    <textarea id="myEditor" style="height:400px;width:100%;" name="content"></textarea>
                </div>
            </div>

            <div class="layui-form-item layui-anim layui-anim-scale" id="dButton">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="formNews" >立即提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>

        </form>
    </div>
</div>
<form id="frmUploadVideo" style="display:none;">
    <input type="file" name="file" id="btnLoadVideoFile" onchange="doUploadVideo()" accept=".mp4,.ogg,.webm"/>
</form>
<script>
    //实例化编辑器
    var ueditor = UE.getEditor('myEditor');
    var kb=new KBLayUI("news");
    var id=getURLParamValue("id");
    var type=getURLParamValue("type");
    var layer;

    if(type != 0){
        //新闻编号不能显示出来
        $("#xwbh").hide();
        getFind(id);
    }
    //如歌是修改
    function getFind(id){
        kb.getMe(id,function (data) {
            $('#news').form('load', data);//导入修改的数据，并绑定在界面上
            $("#newsUrl").find("option[value='"+data.web.id+"']").attr("selected","selected");
            $("#newsBlock").attr("value",data.newsBlock.id);
            //百度富文本动态赋值
            ueditor.addListener("ready", function () {
                // editor准备好之后才可以使用
                ueditor.setContent(data.content);
            });
            $("#video").attr("src",getRootPath()+"/uploadfiles/"+data.videoAddress);
            $("#btnVideoAddress").css({background:"rgba(39, 38, 33, 0.42)"});
            getSelect();
        });
    }

    find();
    //新闻发布来源下来框的查询
    function find(){
        var obj = new Object();
        obj.id = "";
        obj.name = "";
        //获取发布网站的来源
        doData("webSite/find",obj,function (data) {
            if (data == null && data.obj == undefined && data.obj == null) return false;

            for(var i=0;i<data.obj.length;i++)
            {
                $("#newsUrl").append("<option value='"+data.obj[i].id+"' selected=''>"+data.obj[i].name+"</option>");
            }
            //开始渲染select下拉框的值
            getSelect();
        });
        //新闻版块的select
        doData("newsCategory/findForRow",obj,function (data) {
            if (data == null && data.obj == undefined && data.obj == null) return false;

            for(var i=0;i<data.obj.length;i++)
            {
                $("#newsBlocks").append("<option value='"+data.obj[i].id+"' selected=''>"+data.obj[i].name+"</option>");
            }
            //开始渲染select下拉框的值
            getSelect();
        });
    }
    function getSelect(){
        layui.use(['form','layer'],function () {
            var form=layui.form;
            layer = layui.layer;
            form.render('select');
        });
    }
    //视屏新闻上传
    function doUploadVideo() {
        doUploadFile("uploadfile","frmUploadVideo",function(){
            layer.load(0,{offset:"150px",shade: [0.3,'#000']});
        },function (path) {
            layer.msg("上传成功！");
            layer.closeAll('loading');
            $("#btnVideoAddress").hide();
            var imgsrc = getRootPath()+"/uploadfiles/"+path;
            $("#videoAddress").val(path);
            $("#video").attr("src",imgsrc);
        });
    }
    //点击上传视屏
    $("#btnVideoAddress").click(function () {
        $("#btnLoadVideoFile").click();
    });
    layui.use(['form','jquery'], function(){
        var form = layui.form,layer = layui.layer;
        //自定义验证规则
        form.verify({
            title: function(value){
                if(value.length < 5){
                    return '输入框不能为空';
                }
            }
        });
        //监听指定开关
        form.on('switch(switchTest)', function(data){
            if(this.checked == true) layer.tips('温馨提示：新闻子父网站将同步显示', data.othis, {
                tips: [1, '#78BA32'],
                time: 4000
            });
            else layer.tips('温馨提示：新闻将不能子父网站的同步显示', data.othis, {
                tips: [1, '#FF5722'],
                time: 4000
            });
        });
        //监听提交
        form.on('submit(formNews)', function(data){
            var content = UE.getEditor('myEditor').getContent();
            if(content.length > 50000) {
                layer.alert("您在富文本编辑器中输入的数据太大了,无法进行新增！！");
                return false;
            }
            var obj=serializeArrayToObject("news");
            obj.content=content;
            obj.status="关闭";
            obj.type = "视屏新闻";
            if(obj.isShare == "true")  obj.isShare= true;
            if(obj.isShare == "false") obj.isShare= false;
            if (type==0)
                kb.add(obj,function (returnBack) {
                    if(returnBack.code<=0){
                        layer.msg("新闻编号冲突了，请重新换一个吧！！");
                    }
                    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                    parent.layer.close(index);
                    parent.find();
                });
            else
                kb.edit(obj,function (returnBack) {
                    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                    parent.layer.close(index);
                    parent.find();
                });

            return false;
        });
    });
</script>
</body>
</html>
