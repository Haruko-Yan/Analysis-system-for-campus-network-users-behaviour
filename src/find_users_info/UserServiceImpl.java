package find_users_info;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import online_info.OnlineInfo;

public class UserServiceImpl implements UserService {
	UserDao dao = new UserDaoImpl();

	@Override
	public PageBean<OnlineInfo> findUserByPage(String _currentPage, String _rows, Map<String, String[]> condition) {
		int currentPage = Integer.parseInt(_currentPage);
		int rows = Integer.parseInt(_rows);
		
		PageBean<OnlineInfo> pb = new PageBean<OnlineInfo>();
		//设置参数
		pb.setCurrentPage(currentPage);
		pb.setRows(rows);
		int totalCount = dao.findTotalCount(condition);
		pb.setTotalCount(totalCount);
		int start = (currentPage-1) * rows;
		List<OnlineInfo> list = dao.findByPage(start,rows,condition);
		pb.setList(list);
		int totalPage = (totalCount % rows) == 0 ? (totalCount/rows) : (totalCount/rows) + 1;
		pb.setTotalPage(totalPage);
		return pb;
	}
	/**
	 * 个人上网时间偏好
	 */
	@Override
	public int[] preferenceOfTime(Map<String, String[]> condition) {
		int[] hour = new int[24];
		List<OnlineInfo> list = dao.findByNum(condition);
		Date startTime;
		Date endTime;
		int startHour;
		int startDay;
		int endHour;
		int endDay;
		//将数组初始化，元素分别为上条记录结束时的小时与日期
		int[] flag = {-1,-1};
		Calendar calendar = Calendar.getInstance();
		for (OnlineInfo onlineInfo : list) {
			startTime = onlineInfo.getStart_time();
			endTime = onlineInfo.getEnd_time();
			calendar.setTime(startTime);
			startHour = calendar.get(Calendar.HOUR_OF_DAY);
			startDay = calendar.get(calendar.DATE);
			calendar.setTime(endTime);
			endHour = calendar.get(Calendar.HOUR_OF_DAY);
			endDay = calendar.get(calendar.DATE);
			//结束小时数大于等于开始小时数的情况
			if (startHour <= endHour) {
				//当开始与结束小时数相等时
				if (startHour == endHour ) {
					//是同一天
					if (startDay == flag[1]) {
						if (startHour == flag[0]) {
							continue;
						}
						else {
							hour[startHour]++;
							flag[0] = endHour;
							flag[1] = endDay;
						}
					}
					else {
						hour[startHour]++;
						flag[0] = endHour;
						flag[1] = endDay;
					}
				}
				//大于时的情况
				else if (startDay == flag[1]) {
					if (startHour == flag[0]) {
						for (int i = 0; i < endHour-startHour; i++) {
							hour[startHour+1+i]++;
						}
						flag[0] = endHour;
						flag[1] = endDay;
					}
					else {
						for (int i = 0; i < endHour-startHour+1; i++) {
							hour[startHour+i]++;
						}
						flag[0] = endHour;
						flag[1] = endDay;
					}
				}
				else {
					for (int i = 0; i < endHour-startHour+1; i++) {
						hour[startHour+i]++;
					}
					flag[0] = endHour;
					flag[1] = endDay;
				}
			}
			//结束小时数小于开始小时数的情况
			if (startHour > endHour) {
				//是同一天
				if (startDay == flag[1]) {
					if (startHour == flag[0]) {
						for (int i = 0; i < 24-(startHour-endHour); i++) {
							if (startHour + 1 + i > 23) {
								hour[startHour+1+i-24]++;
							}
							else {
								hour[startHour+1+i]++;
							}
						}
						flag[0] = endHour;
						flag[1] = endDay;
					}
					else {
						for (int i = 0; i < 24-(startHour-endHour)+1; i++) {
							if (startHour + i > 23) {
								hour[startHour+i-24]++;
							}
							else {
								hour[startHour+i]++;
							}
						}
						flag[0] = endHour;
						flag[1] = endDay;
					}
				}
				//不是同一天
				else {
					for (int i = 0; i < 24-(startHour-endHour)+1; i++) {
						if (startHour + i > 23) {
							hour[startHour+i-24]++;
						}
						else {
							hour[startHour+i]++;
						}
					}
					flag[0] = endHour;
					flag[1] = endDay;
				}
			}
		}
		return hour;
	}
	/**
	 * 总上网时间偏好
	 */
	@Override
	public Double[] totalPreferenceOfTime() {
		Double[] hour = new Double[24];
		for (int i = 0; i < hour.length; i++) {
			hour[i] = 0.0;
		}
		List<OnlineInfo> list = dao.findAll();
		int totalStudentCount = dao.findTotalStudentCount();
		Date startTime;
		Date endTime;
		int startHour;
		int startDay;
		int endHour;
		int endDay;
		//将数组初始化，元素分别为上条记录结束时的小时与日期
		int[] flag = {-1,-1};
		Calendar calendar = Calendar.getInstance();
		for (OnlineInfo onlineInfo : list) {
			startTime = onlineInfo.getStart_time();
			endTime = onlineInfo.getEnd_time();
			calendar.setTime(startTime);
			startHour = calendar.get(Calendar.HOUR_OF_DAY);
			startDay = calendar.get(calendar.DATE);
			calendar.setTime(endTime);
			endHour = calendar.get(Calendar.HOUR_OF_DAY);
			endDay = calendar.get(calendar.DATE);
			//结束小时数大于等于开始小时数的情况
			if (startHour <= endHour) {
				//当开始与结束小时数相等时
				if (startHour == endHour ) {
					//是同一天
					if (startDay == flag[1]) {
						if (startHour == flag[0]) {
							continue;
						}
						else {
							hour[startHour]++;
							flag[0] = endHour;
							flag[1] = endDay;
						}
					}
					else {
						hour[startHour]++;
						flag[0] = endHour;
						flag[1] = endDay;
					}
				}
				//大于时的情况
				else if (startDay == flag[1]) {
					if (startHour == flag[0]) {
						for (int i = 0; i < endHour-startHour; i++) {
							hour[startHour+1+i]++;
						}
						flag[0] = endHour;
						flag[1] = endDay;
					}
					else {
						for (int i = 0; i < endHour-startHour+1; i++) {
							hour[startHour+i]++;
						}
						flag[0] = endHour;
						flag[1] = endDay;
					}
				}
				else {
					for (int i = 0; i < endHour-startHour+1; i++) {
						hour[startHour+i]++;
					}
					flag[0] = endHour;
					flag[1] = endDay;
				}
			}
			//结束小时数小于开始小时数的情况
			if (startHour > endHour) {
				//是同一天
				if (startDay == flag[1]) {
					if (startHour == flag[0]) {
						for (int i = 0; i < 24-(startHour-endHour); i++) {
							if (startHour + 1 + i > 23) {
								hour[startHour+1+i-24]++;
							}
							else {
								hour[startHour+1+i]++;
							}
						}
						flag[0] = endHour;
						flag[1] = endDay;
					}
					else {
						for (int i = 0; i < 24-(startHour-endHour)+1; i++) {
							if (startHour + i > 23) {
								hour[startHour+i-24]++;
							}
							else {
								hour[startHour+i]++;
							}
						}
						flag[0] = endHour;
						flag[1] = endDay;
					}
				}
				//不是同一天
				else {
					for (int i = 0; i < 24-(startHour-endHour)+1; i++) {
						if (startHour + i > 23) {
							hour[startHour+i-24]++;
						}
						else {
							hour[startHour+i]++;
						}
					}
					flag[0] = endHour;
					flag[1] = endDay;
				}
			}
		}
		//平均上网偏好，保留两位小数
		for (int i = 0; i < 24; i++) {
			String string = new DecimalFormat("#.00").format(hour[i]/totalStudentCount);
			hour[i] = Double.parseDouble(string);
		}
		return hour;
	}
	//平均上网时长
	@Override
	public Double[] averageDuration(Map<String, String[]> condition) {
		Double[] averageDuration = new Double[2];
		if (dao.findTotalDuration(condition) == null || dao.findCountByNumAndDate(condition) == null) {
			return null;
		}
		//总上网时长/总上网天数
		averageDuration[0] = dao.findTotalDuration(condition)/dao.findCountByNumAndDate(condition);//个人
		averageDuration[1] = dao.findTotalDuration()/dao.findCountByNumAndDate();//总体
		String string1 = new DecimalFormat("#.00").format(averageDuration[0]);
		String string2 = new DecimalFormat("#.00").format(averageDuration[1]);
		averageDuration[0] = Double.parseDouble(string1);
		averageDuration[1] = Double.parseDouble(string2);
		return averageDuration;
	}
	//平均上网流量
	@Override
	public Double[] averageDataUsage(Map<String, String[]> condition) {
		Double[] averageDataUsage = new Double[2];
		if (dao.findTotalData(condition) == null || dao.findCountByNumAndDate(condition) == null) {
			return null;
		}
		averageDataUsage[0] = dao.findTotalData(condition)/dao.findCountByNumAndDate(condition);//个人
		averageDataUsage[1] = dao.findTotalData()/dao.findCountByNumAndDate();//总体
		String string1 = new DecimalFormat("#.00").format(averageDataUsage[0]);
		String string2 = new DecimalFormat("#.00").format(averageDataUsage[1]);
		averageDataUsage[0] = Double.parseDouble(string1);
		averageDataUsage[1] = Double.parseDouble(string2);
		return averageDataUsage;
	}

	
	
}
