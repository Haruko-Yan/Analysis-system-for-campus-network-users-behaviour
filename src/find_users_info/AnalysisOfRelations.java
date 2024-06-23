package find_users_info;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.taglibs.standard.lang.jstl.Literal;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import jdbc.JDBCUtils;
import online_info.OnlineInfo;
import online_info.ScoreAndData;
import online_info.ScoreAndDuration;

public class AnalysisOfRelations {
	static JdbcTemplate jt = new JdbcTemplate(JDBCUtils.getDataSourceToOnlineInfo());
	
	public List<ScoreAndData> findScoreAndData(){
		String sql = "select * from scoreanddata";
		return jt.query(sql, new BeanPropertyRowMapper<ScoreAndData>(ScoreAndData.class));
	}
	public List<ScoreAndDuration> findScoreAndDuration(){
		String sql = "select * from scoreandduration";
		return jt.query(sql, new BeanPropertyRowMapper<ScoreAndDuration>(ScoreAndDuration.class));
	}
	/**
	 * 成绩与流量的皮尔森系数
	 * @return
	 */
	public Double pearsonCoefficientWithData() {
		List<ScoreAndData> list = findScoreAndData();
		double totalScore = 0.0;//成绩总和
		double totalData = 0.0;//流量总和
		double totalSquareScore = 0.0;//成绩平方之和
		double totalSquareData = 0.0;//流量平方之和
		double totalScoreAndData = 0.0;//成绩与流量相乘之和
		int count;//学生数据总条数
		double covariation = 0.0;//协方差
		double scoreStandardVariance;//成绩标准差
		double dataStandardVariance;//流量标准差
		double pearsonCoefficient;//皮尔森系数
		for (ScoreAndData scoreAndData : list) {
			totalScore += scoreAndData.getScore();
			totalData += scoreAndData.getData();
			totalSquareScore += scoreAndData.getScore()*scoreAndData.getScore();
			totalSquareData += scoreAndData.getData()*scoreAndData.getData();
			totalScoreAndData += scoreAndData.getScore()*scoreAndData.getData();
		}
		count = list.size();
		covariation = ((totalScoreAndData/count)-(totalScore/count)*(totalData/count));
		scoreStandardVariance = Math.sqrt((totalSquareScore/count)-(totalScore/count)*(totalScore/count));
		dataStandardVariance = Math.sqrt((totalSquareData/count)-(totalData/count)*(totalData/count));
		pearsonCoefficient = covariation/(scoreStandardVariance*dataStandardVariance);
		String string = new DecimalFormat("#.00").format(pearsonCoefficient);
		pearsonCoefficient = Double.parseDouble(string);
		return pearsonCoefficient;
		
	}
	
	/**
	 * 成绩与上网时长的皮尔森系数
	 * @return
	 */
	public Double pearsonCoefficientWithDuration() {
		List<ScoreAndDuration> list = findScoreAndDuration();
		double totalScore = 0.0;//成绩总和
		double totalDuration = 0.0;//时长总和
		double totalSquareScore = 0.0;//成绩平方之和
		double totalSquareDuration = 0.0;//时长平方之和
		double totalScoreAndDuration = 0.0;//成绩与时长相乘之和
		int count;//学生数据总条数
		double covariation = 0.0;//协方差
		double scoreStandardVariance;//成绩标准差
		double durationStandardVariance;//时长标准差
		double pearsonCoefficient;//皮尔森系数
		for (ScoreAndDuration scoreAndDuration : list) {
			totalScore += scoreAndDuration.getScore();
			totalDuration += scoreAndDuration.getDuration();
			totalSquareScore += scoreAndDuration.getScore()*scoreAndDuration.getScore();
			totalSquareDuration += scoreAndDuration.getDuration()*scoreAndDuration.getDuration();
			totalScoreAndDuration += scoreAndDuration.getScore()*scoreAndDuration.getDuration();
		}
		
		count = list.size();
		covariation = ((totalScoreAndDuration/count)-(totalScore/count)*(totalDuration/count));
		scoreStandardVariance = Math.sqrt((totalSquareScore/count)-(totalScore/count)*(totalScore/count));
		durationStandardVariance = Math.sqrt((totalSquareDuration/count)-(totalDuration/count)*(totalDuration/count));
		pearsonCoefficient = covariation/(scoreStandardVariance*durationStandardVariance);
		String string = new DecimalFormat("#.00").format(pearsonCoefficient);
		pearsonCoefficient = Double.parseDouble(string);
		return pearsonCoefficient;
		
	}
	
	/**
	 * 成绩与流量的回归直线方程的系数a和b（y = ax + b）
	 * 	x (流量)
		y (成绩)
	 * @return
	 */
	public Double[] parameterCalculationWithData() {
		Double[] parameter = new Double[2];
		double a;//系数a；
		double b;//系数b
		double averageX = 0;//流量平均值
		double averageY = 0;//成绩平均值
		double sum1 = 0;//x与y相乘之和
		double sum2 = 0;//x平方之和
		int count;//学生数据总条数
		List<ScoreAndData> list = findScoreAndData();
		count = list.size();
		for (ScoreAndData scoreAndData : list) {
			averageX += scoreAndData.getData();
			averageY += scoreAndData.getScore();
			sum1 += scoreAndData.getData()*scoreAndData.getScore();
			sum2 += scoreAndData.getData()*scoreAndData.getData();
		}
		averageX = averageX/count;
		averageY = averageY/count;
		a = (sum1-count*averageX*averageY)/(sum2-count*averageX*averageX);
		b = averageY-a*averageX;
		String string1 = new DecimalFormat("#.00").format(a);
		String string2 = new DecimalFormat("#.00").format(b);
		a = Double.parseDouble(string1);
		b = Double.parseDouble(string2);
		parameter[0] = a;
		parameter[1] = b;
		return parameter;
		
	}
	
	/**
	 成绩与上网时长的回归直线方程的系数a和b（y = ax + b）
	 * 	x (流量)
		y (上网时长)
	 * @return
	 */
	public Double[] parameterCalculationWithDuration() {
		Double[] parameter = new Double[2];
		double a;//系数a；
		double b;//系数b
		double averageX = 0;//流量平均值
		double averageY = 0;//上网时长平均值
		double sum1 = 0;//x与y相乘之和
		double sum2 = 0;//x平方之和
		int count;//学生数据总条数
		List<ScoreAndDuration> list = findScoreAndDuration();
		count = list.size();
		for (ScoreAndDuration scoreAndDuration : list) {
			averageX += scoreAndDuration.getDuration();
			averageY += scoreAndDuration.getScore();
			sum1 += scoreAndDuration.getDuration()*scoreAndDuration.getScore();
			sum2 += scoreAndDuration.getDuration()*scoreAndDuration.getDuration();
		}
		averageX = averageX/count;
		averageY = averageY/count;
		a = (sum1-count*averageX*averageY)/(sum2-count*averageX*averageX);
		b = averageY-a*averageX;
		String string1 = new DecimalFormat("#.00").format(a);
		String string2 = new DecimalFormat("#.00").format(b);
		a = Double.parseDouble(string1);
		b = Double.parseDouble(string2);
		parameter[0] = a;
		parameter[1] = b;
		return parameter;
		
	}
	
	/**
	 * 根据两个点画成绩与流量回归线
	 * @return
	 */
	public List<Double[]> examplePointsForData(){
		double x1 = 0;
		double x2 = 3000;
		Double[] paramter = parameterCalculationWithData();//获取回归线的a和b
		double y1 = paramter[0]*x1 + paramter[1];
		double y2 = paramter[0]*x2 + paramter[1];
		Double[] point1 = {x1,y1};
		Double[] point2 = {x2,y2};
		List<Double[]> list = new ArrayList<Double[]>();
		list.add(point1);
		list.add(point2);
		return list;
	}
	
	public List<Double[]> examplePointsForDuration(){
		double x1 = 0;
		double x2 = 1200;
		Double[] paramter = parameterCalculationWithDuration();//获取回归线的a和b
		double y1 = paramter[0]*x1 + paramter[1];
		double y2 = paramter[0]*x2 + paramter[1];
		Double[] point1 = {x1,y1};
		Double[] point2 = {x2,y2};
		List<Double[]> list = new ArrayList<Double[]>();
		list.add(point1);
		list.add(point2);
		return list;
	}
}
