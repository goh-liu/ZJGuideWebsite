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
                    <li><a href="#" data-toggle="modal" data-target="#loginAdmin">管理后台</a></li>
                    <li><a href="${pageContext.request.contextPath}/anon_anonUI.action">匿名说说</a></li>
                    <li><a href="${pageContext.request.contextPath}/note_noteUINew.action" >学长寄语</a></li>
                    <li><a href="${pageContext.request.contextPath}/team_commonGoal.action">志同道合</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <c:if test="${empty loginUser}">
                        <li><a href="${pageContext.request.contextPath}/user_loginUI.action" class="btn">登录</a></li>
                        <li><a href="${pageContext.request.contextPath}/user_registerUI.action" class="btn" >注册</a></li>
                    </c:if>
                    <c:if test="${not empty loginUser}">
                        <li><a href="" class="btn">欢迎您,<strong>${loginUser.uname}</strong></a></li>
                        <li><a href="${pageContext.request.contextPath}/user_exitLogin.action" class="btn" >退出</a></li>
                    </c:if>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>

<%--弹出管理后台登录界面--%>
<div class="modal fade" id="loginAdmin" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">登录管理后台</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="loginAdminForm"
                      action="${pageContext.request.contextPath}/user_adminLogin.action" method="post">
                    <!--用户名-->
                    <div class="form-group" >
                        <label for="uname" class="col-sm-3 control-label">管理用户：</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control " name="uname" id="uname" placeholder="手机号/用户名">
                        </div>
                    </div>
                    <!--密码-->
                    <div class="form-group">
                        <label for="upassword" class="col-sm-3 control-label">密码：</label>
                        <div class="col-sm-5">
                            <input type="password" class="form-control loadPassword" name="upassword" id="upassword" placeholder="密码">
                        </div>
                    </div>
                    <!--验证码-->
                    <div class="form-group">
                        <label for="verification-code" class="col-sm-3 control-label">验证码：</label>
                        <div class="col-sm-5">
                            <div class="input-group">
                                <input type="text" class="form-control" name="verification-code" id="verification-code" placeholder="4位验证码">
                                <span class="input-group-btn">
                                            <a class="btn btn-default" href="${pageContext.request.contextPath}/user_loginUI.action"
                                               role="button" style="padding: 1px 10px">
                                                <img src="${pageContext.request.contextPath}/images/captcha"
                                                     style="height: 30px;width: 80px;"/>
                                            </a>
                                </span>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="submit" class="btn btn-primary" form="loginAdminForm">登录</button>
            </div>
        </div>
    </div>
</div>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script>
    $(function () {
        if (${empty loginUser}) {
            //用户尚未登录
            var flag1 = $.isEmptyObject($.cookie('autoLogin'));
            var flag2 = $.isEmptyObject($.cookie('autoLogining'));
            if (!flag1 && flag2) {
                //有自动登录的用户
                window.location.href='${pageContext.request.contextPath}/user_autoLogin.action';
            }
        }
    });
</script>
