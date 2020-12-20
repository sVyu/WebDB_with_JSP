<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 선택한 상품은 session을 이용해서 저장 --%>
<%-- 여러 상품을 저장할 수 있어야하기 때문에 보통 ArrayList 사용 --%>
<%-- useBean 액션을 이용해 ArrayList 객체를 session scope에 생성 --%>
<jsp:useBean id="productlist" class="java.util.ArrayList"
	type="java.util.List<String>" scope="session"/>

<%-- 상품 목록에서 선택한 상품 정보를 가져오기 위해 getParameter()사용 --%>
<%-- 가지고 온 다음 String 타입으로 ArrayList에 추가 --%>
<%
	String productname = request.getParameter("product");
	productlist.add(productname);
%>

<%-- 상품 추가 메시지 출력 및 이전 페이지로(입력화면으로) 이동 --%>
<%-- 자바스크립트 코드에서도 jsp 문법 사용 가능 --%>
<script>
	alert("<%=productname%> added to cart !!");
	history.go(-1);
</script>