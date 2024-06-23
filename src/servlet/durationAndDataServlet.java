package servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import find_users_info.UserService;
import find_users_info.UserServiceImpl;

/**
 * Servlet implementation class durationAndData
 */
@WebServlet("/durationAndDataServlet")
public class durationAndDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取条件查询参数
		Map<String,String[]> condition = request.getParameterMap();
		//调用service查询
		UserService service = new UserServiceImpl();
		Double[] averageDuration = service.averageDuration(condition);
		Double[] averageDataUsage = service.averageDataUsage(condition);
		request.setAttribute("averageDuration", averageDuration);
		request.setAttribute("averageDataUsage", averageDataUsage);
		request.getRequestDispatcher("/durationAndData.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
