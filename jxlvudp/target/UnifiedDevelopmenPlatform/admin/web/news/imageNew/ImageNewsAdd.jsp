<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/header/init_layui.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>新闻图片新增页面</title>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>/plugins/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>/plugins/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript"src="<%=basePath %>/static/js/html5media.min.js"></script>
    <style>
        body{overflow-x: hidden;}
        .layui-form-item{width: 98%;}
        .layui-upload-drag{margin: 10px ;}
        .close{cursor:pointer;width:20px;height:20px;background:rgba(255, 255, 255, 0.5);color:#000;position: absolute;right: 0px;top: 0px;z-index:50;text-align:center;line-height:20px;}
        li{list-style: none;width:150px;float: left;margin-right: 10px;position:relative;}
        li img{width:150px;height:116px;}
        li input{width: 150px;height:27px;}
    </style>
</head>
<body>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
    <legend>图片新闻的新增/修改</legend>
</fieldset>
<form class="layui-form" id="imageNewsForm">

    <div class="layui-form-item layui-anim layui-anim-scale">
        <label class="layui-form-label">新闻图片</label>
        <div class="layui-input-block">
            <div style="min-height:200px;border: 1px solid #ededed;overflow: auto;">
                <ul id="imagenews">
                    <li id="sctb">
                        <div class="layui-upload-drag" id="btnVideoAddress"><i class="layui-icon">&#xe634;</i></div>
                    </li>
                </ul>
            </div>
        </div>
    </div>

    <div class="layui-form-item layui-anim layui-anim-scale" id="xwbh">
        <label class="layui-form-label">新闻编号</label>
        <div class="layui-input-block">
            <input id="numberID" type="text" name="id" lay-verify="number" autocomplete="off" placeholder="请输入编号....." class="layui-input" maxlength="38">
        </div>
    </div>

    <div class="layui-form-item layui-anim layui-anim-scale">
        <label class="layui-form-label">新闻标题</label>
        <div class="layui-input-block">
            <input id="newstitle" type="text" name="name" lay-verify='title' autocomplete="off" placeholder="请输入标题....." class="layui-input" maxlength="30">
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

    <div class="layui-form-item layui-anim layui-anim-scale">
        <label class="layui-form-label">新闻同步</label>
        <div class="layui-input-block">
            <input type="checkbox" checked="" name="isShare" lay-skin="switch" lay-filter="switchTest" lay-text="开启|关闭" value="true">
        </div>
    </div>

    <!--如下是需要隐藏的字段-->
    <div class="layui-form-item" style="display: none;">
        <label class="layui-form-label">新闻同步</label>
        <div class="layui-input-block">
            <input type="text" name="status">
            <input type="text" name="content">
            <input type="text" name="type">
            <input type="text" name="videoAddress">
            <input type="text" name="images.title">
            <input type="text" name="images.serial">
            <input type="text" name="images.imageAddress">
        </div>
    </div>

    <div class="layui-form-item layui-anim layui-anim-scale" id="dButton">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit="" lay-filter="dButton">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
<form id="frmUploadVideo" style="display:none;">
    <input type="file" name="file" id="btnLoadVideoFile" onchange="doUploadVideo()" accept=".jpg,.jpeg,.png" multiple="multiple"/>
</form>
<script>
    var kb=new KBLayUI("news");
    var id=getURLParamValue("id");
    var type=getURLParamValue("type");
    var layer;
    if(type != 0){
        //如果是修改，新闻编号就不让它去修改了
        $("#xwbh").hide();
        getFind(id);
    }
    //点击修改页面，数据开始查询
    function getFind(id){
        kb.getMe(id,function (data) {
            $('#imageNewsForm').form('load', data);//导入修改的数据，并绑定在界面上
            $("#newsUrl").find("option[value='"+data.web.id+"']").attr("selected","selected");
            $("#newsBlock").attr("value",data.newsBlock.id);
            if(data.images == null || data.images <=0) return false;
            for(var i = 0;i<data.images.length;i++){
                $("#imagenews").append("<li class='lis'>" +
                    "                        <img src='"+getRootPath()+"/uploadfiles/"+data.images[i].imageAddress+"' alt='"+data.images[i].id+"'/>" +
                    "                        <input type='text' name='images.title' placeholder='请输入图片的标题' class='layui-input' lay-verify='title' value='"+data.images[i].description+"'>" +
                    "                        <div class='close'><i class='layui-icon'>&#x1006;</i></div>"+
                    "                    </li>");
            }
            getSelect();
        });
    }

    find();
    //新闻发布来源下来框的查询
    function find(){
        var obj = new Object();
        obj.id = "";
        obj.name = "";
        //obj.Condition = "";
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
        layer.load();
        imgupload();
    }

    //删除图片
    $(".close").live("click",function(){
        if(type != 0){
            //开始删除数据库中的图片数据，这里是根据图片地址来进行删除的
            var Imgid = $(this).siblings("img").attr("alt");
            var obj = new Object();
            obj.id = Imgid;
            //执行图片的删除
            doData("news/delImg",obj,function (data) {
                if (data.code > 0) layer.msg(data.message);
                else layer.msg(data.message);

            });
        }
        $(this).parent(".lis").remove();//只是单纯的删除页面元素
    });


    //点击上传视屏
    $("#btnVideoAddress").click(function () {
        $("#btnLoadVideoFile").click();
    });
    layui.use(['form','jquery'], function(){
        var form = layui.form,layer = layui.layer;
        //自定义验证规则
        form.verify({
            title: function(value){
                if(value.length < 2){
                    return '输入框至少要有两个字符...';
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
        form.on('submit(dButton)', function(data){
            var obj=serializeArrayToObject("imageNewsForm");
            obj.status="关闭";
            obj.type = "图片新闻";
            obj.videoAddress="";
            obj.content="";
            obj.images = imglist();//图片集
            if(imglist() == null || imglist().length <=0){
                layer.alert("请上传图片！！");
                return false;//判断是不是空的
            }
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
    function imglist(){
        //开始遍历li
        var imgsrcs = new Array();
        var i=1;
        $(".lis").each(function(){
            //获取图片的路径
            var imgsrc =$(this).find("img").attr("src");
            var titles = $(this).find("input").val();
            var temp=new Object();
            temp.id=guid();
            temp.name=titles;
            temp.imageAddress=getSrc(imgsrc,"");
            temp.serial=i++;
            temp.status="正常";
            temp.description=titles;
            imgsrcs.push(temp);
        });
        return imgsrcs;
    };
    //随机生成id
    function guid() {
        function S4() {
            return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
        }
        return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
    };
    function getSrc(obj,str){
        var src = obj;
        var result = src.replace(getRootPath()+"/uploadfiles/",str);
        return result;
    };
    //图片上传
    function imgupload(){
        doUploadFile("uploadfile","frmUploadVideo",null,function (path) {
            for(var i = 0;i<path.length;i++){
                var imgsrc = getRootPath()+"/uploadfiles/"+path[i];
                $("#imagenews").append("<li class='lis'>" +
                    "                        <img src='"+imgsrc+"' />" +
                    "                        <input type='text' name='images.title' placeholder='请输入图片的标题' class='layui-input' lay-verify='title'>" +
                    "                        <div class='close'><i class='layui-icon'>&#x1006;</i></div>"+
                    "                    </li>");
                layer.closeAll('loading');
            }
        });
    };
</script>
</body>
</html>