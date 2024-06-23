package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import find_users_info.AnalysisOfRelations;

/**
 * Servlet implementation class AnalysisOfRelation
 */
@WebServlet("/AnalysisOfRelationServlet")
public class AnalysisOfRelationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		AnalysisOfRelations aor = new AnalysisOfRelations();
		//回归线
		String lineOfData = "y = "+aor.parameterCalculationWithData()[0]+"x + "+aor.parameterCalculationWithData()[1];
		String lineOfDuration = "y = "+aor.parameterCalculationWithDuration()[0]+"x + "+aor.parameterCalculationWithDuration()[1];
		String[] line = {lineOfData,lineOfDuration};
		String lines = mapper.writeValueAsString(line);
		//两个数据集的数据
		List<List<?>> list1 = new ArrayList<List<?>>();
		list1.add(aor.findScoreAndData());
		list1.add(aor.findScoreAndDuration());
		String dataSet = mapper.writeValueAsString(list1);
		//回归线上的两个点
		List<List<Double[]>> list2 = new ArrayList<List<Double[]>>();
		list2.add(aor.examplePointsForData());
		list2.add(aor.examplePointsForDuration());
		String twoPoints = mapper.writeValueAsString(list2);
		//相关系数
		Double[] coefficients = {aor.pearsonCoefficientWithData(),aor.pearsonCoefficientWithDuration()};
		//返回各个属性
		request.setAttribute("lines", lines);
		request.setAttribute("dataSet", dataSet);
		request.setAttribute("twoPoints", twoPoints);
		request.setAttribute("coefficients", coefficients);
		request.getRequestDispatcher("/analysisOfRelation.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
