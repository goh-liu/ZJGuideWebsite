<%--
  Created by IntelliJ IDEA.
  User: 77576
  Date: 2019/12/7
  Time: 16:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>志同道合</title>
    <!-- Bootstrap同时也是header必要的文件-->
    <script src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.cookie.js"></script>
    <!-- 该jsp文件的css文件 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/commonGoal.css">
    <script src="${pageContext.request.contextPath}/js/team.js"></script>
</head>
<body>
<!--导入顶部导航栏 -->
<%@ include file="header.jsp"%>

<!--左边用户信息区-->
<div class="body_left" >
    <div class="well well-lg">
        <div class="row">
            <div class="col-sm-3">
                <img src="${pageContext.request.contextPath}/image/headimg2.jpg" class="headImg">
            </div>
            <div class="col-sm-9">
                <c:if test="${empty loginUser}">
                    <div><strong>您尚未登录!请登录</strong></div>
                </c:if>
                <c:if test="${not empty loginUser}">
                    <div><strong>${loginUser.uname}</strong></div>
                </c:if>
                <div class="itemContent">欢迎来到志同道合板块</div>
            </div>
            <div class="col-sm-12 left_center">
                <c:if test="${not empty loginUser}">
                    <ul id="myTab" class="nav nav-tabs">
                        <li ><a href="#myMessages" data-toggle="tab" onclick="showMyMessages()">我的消息</a></li>
                        <li class="active"><a href="#publish" data-toggle="tab">创建队伍</a></li>
                        <li ><a href="#myTeam" data-toggle="tab" onclick="showMyTeam()">我的队伍</a></li>
                    </ul>
                </c:if>
            </div>
            <div class="col-sm-12 left_footer tab-content"  id="myTabContent" >
                <c:if test="${not empty loginUser}">
                    <div class="tab-pane fade " id="myMessages">
                        <div class="myMessages">
                            <span>您还没有消息哦</span>
                        </div>
                    </div>
                    <div class="tab-pane fade in active" id="publish">
                        <div>
                            <div>
                                <h3>创建队伍</h3>
                                <button type="submit" form="troopsForm" class="btn btn-primary ">&ensp;创&ensp;建&ensp;</button>
                            </div>
                            <div class="troopsAdd">
                                <form class="form-horizontal" id="troopsForm"  method="post" enctype="multipart/form-data"
                                      action="${pageContext.request.contextPath}/team_createTeam.action">
                                    <div class="form-group">
                                        <label for="troopsName" class="col-sm-4 control-label">队伍名称：</label>
                                        <div class="col-sm-8">
                                            <input type="text" class="form-control " name="TeamDistrict.teamName"
                                                   id="troopsName" placeholder="给队伍起个帅气的名字吧">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="troopsType" class="col-sm-4 control-label">队伍类型：</label>
                                        <div class="col-sm-8">
                                            <select class="form-control"  name="TeamDistrict.teamType" id="troopsType">
                                                <option value="study">学习</option>
                                                <option value="clockIn">打卡</option>
                                                <option value="movement">运动</option>
                                                <option value="competition">竞赛</option>
                                                <option value="game">游戏</option>
                                                <option value="other">其他</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="troopsPeopleNumber" class="col-sm-4 control-label">队伍人数：</label>
                                        <div class="col-sm-8">
                                            <input type="number" min="1" class="form-control " name="TeamDistrict.teamPeopleNum"
                                                   id="troopsPeopleNumber" placeholder="队伍的最多人数">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="troopsIntro" class="col-sm-4 control-label">队伍简介：</label>
                                        <div class="col-sm-8">
                                        <textarea class="form-control" name="TeamDistrict.teamIntroduction" id="troopsIntro"
                                                  rows="6" placeholder="请输入队伍的简介"></textarea>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="troopsCondition" class="col-sm-4 control-label">加入条件：</label>
                                        <div class="col-sm-8">
                                        <textarea class="form-control" name="TeamDistrict.joinCondition" id="troopsCondition"
                                                  rows="3" placeholder="填写加入所需的条件"></textarea>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="tab-pane fade" id="myTeam">
                        <div class="myTeam">
                            <span>您尚未创建/加入任何队伍哦</span>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>

    </div>
</div>
<!-- 右边的寄语显示区域 -->
<div class="body_right">
    <div class="TroopsType well well-lg">
        <div class="btn-group" data-toggle="buttons">
            <label class="btn ${"study" eq page.map.get('selected') ? 'active':''}"
                   onclick="window.location.href='/ZJGuideWebsite_war_exploded/team_showTeam.action?teamType=study&num=1'">
                <input type="radio" name="options">学习
            </label>
            <label class="btn ${"clockIn" eq page.map.get('selected') ? 'active':''}"
                   onclick="window.location.href='/ZJGuideWebsite_war_exploded/team_showTeam.action?teamType=clockIn&num=1'">
                <input type="radio" name="options">打卡
            </label>
            <label class="btn ${"movement" eq page.map.get('selected') ? 'active':''}"
                   onclick="window.location.href='/ZJGuideWebsite_war_exploded/team_showTeam.action?teamType=movement&num=1'">
                <input type="radio" name="options">运动
            </label>
            <label class="btn ${"competition" eq page.map.get('selected') ? 'active':''} "
                   onclick="window.location.href='/ZJGuideWebsite_war_exploded/team_showTeam.action?teamType=competition&num=1'">
                <input type="radio" name="options">竞赛
            </label>
            <label class="btn ${"game" eq page.map.get('selected') ? 'active':''} "
                   onclick="window.location.href='/ZJGuideWebsite_war_exploded/team_showTeam.action?teamType=game&num=1'">
                <input type="radio" name="options">游戏
            </label>
            <label class="btn ${"other" eq page.map.get('selected') ? 'active':''} "
                   onclick="window.location.href='/ZJGuideWebsite_war_exploded/team_showTeam.action?teamType=other&num=1'">
                <input type="radio" name="options">其他
            </label>
        </div>
<%--        <div>当前已有999个队伍，其中该类别下有99个队伍</div>--%>
        <div>该分类下队伍数：${page.map.get('currTypeTeamCoun')} / 共${page.map.get('allTeamCoun')}</div>
    </div>
    <!-- 根据队伍的类别显示队伍 -->
    <div class="showTroops well well-lg">
        <div class="row">
            <c:forEach items="${page.list}" var="pageList" >
            <%-- 显示一个队伍 --%>
            <div class="oneTroops col-sm-11">
                <div class="panel panel-default">
                    <div class="panel-body bg-success">
                        <table class="table table-condensed">
                            <tr>
                                <td class="itemTitle">队伍序号</td>
                                <td>:</td>
                                <td>${pageList.counter}</td>
                            </tr>
                            <tr>
                                <td class="itemTitle">队伍名称</td>
                                <td>:</td>
                                <td>${pageList.teamName}</td>
                            </tr>
                            <tr>
                                <td class="itemTitle">队伍人数</td>
                                <td>:</td>
                                <td>${pageList.currPeopleNum} / ${pageList.teamPeopleNum}</td>
                            </tr>
                            <tr>
                                <td class="itemTitle">队伍简介</td>
                                <td>:</td>
                                <td>${pageList.teamIntroduction}</td>
                            </tr>
                            <tr>
                                <td class="itemTitle">加入条件</td>
                                <td>:</td>
                                <td>${pageList.joinCondition}</td>
                            </tr>
                            <tr>
                                <td class="itemTitle">建立时间</td>
                                <td>:</td>
                                <td>${pageList.createTime}</td>
                            </tr>
                        </table>
                        <!-- 功能按钮 -->
                        <div class="troopsHandle">
                            <a type="button" class="btn btn-warning btn-block" onclick="return join_button();"
                                    ${pageList.currPeopleNum==pageList.teamPeopleNum?"disabled='true' style='pointer-events: none'":""}
                                    href="${pageContext.request.contextPath}/team_joinTeam.action?teamId=${pageList.teamId}&teamCaptainUid=${pageList.uid}">申请加入
                            </a>
                            <button type="button" class="btn btn-danger btn-block" data-toggle="modal"
                                    data-target="#ToreportModal" onclick="toreport_button(this,'${pageList.teamId}','${pageList.uid}')">举报
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            </c:forEach>


        </div>

        <%@include file="pageFile.jsp"%>
    </div>
    <%-- 点击举报弹出模态框--%>
    <div class="modal fade" id="ToreportModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel2">举报</h4>
                </div>
                <div class="modal-body">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">队伍信息</h3>
                        </div>
                        <div class="panel-body teamPanel">
                            <table class="table table-condensed">
                            </table>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">请写出您举报的原因</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" id="toreportForm"  method="post" enctype="multipart/form-data"
                                  action="${pageContext.request.contextPath}/team_clickToreport.action">
                                <textarea class="form-control" name="TeamToreport.toreportText" rows="5"
                                                  placeholder="请输入您举报的原因"></textarea>
                                <input class="ToreportFormInput1" type="hidden" name="TeamToreport.teamId" >
                                <input class="ToreportFormInput2" type="hidden" name="TeamToreport.teamCaptainUid" >
                            </form>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="submit" class="btn btn-primary" form="toreportForm">举报</button>
                </div>
            </div>
        </div>
    </div>
    <%-- 点击弹出我的消息详情界面--%>
    <div class="modal fade" id="showMyMessagesDetails" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="teamDetails"></h4>
                </div>
                <div class="modal-body OneTeamDetails-body">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">队伍信息</h3>
                        </div>
                        <div class="panel-body timePanel">
                            <table class="table table-condensed">
                                <tr>
                                    <td class="itemTitle">队伍序号</td>
                                    <td>:</td>
                                    <td class="counter"></td>
                                </tr>
                                <tr>
                                    <td class="itemTitle">队伍名称</td>
                                    <td>:</td>
                                    <td class="teamName"></td>
                                </tr>
                                <tr>
                                    <td class="itemTitle">队伍人数</td>
                                    <td>:</td>
                                    <td class="teamPeople"></td>
                                </tr>
                                <tr>
                                    <td class="itemTitle">队伍简介</td>
                                    <td>:</td>
                                    <td class="teamIntroduction"></td>
                                </tr>
                                <tr>
                                    <td class="itemTitle">加入条件</td>
                                    <td>:</td>
                                    <td class="joinCondition"></td>
                                </tr>
                                <tr>
                                    <td class="itemTitle">建立时间</td>
                                    <td>:</td>
                                    <td class="createTime"></td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">申请人</h3>
                        </div>
                        <div class="panel-body timePanel1">
                        </div>
                    </div>
                </div>
                <div class="modal-footer OneTeamDetails-footer">
                    <a type="button" class="btn btn-danger falseAudit">不同意</a>
                    <a type="button" class="btn btn-primary trueAudit">同意加入</a>
                    <button type="button" class="btn btn-primary " data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>
    <%-- 点击弹出我的队伍详情界面--%>
    <div class="modal fade" id="showMyTeamDetails" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="teamDetails2">队伍详情</h4>
                </div>
                <div class="modal-body OneTeamDetails-body">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">队伍信息</h3>
                        </div>
                        <div class="panel-body timePanel2">
                            <table class="table table-condensed">
                                <tr>
                                    <td class="itemTitle">队伍序号</td>
                                    <td>:</td>
                                    <td class="counter"></td>
                                </tr>
                                <tr>
                                    <td class="itemTitle">队伍名称</td>
                                    <td>:</td>
                                    <td class="teamName"></td>
                                </tr>
                                <tr>
                                    <td class="itemTitle">队伍人数</td>
                                    <td>:</td>
                                    <td class="teamPeople"></td>
                                </tr>
                                <tr>
                                    <td class="itemTitle">队伍简介</td>
                                    <td>:</td>
                                    <td class="teamIntroduction"></td>
                                </tr>
                                <tr>
                                    <td class="itemTitle">加入条件</td>
                                    <td>:</td>
                                    <td class="joinCondition"></td>
                                </tr>
                                <tr>
                                    <td class="itemTitle">建立时间</td>
                                    <td>:</td>
                                    <td class="createTime"></td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">队伍成员</h3>
                        </div>
                        <div class="panel-body timePanel3">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary " data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>
<script>
    //弹窗，显示action返回的信息
    var popupMessage=""+'${popupMessage}';
    if (popupMessage != ""){
        alert(popupMessage);
        <%session.setAttribute("popupMessage","");%>
    }

</script>