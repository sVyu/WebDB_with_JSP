<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 기본 URL 경로 지정 -->
<c:set var="baseUrl" value="${pageContext.request.contextPath}/twitter" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<!-- 공통헤더파일 include -->
	<%@ include file="/common/inc_header.html" %>
	<title>Twitter On</title>
</head>
<body>
	<div class="container shadow mx-auto mt-5 p-5 w-60">
		<H3>My Simple Twitter!!</H3>
		<HR>
		<form action="${baseUrl}" method="post">
			<input type="hidden" name="action" value="tweet">

			<div class="input-group">
				<button type="button" class="btn btn-outline-success">@${user}</button>
				<input class="form-control" type="text" name="msg">
				<input class="btn btn-warning ml-2" type="submit" value="Tweet"> 
				<a href="${baseUrl}?action=list" class="btn btn-success ml-2">Refresh</a> 
				<a href="${baseUrl}?action=logout" class="btn btn-secondary ml-2">Sign out</a>
			</div>
		</form>
		<c:if test="${error != null}">
			<div class="alert alert-danger mt-3">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				에러발생: ${error}
			</div>
		</c:if>
		<div align="left">
			<ul class="list-group">
				<c:forEach var="msg" items="${tweetlist}">
					<li class="list-group-item list-group-item-action">${msg}</li>
				</c:forEach>
			</ul>
		</div>
	</div>

</body>
</html>