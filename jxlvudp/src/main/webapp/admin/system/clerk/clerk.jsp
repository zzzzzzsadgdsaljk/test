<%--
  Created by IntelliJ IDEA.
  User: wph-pc
  Date: 2017/10/12
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/header/init_layui.jsp"%>
<html>
<head>
    <title>员工基本信息</title>
</head>
<body style="padding-top: 10px;">
<form class="layui-form" id="ff">
    <input type="hidden" name="oldId"/>


    <div class="layui-form-item">
        <label class="layui-form-label">员工工号：</label>
        <div class="layui-input-block">
            <input name="id"  required  lay-verify="required|number" autocomplete="off" placeholder="请输入网站的编号，要求5位以内的数字" class="layui-input" type="text" maxlength="5">
        </div>
    </div>

    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>员工基本信息</legend>
    </fieldset>




    <div class="layui-form-item">

        <div class="layui-inline">
            <label class="layui-form-label">姓名：</label>
            <div class="layui-input-block">
                <input name="xm" id="clerkName" required  lay-verify="required" autocomplete="off" placeholder="请输入姓名" class="layui-input" type="text" maxlength="10">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">英文姓名：</label>
            <div class="layui-input-block">
                <input name="ywxm"  autocomplete="off" placeholder="请输入英文姓名" class="layui-input" type="text" maxlength="8">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">姓名拼音：</label>
            <div class="layui-input-block">
                <input name="xmpy" required  lay-verify="required" autocomplete="off" placeholder="请输入姓名拼音" class="layui-input" type="text" maxlength="20">
            </div>
        </div>

        <div class="layui-inline">
            <label class="layui-form-label">曾用名：</label>
            <div class="layui-input-block">
                <input name="cym"  autocomplete="off" placeholder="请输入曾用名" class="layui-input" type="text" maxlength="10">
            </div>
        </div>
    </div>

    <div class="layui-form-item">

        <label class="layui-form-label">组织机构：</label>
        <div class="layui-input-block">
            <select name="org.id" id="sltOrg"></select>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-inline">
        <label class="layui-form-label">手机：</label>
        <div class="layui-input-block">
            <input name="contacts" id="clerkValue" autocomplete="off" placeholder="请输入电话号码" class="layui-input" type="text" maxlength="13">
        </div>
        </div>

        <div class="layui-inline">
            <label class="layui-form-label">QQ：</label>
            <div class="layui-input-block">
                <input name="contacts" autocomplete="off" placeholder="请输入qq号码" class="layui-input" type="text" maxlength="13">
            </div>
        </div>

        <div class="layui-inline">
            <label class="layui-form-label">电子邮件：</label>
            <div class="layui-input-block">
                <input name="contacts"  autocomplete="off" placeholder="请输入电子邮件" class="layui-input" type="text" maxlength="13">
            </div>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">性别：</label>
        <div class="layui-input-block">
            <input name="xbm" value="男" title="男" checked="checked" type="radio">
            <input name="xbm" value="女" title="女" type="radio">
        </div>
    </div>



            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">出生日期:</label>
                    <div class="layui-input-inline">
                        <input name="csrq" class="layui-input" id="test1" placeholder="请输入出生日期" type="text">
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label">出生地：</label>
                    <div class="layui-input-block">
                        <input name="csdm" required  lay-verify="required" autocomplete="off" placeholder="请输入出生地" class="layui-input" type="text" maxlength="50">
                    </div>
                </div>
            </div>









            <div class="layui-form-item">
                <div class="layui-inline"><label class="layui-form-label">籍贯：</label>
                    <div class="layui-input-block">
                        <input name="jg" required  lay-verify="required" autocomplete="off" placeholder="请输入籍贯" class="layui-input" type="text" maxlength="10">
                    </div></div>
                <div class="layui-inline">
                    <label class="layui-form-label">民族：</label>
                    <div class="layui-input-block">
                        <input name="mzm" required  lay-verify="required" autocomplete="off" placeholder="请输入民族" class="layui-input" type="text">
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label">国籍或地区码：</label>
                    <div class="layui-input-block">
                        <input name="gjdqm" required  lay-verify="required" autocomplete="off" placeholder="请输入国籍或地区码" class="layui-input" type="text" maxlength="10">
                    </div>
                </div>
            </div>





            <div class="layui-form-item">
                <label class="layui-form-label">证件类型：</label>
                <div class="layui-input-block">

                    <select id="sfzjlxm" name="sfzjlxm" lay-filter="zjian">
                        <option value=""></option>
                        <option  value="身份证"selected="" >身份证</option>
                        <option value="港澳通行证"  >港澳通行证</option>
                        <option value="台湾通行证">台湾通行证</option>
                        <option value="护照">护照</option>

                    </select>

                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">证件号：</label>
                <div class="layui-input-block">
                    <input id="sfzjh" name="sfzjh" required  lay-verify="required"  autocomplete="off" placeholder="请输入身份证号,由数字和字母组成" class="layui-input" type="text"maxlength="18"value="">

                </div>

            </div>




            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">婚姻状况：</label>
                    <div class="layui-input-block">
                        <select name="hyzkm" lay-filter="zjian">
                            <option value=""></option>
                            <option value="未婚"selected="">未婚</option>
                            <option value="已婚" >已婚</option>

                        </select>
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label">政治面貌：</label>
                    <div class="layui-input-block">
                        <select  name="zzmmm" lay-filter="zjian">
                            <option value=""></option>
                            <option  value="共青团员"selected="" >共青团员</option>
                            <option value="中共党员"  >中共党员</option>
                            <option value="其他">其他</option>


                        </select>

                    </div>
                </div>
            </div>


            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">健康状况：</label>
                    <div class="layui-input-block">
                        <select  name="jkzkm" lay-filter="zjian">
                            <option value=""></option>
                            <option  value="健康"selected="" >健康</option>
                            <option value="亚健康"  >亚健康</option>
                            <option value="疾病">疾病</option>

                        </select>

                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label">宗教信仰</label>
                    <div class="layui-input-block">
                        <select name="xyzjm" lay-filter="zjian">
                            <option value=""></option>
                            <option  value="佛教">佛教</option>
                            <option value="伊斯兰教" >伊斯兰教</option>
                            <option value="基督教">基督教</option>
                            <option value="道教">道教</option>
                            <option value="无"selected="">无</option>
                        </select>
                    </div>
                </div>
            </div>



            <div class="layui-form-item">
                <label class="layui-form-label">血型：</label>
                <div class="layui-input-block">
                    <select name="xxm" lay-filter="zjian">
                        <option value=""></option>
                        <option value="O型">O型</option>
                        <option value="A型"selected="" >A型</option>
                        <option value="B型">B型</option>
                        <option value="AB型">AB型</option>

                    </select>

                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">照片：</label>
                <div class="layui-input-block">
                    <input name="zp" id="imgAddress"type="hidden"/>

                    <img id="imgImageAddress" alt="暂无图片" src="<%=basePath %>/images/zanwu.jpg" style="width:100px;height:100px;cursor:pointer;" title="双击图片，完成新图片上传"/>

                </div>
            </div>



            <div class="layui-form-item">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">证件有效期：</label>
                        <div class="layui-input-inline">
                            <input name="sfzjyxq" class="layui-input" id="test2" placeholder="请输入身份证件有效期" type="text">
                        </div>
                    </div>

                    </div>
                </div>


                    <div class="layui-form-item">
                        <label class="layui-form-label">状态：</label>
                        <div class="layui-input-block">
                            <input name="status" value="正常" title="正常" checked="checked" type="radio">
                            <input name="status" value="关闭" title="关闭" type="radio">
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
<script src="<%=basePath%>/script/common/jquery.form.js"></script>
<script>
    var kb=new KBLayUI("/clerk");
    var parentId=getURLParamValue("parentId");
    var type=getURLParamValue("type");//获取操作类型，0表示新增，其他表示修改

    if(type!=0)
        kb.getMe(getURLParamValue("id"),function (data) {

            $('#ff').form('load', data);//导入修改的数据，并绑定在界面

            $("#test1").val(formatDatebox(data.csrq));//转换为时间格式...
            var imgsrc = $("#imgAddress").val();
            $("#imgImageAddress").attr("src",imgsrc);


        });
    if (type==2)//表示浏览
    {

        $("#dButton").hide();



    }

    kb.doFormLayui("formDemo",function () {
        var obj=serializeArrayToObject("ff");


        if (type==0)
            kb.add(obj,function (returnBack) {});
        else

            kb.edit(obj,function (returnBack) { });

        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        parent.layer.close(index);
        parent.find();


    });
    /*时间选择*/
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //常规用法
        laydate.render({
            elem: '#test1'
        });
    });

    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //常规用法
        laydate.render({
            elem: '#test2'
            ,type: 'month'
        });
    });
    /*图片上传*/
    $("#imgImageAddress").click(function () {
        $("#btnLoadFile").click();
    });

    function doUploadImage() {
        doUploadFile("uploadfile","frmUploadImg",null,function (path) {
            var imgsrc = getRootPath()+"/uploadfiles/"+path;
            $("#imgAddress").val(imgsrc);
            $("#imgImageAddress").attr("src",imgsrc);
        });
    };

    layui.use(['form'], function() {
        var form = layui.form;
        form.on('select(zjian)', function (data) {
            console.log(data.elem); //得到select原始DOM对象
            console.log(data.value); //得到被选中的值
            console.log(data.othis); //得到美化后的DOM对象

            if("身份证"==data.value)
            {


                $('#sfzjh').attr("placeholder","请输入身份证号,由数字和字母组成");

            }else if("港澳通行证"==data.value)
            {
                $('#sfzjh').attr("placeholder","请输入港澳通行证号,由数字和字母组成");
            }else if("台湾通行证"==data.value)
            {
                $('#sfzjh').attr("placeholder","请输入台湾通行证号,由数字和字母组成");
            }else if("护照"==data.value)
            {
                $('#sfzjh').attr("placeholder","请输入护照号,由数字和字母组成");
            }

        });

    });



    /*完成站点加载，如果是不是新增，要实现数据选中*/
    doData("org/find",{name:''},function (data) {
        if (data!=null && data.obj!=undefined && data.obj!=null)
        {
            bindSelectData("sltOrg",data.obj,"id","name");
        }
        if(type!=0)
            kb.getMe(getURLParamValue("id"),function (data) {
                $('#ff').form('load', data);//导入修改的数据，并绑定在界面上
                $("#sltActorType").find("option[value='"+data.actorType.id+"']").attr("selected","selected");
                layui.use(['form'],function () {
                    var form=layui.form;
                    form.render('select'); //刷新select选择框渲染
                });
            });
        layui.use(['form'],function () {
            var form=layui.form;
            form.render('select'); //刷新select选择框渲染
        });
    });


</script>
</body>
</html>
