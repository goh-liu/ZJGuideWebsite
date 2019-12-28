<%--
  Created by IntelliJ IDEA.
  User: 77576
  Date: 2019/8/3
  Time: 8:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>匿名友人</title>
    <!-- Bootstrap同时也是header必要的文件-->
    <script src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.cookie.js"></script>
    <!-- Bootstrap文件上传 -->
    <script src="${pageContext.request.contextPath}/js/fileinput.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/fileinput.min.css">
    <script src="${pageContext.request.contextPath}/js/locales/zh.js"></script>
    <!--本页面的js外部文件-->
    <script src="${pageContext.request.contextPath}/js/anon.js"></script>
    <!--本页面的css外部文件-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/anon.css">
</head>



<body>
    <!--导入导航栏-->
   <%@include file="header.jsp"%>
    <!--主体-->
    <div class="row">
        <!--左边用户信息区-->
        <div class="col-md-3 " id="div1">
            <div class="well well-lg" style="padding: 0px 10px;">
                <div class="anonymityItem row">
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
                        <div class="itemContent">欢迎来到匿名友人板块</div>
                    </div>
                    <div class="col-sm-12 div1Item2 ">
                        <c:if test="${not empty loginUser}">
                            <ul id="myTab" class="nav nav-tabs">
                                <li class="active"><a href="#advices" data-toggle="tab" onclick="showMessages()">消息</a></li>
                                <li ><a href="#publish" data-toggle="tab">发表</a></li>
                                <li ><a href="#published" data-toggle="tab" onclick="showUserAnon()">已发表</a></li>
                                <li ><a href="#comment" data-toggle="tab" onclick="showUserComment()">评论</a></li>
                            </ul>
                        </c:if>
                    </div>
                    <div class="col-sm-12 div1Item3 tab-content"  id="myTabContent" >
                        <c:if test="${not empty loginUser}">
                            <div class="tab-pane fade in active" id="advices">
                                <div class="anonWrite">
                                    <span>您还没有消息哦</span>
                                </div>
                            </div>
                            <div class="tab-pane fade " id="publish">
                                <div class="anonWrite">
                                    <h3>发表匿名内容</h3>
                                    <div>
                                        <form class="form-horizontal" id="anonForm"  method="post" enctype="multipart/form-data"
                                              action="${pageContext.request.contextPath}/AnonServlet?method=anonWrite">
                                            <div class=form-group" >
                                                <textarea class="form-control" name="anonWriteComment" rows="9" placeholder="请在此处输入您要说的内容"></textarea>
                                            </div>
                                            <div class="form-group">
                                                <div class="anonPrice" >
                                                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target=".bs-example-modal-lg">上传图片</button>
                                                    <button type="submit" form="anonForm" class="btn btn-primary ">&ensp;提&ensp;交&ensp;</button>
                                                </div>
                                                <!--上传图片弹出框-->
                                                <div class="modal fade bs-example-modal-lg " tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
                                                    <div class="modal-dialog modal-lg" role="document">
                                                        <div class="modal-content">
                                                            <div class="container-fluid">
                                                                <div class="row form-group">
                                                                    </br>
                                                                    <div class="col-sm-12">
                                                                        <input id="input-id" name="file" multiple type="file" data-show-caption="true">
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <div class="tab-pane fade" id="published">
                                <div class="published">
                                    <span>您尚未有发表过哦</span>
                                </div>
                            </div>
                            <div class="tab-pane fade" id="comment" >
                                <div class="comment" >
                                    <span>您尚未有评论</span>
                                    <script>showMessages()</script>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>

            </div>
        </div>


        <!--右边论坛信息区-->
        <div class="col-md-9" id="div2" >
            <div class="well well-lg" id="div2Panel">
                <!--遍历内容列表，将其显示出来-->
                <c:forEach items="${page.map.get('anonCommentList')}" var="ac">
                    <!--单个用户-->
                    <div class="anonymityItem row">
                        <!--用户头像-->
                        <div class="col-sm-1">
                            <img src="${pageContext.request.contextPath}/image/headimg2.jpg" class="headImg">
                        </div>
                        <!--其他-->
                        <div class="col-sm-11">
                            <div>
                                <strong style="margin-right: 30px;">匿名者${ac.counter}</strong>
                                <a class="btn btn-xs anon_comment" onclick="comment_click(${ac.counter})">
                                    <img src="${pageContext.request.contextPath}/image/comment.png"
                                         style="width: 18px;height: 18px;">&ensp;评论
                                </a>
                                <a class="btn btn-xs anon_like" onclick="anon_like(this,'${ac.counter}')">
                                    <c:set var="like1" value="no"/>
                                    <c:forEach items="${page.map.get('likeList')}" var="ll">
                                        <c:if test="${ll.anonID == ac.anonID and ll.likeUID == loginUser.uid}">
                                            <img src="${pageContext.request.contextPath}/image/like1.png"
                                                 style="width: 18px;height: 18px;">&ensp;点赞(<span class="anon_like_coun">${ac.likeCoun}</span>)
                                            <c:set var="like1" value="yes"/>
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${!(like1.equals('yes'))}">
                                        <img src="${pageContext.request.contextPath}/image/like2.png"
                                             style="width: 18px;height: 18px;">&ensp;点赞(<span class="anon_like_coun">${ac.likeCoun}</span>)
                                        <c:remove var="like1"/>
                                    </c:if>
                                </a>
                                <c:if test="${ac.uid== loginUser.uid}">
                                    <a class="btn btn-xs anon_del" onclick="anon_del(this,'${ac.counter}')">
                                            <img src="${pageContext.request.contextPath}/image/del.png"
                                                 style="width: 18px;height: 18px;">&ensp;删除
                                    </a>
                                </c:if>
                            </div>
                            <div class="itemContent">
                                <div>${ac.anonContent}</div>
                                <div>
                                    <!--遍历图片集合-->
                                    <c:forEach items="${page.map.get('anonPriceList')}" var="p">
                                        <!--判断是否是该条的图片-->
                                        <c:if test="${p.anonID == ac.anonID}">
                                            <img src="imgUrl/${p.priceUrl}" class="headImg">
                                        </c:if>
                                    </c:forEach>
                                </div>

                                <!--用户回复评论-->
                                <div class="row itemComment">
                                    <c:forEach items="${page.map.get('commentList')}" var="cl">
                                        <!--等于1表示为评论-->
                                        <c:if test="${cl.commentOrReply==1}">
                                            <c:if test="${cl.anonID == ac.anonID}">
                                                <div class="col-sm-12">
                                                    <strong>${cl.sourceUname}</strong>&ensp;：
                                                    <span>${cl.commentText}</span>&ensp;&ensp;
                                                    <a class="btn btn-default btn-xs" onclick="comment_reply1('${ac.counter}','${cl.sourceUid}','${cl.sourceUname}','${cl.commentText}')">回复</a>&ensp;&ensp;
                                                </div>
                                            </c:if>
                                        </c:if>
                                        <!--等于2表示为回复-->
                                        <c:if test="${cl.commentOrReply==2}">
                                            <c:if test="${cl.anonID == ac.anonID}">
                                                <div class="col-sm-12">
                                                    <strong>${cl.sourceUname}</strong>&ensp;回复&ensp;<strong>${cl.destUname}</strong>&ensp;：
                                                    <span>${cl.commentText}</span>&ensp;&ensp;
                                                    <a class="btn btn-default btn-xs" onclick="comment_reply2('${ac.counter}','${cl.sourceUid}','${cl.sourceUname}','${cl.destUname}','${cl.commentText}')">回复</a>&ensp;&ensp;
                                                </div>
                                            </c:if>
                                        </c:if>
                                    </c:forEach>

                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <%@include file="pageFile.jsp"%>
            </div>
        </div>


    </div>

</body>
</html>
<script>
    //判断用户自己是否有点赞自己的匿名说说
    function isOneselfLike(data) {
        return (JSON.stringify(data)).indexOf("${sessionScope.loginUser.uid}");
    }
    // 获取登录用户的id
    function returnLoginUid() {
        return "${sessionScope.loginUser.uid}";
    }
</script>