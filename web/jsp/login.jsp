<%--
  Created by IntelliJ IDEA.
  User: 77576
  Date: 2019/7/30
  Time: 14:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>用户登陆</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <style>
        .loginpanel{
            margin-top: 100px;
        }
        .loginpanelborder{
            border-left: #8c8c8c solid 1px;
        }
    </style>
</head>
<body>
    <div class="panel row loginpanel">
        <div class="panel-body panel-default">
            <%--系统简介--%>
            <div class="col-md-5 col-md-offset-1">
                <div class="modal-header">
                    <h2 class="modal-title">系统简介</h2>
                </div>
                <div class="panel panel-default col-md-11" style="border: 0px">
                    <div class="panel-body">
                        <p>本系统拥有本系统拥有本系统拥有本系统拥有本系统拥有
                            本系统拥有本系统拥有本系统拥有本系统拥有本系统拥有
                            本系统拥有本系统拥有本系统拥有本系统拥有本系统拥有
                            本系统拥有本系统拥有本系统拥有本系统拥有本系统拥有
                            本系统拥有本系统拥有本系统拥有本系统拥有本系统拥有
                            本系统拥有本系统拥有本系统拥有本系统拥有本系统拥有
                            本系统拥有本系统拥有本系统拥有本系统拥有本系统拥有
                        </p>
                    </div>
                </div>

            </div>

            <%--登陆模块--%>
            <div class="col-md-4 loginpanelborder">
                <div class="modal-header">
                    <h2 class="modal-title" style="display: inline;">欢迎登陆</h2>
                    <div class="header01 col-md-offset-3" style="display: inline;">
                        &ensp;&ensp;没有账号?
                        <a href="${pageContext.request.contextPath}/UserServlet?method=registerUI">点此注册</a>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <form class="form-horizontal" id="loginForm"
                              action="${pageContext.request.contextPath}/UserServlet?method=userLogin" method="post">
                            <!--用户名-->
                            <div class="form-group">
                                <label for="uname" class="col-sm-1 control-label">用户名：</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control " name="uname" id="uname" placeholder="手机号/用户名">
                                </div>
                            </div>
                            <!--密码-->
                            <div class="form-group">
                                <label for="upassword" class="col-sm-1 control-label">密码：</label>
                                <div class="col-sm-3">
                                    <input type="password" class="form-control loadPassword" name="upassword" id="upassword" placeholder="密码">
                                </div>
                            </div>
                            <!--验证码-->
                            <div class="form-group">
                                <label for="verification-code" class="col-sm-1 control-label">验证码：</label>
                                <div class="col-sm-3">
                                    <div class="input-group">
                                        <input type="text" class="form-control" name="verification-code" id="verification-code" placeholder="5位验证码">
                                        <span class="input-group-btn">
                                            <a class="btn btn-default" href="${pageContext.request.contextPath}/jsp/login.jsp" role="button">
                                                <img src="${pageContext.request.contextPath}/images/captcha" style="height: 20px"/></a>
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="autoLogin" class="col-sm-1 control-label"></label>
                                <div class="col-sm-2">
                                    <input type="checkbox" name="autoLogin" id="autoLogin">自动登录
                                </div>
                                <div class="col-sm-1">
                                    <a href="#" data-toggle="modal" data-target="#myLoadModal">忘记密码</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="modal-footer">
                    <a href="${pageContext.request.contextPath}/UserServlet?method=indexUI"  class="btn btn-default" >返回首页</a>
                    <button type="submit" form="loginForm" id="loginButton"
                          class="btn btn-primary">&ensp;登&ensp;陆&ensp;</button>
                </div>
            </div>
        </div>
    </div>

    <!--忘记密码-->
    <div class="modal fade" id="myLoadModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog modal-md" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel">找回密码</h4>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <form class="form-horizontal" method="post" id="findpassword"
                              action="${pageContext.request.contextPath}/UserServlet?method=findpassword">
                            <!--手机号码-->
                            <div class="form-group">
                                <label for="telephone" class="col-sm-2 control-label">手机号码：</label>
                                <div class="col-sm-3">
                                    <input type="text" name="telephone" class="form-control" id="telephone" placeholder="手机号码">
                                </div>
                            </div>
                            <!--手机验证码-->
                            <div class="form-group">
                                <label for="telephoneCode" class="col-sm-2 control-label">验证码：</label>
                                <div class="col-sm-3">
                                    <div class="input-group">
                                        <input type="text" name="telephoneCode" class="form-control" id="telephoneCode" placeholder="验证码">
                                        <span class="input-group-btn">
                                             <a class="btn btn-default" href="#" role="button">获取验证码</a>
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <!--新密码-->
                            <div class="form-group">
                                <label for="newpassword" class="col-sm-2 control-label">新密码：</label>
                                <div class="col-sm-3 ">
                                    <input type="password" name="newpassword" class="form-control " id="newpassword" placeholder="新密码">
                                </div>
                            </div>
                            <!--确认新密码-->
                            <div class="form-group">
                                <label for="reNewpassword" class="col-sm-2 control-label">确认新密码：</label>
                                <div class="col-sm-3">
                                    <input type="password" class="form-control" id="reNewpassword" placeholder="确认新密码">
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">&ensp;关&ensp;闭&ensp;</button>
                    <button type="submit" form="findpassword" class="btn btn-primary">&ensp;确&ensp;定&ensp;</button>
                </div>
            </div>
        </div>
    </div>
</body>
</html>


