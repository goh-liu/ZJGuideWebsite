<%--
  Created by IntelliJ IDEA.
  User: 77576
  Date: 2019/12/10
  Time: 10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理后台</title>
    <!--引入本页面的css文件-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/adminUI/css/adminUI.css">
    <!-- 引入element ui样式 -->
    <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
</head>
<body>
    <div id="app">
        <!-- 顶部导航栏 -->
        <div class="topNav">
            <div class="markedWord">校园论坛管理后台</div>
            <div class="enterArea">
                欢迎登陆：管理员
                <div>
                    <el-link type="danger">退出</el-link>
                </div>
            </div>
        </div>
        <!-- 侧边导航栏 -->
        <div class="leftNav">
            <el-menu
                    default-active="2"
                    class="el-menu-vertical-demo"
                    @open="handleOpen"
                    @close="handleClose"
                    background-color="#545c64"
                    text-color="#fff"
                    active-text-color="#ffd04b">
                <el-submenu index="1">
                    <template slot="title">
                        <i class="el-icon-menu"></i>
                        <span>匿名友人</span>
                    </template>
                    <el-menu-item-group>
                        <el-menu-item index="1-1">查看说说</el-menu-item>
                        <el-menu-item index="1-2">查看评论</el-menu-item>
                        <el-menu-item index="1-3">查看热门</el-menu-item>
                        <el-menu-item index="1-4">查看举报</el-menu-item>
                    </el-menu-item-group>
                </el-submenu>
                <el-submenu index="2">
                    <template slot="title">
                        <i class="el-icon-menu"></i>
                        <span>学长寄语</span>
                    </template>
                    <el-menu-item-group>
                        <el-menu-item index="2-1">查看寄语</el-menu-item>
                        <el-menu-item index="2-2">查看热门</el-menu-item>
                        <el-menu-item index="2-3">查看异议</el-menu-item>
                        <el-menu-item index="2-4">查看举报</el-menu-item>
                    </el-menu-item-group>
                </el-submenu>
                <el-submenu index="3">
                    <template slot="title">
                        <i class="el-icon-menu"></i>
                        <span>志同道合</span>
                    </template>
                    <el-menu-item-group>
                        <el-menu-item index="3-1">查看队伍</el-menu-item>
                        <el-menu-item index="3-2">查看举报</el-menu-item>
                        <el-menu-item index="3-3">已满队伍</el-menu-item>
                    </el-menu-item-group>
                </el-submenu>
                <el-submenu index="4">
                    <template slot="title">
                        <i class="el-icon-menu"></i>
                        <span>用户管理</span>
                    </template>
                    <el-menu-item-group>
                        <el-menu-item index="4-1">查看普通用户</el-menu-item>
                        <el-menu-item index="4-2">查看管理用户</el-menu-item>
                    </el-menu-item-group>
                </el-submenu>

            </el-menu>
        </div>
        <!-- 中间显示区域 -->
        <div class="primaryShow">
            <div class="bread">
                <el-breadcrumb separator-class="el-icon-arrow-right">
                    <el-breadcrumb-item :to="{ path: '/' }">当前位置：首页</el-breadcrumb-item>
                    <el-breadcrumb-item>匿名说说</el-breadcrumb-item>
                    <el-breadcrumb-item>查看说说</el-breadcrumb-item>
                </el-breadcrumb>
            </div>
            <div class="showPanel1">
                <el-table
                        :data="tableData"
                        border
                        style="width: 100%"
                        max-height="478">
                    <el-table-column
                            fixed
                            prop="num"
                            label="序号"
                            width="55">
                    </el-table-column>
                    <el-table-column
                            prop="name"
                            label="名称"
                            width="120">
                    </el-table-column>
                    <el-table-column
                            prop="content"
                            label="内容"
                            width="340">
                    </el-table-column>
                    <el-table-column
                            prop="time"
                            label="时间"
                            width="110">
                    </el-table-column>
                    <el-table-column
                            prop="likeCoun"
                            label="被评论数"
                            width="100">
                    </el-table-column>
                    <el-table-column
                            prop="complainCoun"
                            label="被喜欢数"
                            width="100">
                    </el-table-column>
                    <el-table-column
                            prop="complainCoun"
                            label="被投诉数"
                            width="100">
                    </el-table-column>
                    <el-table-column
                            fixed="right"
                            label="操作"
                            width="120">
                        <template slot-scope="scope">
                            <el-button
                                    @click.native.prevent="deleteRow(scope.$index, tableData)"
                                    type="text"
                                    size="small">
                                查看
                            </el-button>
                            <el-button
                                    @click.native.prevent="deleteRow(scope.$index, tableData)"
                                    type="text"
                                    size="small">
                                删除
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div class="tablefooter">
                <el-pagination
                        background
                        layout="prev, pager, next"
                        :total="1000">
                </el-pagination>
            </div>
        </div>
    </div>
</body>
</html>

<!-- 引入vue和element ui组件库 -->
<script src="https://unpkg.com/vue/dist/vue.js"></script>
<script src="https://unpkg.com/element-ui/lib/index.js"></script>
<!--引入本页面的js文件（问题：js文件要放在vue和element ui后面）-->
<script src="${pageContext.request.contextPath}/adminUI/js/adminUI.js"></script>
