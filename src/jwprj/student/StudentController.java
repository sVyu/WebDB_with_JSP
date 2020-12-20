package jwprj.student;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/student")
public class StudentController extends HttpServlet {
	private StudentService studentService = new StudentService();
       
	private void processRequest(
		HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
			String studentID = request.getParameter("id");
			if (studentID != null) {
				int id = Integer.parseInt(studentID);
				studentService.getStudent(id)
					.ifPresent(s -> request.setAttribute("student",  s));
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/student/studentInfo.jsp");
			dispatcher.forward(request, response);
	}
	
	/*
    public StudentController() {
        super();
        // TODO Auto-generated constructor stub
    }*/

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

}
