package jwprj.twitter;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

public class EmbeddedService implements TwitterService{
	ServletContext application;
	List<String> tweetdb;
	
	public EmbeddedService(ServletContext application) {
		this.application = application;
    	// 메시지 저장을 위해, application 에서 msgs 로 저장된 ArrayList 가지고 옴
    	tweetdb = (List<String>)application.getAttribute("tweetdb");
    	
    	// null 인 경우 새로운 ArrayList 객체를 생성
    	if(tweetdb == null) {
    		tweetdb = new ArrayList<String>();
    		// application 에 ArrayList 저장
    		application.setAttribute("tweetdb",tweetdb);
    	}
	}
	
	@Override
	public void write(String msg)  {
    	tweetdb.add(msg);
	}
	
	@Override
	public List<String> getList() {
		return tweetdb;
	}
}
