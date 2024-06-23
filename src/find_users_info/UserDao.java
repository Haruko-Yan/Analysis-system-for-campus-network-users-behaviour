package find_users_info;

import java.util.List;
import java.util.Map;

import online_info.OnlineInfo;

public interface UserDao {
	/**
	 * 根据SQL语句查询
	 * @param condition 
	 * @return
	 */
	int findTotalCount(Map<String, String[]> condition);
	List<OnlineInfo> findByPage(int start,int rows, Map<String, String[]> condition);
	List<OnlineInfo> findByNum(Map<String, String[]> condition);
	List<OnlineInfo> findAll();
	int findTotalStudentCount();
	Double findTotalDuration();
	Double findTotalDuration(Map<String, String[]> condition);
	Double findTotalData();
	Double findTotalData(Map<String, String[]> condition);
	Double findCountByNumAndDate();
	Double findCountByNumAndDate(Map<String, String[]> condition);
	List<OnlineInfo> findAllNum();

}
