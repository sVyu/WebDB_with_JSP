<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%--JSTL 사용을 위한 taglib 지시어 추가 --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- post 방식으로 전달되는 한글 데이터의 정상적인 처리를 위해 다음 코드를 추가 --%>
<%
	request.setCharacterEncoding("utf-8");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign-up Results</title>
</head>
<body>
	<H2>Sign-up Results</H2>
	<hr>
	<ul>
		<li>Name: <%=request.getParameter("username") %></li>
		<li>Job: <%=request.getParameter("job") %></li>
		<li>Favorites:
		<ul>
		<%-- JSTL과 EL 사용 --%>
		<c:forEach items="${paramValues.hobby}" var="hobby">
			<li>${hobby}</li>
		</c:forEach>
	
		<%-- 스크립트릿 사용--%>
		<%-- 중복된 name 속성으로 입력된 모든 항목을 가져와야하므로 getPameterValues() --%>
		<%-- 
		String hobbies[] = request.getParameterValues("hobby");
		for(String hobby : hobbies){
			out.println("<li>" + hobby + "</li>");
		}
		--%>
		</ul>
		</li>
	</ul>
	<hr>
	
	<h2>Request Information</h2>
	<%-- request.getRemoteAddr() :
		현재 페이지를 요청하는 클라이언트 컴퓨터의 IP주소를 가져온다 --%>
	<%-- 서버와 클라이언트가 동일 컴퓨터인경우 loopback IP 출력(127.0.0.1) --%>
	1. Client IP address : <%=request.getRemoteAddr() %><br>
	2. Request Method : <%=request.getMethod() %>
	<hr>
	
	<h2>Response Information</h2>
	<%-- 정상 응답 : 200, server error : 500, not found : 404 --%>
	1. Status Code : <%=response.getStatus() %><br>
	<%-- Locale은 언어_지역 형식으로 출력된다 --%>
	2. Locale : <%=response.getLocale() %>
	<hr>
	
	<h2>Server Information</h2>
	<%-- application 내장객체를 통해 서버 버전과 요청 리소스의 경로 정보 등을 알 수 있음 --%>
	1. Server info : <%=application.getServerInfo() %><br>
	2. Context Path : <%=application.getContextPath() %>
	<%
		application.log("Log from signupResult.jsp");
	%>
	<%-- log 메시지는 이클립스의 톰캣 콘솔창에서 확인 가능 --%>
</body>
</html>