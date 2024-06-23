package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import login.Teacher;
import login.TeacherDao;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=utf-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Teacher loginTeacher = new Teacher();
		loginTeacher.setUsername(username);
		loginTeacher.setPassword(password);
		TeacherDao dao = new TeacherDao();
		Teacher resultTeacher = dao.login(loginTeacher);
		if (resultTeacher == null) {
			//登录失败
			request.setAttribute("error", "账号或密码输入错误！");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			
		}
		else {
			//登录成功
			System.out.println("登录成功");
			request.setAttribute("resultTeacher", resultTeacher);
			request.getRequestDispatcher("/mainPage.jsp").forward(request, response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
