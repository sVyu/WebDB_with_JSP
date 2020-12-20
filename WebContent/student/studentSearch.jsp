<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Student search</title>
</head>
<body>
<%-- /jwprj/student 처럼 컨텍스트 경로를 작성해야 하지만 컨텍스트 변경 시 소스 수정의 필요성이 발생,
	이를 보완하기 위해 아래와 같이 경로를 가져올 수 있음,, jsp 컨트롤러 구현시 action 수정 필요 --%>
<form method="get" action="${pageContext.request.contextPath}/student">
Student ID: <input type="text" name="id"><input type="submit" value="Search">
<%-- 학생의 name 속성은 "id"이며, Student 클래스의 id 멤버변수와 동일한 이름을 사용(private int id;)--%>
</form>
</body>
</html>