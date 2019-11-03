<%--
  Created by IntelliJ IDEA.
  User: 77576
  Date: 2019/7/30
  Time: 20:18
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>用户注册</title>
    <!-- Bootstrap同时也是header必要的文件-->
    <script src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/register.js"></script>
</head>
<body>
<div class="panel panel-default">
    <div class="panel-body col-sm-8 col-sm-offset-1">
        <!-- 注册页面 -->
        <div class="modal-header">
            <div>
                <h2 class="modal-title" style="display: inline;">注册</h2>
                <div  class=" col-sm-offset-9"  style="display: inline;">
                    已有账号?
                    <a href="${pageContext.request.contextPath}/UserServlet?method=loginUI">点此登陆</a>
                </div>
            </div>
        </div>
        <div class="modal-body">
            <div class="container">
                <form class="form-horizontal" id="registerForm" method="post"
                      action="${pageContext.request.contextPath}/UserServlet?method=userRegister" >
                    <!--用户名-->
                    <div class="form-group">
                        <label for="uname" class="col-sm-2 control-label">用户名：</label>
                        <div class="col-sm-3 bitian">
                            <input type="text" name="uname" class="form-control " id="uname" placeholder="用户名">
                        </div>
                    </div>
                    <!--密码-->
                    <div class="form-group">
                        <label for="upassword" class="col-sm-2 control-label">密码：</label>
                        <div class="col-sm-3 bitian">
                            <input type="password" name="upassword" class="form-control " id="upassword" placeholder="密码">
                        </div>
                    </div>
                    <!--确认密码-->
                    <div class="form-group">
                        <label for="Reupassword" class="col-sm-2 control-label">确认密码：</label>
                        <div class="col-sm-3 bitian">
                            <input type="password" class="form-control" id="Reupassword" placeholder="确认密码">
                        </div>
                    </div>
                    <!--性别-->
                    <div class="form-group ">
                        <label for="" class="col-sm-2 control-label">性别：</label>

                        <div class="col-sm-1">
                            <label class="radio" style="margin-left: 30px;">
                                <input type="radio" checked="checked" name="sex" value="男">男
                            </label>
                        </div>
                        <div class="col-sm-1">
                            <label class="radio">
                                <input type="radio" name="sex" value="女">女
                            </label>
                        </div>
                        <div class="col-sm-1 bitian"></div>
                    </div>
                    <!--手机号码-->
                    <div class="form-group">
                        <label for="telephone" class="col-sm-2 control-label">手机号码：</label>
                        <div class="col-sm-3 bitian">
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
                    <div>
                        <!--归属学院的第一行:学校、学院-->
                        <div class="row form-group">
                            <label for="" class="col-sm-2 control-label">归属院校：</label>

                            <div class="col-md-3">
                                <select class="form-control"name="school" id="school">
                                    <option value="-1" >--学校--</option>
                                    <option value="0">华农珠江</option>
                                    <option value="1">华农</option>
                                </select>
                            </div>
                            <div class="col-md-3">
                                <select class="form-control" id="academy" name="secondarySchool">
                                    <option value="-1">--学院--</option>
                                </select>
                            </div>
                        </div>
                        <!--归属学院的第二行：年级、专业-->
                        <div class="row form-group">
                            <label class="col-sm-2 control-label"></label>

                            <div class="col-md-3">
                                <select class="form-control" id="grade"  name="ugrade">
                                    <option value="-1">--年级--</option>
                                    <option value="0">2015</option>
                                    <option value="1">2016</option>
                                    <option value="2">2017</option>
                                    <option value="3">2018</option>
                                </select>
                            </div>
                            <div class="col-md-3">
                                <select class="form-control" id="major" name="uclass">
                                    <option value="-1">--专业--</option>
                                </select>
                            </div>

                        </div>

                    </div>


                </form>
            </div>
        </div>
        <div class="modal-footer">
            <a href="${pageContext.request.contextPath}/UserServlet?method=indexUI"  class="btn btn-default">返回首页</a>
            <button type="submit" form="registerForm" id="registerButton"
                    class="btn btn-primary">&ensp;注&ensp;册&ensp;</button>
        </div>
    </div>
</div>

</body>
</html>

