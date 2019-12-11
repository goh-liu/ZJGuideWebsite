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
    <script src="js/jquery.cookie.js"></script>
    <!-- 该jsp文件的css文件 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/commonGoal.css">
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
                        <li ><a href="#advices" data-toggle="tab" onclick="">我的消息</a></li>
                        <li class="active"><a href="#publish" data-toggle="tab">创建队伍</a></li>
                        <li ><a href="#published" data-toggle="tab" onclick="">我的队伍</a></li>
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
                                <h3>创建队伍</h3>
                                <button type="submit" form="troopsForm" class="btn btn-primary ">&ensp;创&ensp;建&ensp;</button>
                            </div>
                            <div class="troopsAdd">
                                <form class="form-horizontal" id="troopsForm"  method="post" enctype="multipart/form-data"
                                      action="${pageContext.request.contextPath}">
                                    <div class="form-group">
                                        <label for="troopsName" class="col-sm-4 control-label">队伍名称：</label>
                                        <div class="col-sm-8">
                                            <input type="text" class="form-control " name="troopsName"
                                                   id="troopsName" placeholder="给队伍起个帅气的名字吧">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="troopsType" class="col-sm-4 control-label">队伍类型：</label>
                                        <div class="col-sm-8">
                                            <select class="form-control"  name="troopsType" id="troopsType">
                                                <option>学习</option>
                                                <option>打卡</option>
                                                <option>运动</option>
                                                <option>竞赛</option>
                                                <option>其他</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="troopsPeopleNumber" class="col-sm-4 control-label">队伍人数：</label>
                                        <div class="col-sm-8">
                                            <input type="number" class="form-control " name="troopsPeopleNumber"
                                                   id="troopsPeopleNumber" placeholder="队伍的最多人数">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="troopsIntro" class="col-sm-4 control-label">队伍简介：</label>
                                        <div class="col-sm-8">
                                        <textarea class="form-control" name="troopsIntro" id="troopsIntro"
                                                  rows="6" placeholder="请输入队伍的简介"></textarea>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="troopsCondition" class="col-sm-4 control-label">加入条件：</label>
                                        <div class="col-sm-8">
                                        <textarea class="form-control" name="troopsCondition" id="troopsCondition"
                                                  rows="3" placeholder="填写加入所需的条件"></textarea>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="tab-pane fade" id="published">
                        <div class="published">
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
            <label class="btn active">
                <input type="radio" name="options">学习
            </label>
            <label class="btn ">
                <input type="radio" name="options">打卡
            </label>
            <label class="btn ">
                <input type="radio" name="options">运动
            </label>
            <label class="btn ">
                <input type="radio" name="options">竞赛
            </label>
            <label class="btn ">
                <input type="radio" name="options">其他
            </label>
        </div>
<%--        <div>当前已有999个队伍，其中该类别下有99个队伍</div>--%>
        <div>该分类下队伍数：99/2000</div>
    </div>
    <!-- 根据队伍的类别显示队伍 -->
    <div class="showTroops well well-lg">
        <div class="row">
            <!-- 显示一个队伍 -->
            <div class="oneTroops col-sm-11">
                <div class="panel panel-default">
                    <div class="panel-body bg-success">
                        <table class="table table-condensed">
                    <tr>
                        <td class="itemTitle">队伍ID</td>
                        <td>:</td>
                        <td>666</td>
                    </tr>
                    <tr>
                        <td class="itemTitle">队伍名称</td>
                        <td>:</td>
                        <td>吃鸡小分队</td>
                    </tr>
                    <tr>
                        <td class="itemTitle">队伍人数</td>
                        <td>:</td>
                        <td>1 / 4</td>
                    </tr>
                    <tr>
                        <td class="itemTitle">队伍简介</td>
                        <td>:</td>
                        <td>吃鸡小分队吃鸡小分队吃鸡小分队吃鸡小分队吃鸡小分队
                            吃鸡小分队吃鸡小分队吃鸡小分队吃鸡小分队吃鸡小分队
                            吃鸡小分队吃鸡小分队吃鸡小分队吃鸡小分队吃鸡小分队
                            吃鸡小分队吃鸡小分队吃鸡小分队吃鸡小分队吃鸡小分队</td>
                    </tr>
                    <tr>
                        <td class="itemTitle">加入条件</td>
                        <td>:</td>
                        <td>仅限男生</td>
                    </tr>

                </table>
                        <!-- 功能按钮 -->
                        <div class="troopsHandle">
                            <button type="button" class="btn btn-warning btn-block">申请加入</button>
                            <button type="button" class="btn btn-danger btn-block">举报</button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 显示一个队伍 -->
            <div class="oneTroops col-sm-11">
                <div class="panel panel-default">
                    <div class="panel-body bg-success">
                        <!-- 显示队伍的信息 -->
                        <table class="table table-condensed">
                            <tr>
                                <td class="itemTitle">队伍ID</td>
                                <td>:</td>
                                <td>666</td>
                            </tr>
                            <tr>
                                <td class="itemTitle">队伍名称</td>
                                <td>:</td>
                                <td>吃鸡小分队</td>
                            </tr>
                            <tr>
                                <td class="itemTitle">队伍人数</td>
                                <td>:</td>
                                <td>1 / 4</td>
                            </tr>
                            <tr>
                                <td class="itemTitle">队伍简介</td>
                                <td>:</td>
                                <td>吃鸡小分队吃鸡小分队吃鸡小分队吃鸡小分队吃鸡小分队</td>
                            </tr>
                            <tr>
                                <td class="itemTitle">加入条件</td>
                                <td>:</td>
                                <td>仅限男生</td>
                            </tr>
                        </table>
                        <!-- 功能按钮 -->
                        <div class="troopsHandle">
                            <button type="button" class="btn btn-warning btn-block">申请加入</button>
                            <button type="button" class="btn btn-danger btn-block">举报</button>
                        </div>
                    </div>
                </div>
            </div>

        </div>



    </div>

</div>
</body>
</html>
