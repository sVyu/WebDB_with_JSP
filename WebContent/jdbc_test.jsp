<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.sql.*"%>
<%
	request.setCharacterEncoding("utf-8");
	// 데이터베이스 연결 정보 필요
	// 이하의 값들은  h2 console에 명시되어있음
	String jdbc_driver = "org.h2.Driver";
	String jdbc_url = "jdbc:h2:~/jwprjdb";
	
	// 기본 변수 선언, 이번에는 if block 밖에서도 쓰도록, 재활용 가능하게 꺼냈음
	String sql = null;
	PreparedStatement pstmt;
	
	// 1단계 : JDBC 드라이버 로드
	Class.forName(jdbc_driver);
	
	// 2단계 : 데이터베이스 연결
	// Connection 이나 DriverManager를 위해서 import가 필요
	Connection conn = DriverManager.getConnection(jdbc_url,"sa","1234");

	// (2.5단계) 입력 방식이 get인가 post인가 비교
	if(request.getMethod().equals("POST")){
		// 3단계 : 쿼리 및 Statement 객체
		// Error 처리 필요
		sql = "insert into jdbc_test values(?,?)";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, request.getParameter("username"));
		pstmt.setString(2, request.getParameter("email"));
		
		// 4단계 : 쿼리 실행
		pstmt.executeUpdate();
	}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>이벤트 등록</h2>
	<hr>
	<!-- name은 사실 불필요, 등록은 post가 더 적합 -->
	<form name=form1 method=post>
		등록이름 : <input type=text name="username">
		이메일 : <input type="email" name="email" size=20>
		<input type=submit value="등록">
	<hr>
	<h2>등록 목록</h2>
	<%
		//select 문장을 문자열 형태로 구성한다.
		sql = "select username, email from jdbc_test";
		pstmt = conn.prepareStatement(sql);
		
		//select 를 수행하면 데이터정보가 ResultSet 클래스의 인스턴스로 리턴됨.
		ResultSet rs = pstmt.executeQuery();
		int i=1;
		
		//마지막 데이터까지 반복
		while(rs.next()){
			out.println(i+" : "+rs.getString(1)+" , " +rs.getString("email")+"<br/>");
			i++;
		}

		// 사용한 자원의 반납
		rs.close();
		pstmt.close();
		conn.close();
	%>
	</form>
</body>
</html>