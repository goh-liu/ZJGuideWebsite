<%--
  Created by IntelliJ IDEA.
  User: 77576
  Date: 2019/7/29
  Time: 10:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
    <!-- Bootstrap同时也是header必要的文件-->
    <script src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="js/jquery.cookie.js"></script>
  </head>
  <body>
    <%@include file="jsp/header.jsp"%>
    <div style="width: 100%;height: 99%;margin-top: 53px;padding-left: 35%;padding-top: 10%">
      <h1>欢迎来到校园论坛系统<br/>您尚未登录，请先登录！！</h1>
    </div>
  </body>
</html>
