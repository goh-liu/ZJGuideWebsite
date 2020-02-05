//点击举报按钮
function toreport_button(that,teamId,teamCaptainUid) {
   var tableHtml = $(that).parent().prev().html();
    $('.teamPanel>table').html(tableHtml);
    $('.ToreportFormInput1').val(teamId);
    $('.ToreportFormInput2').val(teamCaptainUid);
}

//点击申请加入按钮
function join_button(that,teamId) {
    if (confirm("您确定申请加入吗")){
      return true;
    }
    return false;
}

//显示我的消息
function showMyMessages() {
    $.post("/ZJGuideWebsite_war_exploded/team_showMyMessages_JSON.action",function (data,status) {
        if (data.teamMemberList.length != 0 || data.teamToreportList.length != 0) {
            $('.myMessages').html("");
        }
        console.log(data);
        //加入
        for (var index in data.teamMemberList) {
            var member = data.teamMemberList[index];
            var onclickUrl = "showMyMessagesDetails(&apos;"+member.teamId+"&apos;,&apos;"+member.teamMemberId+"&apos;,&apos;"+member.consentJoin+"&apos;,&apos;"+member.teamUser.uname+"&apos;)";
            var panelbody = "";
            switch (member.consentJoin) {
                case "false" :
                    panelbody = member.teamUser.uname+"希望加入您的队伍<"+member.teamIdAndName.teamName+">";
                    $('#teamDetails').html("审核加入申请");
                    break;
                case "agree" :
                    panelbody = "队长同意了您的加入申请";
                    $('#teamDetails').html("查看队伍信息");
                    $('.OneTeamDetails-body>div:last-child').remove();
                    $('.OneTeamDetails-footer>a').remove();
                    break;
                case "disagree" :
                    panelbody = "队长拒绝了您的加入申请";
                    $('#teamDetails').html("查看队伍信息");
                    $('.OneTeamDetails-body>div:last-child').remove();
                    $('.OneTeamDetails-footer>a').remove();
                    break;
            }
            var oneHtml =
                "<a href='#' data-toggle=\"modal\" data-target=\"#showMyMessagesDetails\"" +
                "        onclick="+onclickUrl+">\n"+
                "<div class=\"panel panel-default\" title='点击查看队伍的信息'>\n" +
                "  <div class=\"panel-heading\">加入队伍申请通知</div>\n" +
                "  <div class=\"panel-body\">\n"+panelbody+"</div>\n" +
                "</div>\n"+
                "</a>"
            $('.myMessages').append(oneHtml);
        }
        //举报
        for (var index in data.teamToreportList) {
            var toreport = data.teamToreportList[index];
            var onclickUrl1 = "showMyMessagesDetails1(&apos;"+toreport.teamId+"&apos;,&apos;"+toreport.toreportId+"&apos;)";
            var oneHtml =
                "<a href='#' data-toggle=\"modal\" data-target=\"#showMyMessagesDetails\" " +
                "       onclick="+onclickUrl1+">\n"+
                "<div class=\"panel panel-default\" title='点击查看队伍的信息'>\n" +
                "  <div class=\"panel-heading\">您的队伍被人举报</div>\n" +
                "  <div class=\"panel-body\">\n举报原因："+toreport.toreportText+"</div>\n" +
                "</div>\n"+
                "</a>"
            $('.myMessages').append(oneHtml)
        }
    });
}

//点击消息出现的详细面板
function showMyMessagesDetails(teamId,teamMemberId,consentJoin,uname) {

    $.post("/ZJGuideWebsite_war_exploded/team_showTeamByTeamId_JSON.action",{teamId:teamId},function (data, status) {
        var teamPeople = data.currPeopleNum +" / "+data.teamPeopleNum;
        $('.timePanel .counter').html(data.counter);
        $('.timePanel .teamName').html(data.teamName);
        $('.timePanel .teamPeople').html(teamPeople);
        $('.timePanel .teamIntroduction').html(data.teamIntroduction);
        $('.timePanel .joinCondition').html(data.joinCondition);
        $('.timePanel .createTime').html(data.createTime);
        $('.timePanel1').html(uname);
        if ("false" == consentJoin) {
            $('.falseAudit').attr("href","/ZJGuideWebsite_war_exploded/team_auditApply.action?audit=disagree&teamMemberId="+teamMemberId);
            $('.trueAudit').attr("href","/ZJGuideWebsite_war_exploded/team_auditApply.action?audit=agree&teamMemberId="+teamMemberId);
        }else {
            $.get("/ZJGuideWebsite_war_exploded/team_auditApplyTrue_JSON.action?audit=true&teamMemberId="+teamMemberId);
        }

    });

}
function showMyMessagesDetails1(teamId,toreportId) {
    $.post("/ZJGuideWebsite_war_exploded/team_showTeamByTeamId_JSON.action",{teamId:teamId},function (data, status) {
        $('#teamDetails').html("被举报的队伍信息");
        var teamPeople = data.currPeopleNum +" / "+data.teamPeopleNum;
        $('.timePanel .counter').html(data.counter);
        $('.timePanel .teamName').html(data.teamName);
        $('.timePanel .teamPeople').html(teamPeople);
        $('.timePanel .teamIntroduction').html(data.teamIntroduction);
        $('.timePanel .joinCondition').html(data.joinCondition);
        $('.timePanel .createTime').html(data.createTime);
        $('.OneTeamDetails-body>div:last-child').remove();
        $('.OneTeamDetails-footer>a').remove();
    });
    $.post("/ZJGuideWebsite_war_exploded/team_toreportIsRead_JSON.action",{toreportId:toreportId});
}

//显示我的队伍
function showMyTeam() {
    $.post("/ZJGuideWebsite_war_exploded/team_showMyTeam_JSON.action",function (data, status) {
        if (data.length != 0) {
            $('.myTeam').html("");
        }
        for (var index in data) {
            var teamType = "";
            switch (data[index].teamType) {
                case "study":
                    teamType = "学习";
                    break;
                case "clockIn":
                    teamType = "打卡";
                    break;
                case "movement":
                    teamType = "运动";
                    break;
                case "competition":
                    teamType = "竞赛";
                    break;
                case "game":
                    teamType = "游戏";
                    break;
                case "other":
                    teamType = "其他";
                    break;
            }
            var panelheading = "序号："+data[index].counter+"&ensp;&ensp;类型："+teamType;
            var onclickUrl = "showTeamDetail(&apos;"+data[index].teamId+"&apos;)";
            var oneHtml =
                "<a href='#' data-toggle=\"modal\" data-target=\"#showMyTeamDetails\"" +
                "        onclick="+onclickUrl+">\n"+
                "<div class=\"panel panel-default\" title='点击查看队伍的信息'>\n" +
                "  <div class=\"panel-heading\">"+panelheading+"</div>\n" +
                "  <div class=\"panel-body\">\n&ensp;&ensp;&ensp;队伍名："+data[index].teamName+"</div>\n" +
                "</div>\n"+
                "</a>"
            $('.myTeam').append(oneHtml);
        }
    });

}

//显示队伍详细信息
function showTeamDetail(teamId) {
    $.post("/ZJGuideWebsite_war_exploded/team_showTeamByTeamId_JSON.action",{teamId:teamId},function (data, status){
        let teamPeople = data.currPeopleNum +" / "+data.teamPeopleNum;
        $('.timePanel2 .counter').html(data.counter);
        $('.timePanel2 .teamName').html(data.teamName);
        $('.timePanel2 .teamPeople').html(teamPeople);
        $('.timePanel2 .teamIntroduction').html(data.teamIntroduction);
        $('.timePanel2 .joinCondition').html(data.joinCondition);
        $('.timePanel2 .createTime').html(data.createTime);
        $('.timePanel3').html("");
        for (var index in data.teamMember) {
            var num = Number(1)+Number(index);
            $('.timePanel3').append(num+"&ensp;:&ensp;&ensp;"+data.teamMember[index].teamUser.uname+"&ensp;&ensp;&ensp;")
        }
    });
}

// // 转化时间戳
// function time(time = +new Date()) {
//     var date = new Date(time + 8 * 3600 * 1000); // 增加8小时
//     return date.toJSON().substr(0, 19).replace('T', ' ');
// }
