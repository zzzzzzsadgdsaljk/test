<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../../../header/init_layui.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>普通新闻新增页面/修改</title>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>/plugins/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>/plugins/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript"src="<%=basePath %>/static/js/html5media.min.js"></script>
    <style>
        body{overflow-x: hidden;}
        .layui-form-item{width: 98%;}
    </style>
</head>
<body>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
    <legend>普通新闻的新增/修改</legend>
</fieldset>
<form id="normalform" class="layui-form">

    <div class="layui-form-item">
        <label class="layui-form-label">发布网站</label>
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

    <div class="layui-form-item layui-anim layui-anim-scale" id="xwbh">
        <label class="layui-form-label">新闻编号</label>
        <div class="layui-input-block">
            <input type="text" name="id" lay-verify="number" autocomplete="off" placeholder="请输入编号....." class="layui-input" maxlength="38">
        </div>
    </div>

    <div class="layui-form-item layui-anim layui-anim-scale">
        <label class="layui-form-label">新闻标题</label>
        <div class="layui-input-block">
            <input type="text" name="name" autocomplete="off" lay-verify="title" placeholder="请输入标题....." class="layui-input" maxlength="38">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">新闻同步</label>
        <div class="layui-input-block">
            <input type="checkbox" checked="" name="isShare" lay-skin="switch" lay-filter="switchTest" lay-text="开启|关闭" value="true">
        </div>
    </div>

    <div class="layui-form-item layui-anim layui-anim-scale">
        <label class="layui-form-label">编辑内容</label>
        <div class="layui-input-block">
            <div id="pContent" title="" style="width:100%;padding:10px;">
                <textarea id="myEditor" style="height:400px;width:100%;" name="content"></textarea>
            </div>
        </div>
    </div>

    <div class="layui-form-item layui-anim layui-anim-scale" id="dButton">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="formNews" >立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>

</form>
<script>
    //创建一个编辑器
    var ueditor = UE.getEditor('myEditor');
    var kb=new KBLayUI("news");
    var id=getURLParamValue("id");
    var type=getURLParamValue("type");
    var layer;
    var $content = $("#LAY_demo_editor");
    if(type != 0){
        //新闻修改就不让它的编号出现
        $("#xwbh").hide();
        getFind(id);
    }
    function getFind(id){
        kb.getMe(id,function (data) {
            $('#normalform').form('load', data);//导入修改的数据，并绑定在界面上
            $("#newsBlock").attr("option[value='"+data.newsBlock.id+"']").attr("selected","selected");
            $("#newsUrl").find("option[value='"+data.web.id+"']").attr("selected","selected");
            getSelect();//主要把返回的content的值赋值给富文本编辑器中
            //百度富文本动态赋值
            ueditor.addListener("ready", function () {
                // editor准备好之后才可以使用
                ueditor.setContent(data.content);
            });
        });
    };

    //开始查找
    find();

    //开始渲染数据
    getSelect();

    //新闻发布来源下来框的查询
    function find(){
        var obj = new Object();
        obj.id = "";
        obj.name = "";
        //获取发布的网站
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
    };

    function getSelect(){
        //使用原生的layui
        layui.use(['form','layedit'], function(){
            var form = layui.form,layer = layui.layer,layedit = layui.layedit;
            //自定义验证规则
            form.verify({
                title: function(value){
                    if(value.length < 3){
                        return '输入框中的值至少不能小于3个字符！！';
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

                if(content.length > 5000) {
                    layer.alert("您在富文本编辑器中输入的数据太大了,无法进行新增！！",{offset:"50px"});
                    return false;
                }
                var obj = serializeArrayToObject("normalform");

                obj.status="关闭";

                obj.content=content;

                obj.type = "普通新闻";

                obj.videoAddress="";

                if(obj.isShare == "true")  obj.isShare= true;

                if(obj.isShare == "false") obj.isShare= false;

                if(type == 0)
                    kb.add(obj,function(returnBack){
                        if(returnBack.code<=0){
                            layer.msg("新闻编号冲突了，请重新换一个吧！！");
                        }
                        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                        parent.layer.close(index);
                        parent.find();
                    });
                else
                    kb.edit(obj,function(returnBack){
                        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                        parent.layer.close(index);
                        parent.find();
                    });
                return false;
            });
            form.render('select');
        });
    };
    //禁止留言板提交代码
    function stripscript(s)
    {
        var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？[\\n]/ig]")
        var rs = "";
        for (var i = 0; i < s.length; i++) {
            rs = rs+s.substr(i, 1).replace(pattern, '');
        }
        return rs;
    }
</script>
</body>
</html>
