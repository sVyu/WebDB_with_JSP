package jwprj.twitter;
import java.util.List;

public interface TwitterService {
	public void write(String msg);	// 게시글 작성은 인자로 받은 문자열 값 저장!
	public List<String> getList();	// 게시글 목록을 리턴
}
