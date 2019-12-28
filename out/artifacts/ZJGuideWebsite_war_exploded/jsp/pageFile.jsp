<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%--页码的显示----分页显示的开始 --%>
    	<div style="text-align:center">
			<!--分页-->
			<nav aria-label="Page navigation" style="text-align: center">
				<ul class="pagination">
					<li><a>当前&ensp;${page.currentPageNum}&ensp;/共&ensp;${page.totalPageNum}&ensp;页</a></li>
					<li><a href="${pageContext.request.contextPath}/${page.url}num=1">首页</a></li>
					<li>
						<a href="${pageContext.request.contextPath}/${page.url}num=${page.prePageNum}" aria-label="Previous">
							<span aria-hidden="true">上一页</span>
						</a>
					</li>
					<%--显示的页码，使用forEach遍历显示的页面 --%>
					<c:forEach begin="${page.startPage}" end="${page.endPage}" var="pagenum">
						<li><a href="${pageContext.request.contextPath}/${page.url}num=${pagenum}">${pagenum}</a></li>
					</c:forEach>
					<li>
						<a href="${pageContext.request.contextPath}/${page.url}num=${page.nextPageNum}" aria-label="Next">
							<span aria-hidden="true">下一页</span>
						</a>
					</li>
					<li><a href="${pageContext.request.contextPath}/${page.url}num=${page.totalPageNum}">末页</a></li>
				</ul>
			</nav>

    	</div>
