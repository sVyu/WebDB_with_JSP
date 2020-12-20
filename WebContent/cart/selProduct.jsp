<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%
	request.setCharacterEncoding("UTF-8");
	session.setAttribute("username",request.getParameter("username"));
%>
<%--loginForm.html에서 입력한 사용자 이름(username)을 request로 가져와서 session에 저장하는 부분 --%>

<title>Product select</title>
</head>
<body>
<div align="center">
	<h2>Product List</h2>
	<hr>
	Welcome !! <%=session.getAttribute("username") %>
	<hr>
	
	<%--submit 버튼 클릭 시 cart.jsp 호출 --%>
	<form name="form1" method="POST" action="cart.jsp">
			<%--상품 목록 --%>
			<SELECT name="product">
				<option>Apple</option>
				<option>Orange</option>
				<option>Pineapple</option>
				<option>Lemon</option>
				<option>Banana</option>
			</SELECT>
			<input type="submit" value="Add"/>
	</form>
	<a href="checkOut.jsp">Check Out !</a>
</div>
</body>
</html>