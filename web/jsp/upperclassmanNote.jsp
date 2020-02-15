<%--
  Created by IntelliJ IDEA.
  User: 77576
  Date: 2019/12/4
  Time: 10:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
    <title>学长寄语</title>
    <!-- Bootstrap同时也是header必要的文件-->
    <script src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.cookie.js"></script>
    <!-- 该jsp文件的css和js文件 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/upperclassmanNote.css">
    <script src="${pageContext.request.contextPath}/js/note.js"></script>
</head>
<body onload="showHotOrNew(returnHotOrNew())">
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
                    <div class="itemContent">欢迎来到学长寄语板块</div>
                </div>
                <div class="col-sm-12 left_center">
                    <c:if test="${not empty loginUser}">
                        <ul id="myTab" class="nav nav-tabs">
                            <li ><a href="#myMessages" data-toggle="tab" onclick="showMyMessages()">我的消息</a></li>
                            <li class="active"><a href="#publish" data-toggle="tab">编写寄语</a></li>
                            <li ><a href="#myNote" data-toggle="tab" onclick="showMyNote()">我的寄语</a></li>
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
                                    <h3>编写寄语的内容</h3>
                                    <button type="submit" form="ucmNForm" id="alterThisNoteButton" class="btn btn-primary ">&ensp;提&ensp;交&ensp;</button>
                                </div>
                                <form class="form-horizontal" id="ucmNForm"  method="post" enctype="multipart/form-data"
                                      action="${pageContext.request.contextPath}/note_published.action">
                                    <div class=form-group" >
                                        <textarea class="form-control" id="alterThisNote" name="note" rows="19"
                                                  placeholder="请在此处输入您要对师弟师妹说的话"></textarea>
                                    </div>
                                </form>
                            </div>
                        </div>

                        <div class="tab-pane fade" id="myNote">
                            <div class="myNote">
                                <span>您尚未有发表过哦</span>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>

        </div>
    </div>
    <!-- 右边的寄语显示区域 -->
    <div class="body_right">
        <div class="showHotOrNew well well-lg">
            <a href="${pageContext.request.contextPath}/note_showNew.action" onclick="saveHotOrNew('new')"
               class="btn btn-success" role="button">按“时间”显示</a>
            <a href="${pageContext.request.contextPath}/note_showHot.action" onclick="saveHotOrNew('hot')"
               class="btn btn-default" role="button">按“热度”显示</a>
        </div>
        <!-- 显示热门或者最新的寄语 -->
        <div class="showNote well well-lg">
            <c:forEach items="${page.list}" var="ac" varStatus="acIndex">
                <!-- 显示一条寄语 -->
                <div class="oneNote">
                    <!-- 用户头像、名称、编写日期时间 -->
                    <div class="oneNoteLeft">
                        <div>
                            <img class="headImg" src="${pageContext.request.contextPath}/image/headimg2.jpg">
                            <h5>2016级_${ac.userIdAndName.uname}</h5>
                        </div>
                    </div>
                    <div class="oneNoteRight row">
                        <!-- 寄语的内容 -->
                        <div class="noteText col-sm-12">
                            <h3>
                               ${ac.note}
                            </h3>
                        </div>
                        <!-- 对寄语的操作（认可、提出异议、举报） -->
                        <div class="noteAction col-sm-12">
                            <button onclick="useful_button(this,'${ac.noteId}')" type="button"
                                    class="btn btn-default ${page.clickMarkList1[acIndex.index] eq 'yes'? 'btn-warning':' '}" >
                                有用 (${ac.usefulCoun})
                            </button>
                            &nbsp;&nbsp;
                            <button  type="button" data-toggle="modal" data-target="#ObjectionModal"
                                     class="btn btn-default ${page.clickMarkList2[acIndex.index] eq 'yes'? 'btn-warning':' '}"
                                     onclick="Objection_button(this,'${ac.noteId}')">提出异议(${ac.objectionCoun})
                            </button>
                            &nbsp;&nbsp;
                            <button type="button"  data-toggle="modal" data-target="#ToreportModal"
                                    class="btn btn-default  ${page.clickMarkList3[acIndex.index] eq 'yes'? 'btn-warning':' '}"
                                    onclick="Toreport_button(this,'${ac.noteId}')">举报(${ac.toreportCoun})</button>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <span>发表于：${ac.publishedTime}</span>
                        </div>
                    </div>
               </div>
            </c:forEach>
            <%@include file="pageFile.jsp"%>
        </div>
    </div>
    <%-- 点击提出异议弹出模态框--%>
    <div class="modal fade" id="ObjectionModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">提出异议</h4>
                </div>
                <div class="modal-body">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">寄语</h3>
                        </div>
                        <div class="panel-body notePanel">
                            <textarea id="notePrompt" class="form-control" rows="5" readonly="readonly"></textarea>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">请写出您认为该寄语有误的地方</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" id="objectionForm"  method="post" enctype="multipart/form-data"
                                  action="${pageContext.request.contextPath}/note_clickObjection.action">
                                        <textarea class="form-control" name="NoteObjection.objectionText" rows="5"
                                                  placeholder="请输入您认为有误的地方"></textarea>
                                <input class="objectionFormInput" type="hidden" name="NoteObjection.noteId" >
                            </form>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="submit" class="btn btn-primary" form="objectionForm">提出</button>
                </div>
            </div>
        </div>
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
                            <h3 class="panel-title">寄语</h3>
                        </div>
                        <div class="panel-body notePanel">
                            <textarea id="notePrompt2" class="form-control" rows="5" readonly="readonly"></textarea>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">请写出您举报的原因</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" id="toreportForm"  method="post" enctype="multipart/form-data"
                                  action="${pageContext.request.contextPath}/note_clickToreport.action">
                                        <textarea class="form-control" name="NoteToreport.toreportText" rows="5"
                                                  placeholder="请输入您举报的原因"></textarea>
                                <input class="ToreportFormInput" type="hidden" name="NoteToreport.noteId" >
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
    <%-- 弹出模态框显示寄语详情--%>
    <div class="modal fade" id="showOneNoteDetails" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="NoteDetails">寄语详情</h4>
                </div>
                <div class="modal-body OneNoteDetails-body"></div>
                <div class="modal-footer OneNoteDetails-footer">
                    <button type="button" class="btn btn-danger">删除</button>
                    <button type="button" class="btn btn-default" disabled="disabled">有用</button>
                    <button type="button" class="btn btn-default" disabled="disabled">提出异议</button>
                    <button type="button" class="btn btn-default" disabled="disabled">举报</button>
                    <button type="button" class="btn btn-primary" >修改</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
<script>
    //弹窗，显示action返回的信息
    let popupMessage=""+'${popupMessage}';
    if (popupMessage != ""){
        alert(popupMessage);
        <%session.setAttribute("popupMessage","");%>
    }

    //新生无法编写寄语
    let year = new Date().getFullYear();
    if (year == ${loginUser.ugrade}){
       $('#alterThisNote').attr('placeholder','新生不能编写寄语');
       $('#alterThisNote').attr('disabled','disabled');
       $('#alterThisNoteButton').attr('disabled','disabled');
    }

</script>

