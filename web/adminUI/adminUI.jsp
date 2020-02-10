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
    <script src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"></script>

</head>
<body>
    <div id="app">
        <!-- 顶部导航栏 -->
        <div class="topNav">
            <div class="markedWord">校园论坛管理后台</div>
            <div class="enterArea">
                欢迎登录：<strong>${adminUser.uname}</strong>
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
                    @select="handleSelect"
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
                        <el-menu-item index="1-2">查看热门</el-menu-item>
                        <el-menu-item index="1-3">查看评论</el-menu-item>
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
                        <el-menu-item index="3-2">查看队员</el-menu-item>
                        <el-menu-item index="3-3">查看举报</el-menu-item>
                        <el-menu-item index="3-4">已满队伍</el-menu-item>
                    </el-menu-item-group>
                </el-submenu>
                <el-submenu index="4">
                    <template slot="title">
                        <i class="el-icon-menu"></i>
                        <span>用户管理</span>
                    </template>
                    <el-menu-item-group>
                        <el-menu-item index="4-1">普通用户</el-menu-item>
                        <el-menu-item index="4-2">管理用户</el-menu-item>
                    </el-menu-item-group>
                </el-submenu>

            </el-menu>
        </div>
        <!-- 中间显示区域 -->
        <div class="primaryShow">
            <div class="bread">
                <el-breadcrumb separator-class="el-icon-arrow-right">
                    <el-breadcrumb-item class="breadcrumbTitle1" >{{crumbsTitle1}}</el-breadcrumb-item>
                    <el-breadcrumb-item class="breadcrumbTitle2" >{{crumbsTitle2}}</el-breadcrumb-item>
                </el-breadcrumb>
            </div>
            <div class="showPanel1">
                <el-table
                        :data="tableData"
                        border
                        style="width: 100%"
                        max-height="478">

                    <el-table-column
                           v-for="info in tableTitle[index2]"
                           :key="info.key"
                           :property="info.key"
                           :label="info.lable">
                        <template slot-scope="scope">
                            {{scope.row[scope.column.property]}}
                        </template>
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
                                删除
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <template>
                    <div class="block">
                        <el-pagination
                                @size-change="handleSizeChange"
                                @current-change="handleCurrentChange"
                                :current-page="currentPage"
                                :page-sizes="[10, 20, 30, 50]"
                                :page-size="pageSize"
                                layout="total, sizes, prev, pager, next, jumper"
                                :total="totalNum">
                        </el-pagination>
                    </div>
                </template>
                <div class="indexpanel">
                    <h1>欢迎来到后台管理</h1>
                </div>
            </div>

        </div>
    </div>
</body>
</html>

<!-- 引入vue和element ui组件库 -->
<script src="https://unpkg.com/vue/dist/vue.js"></script>
<script src="https://unpkg.com/element-ui/lib/index.js"></script>
<%--  axios  --%>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<!--引入本页面的js文件（问题：js文件要放在vue和element ui后面）-->
<script src="${pageContext.request.contextPath}/adminUI/js/adminUI.js"></script>
