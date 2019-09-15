
//初始化函数
$(function () {
    //初始化文件上传插件
    initFileInput("input-id");
});

//初始化图片上传插件函数
function initFileInput(ctrlName) {
    var control = $('#' + ctrlName);
    control.fileinput({
        language: 'zh', //设置语言
        allowedFileExtensions: ['jpg', 'gif', 'png'],//接收的文件后缀
        uploadAsync: true, //默认异步上传
        uploadLabel: '发表',
        uploadTitle: '请确定内容是否写好',
        showUpload: true, //是否显示上传按钮
        showRemove : true, //显示移除按钮
        showPreview : true, //是否显示预览
        showCaption: false,//是否显示标题
        browseClass: "btn btn-primary", //按钮样式
        //dropZoneEnabled: true,//是否显示拖拽区域
        //minImageWidth: 50, //图片的最小宽度
        //minImageHeight: 50,//图片的最小高度
        //maxImageWidth: 1000,//图片的最大宽度
        //maxImageHeight: 1000,//图片的最大高度
        //maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
        //minFileCount: 0,
        maxFileCount: 9, //表示允许同时上传的最大文件个数
        enctype: 'multipart/form-data',
        previewSettings: {
            image: {width: "150px", height: "200px"},

        },
        initialPreviewConfig: {
            width: '80px',

        },
        validateInitialCount:true,
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
        msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",

    }).on('filepreupload', function(event, data, previewId, index) {     //上传中
        var form = data.form, files = data.files, extra = data.extra,
            response = data.response, reader = data.reader;
        console.log('文件正在上传');
    }).on("fileuploaded", function (event, data, previewId, index) {    //一个文件上传成功
        console.log('文件上传成功！'+data.id);

    }).on('fileerror', function(event, data, msg) {  //一个文件上传失败
        console.log('文件上传失败！'+data.id);


    })
}

//评论、回复时的输入框代码模板
var commenthtm = '<div class="anonWrite anonCommentHtml">\n' +
            '<span>回复</span>\n' +
            '<div>\n' +
            '<form class="form-horizontal" id="anonCommentForm"  method="post"\n' +
            '    action="/ZJGuideWebsite_war_exploded/AnonServlet?method=anonComment">\n' +
            '   <div class=form-group" >\n' +
            '       <textarea class="form-control anonComment" name="anonComment" rows="9" placeholder=""></textarea>\n' +
            '   </div>\n' +
            '   <div class="form-group">\n' +
            '       <input type="hidden" name="counter" class="anonComment_counter" value="">\n' +
            '       <input type="hidden" name="comment_destUid" class="comment_destUid" value="">\n' +
            '       <input type="hidden" name="comment_destUname" class="comment_destUname" value="">\n' +
            '       <input type="hidden" name="commentOrReply" class="commentOrReply" value="2">\n' +
            '       <button type="submit" form="anonCommentForm" class="btn btn-primary ">&ensp;评&ensp;论&ensp;</button>\n' +
            '    </div>\n' +
            '</form>\n' +
            '</div>\n' +
            '</div>';

//点击评论按钮
function comment_click(counter) {
    $('#myTab li:eq(3) a').tab('show');//显示评论区域
    $('#comment').html('');//对comment区域清空
    $('#comment').append(commenthtm);//向comment中添加代码
    $('.anonCommentHtml>span').html("评论");
    //将第几位匿名者写进评论文本框
    $('.anonComment').attr('placeholder','评论>>>>> 匿名者'+counter);
    //第N名---->例如：8，用于确定是哪个用户发表的说说
    $('.anonComment_counter').attr('value',counter);
    $('.comment_destUid').attr('value','anonUID');
    $('.comment_destUname').attr('value',"匿名者"+counter);
    $('.commentOrReply').attr('value',1);
}

//点击回复按钮--->回复评论
function comment_reply1(counter,sourceUid,sourceUname,commentText) {
    $('#myTab li:eq(3) a').tab('show');//显示评论区域
    $('#comment').html('');//对comment区域清空
    $('#comment').append(commenthtm);//向comment中添加代码
    $('.anonComment').attr('placeholder','回复>>>>> '+sourceUname+" :"+commentText);
    $('.comment_destUid').attr('value',sourceUid);
    $('.comment_destUname').attr('value',sourceUname);
    $('.anonComment_counter').attr('value',counter);

}

//点击回复按钮---->回复用户回复
function comment_reply2(counter,sourceUid,sourceUname,destUname,commentText) {
    $('#myTab li:eq(3) a').tab('show');//显示评论区域
    $('#comment').html('');//对comment区域清空
    $('#comment').append(commenthtm);//向comment中添加代码
    $('.anonComment').attr('placeholder','回复>>>>> '+sourceUname+"回复"+destUname+":"+commentText);
    $('.comment_destUid').attr('value',sourceUid);
    $('.comment_destUname').attr('value',sourceUname);
    $('.anonComment_counter').attr('value',counter);
}

//点赞功能
function anon_like(thisobject,counter) {
    var likeSrc = "/ZJGuideWebsite_war_exploded/image/like2.png";
    if (likeSrc==$(thisobject).find('img').attr('src')){
        $(thisobject).find('img').attr('src','/ZJGuideWebsite_war_exploded/image/like1.png');
        $.post("/ZJGuideWebsite_war_exploded/AnonServlet?method=anonLike",{counter:counter},function (data, status) {
            for (var key in data) {
                $(thisobject).find("span").html(data[key]);
            }
        });
    }else {
        $(thisobject).find('img').attr('src','/ZJGuideWebsite_war_exploded/image/like2.png');
        $.post("/ZJGuideWebsite_war_exploded/AnonServlet?method=cancelAnonLike",{counter:counter},function (data, status) {
            for (var key in data) {
                $(thisobject).find("span").html(data[key]);
            }
        });
    }

}

//删除匿名说说功能
function anon_del(thisobject,counter) {
    var returnVal = window.confirm("是否要删除本条匿名说说(匿名者"+counter+")？", "删除");
    if(returnVal){
        $.post("/ZJGuideWebsite_war_exploded/AnonServlet?method=anonDel",{counter:counter},function (data, status) {
            $(thisobject).parent().parent().parent().remove();
            alert("删除成功！")
        });
    }

}

//用户点击左边的消息
function showMessages() {
    $.post("/ZJGuideWebsite_war_exploded/AnonServlet?method=showMessages",function (data, status) {
        var publishedHtml = '<div class="userAnonsHtml">\n' +
                                '<div class="userAnonsPanel">\n' +
                                '</div>\n'+
                            '</div>\n';
        //判断用户是否有消息未查看
        if (!$.isEmptyObject(data)){
            $('#advices').html('');//对comment区域清空
            $('#advices').append(publishedHtml);
            $.each(data,function (index, item) {
                var servletUrl = "showAnonDetails(\""+item.anonID+"\","+true+")";
                $('#advices .userAnonsPanel').append("<a class='thisA' style='color: black' href='#' onclick=''><div class='userAnonsItem'><B>"
                    +item.commentTime+"&ensp;&ensp;"+item.sourceUname+"</B><div>&ensp;&ensp;"
                    +item.commentText+"</div></div></input>");
                $('.thisA').attr("onclick",servletUrl);
                $('.thisA').removeAttr("class");
            });
        }
    });
}

//用户点击左边的已发表选项卡
function showUserAnon() {
    $.post("/ZJGuideWebsite_war_exploded/AnonServlet?method=showUserAnon",function (data, status) {
        var publishedHtml = '<div class="userAnonsHtml">\n' +
                                '<div class="userAnonsPanel">\n' +
                                '</div>\n'+
                            '</div>\n';
        //判断用户是否有发表过
        if (!$.isEmptyObject(data)){
            $('#published').html('');//对comment区域清空
            $('#published').append(publishedHtml);
            $.each(data,function (index, item) {
                var servletUrl = "showAnonDetails("+item.counter+","+false+")";
                $('#published .userAnonsPanel').append("<a class='thisA' style='color: black' href='#' onclick=''><div class='userAnonsItem'><B>匿名者"
                    +item.counter+"&ensp;&ensp;"+item.deliveryTime+"</B><div>&ensp;&ensp;"
                    +item.anonContent+"</div></div></input>");
                $('.thisA').attr("onclick",servletUrl);
                $('.thisA').removeAttr("class");
            });
        }
    });
}


//用户点击左边的评论选项卡
function showUserComment() {
    $.post("/ZJGuideWebsite_war_exploded/AnonServlet?method=showUserComment",function (data, status) {
        var publishedHtml = '<div class="userAnonsHtml">\n' +
            '<div class="userAnonsPanel">\n' +
            '</div>\n'+
            '</div>\n';
        //判断用户是否有发表过
        if (!$.isEmptyObject(data)){
            $('#comment').html('');//对comment区域清空
            $('#comment').append(publishedHtml);
            $.each(data,function (index, item) {
                var servletUrl = "showAnonDetails(\""+item.anonID+"\","+false+")";
                $('#comment .userAnonsPanel').append("<a class='thisA' style='color: black' href='#' onclick=''><div class='userAnonsItem'><B>"
                    +item.commentTime+"&ensp;&ensp;"+item.destUname+"</B><div>&ensp;&ensp;"
                    +item.commentText+"</div></div></input>");
                $('.thisA').attr("onclick",servletUrl);
                $('.thisA').removeAttr("class");
            });
        }
    });
}

//用户点击左边区域的已发表中的其中一项，显示该项的详细信息
function showAnonDetails(counter,isMessage){
    $.post("/ZJGuideWebsite_war_exploded/AnonServlet?method=showAnonDetails&counter="+counter,function (data,status) {
        console.log(data);
            $('#div2Panel').html('');
            var oneAnon = "<div class='anonymityItem row'>\n" +
                "    <!--用户头像-->\n" +
                "    <div class='col-sm-1'>\n" +
                "        <img src='image/headimg2.jpg' class='headImg'>\n" +
                "    </div>\n" +
                "    <!--其他-->\n" +
                "    <div class='col-sm-11'>\n" +
                "        <div>\n" +
                "            <strong style='margin-right: 30px;'>匿名者"+data[2].counter+"</strong>\n" +
                "            <a class='btn btn-xs anon_comment' onclick='comment_click("+data[2].counter+")'>\n" +
                "                <img src='image/comment.png'\n" +
                "                     style='width: 18px;height: 18px;'>&ensp;评论\n" +
                "            </a>\n" +
                "            <a class='btn btn-xs anon_like' id='thisAnon_like' onclick='anon_like(this,"+data[2].counter+")'>\n" +
                "            </a>\n" +
                "            <a class='btn btn-xs anon_del' onclick='anon_del(this,"+data[2].counter+")'>\n" +
                "                <img src='image/del.png'\n" +
                "                     style='width: 18px;height: 18px;'>&ensp;删除\n" +
                "            </a>\n" +
                "        </div>\n" +
                "        <div class='itemContent'>\n" +
                "            <div>"+data[2].anonContent+"</div>\n" +
                "            <div id='thisAnon_price'>\n" +
                "                <!--遍历图片集合-->\n" +
                "            </div>\n" +
                "            <!--用户回复评论-->\n" +
                "            <div class='row itemComment' id='thisAnon_comment'>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>";
            $('#div2Panel').append(oneAnon);
            //判断用户自己是否有点赞自己
            var oneselfLike = isOneselfLike(data[1]);
            if(-1 != oneselfLike){
                $('#thisAnon_like').append("<img src='image/like1.png'\n"
                    +"style='width: 18px;height: 18px;'>&ensp;点赞(<span class='anon_like_coun'>"
                    +data[2].likeCoun+"</span>)\n"
                )
            }else if (-1 == oneselfLike) {
                $('#thisAnon_like').append("<img src='image/like2.png'\n"
                    +"style='width: 18px;height: 18px;'>&ensp;点赞(<span class='anon_like_coun'>"
                    +data[2].likeCoun+"</span>)\n"
                )
            };
            //显示该匿名说说的图片
            for (var i = 0; i < data[3].length; i++) {
                $('#thisAnon_price').append("<img src='imgUrl/"+data[3][i].priceUrl+"' class='headImg'>\n");
            }
            //显示该匿名说说的评论
            for (var i = 0; i < data[0].length; i++) {
                if (data[0][i].commentOrReply==1) {
                    $('#thisAnon_comment').append("<div class='col-sm-12'>\n" +
                        "<strong>"+data[0][i].sourceUname+"</strong>&ensp;:\n" +
                        "<span>"+data[0][i].commentText+"</span>&ensp;&ensp;\n" +
                        "<a class='btn btn-default btn-xs' onclick='comment_reply1("+data[2].counter+
                        ",\""+data[0][i].sourceUid+"\",\""+data[0][i].sourceUname+"\",\""+data[0][i].commentText+"\")'>回复</a>&ensp;&ensp;\n" +
                        "</div>");

                }else {
                    $('#thisAnon_comment').append("<div class='col-sm-12'>\n" +
                        "<strong>"+data[0][i].sourceUname+"</strong>&ensp;回复&ensp;<strong>"+data[0][i].destUname+"</strong>&ensp;:\n" +
                        "<span>"+data[0][i].commentText+"</span>&ensp;&ensp;\n" +
                        "<a class='btn btn-default btn-xs' onclick='comment_reply2("+data[2].counter+
                        ",\""+data[0][i].sourceUid+"\",\""+data[0][i].sourceUname+"\",\""+data[0][i].destUname+"\",\""+data[0][i].commentText+"\")'>回复</a>&ensp;&ensp;\n" +
                        "</div>");
                }
            }
        if (isMessage){
            $.post("/ZJGuideWebsite_war_exploded/AnonServlet?method=readMessage&anonID="+data[2].anonID+"&destUid="+returnLoginUid());
        }
    });

}
