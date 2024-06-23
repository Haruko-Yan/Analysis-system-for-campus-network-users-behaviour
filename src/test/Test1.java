package test;

import java.util.ArrayList;
import java.util.List;

import find_users_info.AnalysisOfRelations;
import find_users_info.InsertIntoNewTable;
import jdbc.JDBCUtils;
import online_info.OnlineInfo;
import online_info.ScoreAndData;
import online_info.ScoreAndDuration;

public class Test1 {

	public static void main(String[] args) {
		
		InsertIntoNewTable insertIntoNewTable = new InsertIntoNewTable();
//		InsertIntoNewTable.tablePreferenceOfTime();
//		insertIntoNewTable.tableDataByWeek();
//		insertIntoNewTable.scoreAndData();
//		insertIntoNewTable.scoreAndDuration();
//		AnalysisOfRelations aor = new AnalysisOfRelations();
//		double a = aor.pearsonCoefficientWithData();
//		System.out.println(a);
		AnalysisOfRelations aor = new AnalysisOfRelations();
//		String lineOfData = "y = "+aor.parameterCalculationWithData()[0]+"x + "+aor.parameterCalculationWithData()[1];
//		String lineOfDuration = "y = "+aor.parameterCalculationWithDuration()[0]+"x + "+aor.parameterCalculationWithDuration()[1];
//		System.out.println(lineOfData);
//		System.out.println(lineOfDuration);
		List<List<?>> list1 = new ArrayList<List<?>>();
		list1.add(aor.findScoreAndData());
		list1.add(aor.findScoreAndDuration());
		ScoreAndDuration a = (ScoreAndDuration) list1.get(1).get(1);
		System.out.println(a.getScore());
	}

}
