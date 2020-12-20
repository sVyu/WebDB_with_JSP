package jwprj.twitter;

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

// 컨트롤러 호출 url은 /컨텍스트/twitter
@WebServlet(urlPatterns = "/twitter")
public class TwitterController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	// ServletContext 객체 전달 및 로깅을 위해 변수 선언
	private ServletContext application;
	
	// 웹 리소스 기본 경로 지정 
	private final String BASE_DIR = "/twitter/";
	private final String START_PAGE = "twitterLogin.jsp";
	
	// 데이터 서비스 인터페이스
	TwitterService service;
	
	// 서블릿이 초기화될때 서비스 객체 생성
	@Override
	public void init() throws ServletException {
		// 데이터 서비스 선택 지정
		// Error ?
		service = new EmbeddedService(getServletContext());
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
		} catch(Exception e){
			e.printStackTrace();
		}
		
		/*
		// 메서드 실행후 리턴값 받아옴
		try {
			view = (String)m.invoke(this, request);
		} catch (IllegalAccessException e) {
			application.log("IllegalAccessException!!");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			application.log("IllegalArgumentException!!");
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			application.log("InvocationTargetException!!");
			e.printStackTrace();
		}*/
		
		// 지정된 뷰로 포워딩
		RequestDispatcher dispatcher = request.getRequestDispatcher(BASE_DIR+view);
		dispatcher.forward(request, response);
	}
	
	// 로그인 처리
	public String login(HttpServletRequest request) {
		// HTML 폼에서 username으로 전달된 값을 가지고 옴
		String username = request.getParameter("username");
		
		// username이 입력된 경우에만 세션에 값을 저장
		if (username != "") {
			request.getSession().setAttribute("user", username);
		} else {
			request.setAttribute("error", "로그인 아이디를 입력하세요!!");
			return "twitterLogin.jsp";
		}
		// 로그인후 게시글 목록을 포함해 목록화면으로 이동해야 하므로 list()메서드 호출 
		return(list(request));
	}
	
	// 로그아웃 처리
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "twitterLogin.jsp";
	}
	
	// 게시글 목록을 포함해 목록화면으로 이동하는 메서드로 로그인 및 게시글 작성에서 공통으로 호출
	public String list(HttpServletRequest request) {
		List<String> tweetlist = new ArrayList<String>();
		tweetlist = service.getList();
		request.setAttribute("tweetlist", tweetlist);
		return "tweetList.jsp";
	}
	// 게시글을 저장하기위한 메서드로 입력한 메시지에 이름, 날짜등으로 메시지를 조합하고 application scope 에 저장후 list() 호출
	public String tweet(HttpServletRequest request) throws IOException {
		// HTML 폼에서 전달된 msg 값을 가지고 옴
		String msg = request.getParameter("msg");

		// 메시지 없는 경우 에러 처리
		if(msg == "") {
			request.setAttribute("error", "메시지를 입력하세요!!");
			return(list(request));
		}
		
		// 세션에 저장된 로그인 사용자 이름을 가지고 옴
		String username = (String) request.getSession().getAttribute("user");

		// 사용자 이름, 메시지, 날짜 정보를 포함하여 ArrayList에 추가
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		msg = username + " :: " + msg + " , " + date.format(f);

		service.write(msg);

		// 톰캣 콘솔을 통한 로깅
		application.log(msg + "추가됨");

		// 목록 화면 데이터 로딩
		return(list(request));
	}
}
