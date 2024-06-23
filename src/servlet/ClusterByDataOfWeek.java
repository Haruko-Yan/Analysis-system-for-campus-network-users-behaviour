package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import cluster.ClusterService;
import online_info.DataByWeek;

/**
 * Servlet implementation class ClusterByDataOfWeek
 */
@WebServlet("/ClusterByDataOfWeek")
public class ClusterByDataOfWeek extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sizeString = request.getParameter("valueK");
		int size = Integer.parseInt(sizeString);
		ClusterService cs = new ClusterService();
		List<List<DataByWeek>> list = cs.clusterDataByWeek(size);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(list);
		request.setAttribute("json", json);
		request.setAttribute("valueK", size);
		request.getRequestDispatcher("/cluster.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
