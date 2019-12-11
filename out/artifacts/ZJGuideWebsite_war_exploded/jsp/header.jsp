<%--
  Created by IntelliJ IDEA.
  User: 77576
  Date: 2019/7/29
  Time: 11:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!--导航条-->
    <nav class="navbar navbar-default navbar-fixed-top" style="margin-bottom: 1px;">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">logo的地方</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">logo图标</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li><a href="${pageContext.request.contextPath}/adminUI/adminUI.jsp">管理后台<span class="sr-only">(current)</span></a></li>
                    <li><a href="${pageContext.request.contextPath}/jsp/upperclassmanNote.jsp">学长寄语</a></li>
                    <li><a href="${pageContext.request.contextPath}/jsp/commonGoal.jsp">志同道合</a></li>
                    <li><a href="${pageContext.request.contextPath}/AnonServlet?method=anonUI">匿名友人</a></li>
                </ul>
                <form class="navbar-form navbar-left">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="您要查找什么">
                    </div>
                    <button type="submit" class="btn btn-default">搜索</button>
                </form>
                <ul class="nav navbar-nav navbar-right">
                    <c:if test="${empty loginUser}">
                        <li><a href="${pageContext.request.contextPath}/UserServlet?method=loginUI" class="btn">登陆</a></li>
                        <li><a href="${pageContext.request.contextPath}/UserServlet?method=registerUI" class="btn" >注册</a></li>
                    </c:if>
                    <c:if test="${not empty loginUser}">
                        <li><a href="" class="btn">欢迎您,<strong>${loginUser.uname}</strong></a></li>
                        <li><a href="${pageContext.request.contextPath}/UserServlet?method=exitLogin" class="btn" >退出</a></li>
                    </c:if>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script>
    $(function () {
        if (${empty loginUser}) {
            //用户尚未登录
            var flag1 = $.isEmptyObject($.cookie('autoLogin'));
            var flag2 = $.isEmptyObject($.cookie('autoLogining'));
            if (!flag1 && flag2) {
                //有自动登录的用户
                window.location.href='${pageContext.request.contextPath}/UserServlet?method=autoLogin';
            }
        }
    });
</script>
