package servlet;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import find_users_info.PageBean;
import find_users_info.UserService;
import find_users_info.UserServiceImpl;
import online_info.OnlineInfo;

/**
 * Servlet implementation class FindUsersByPageServlet
 */
@WebServlet("/FindUserByPageServlet")
public class FindUserByPageServlet extends HttpServlet {

	/**
	 * 分页显示查询结果
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.获取参数
		String currentPage = request.getParameter("currentPage");//当前页码
		String rows = request.getParameter("rows");//每页显示条数
		if (currentPage==null||"".equals(currentPage)) {
			currentPage="1";
		}
		if (rows==null||"".equals(rows)){
			rows="500";
		}
		//获取条件查询参数
		Map<String,String[]> condition = request.getParameterMap();
		//2.调用service查询
		UserService service = new UserServiceImpl();
		PageBean<OnlineInfo> pb = service.findUserByPage(currentPage,rows,condition);
		//3.将Pagebean传入request
		request.setAttribute("pb",pb);
		//4.转发到query.jsp
		request.getRequestDispatcher("/query.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
