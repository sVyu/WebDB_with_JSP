<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>News Desk</title>
</head>
<body>
	<h2>${news.title}</h2>
	<hr>
	<div>
		<img src="${news.img}">
		<div>
			<h4>Date : ${news.date}</h4>
			<p>Content: ${news.content}</p>
		</div>
	</div>
	<hr>
	<a href="javascript:history.go(-1)"> << Back </a>
</body>
</html>