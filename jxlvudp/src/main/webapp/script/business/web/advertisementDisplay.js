/**
 * 实现角色类型各种操作
 */
var baseURL = "../advertisement";
/*处理固定广告*/
function doFixedAdvertisement(data) {
    $("#dContent").html(data.content);
   var advertise="<img link='"+data.linkUrl+"' src='"+getRootPath()+"/uploadfiles/"+data.imgAddress+"' width='"+data.width+"px' height='"+data.height+"px' style='cursor:pointer;position:absolute;top:"+data.posY+"px;left:"+data.posX+"px'/>";
    if (data.imgAddress!=null && data.imgAddress!="")
       $(document.body).append(advertise);
    if (data.videoAddress!=null && data.videoAddress!="")
    $(document.body).append("<video id='vVideoAddress' controls='controls' src='"+getRootPath()+"/uploadfiles/"+data.videoAddress+"' width='"+data.width+"px;' height='"+data.height+"px;' controls preload autoplay>您的浏览器不支持 video 标签。</video>");

}

$(function () {
    var obj=new Object();
    obj.id=getURLParamValue("id");
    doData(baseURL+"/getMe",obj,function (data) {
        doFixedAdvertisement(data);
    });

    $("body img").live("click",function () {
        window.open($(this).attr("link"));
    })
});


 