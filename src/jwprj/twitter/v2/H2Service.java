package jwprj.twitter.v2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class H2Service implements TwitterService {
	// 데이터베이스 연결 정보 필요
	// 이하의 값들은  h2 console에 명시되어있음
	String jdbc_driver = "org.h2.Driver";
	String jdbc_url = "jdbc:h2:~/jwprjdb";
	
	// import java.sql.~
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	public void connect() {
		// 1단계 : JDBC 드라이버 로드
		// Error? ClassNotFoundException(예외), 즉, 실행이 될 때도 있고 안 될 때도 있다
		// 여기서 예외? H2 드라이버가 틀리거나 없을 때 혹은 실행 과정에서 라이브러리가 참조 경로에 없을 때
		try {
			Class.forName(jdbc_driver);

			// 2단계 : 데이터베이스 연결
			conn = DriverManager.getConnection(jdbc_url,"sa","1234");
			
			// 모든 예외를 한번에 처리하려면 Exception으로 한다
			// 정밀한 시스템을 만든다면 예외와 관련된 코드를 써주어야 겠지만
			// 여기서는 에러가 나면 개발자한테 출력만 해주는 정도로,
		} catch (Exception e) {
			e.printStackTrace();	// 콘솔에 error 메시지 출력
		}
	}
	
	public void close() {
		try {
			pstmt.close();
			conn.close();
		// 둘 다 SQLException이므로 아래와 같이 처리
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	//				여기 Tweet은 controller 가 보내준다
	public void write(Tweet tweet) {
		// 기존에는 문자열 조합으로 했으니 이번에는 그런 과정 없이 DB 처리
		// name과 email과 date와 msg 가 필요
		connect();
		
		// 여기도 검증이 안 되기 때문에 틀린 부분이 없는지 잘 확인
		String sql = "insert into tweet(name, email, date, msg) values(?,?,?,?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tweet.getName());
			pstmt.setString(2, tweet.getEmail());
			pstmt.setString(3, tweet.getDate());
			pstmt.setString(4, tweet.getMsg());
			
			pstmt.executeUpdate();	// 쿼리 실행
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	@Override
	public List<Tweet> getList() {
		connect();
		String sql = "select * from tweet";
		List<Tweet> tweets = new ArrayList<Tweet>();
		//List<Tweet> tweets = new ArrayList<>();
		
		try {
			pstmt = conn.prepareStatement(sql);
		
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Tweet t = new Tweet();
				t.setId(rs.getInt("id"));
				t.setName(rs.getString("name"));
				t.setEmail(rs.getString("email"));
				t.setDate(rs.getString("date"));
				t.setMsg(rs.getString("msg"));
				
				tweets.add(t);
			}
			rs.close();			// 추가
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return tweets;
	}

	@Override
	public void delTweet(String email, Integer id) throws Exception {
		connect();
		// ,,게시글 id 와 작성자 email 이 일치하는 경우에만 삭제
		String sql = "delete from tweet where email=? and id=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, email);
		pstmt.setInt(2, id);
		
		// delete 쿼리문에 의해 제대로 수행 됐다면 1개만 삭제 처리
		if(pstmt.executeUpdate() != 1) {
			close();
			throw new Exception("게시글 삭제 중 문제가 생겼습니다.!!");
		}
		close();
	}

}
