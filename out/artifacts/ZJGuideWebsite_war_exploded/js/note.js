// 保存按时间还是热门的cookie
function saveHotOrNew(hotOrNew) {
    $.cookie('hotOrNew',hotOrNew);
    showHotOrNew(hotOrNew);
}
// 返回按时间还是热门的cookie
function returnHotOrNew() {
    var hotOrNew = $.cookie('hotOrNew');
    $.cookie('hotOrNew',null);
    return hotOrNew;
}
// 页面加载完成后显示是按时间还是按热门
function showHotOrNew(hotOrNew) {
    if ("hot" == hotOrNew){
        // 显示最热的寄语
        $('.showHotOrNew>a:first-child').removeClass('btn-success');
        $('.showHotOrNew>a:first-child').addClass('btn-default');
        $('.showHotOrNew>a:last-child').addClass('btn-success');
    }else if("new" == hotOrNew){
        // 显示最新的寄语
        $('.showHotOrNew>a:first-child').addClass('btn-success');
        $('.showHotOrNew>a:last-child').removeClass('btn-success');
    }
}
// 点击“有用”按钮，进行一系列操作
function useful_button(that,noteId) {
    var usefuled = $(that).hasClass('btn-warning');
    $.post("/ZJGuideWebsite_war_exploded/note_clickUseful.action",{noteId:noteId,usefuled:usefuled},function () {
        if (false==usefuled){
            $(that).addClass('btn-warning');
            var coun = $(that).html().match(/\d+/);
            coun++;
            $(that).html("有用("+coun+")");
        }else {
            $(that).removeClass('btn-warning');
            var coun = $(that).html().match(/\d+/);
            coun--;
            $(that).html("有用("+coun+")");
        }


    });
}

// 点击“提出异议”按钮
function Objection_button(that, noteId) {
    var note = $(that).parent().prev().children("h3").html().replace(/^\s*|\s*$/g,"");
    $('#notePrompt').val(note);
    $('#objectionForm .objectionFormInput').val(noteId);
}

// 点击“举报”按钮
function Toreport_button(that, noteId) {
    var note = $(that).parent().prev().children("h3").html().replace(/^\s*|\s*$/g,"");
    $('#notePrompt2').val(note);
    $('#ToreportModal .ToreportFormInput').val(noteId);
}

// 显示登陆用户发表的寄语
function showMyNote() {
    $.post("/ZJGuideWebsite_war_exploded/note_showMyNote_JSON.action",function (data, status) {
        if(data != null){
            $('.myNote').html("");
        }
        for (var index in data) {

            var oneHtml =
                "<a href='#' data-toggle=\"modal\" data-target=\"#showOneNoteDetails\" onclick='showMyNoteDetails(&apos;"+data[index].noteId+"&apos;)'>\n"+
                    "<div class=\"panel panel-default\" title='点击查看寄语的详情'>\n" +
                    "  <div class=\"panel-heading\">"+time(data[index].publishedTime)+"</div>\n" +
                    "  <div class=\"panel-body\">\n"+data[index].note+"</div>\n" +
                    "</div>\n"+
                "</a>"
            $('.myNote').append(oneHtml)
        }

    })
}
//显示寄语详情
function showMyNoteDetails(noteId) {
   $.post("/ZJGuideWebsite_war_exploded/note_showOne_JSON.action",{noteId:noteId},function (data,status) {
       $('.OneNoteDetails-body').html(data.note);
       var onclickURL = "window.location.href='/ZJGuideWebsite_war_exploded/note_deleteNote.action?noteId="+noteId+"'";
       var usefulCoun = "有用("+data.usefulCoun+")";
       var objectionCoun = "提出异议("+data.objectionCoun+")";
       var toreportCoun = "举报("+data.toreportCoun+")";
       var alterURL = "alterNote('"+noteId+"')"

       $('.OneNoteDetails-footer>button:nth-child(1)').attr("onclick",onclickURL);
       $('.OneNoteDetails-footer>button:nth-child(2)').html(usefulCoun);
       $('.OneNoteDetails-footer>button:nth-child(3)').html(objectionCoun);
       $('.OneNoteDetails-footer>button:nth-child(4)').html(toreportCoun);
       $('.OneNoteDetails-footer>button:nth-child(5)').attr("onclick",alterURL);
   })
}

//显示我的信息
function showMyMessages() {
    $.post("/ZJGuideWebsite_war_exploded/note_showMyMessages_JSON.action",function (data,status) {
        console.log(data);
        if(data != null){
            $('.myMessages').html("");
        }
        //提出异议
        for (var index in data.noteObjectionList) {
            var oneHtml =
                "<a href='#' data-toggle=\"modal\" data-target=\"#showOneNoteDetails\"" +
                "        onclick='showDetailsAndchangeIsRead(&apos;"+data.noteObjectionList[index].noteId+
                         "&apos;,&apos;noteObjection&apos;,&apos;"+data.noteObjectionList[index].uid+"&apos;)'>\n"+
                "<div class=\"panel panel-default\" title='点击查看寄语的详情'>\n" +
                "  <div class=\"panel-heading\">"+data.noteObjectionUserName[index].uname+":本人觉得您的寄语有以下问题</div>\n" +
                "  <div class=\"panel-body\">\n"+data.noteObjectionList[index].objectionText+"</div>\n" +
                "</div>\n"+
                "</a>"
            $('.myMessages').append(oneHtml)
        }
        //有用
        for (var index in data.noteUsefulList) {
            var oneHtml =
                "<a href='#' data-toggle=\"modal\" data-target=\"#showOneNoteDetails\" " +
                "       onclick='showDetailsAndchangeIsRead(&apos;"+data.noteUsefulList[index].noteId+
                        "&apos;,&apos;noteUseful&apos;,&apos;"+data.noteUsefulList[index].uid+"&apos;)'>\n"+
                "<div class=\"panel panel-default\" title='点击查看寄语的详情'>\n" +
                "  <div class=\"panel-heading\">有人觉得您的寄语对他有用啦</div>\n" +
                "  <div class=\"panel-body\">\n"+data.noteUsefulUserName[index].uname+"：您的寄语很有用，感谢您的分享</div>\n" +
                "</div>\n"+
                "</a>"
            $('.myMessages').append(oneHtml)
        }
        //举报
        for (var index in data.noteToreportList) {
            var oneHtml =
                "<a href='#' data-toggle=\"modal\" data-target=\"#showOneNoteDetails\" " +
                "       onclick='showDetailsAndchangeIsRead(&apos;"+data.noteToreportList[index].noteId+
                        "&apos;,&apos;noteToreport&apos;,&apos;"+data.noteToreportList[index].uid+"&apos;)'>\n"+
                "<div class=\"panel panel-default\" title='点击查看寄语的详情'>\n" +
                "  <div class=\"panel-heading\">您的寄语遭人举报</div>\n" +
                "  <div class=\"panel-body\">\n很抱歉，您的寄语存在重大问题，请您注意，请不要分享无关的东西！！</div>\n" +
                "</div>\n"+
                "</a>"
            $('.myMessages').append(oneHtml)
        }
    })
}

//查看寄语的详情和修改读的情况,uid是发送这条消息的人的ID
function showDetailsAndchangeIsRead(noteId,databaseTable,uid) {
    showMyNoteDetails(noteId);
    var url = "/ZJGuideWebsite_war_exploded/note_changeIsRead.action?noteId=" +noteId+"&databaseTable="+databaseTable+"&uid="+uid;
    $.post(url);
}

// 修改寄语
function alterNote(noteId) {
    alert("请关闭寄语详情界面后在编辑寄语中修改");
    //弹出编写寄语的标签页
    $('#myTab>li:nth-child(2)').addClass('active');
    $('#myTab>li:nth-child(2)').siblings().removeClass('active');
    $('#myTabContent>#publish').addClass('active in');
    $('#myTabContent>#publish').siblings().removeClass('active in');
    //将要修改的内容写入编写寄语框中
    var note = $('.OneNoteDetails-body').html().replace(/^\s*|\s*$/g,"");
    $('#alterThisNote').val(note);
    $('#alterThisNoteButton').html("确认修改");
    console.log(noteId);
    $('#ucmNForm').attr("action","/ZJGuideWebsite_war_exploded/note_alterNote.action?noteId="+noteId);

}

// 转化时间戳
function time(time = +new Date()) {
    var date = new Date(time + 8 * 3600 * 1000); // 增加8小时
    return date.toJSON().substr(0, 19).replace('T', ' ');
}
