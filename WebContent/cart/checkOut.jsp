<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--@ page import="java.util.*"--%>

<%-- jstl 및 jstl function 사용을 위한 taglib --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Check out</title>
</head>
<body>
<div align="center">
	<h2>Check Out</h2>
	Selected Products
	<hr>
	
	<%-- 상품 목록 출력을 위해 session에 저장된 ArrayList를 가져와야 한다 ! --%>
	<%-- useBean 액션과 jstl을 사용하는 경우 --%>
	<jsp:useBean id="productlist" class="java.util.ArrayList"
				type="java.util.List<String>" scope="session"/>
	<c:if test="${fn:length(productlist) == 0}">
		<h3>Your cart is empty !!</h3>
	</c:if>
	<c:forEach items="${productlist}" var="p">
		${p} <br>
	</c:forEach>
	<hr>
	
	<%-- 스크립트릿을 사용하는 경우 --%>
	<%--
		List<String> productlist = (List<String>)session.getAttribute("productlist");
		if(productlist.size() == 0){
			out.println("<h3>Your cart is empty!!</h3>");
		}
		else{
			for(String productname:productlist){
				out.println(productname+"<br>");
			}
		}
	--%>
</div>
</body>
</html>