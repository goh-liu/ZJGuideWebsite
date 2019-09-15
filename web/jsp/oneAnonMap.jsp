<%--
  Created by IntelliJ IDEA.
  User: 77576
  Date: 2019/9/1
  Time: 11:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--单个用户-->
<div class="anonymityItem row">
    <!--用户头像-->
    <div class="col-sm-1">
        <img src="${pageContext.request.contextPath}/image/headimg2.jpg" class="headImg">
    </div>
    <!--其他-->
    <div class="col-sm-11">
        <div>
            <strong style="margin-right: 30px;">匿名者${oneAnonMap.get('anonDistrict').counter}</strong>
            <a class="btn btn-xs anon_comment">
                <img src="${pageContext.request.contextPath}/image/comment.png"
                     style="width: 18px;height: 18px;">&ensp;评论
            </a>
            <a class="btn btn-xs anon_like" onclick="anon_like(this,'${oneAnonMap.get('anonDistrict').counter}')">
                <c:set var="like1" value="no"/>
                <c:forEach items="${oneAnonMap.get('likeList')}" var="ll">
                    <c:if test="${ll.anonID == oneAnonMap.get('anonDistrict').anonID and ll.likeUID == loginUser.uid}">
                        <img src="${pageContext.request.contextPath}/image/like1.png"
                             style="width: 18px;height: 18px;">&ensp;点赞(<span class="anon_like_coun">${oneAnonMap.get('anonDistrict').likeCoun}</span>)
                        <c:set var="like1" value="yes"/>
                    </c:if>
                </c:forEach>
                <c:if test="${!(like1.equals('yes'))}">
                    <img src="${pageContext.request.contextPath}/image/like2.png"
                         style="width: 18px;height: 18px;">&ensp;点赞(<span class="anon_like_coun">${oneAnonMap.get('anonDistrict').likeCoun}</span>)
                    <c:remove var="like1"/>
                </c:if>
            </a>
            <c:if test="${oneAnonMap.get('anonDistrict').uid== loginUser.uid}">
                <a class="btn btn-xs anon_del" onclick="anon_del(this,'${oneAnonMap.get('anonDistrict').counter}')">
                    <img src="${pageContext.request.contextPath}/image/del.png"
                         style="width: 18px;height: 18px;">&ensp;删除
                </a>
            </c:if>
        </div>
        <div class="itemContent">
            <div>${oneAnonMap.get('anonDistrict').anonContent}</div>
            <div>
                <!--遍历图片集合-->
                <c:forEach items="${oneAnonMap.get('priceList')}" var="p">
                    <!--判断是否是该条的图片-->
                    <c:if test="${p.anonID == oneAnonMap.get('anonDistrict').anonID}">
                        <img src="imgUrl/${p.priceUrl}" class="headImg">
                    </c:if>
                </c:forEach>
            </div>

            <!--用户回复评论-->
            <div class="row itemComment">
                <c:forEach items="${oneAnonMap.get('commentList')}" var="cl">
                    <!--等于1表示为评论-->
                    <c:if test="${cl.commentOrReply==1}">
                        <c:if test="${cl.anonID == oneAnonMap.get('anonDistrict').anonID}">
                            <div class="col-sm-12">
                                <strong>${cl.sourceUname}</strong>&ensp;：
                                <span>${cl.commentText}</span>&ensp;&ensp;
                                <a class="btn btn-default btn-xs" onclick="comment_reply1('${oneAnonMap.get('anonDistrict').counter}','${cl.sourceUid}','${cl.sourceUname}','${cl.commentText}')">回复</a>&ensp;&ensp;
                            </div>
                        </c:if>
                    </c:if>
                    <!--等于2表示为回复-->
                    <c:if test="${cl.commentOrReply==2}">
                        <c:if test="${cl.anonID == oneAnonMap.get('anonDistrict').anonID}">
                            <div class="col-sm-12">
                                <strong>${cl.sourceUname}</strong>&ensp;回复&ensp;<strong>${cl.destUname}</strong>&ensp;：
                                <span>${cl.commentText}</span>&ensp;&ensp;
                                <a class="btn btn-default btn-xs" onclick="comment_reply2('${oneAnonMap.get('anonDistrict').counter}','${cl.sourceUid}','${cl.sourceUname}','${cl.destUname}','${cl.commentText}')">回复</a>&ensp;&ensp;
                            </div>
                        </c:if>
                    </c:if>
                </c:forEach>

            </div>
        </div>
    </div>
</div>