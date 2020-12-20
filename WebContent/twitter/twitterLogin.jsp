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
	<title>MyTwitter 로그인</title>
</head>
<body>
	<div class="container mx-auto mt-5 p-5 w-50 shadow bg-info">
		<H2>Twitter::Login</H2>
		<form name="form1" method="post" action="${baseUrl}">
			<input type="hidden" name="action" value="login" />
			<div class="input-group">
				<input class="form-control" type="text" name="username" placeholder="아이디를 입력하세요..!" /> 
				<input class="btn btn-warning" type="submit" value="Login" />
			</div>
		</form>
		<c:if test="${error != null}">
			<div class="alert alert-danger mt-3">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				에러발생: ${error}
		</div>
		</c:if><%-- 추가 --%> 
	</div>
</body>
</html>