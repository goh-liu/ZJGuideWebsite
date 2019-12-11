<%--
  Created by IntelliJ IDEA.
  User: 77576
  Date: 2019/12/4
  Time: 10:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>学长寄语</title>
    <!-- Bootstrap同时也是header必要的文件-->
    <script src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="js/jquery.cookie.js"></script>
    <!-- 该jsp文件的css文件 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/upperclassmanNote.css">
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
                    <div class="itemContent">欢迎来到学长寄语板块</div>
                </div>
                <div class="col-sm-12 left_center">
                    <c:if test="${not empty loginUser}">
                        <ul id="myTab" class="nav nav-tabs">
                            <li ><a href="#advices" data-toggle="tab" onclick="">我的消息</a></li>
                            <li class="active"><a href="#publish" data-toggle="tab">编写寄语</a></li>
                            <li ><a href="#published" data-toggle="tab" onclick="">我的寄语</a></li>
                        </ul>
                    </c:if>
                </div>
                <div class="col-sm-12 left_footer tab-content"  id="myTabContent" >
                    <c:if test="${not empty loginUser}">
                        <div class="tab-pane fade " id="advices">
                            <div>
                                <span>您还没有消息哦</span>
                            </div>
                        </div>
                        <div class="tab-pane fade in active" id="publish">
                            <div>
                                <div>
                                    <h3>编写寄语的内容</h3>
                                    <button type="submit" form="ucmNForm" class="btn btn-primary ">&ensp;提&ensp;交&ensp;</button>
                                </div>
                                <form class="form-horizontal" id="ucmNForm"  method="post" enctype="multipart/form-data"
                                      action="${pageContext.request.contextPath}">
                                    <div class=form-group" >
                                        <textarea class="form-control" name="ucmNWriteComment" rows="19" placeholder="请在此处输入您要对师弟师妹说的话"></textarea>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="published">
                            <div class="published">
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
            <div class="btn-group" data-toggle="buttons">
                <label class="btn active">
                    <input type="radio" name="options"> 按“热度”查看
                </label>
                <label class="btn ">
                    <input type="radio" name="options"> 按“时间”查看
                </label>
            </div>
        </div>
        <!-- 显示热门或者最新的寄语 -->
        <div class="showNote well well-lg">
            <!-- 显示一条寄语 -->
            <div class="oneNote">
                <!-- 用户头像、名称、编写日期时间 -->
                <div class="oneNoteLeft">
                    <div>
                        <img class="headImg" src="${pageContext.request.contextPath}/image/headimg2.jpg">
                        <h5>2016级_某某某</h5>
                    </div>
                </div>
                <div class="oneNoteRight row">
                    <!-- 寄语的内容 -->
                    <div class="noteText col-sm-12">
                        <h3>
                           九分裤收款方京东方卡萨分开是煎熬复健科撒

                        </h3>
                    </div>
                    <!-- 对寄语的操作（认可、提出异议、举报） -->
                    <div class="noteAction col-sm-12">
                        <button type="button" class="btn btn-default">有用 (15)</button>
                        &nbsp;&nbsp;
                        <button type="button" class="btn btn-default">提出异议</button>
                        &nbsp;&nbsp;
                        <button type="button" class="btn btn-default">举报</button>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <span>发表于：2019年12月5日 22:44:51</span>
                    </div>
                </div>
           </div>
            <!-- 显示一条寄语 -->
            <div class="oneNote">
                <!-- 用户头像、名称、编写日期时间 -->
                <div class="oneNoteLeft">
                    <div>
                        <img class="headImg" src="${pageContext.request.contextPath}/image/headimg2.jpg">
                        <h5>2016级_某某某</h5>
                    </div>
                </div>
                <div class="oneNoteRight row">
                    <!-- 寄语的内容 -->
                    <div class="noteText col-sm-12">
                        <h3>
                           九分裤收款方京东方卡萨分开是煎熬复健科撒
                           九分裤收款方京东方卡萨分开是煎熬复健科撒
                           九分裤收款方京东方卡萨分开是煎熬复健科撒
                           九分裤收款方京东方卡萨分开是煎熬复健科撒
                           九分裤收款方京东方卡萨分开是煎熬复健科撒
                        </h3>
                    </div>
                    <!-- 对寄语的操作（认可、提出异议、举报） -->
                    <div class="noteAction col-sm-12">
                        <button type="button" class="btn btn-default">有用 (15)</button>
                        &nbsp;&nbsp;
                        <button type="button" class="btn btn-default">提出异议</button>
                        &nbsp;&nbsp;
                        <button type="button" class="btn btn-default">举报</button>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <span>发表于：2019年12月5日 22:44:51</span>
                    </div>
                </div>
           </div>
            <!-- 显示一条寄语 -->
            <div class="oneNote">
                <!-- 用户头像、名称、编写日期时间 -->
                <div class="oneNoteLeft">
                    <div>
                        <img class="headImg" src="${pageContext.request.contextPath}/image/headimg2.jpg">
                        <h5>2016级_某某某</h5>
                    </div>
                </div>
                <div class="oneNoteRight row">
                    <!-- 寄语的内容 -->
                    <div class="noteText col-sm-12">
                        <h3>
                           九分裤收款方京东方卡萨分开是煎熬复健科撒
                           九分裤收款方京东方卡萨分开是煎熬复健科撒
                           九分裤收款方京东方卡萨分开是煎熬复健科撒
                           九分裤收款方京东方卡萨分开是煎熬复健科撒
                           九分裤收款方京东方卡萨分开是煎熬复健科撒
                           九分裤收款方京东方卡萨分开是煎熬复健科撒
                           九分裤收款方京东方卡萨分开是煎熬复健科撒
                           九分裤收款方京东方卡萨分开是煎熬复健科撒
                           九分裤收款方京东方卡萨分开是煎熬复健科撒
                           九分裤收款方京东方卡萨分开是煎熬复健科撒
                           九分裤收款方京东方卡萨分开是煎熬复健科撒
                           九分裤收款方京东方卡萨分开是煎熬复健科撒
                           九分裤收款方京东方卡萨分开是煎熬复健科撒
                           九分裤收款方京东方卡萨分开是煎熬复健科撒
                           九分裤收款方京东方卡萨分开是煎熬复健科撒
                        </h3>
                    </div>
                    <!-- 对寄语的操作（认可、提出异议、举报） -->
                    <div class="noteAction col-sm-12">
                        <button type="button" class="btn btn-default">有用 (15)</button>
                        &nbsp;&nbsp;
                        <button type="button" class="btn btn-default">提出异议</button>
                        &nbsp;&nbsp;
                        <button type="button" class="btn btn-default">举报</button>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <span>发表于：2019年12月5日 22:44:51</span>
                    </div>
                </div>
           </div>
       </div>

   </div>
</body>
</html>
