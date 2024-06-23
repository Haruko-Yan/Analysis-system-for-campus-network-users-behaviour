package find_users_info;

import java.util.Map;

import online_info.OnlineInfo;

public interface UserService {
	/**
	 * 分页条件查询
	 * @param condition 
	 * @param currentPage
	 * @param rows
	 * @return
	 */
	PageBean<OnlineInfo> findUserByPage(String _currentPage,String _rows, Map<String, String[]> condition);
	int [] preferenceOfTime(Map<String, String[]> condition);
	Double [] totalPreferenceOfTime();
	Double [] averageDuration(Map<String, String[]> condition);
	Double [] averageDataUsage(Map<String, String[]> condition);
}
