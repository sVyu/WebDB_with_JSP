package jwprj.news;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NewsController
 */
@WebServlet("/news.nhn")
public class NewsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	NewsService newsService = new NewsService();
	HttpServletRequest request;
	HttpServletResponse response;
	String view;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewsController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	this.request = request;
    	this.response = response;
    	
    	// 사용자 요청을 구분해주기 위한 과정
    	String action = request.getParameter("action");
    	// action이 null인 경우 == controller를 direct로 실행한 경우
    	if(action == null) {
    		action = "list";
    	}
    	
    	// 요청에 따라서 실행할 기능을 분리 가능
    	switch(action) {
    	case "list":
    		listNews();
    		break;
    	case "view":
    		viewNews();
    		break;
    	}
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher(view);
    	dispatcher.forward(request, response);
    }
    
    public void listNews() {
    	List<News> list = newsService.getNewsList();
    	request.setAttribute("newslist", list);
    	view = "/news/newslist.jsp";
    }
    
    public void viewNews() {
    	String aid = request.getParameter("aid");
    	
    	if(aid != null) {
    		newsService.getNews(aid)
    		.ifPresent(s -> request.setAttribute("news", s));
    	}
    	view = "/news/newsview.jsp";
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 어떤 것이 오든 우리는 process에서 처리하겠다는 의미의 코드,,
		processRequest(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}
}
