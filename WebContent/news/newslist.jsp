<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>News List</title>
</head>
<body>
<h3>News List</h3>
<hr>
	<ul>
		<c:forEach var="news" items="${newslist}" varStatus="status">
		<li><a href="news.nhn?action=view&aid=${news.aid}">[${status.count}] ${news.title}, ${news.date}</a></li>
		</c:forEach>
	</ul>
</body>
</html>