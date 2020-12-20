<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<%
	// 브라우저 에러 화면이 아닌 서버 에러페이지가 표시되게 하기 위한 코드
	response.setStatus(HttpServletResponse.SC_OK);
%>
<c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>		

<!DOCTYPE html>
<html>
<head>
<%@include file="inc_header.html" %>
<title>에러 페이지</title>
</head>
<body>
<div class="container shadow w-75 p-3 mt-5">
	<H2>에러 페이지</H2>
	<HR>
	<div class="bg-warning p-3">
		<strong>다음의 에러 메시지를 확인하세요!!</strong>
		<hr>
		Error : ${exception} <button class="ml-3 btn btn-outline-danger" data-toggle="collapse" data-target="#msg">Show/Hide</button>
		<hr>
		<div id="msg" class="collapse">		
		  <c:forEach var="stackTraceElem" items="${exception.stackTrace}">
		    <c:out value="${stackTraceElem}"/><br/>
		  </c:forEach>		
		  <hr>
		</div>
		<button class="btn btn-success" onclick="history.go(-1)">&lt;&lt; Back</button>
	</div>
</div>
</body>
</html>