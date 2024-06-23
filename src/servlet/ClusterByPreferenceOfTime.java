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
import online_info.PreferenceOfTime;

/**
 * Servlet implementation class ClusterByPreferenceOfTime
 */
@WebServlet("/ClusterByPreferenceOfTime")
public class ClusterByPreferenceOfTime extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ClusterService cs = new ClusterService();
		List<List<PreferenceOfTime>> list = cs.clusterPreferenceOfTime();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(list);
		request.setAttribute("json", json);
		request.getRequestDispatcher("/bar.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
