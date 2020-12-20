package jwprj.twitter.v2;
import java.util.List;

public interface TwitterService {
	public void write(Tweet tweet);	// 기존의 문자열 형식에서 Tweet으로 받게끔 변경
	public List<Tweet> getList();
	
	// v2에서 추가 (삭제 기능)
	// 사실은 pk(id)만 있으면 삭제가 되는데, 그러면 인증이 안 되어있기 때문에 pk만 알면 누구나 삭제 가능
	// 그래서 그 게시글이 자기 글인가 확인을 위해서 email도 인자로 받게끔 설계
	public void delTweet(String email, Integer id) throws Exception;
}
