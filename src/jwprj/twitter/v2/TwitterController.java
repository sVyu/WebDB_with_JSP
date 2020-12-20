package jwprj.twitter.v2;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 컨트롤러 호출 url은 /컨텍스트/twitter/v2
@WebServlet(urlPatterns = "/twitter/v2")
public class TwitterController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	// ServletContext 객체 전달 및 로깅을 위해 변수 선언
	private ServletContext application;
	
	// 웹 리소스 기본 경로 지정 
	private final String BASE_DIR = "/twitter/v2/";
	private final String START_PAGE = "twitterLogin.jsp";
	
	// 데이터 서비스 인터페이스
	TwitterService service;
	
	// 서블릿이 초기화될때 서비스 객체 생성
	@Override
	public void init() throws ServletException {
		// 데이터 서비스 선택 지정
		// H2Service를 참조하도록 수정
		service = new H2Service();
		application = getServletContext();
	}

	// 모든 처리를 processRequest 에서 처리하도록 함,, doGet/doPost X
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException{
		processRequest(arg0, arg1);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 한글 인코딩 설정
		request.setCharacterEncoding("utf-8");
		
		// 서블릿 컨텍스트경로
		String ctxPath = request.getContextPath();
		String action = request.getParameter("action");
		
		// action 파라미터 없이 접근한 경우 세션을 초기화 하고 로그인 화면으로 이동
		if (action == null) {
			request.getSession().invalidate();
			response.sendRedirect(ctxPath+BASE_DIR+START_PAGE);
			// 로그인 화면으로 리디렉션후 현재 메서드를 종료
			return;
		}
		
		// 자바 리플렉션을 사용해 if, switch 없이 요청에 따라 구현 메서드가 실행되도록 함.
		String view = null;

		try {
			Method m;
			// 현재 클래스에서 action 이름과 HttpServletRequest 를 파라미터로 하는 메서드 찾음
			m = this.getClass().getMethod(action, HttpServletRequest.class);
			view = (String)m.invoke(this, request);
			// action으로 전달된 이름과 일치하는 메서드가 없는 경우
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			// 에러 로그를 남기고 view 를 로그인 화면으로 지정, 앞에서와 같이 redirection 사용도 가능.
			application.log("요청 action 없음!!");
			request.setAttribute("error", "정상적인 요청이 아닙니다. 다시 로그인 하세요!!");
			view = START_PAGE;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 지정된 뷰로 포워딩
		RequestDispatcher dispatcher = request.getRequestDispatcher(BASE_DIR+view);
		dispatcher.forward(request, response);
	}
	
	// 로그인 처리, User 객체를 사용하는 것으로 수정
	public String login(HttpServletRequest request) {
		User user = new User();
		// 이름과 이메일을 동일한 값으로 저장
		// email은 구글인증에서 가지고 와야하는데 향후 구현 기능이므로 일단은 아래와 같이 처리
		user.setName(request.getParameter("username"));
		user.setEmail(request.getParameter("username"));
		
		// username이 null 이 아닌 경우
		if (user.getName() != "") {
			//세션에 값을 저장
			request.getSession().setAttribute("user", user);
			// 로그인후 게시글 목록을 포함해 목록화면으로 이동해야 하므로 list()메서드 호출 
			return(list(request));
		}
		else {
			request.setAttribute("error", "로그인 아이디를 입력하세요!!");
			return "twitterLogin.jsp";
		}
	}
	
	// 로그아웃 처리
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "twitterLogin.jsp";
	}
	
	// 게시글 목록을 포함해 목록화면으로 이동하는 메서드로 로그인 및 게시글 작성에서 공통으로 호출
	public String list(HttpServletRequest request) {
		List<Tweet> tweetlist = new ArrayList<Tweet>();
		tweetlist = service.getList();
		request.setAttribute("tweetlist", tweetlist);
		return "tweetList.jsp";
	}
	
	// 게시글을 저장하기위한 메서드
	public String tweet(HttpServletRequest request) throws IOException{
		User user = (User) request.getSession().getAttribute("user");
		Tweet tweet = new Tweet();
		String msg = request.getParameter("msg");
		
		// 메시지 없는 경우 에러 처리
		if(msg == "") {
			request.setAttribute("error", "메시지를 입력하세요!!");
			return(list(request));
		}
		
		else {
			// 메시지 저장
			tweet.setMsg(msg);
			// 이름, 날짜 저장
			tweet.setName(user.getName());
			tweet.setEmail(user.getEmail());
			//													연      월    일    시    분    초
			DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			tweet.setDate(LocalDateTime.now().format(f));
			service.write(tweet);

			// 목록 화면 데이터 로딩
			return (list(request));
		}
	}
	
	public String del(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		try {
			service.delTweet(user.getEmail(), Integer.parseInt(request.getParameter("id")));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			request.setAttribute("error", "삭제는 본인만 가능합니다!");
			e.printStackTrace();
		}
		// 목록 화면 데이터 로딩
		return(list(request));
	}
}
