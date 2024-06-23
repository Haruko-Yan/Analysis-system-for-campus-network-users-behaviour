package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		double a[] = {0.2,1.3,4.8,5.5,3.2};
		double b[] = {1.1,3.4,5.2,4.2,5.1};
		double c[] = {0.8,0.7,2.2,5.3,8.1};
		double d[][] = {a,b,c};
		String json = "{name:'zhangsan',age:3,gender:true}";
		request.setAttribute("json", json);
		request.getRequestDispatcher("/bar.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
