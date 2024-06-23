package find_users_info;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.midi.VoiceStatus;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import jdbc.JDBCUtils;
import online_info.DataByWeek;
import online_info.OnlineInfo;
import sun.security.x509.AVA;

public class InsertIntoNewTable {
	private static JdbcTemplate jt = new JdbcTemplate(JDBCUtils.getDataSourceToOnlineInfo());
	/**
	 * 插入数据到上网时间偏好表
	 */
	public static void tablePreferenceOfTime() {
		String deleteSql = "delete from preferenceoftime";
		int hour[] = new int[24];
		String sql1 = " select * from userinfo group by num ";
		List<OnlineInfo> list = jt.query(sql1, new BeanPropertyRowMapper<OnlineInfo>(OnlineInfo.class));
		String num[] = new String[list.size()];
		String sql2 = "insert into preferenceOfTime(num,a0,a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,"
				+ "a16,a17,a18,a19,a20,a21,a22,a23) values ";
		StringBuilder sb = new StringBuilder(sql2);
		for (int i = 0; i < list.size(); i++) {
			num[i] = list.get(i).getNum();
			hour = preferenceOfTime(num[i]);
			sb.append("('"+num[i]+"','"+hour[0]+"','"+hour[1]+"','"+hour[2]+"','"+hour[3]+"','"+hour[4]+"',"
					+ "'"+hour[5]+"','"+hour[6]+"','"+hour[7]+"','"+hour[8]+"','"+hour[9]+"','"+hour[10]+"',"
					+ "'"+hour[11]+"','"+hour[12]+"','"+hour[13]+"','"+hour[14]+"','"+hour[15]+"','"+hour[16]+"',"
					+ "'"+hour[17]+"','"+hour[18]+"','"+hour[19]+"','"+hour[20]+"','"+hour[21]+"','"+hour[22]+"',"
					+ "'"+hour[23]+"'),");
		}
		//将拼接好的字符串去掉最后一个逗号
		String sql3 = sb.toString().substring(0,sb.toString().length()-1);
		jt.update(deleteSql);
		jt.update(sql3);
		
	}
	/**
	 * 得到单个学生上网时间偏好
	 * @param num
	 * @return
	 */
	public static int[] preferenceOfTime(String num) {
		int[] hour = new int[24];
		String sql = "select * from userinfo where num = ? ";
		StringBuilder sb = new StringBuilder(sql);
		sb.append(" order by num asc,start_time asc ");
		List<OnlineInfo> list = jt.query(sb.toString(), new BeanPropertyRowMapper<OnlineInfo>(OnlineInfo.class),num);
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
	 * 插入数据到一周流量表
	 */
	public void tableDataByWeek() {
		String deleteSql = "delete from databyweek";
		double[] dataOfWeek = new double[168];
		String sql1 = " select * from userinfo group by num ";
		List<OnlineInfo> list = jt.query(sql1, new BeanPropertyRowMapper<OnlineInfo>(OnlineInfo.class));
		String num[] = new String[list.size()];
		String sql2 = "insert into dataByWeek(num,mon0,mon1,mon2,mon3,mon4,mon5,mon6,mon7,mon8,mon9,mon10,mon11,mon12,mon13,mon14,mon15,mon16,mon17,mon18,mon19,mon20,mon21,mon22,mon23,"+
				"tue0,tue1,tue2,tue3,tue4,tue5,tue6,tue7,tue8,tue9,tue10,tue11,tue12,tue13,tue14,tue15,tue16,tue17,tue18,tue19,tue20,tue21,tue22,tue23,"+
				"wed0,wed1,wed2,wed3,wed4,wed5,wed6,wed7,wed8,wed9,wed10,wed11,wed12,wed13,wed14,wed15,wed16,wed17,wed18,wed19,wed20,wed21,wed22,wed23,"+
				"thu0,thu1,thu2,thu3,thu4,thu5,thu6,thu7,thu8,thu9,thu10,thu11,thu12,thu13,thu14,thu15,thu16,thu17,thu18,thu19,thu20,thu21,thu22,thu23,"+
				"fri0,fri1,fri2,fri3,fri4,fri5,fri6,fri7,fri8,fri9,fri10,fri11,fri12,fri13,fri14,fri15,fri16,fri17,fri18,fri19,fri20,fri21,fri22,fri23,"+
				"sat0,sat1,sat2,sat3,sat4,sat5,sat6,sat7,sat8,sat9,sat10,sat11,sat12,sat13,sat14,sat15,sat16,sat17,sat18,sat19,sat20,sat21,sat22,sat23,"+
				"sun0,sun1,sun2,sun3,sun4,sun5,sun6,sun7,sun8,sun9,sun10,sun11,sun12,sun13,sun14,sun15,sun16,sun17,sun18,sun19,sun20,sun21,sun22,sun23) values ";
		StringBuilder sb = new StringBuilder(sql2);
		for (int i = 0; i < list.size(); i++) {
			num[i] = list.get(i).getNum();
			dataOfWeek = dataByWeek(num[i]);
			sb.append("('"+num[i]+"','"+dataOfWeek[0]+"','"+dataOfWeek[1]+"','"+dataOfWeek[2]+"','"+dataOfWeek[3]+"','"+dataOfWeek[4]+"',"
					+ "'"+dataOfWeek[5]+"','"+dataOfWeek[6]+"','"+dataOfWeek[7]+"','"+dataOfWeek[8]+"','"+dataOfWeek[9]+"','"+dataOfWeek[10]+"',"
					+ "'"+dataOfWeek[11]+"','"+dataOfWeek[12]+"','"+dataOfWeek[13]+"','"+dataOfWeek[14]+"','"+dataOfWeek[15]+"','"+dataOfWeek[16]+"',"
					+ "'"+dataOfWeek[17]+"','"+dataOfWeek[18]+"','"+dataOfWeek[19]+"','"+dataOfWeek[20]+"','"+dataOfWeek[21]+"','"+dataOfWeek[22]+"',"
					+ "'"+dataOfWeek[23]+"','"+dataOfWeek[24]+"','"+dataOfWeek[25]+"','"+dataOfWeek[26]+"','"+dataOfWeek[27]+"','"+dataOfWeek[28]+"',"
					+ "'"+dataOfWeek[29]+"','"+dataOfWeek[30]+"','"+dataOfWeek[31]+"','"+dataOfWeek[32]+"','"+dataOfWeek[33]+"','"+dataOfWeek[34]+"',"
					+ "'"+dataOfWeek[35]+"','"+dataOfWeek[36]+"','"+dataOfWeek[37]+"','"+dataOfWeek[38]+"','"+dataOfWeek[39]+"','"+dataOfWeek[40]+"',"
					+ "'"+dataOfWeek[41]+"','"+dataOfWeek[42]+"','"+dataOfWeek[43]+"','"+dataOfWeek[44]+"','"+dataOfWeek[45]+"','"+dataOfWeek[46]+"',"
					+ "'"+dataOfWeek[47]+"','"+dataOfWeek[48]+"','"+dataOfWeek[49]+"','"+dataOfWeek[50]+"','"+dataOfWeek[51]+"','"+dataOfWeek[52]+"',"
					+ "'"+dataOfWeek[53]+"','"+dataOfWeek[54]+"','"+dataOfWeek[55]+"','"+dataOfWeek[56]+"','"+dataOfWeek[57]+"','"+dataOfWeek[58]+"',"
					+ "'"+dataOfWeek[59]+"','"+dataOfWeek[60]+"','"+dataOfWeek[61]+"','"+dataOfWeek[62]+"','"+dataOfWeek[63]+"','"+dataOfWeek[64]+"',"
					+ "'"+dataOfWeek[65]+"','"+dataOfWeek[66]+"','"+dataOfWeek[67]+"','"+dataOfWeek[68]+"','"+dataOfWeek[69]+"','"+dataOfWeek[70]+"',"
					+ "'"+dataOfWeek[71]+"','"+dataOfWeek[72]+"','"+dataOfWeek[73]+"','"+dataOfWeek[74]+"','"+dataOfWeek[75]+"','"+dataOfWeek[76]+"',"
					+ "'"+dataOfWeek[77]+"','"+dataOfWeek[78]+"','"+dataOfWeek[79]+"','"+dataOfWeek[80]+"','"+dataOfWeek[81]+"','"+dataOfWeek[82]+"',"
					+ "'"+dataOfWeek[83]+"','"+dataOfWeek[84]+"','"+dataOfWeek[85]+"','"+dataOfWeek[86]+"','"+dataOfWeek[87]+"','"+dataOfWeek[88]+"',"
					+ "'"+dataOfWeek[89]+"','"+dataOfWeek[90]+"','"+dataOfWeek[91]+"','"+dataOfWeek[92]+"','"+dataOfWeek[93]+"','"+dataOfWeek[94]+"',"
					+ "'"+dataOfWeek[95]+"','"+dataOfWeek[96]+"','"+dataOfWeek[97]+"','"+dataOfWeek[98]+"','"+dataOfWeek[99]+"','"+dataOfWeek[100]+"',"
					+ "'"+dataOfWeek[101]+"','"+dataOfWeek[102]+"','"+dataOfWeek[103]+"','"+dataOfWeek[104]+"',"
					+ "'"+dataOfWeek[105]+"','"+dataOfWeek[106]+"','"+dataOfWeek[107]+"','"+dataOfWeek[108]+"','"+dataOfWeek[109]+"','"+dataOfWeek[110]+"',"
					+ "'"+dataOfWeek[111]+"','"+dataOfWeek[112]+"','"+dataOfWeek[113]+"','"+dataOfWeek[114]+"','"+dataOfWeek[115]+"','"+dataOfWeek[116]+"',"
					+ "'"+dataOfWeek[117]+"','"+dataOfWeek[118]+"','"+dataOfWeek[119]+"','"+dataOfWeek[120]+"','"+dataOfWeek[121]+"','"+dataOfWeek[122]+"',"
					+ "'"+dataOfWeek[123]+"','"+dataOfWeek[124]+"','"+dataOfWeek[125]+"','"+dataOfWeek[126]+"','"+dataOfWeek[127]+"','"+dataOfWeek[128]+"',"
					+ "'"+dataOfWeek[129]+"','"+dataOfWeek[130]+"','"+dataOfWeek[131]+"','"+dataOfWeek[132]+"','"+dataOfWeek[133]+"','"+dataOfWeek[134]+"',"
					+ "'"+dataOfWeek[135]+"','"+dataOfWeek[136]+"','"+dataOfWeek[137]+"','"+dataOfWeek[138]+"','"+dataOfWeek[139]+"','"+dataOfWeek[140]+"',"
					+ "'"+dataOfWeek[141]+"','"+dataOfWeek[142]+"','"+dataOfWeek[143]+"','"+dataOfWeek[144]+"','"+dataOfWeek[145]+"','"+dataOfWeek[146]+"',"
					+ "'"+dataOfWeek[147]+"','"+dataOfWeek[148]+"','"+dataOfWeek[149]+"','"+dataOfWeek[150]+"','"+dataOfWeek[151]+"','"+dataOfWeek[152]+"',"
					+ "'"+dataOfWeek[153]+"','"+dataOfWeek[154]+"','"+dataOfWeek[155]+"','"+dataOfWeek[156]+"','"+dataOfWeek[157]+"','"+dataOfWeek[158]+"',"
					+ "'"+dataOfWeek[159]+"','"+dataOfWeek[160]+"','"+dataOfWeek[161]+"','"+dataOfWeek[162]+"','"+dataOfWeek[163]+"','"+dataOfWeek[164]+"',"
					+ "'"+dataOfWeek[165]+"','"+dataOfWeek[166]+"','"+dataOfWeek[167]+"'),");
		}
		//将拼接好的字符串去掉最后一个逗号
		String sql3 = sb.toString().substring(0,sb.toString().length()-1);
		jt.update(deleteSql);
		jt.update(sql3);
		System.out.println("success");
	}
	

	/**
	 * 得到单个学生的一周流量分布
	 * @param num
	 * @return
	 */
	public static double[] dataByWeek(String num) {
		double[] dataOfWeek = new double[168];
		String sql = "select * from userinfo where num = ? and start_time >= 2015-10-03 order by num asc,start_time asc";
		List<OnlineInfo> list = jt.query(sql, new BeanPropertyRowMapper<OnlineInfo>(OnlineInfo.class),num);
		double data;
		int duration;
		double averageData;
		Date startTime;
		Date endTime;
		int startDay;
		int endDay;
		int startHour;
		int endHour;
		int startMinute;
		int endMinute;
		int startDayOfWeek;
		int endDayOfWeek;
		Calendar calendar = Calendar.getInstance();
		for (OnlineInfo onlineInfo : list) {
			data = Double.parseDouble(onlineInfo.getData_usage());
			duration = Integer.parseInt(onlineInfo.getDuration());
			averageData = data / duration;
			startTime = onlineInfo.getStart_time();
			endTime = onlineInfo.getEnd_time();
			//获取开始日期信息
			calendar.setTime(startTime);
			startDay = calendar.get(Calendar.DATE);
			startHour = calendar.get(Calendar.HOUR_OF_DAY);
			startMinute = calendar.get(Calendar.MINUTE);
			if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
				startDayOfWeek = 7;
			}
			else {
				startDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			}
			//获取结束日期信息
			calendar.setTime(endTime);
			endDay = calendar.get(Calendar.DATE);
			endHour = calendar.get(Calendar.HOUR_OF_DAY);
			endMinute = calendar.get(Calendar.MINUTE);
			if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
				endDayOfWeek = 7;
			}
			else {
				endDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			}
			//日期一样时
			if (startDay == endDay) {
				if (startHour == endHour) {
					dataOfWeek[startHour+(startDayOfWeek-1)*24] += (endMinute-startMinute)*averageData;
				}
				else {
					dataOfWeek[startHour+(startDayOfWeek-1)*24] += (60-startMinute)*averageData;//开始小时数的流量
					dataOfWeek[endHour+(startDayOfWeek-1)*24] += endMinute*averageData;//结束小时数的流量
					for (int i = 0; i < endHour-startHour-1; i++) {
						//中间的流量
						dataOfWeek[startHour+1+i+(startDayOfWeek-1)*24] += 60*averageData;
					}
				}
			}
			//日期不一样时
			if (startDay != endDay) {
				//开始小时到23点的流量
				dataOfWeek[startHour+(startDayOfWeek-1)*24] += (60-startMinute)*averageData;
				for (int i = 0; i < 23-startHour; i++) {
					dataOfWeek[startHour+1+i+(startDayOfWeek-1)*24] += 60*averageData;
				}
				//处理结束日-开始日>1的情况
				for (int i = 0; i < (endDay-startDay-1)*24 && i < 168; i++) {
					if (startHour+1+i+(startDayOfWeek-1)*24 > 167) {
						dataOfWeek[i] += 60*averageData;
					}
					else {
						dataOfWeek[startHour+1+i+(startDayOfWeek-1)*24] += 60*averageData;
					}
					
				}
				//最后一天0点到结束小时的流量
				for (int i = 0; i < endHour; i++) {
					dataOfWeek[i+(endDayOfWeek-1)*24] += 60*averageData;
				}
				dataOfWeek[endHour+(endDayOfWeek-1)*24] += 60*averageData;
			}
		}
		
		return dataOfWeek;
	}
	/**
	 * 成绩与流量（相关性分析）
	 */
	public void scoreAndData() {
		String deleteSql = "delete from scoreanddata";
		jt.update(deleteSql);
		File file = new File("C:\\Users\\hasee\\Desktop\\校园网用户数据\\新建文件夹\\2014成绩（改）.xls");
		Map<String, String[]> num = new HashMap<String, String[]>();
		try {
			POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream(file));
			HSSFWorkbook workbook = new HSSFWorkbook(poifsFileSystem);
			HSSFSheet hssfSheet = workbook.getSheetAt(0);
			int rowLength = hssfSheet.getLastRowNum();
			HSSFRow hssfRow;
			HSSFCell hssfCell;
			String[] string = new String[1];
			Double averageDataUsage[] = new Double[2];
			double sum = 0;
			int count = 0;
			double score;
			String sql;
			next:for (int i = 4; i < rowLength; i++) {
				hssfRow = hssfSheet.getRow(i);
				for (int j = 0; j < 17; j++) {
					hssfCell = hssfRow.getCell(j);
					if (hssfCell == null) {
						continue;
					}
					else if(j == 0){
						//将字符串变成字符串数组，目的是匹配之前查询流量与时长的方法
						hssfCell.setCellType(CellType.STRING);
						string[0] = hssfCell.getStringCellValue();
						num.put("num", string);
						UserService us = new UserServiceImpl();
						averageDataUsage = us.averageDataUsage(num);
						if (averageDataUsage == null) {
							continue next;
						}
					}
					else {
						hssfCell.setCellType(CellType.STRING);
						sum += Double.parseDouble(hssfCell.getStringCellValue());
						count++;
					}
				}
				score = sum/count;
				sql = "insert into scoreanddata (num,score,data) values('"+string[0]+"','"+score+"','"
						+averageDataUsage[0]+"')";
				jt.update(sql);
				//重置sum与count，进行下一行的累加
				count = 0;
				sum = 0;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 成绩与上网时长（相关性分析）
	 */
	public void scoreAndDuration() {
		String deleteSql = "delete from scoreandduration";
		jt.update(deleteSql);
		File file = new File("C:\\Users\\hasee\\Desktop\\校园网用户数据\\新建文件夹\\2014成绩（改）.xls");
		Map<String, String[]> num = new HashMap<String, String[]>();
		try {
			POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream(file));
			HSSFWorkbook workbook = new HSSFWorkbook(poifsFileSystem);
			HSSFSheet hssfSheet = workbook.getSheetAt(0);
			int rowLength = hssfSheet.getLastRowNum();
			HSSFRow hssfRow;
			HSSFCell hssfCell;
			String[] string = new String[1];
			Double averageDuration[] = new Double[2];
			double sum = 0;
			int count = 0;
			double score;
			String sql;
			next:for (int i = 4; i < rowLength; i++) {
				hssfRow = hssfSheet.getRow(i);
				for (int j = 0; j < 17; j++) {
					hssfCell = hssfRow.getCell(j);
					if (hssfCell == null) {
						continue;
					}
					else if(j == 0){
						//将字符串变成字符串数组，目的是匹配之前查询流量与时长的方法
						hssfCell.setCellType(CellType.STRING);
						string[0] = hssfCell.getStringCellValue();
						num.put("num", string);
						UserService us = new UserServiceImpl();
						averageDuration = us.averageDuration(num);
						if (averageDuration == null) {
							continue next;
						}
					}
					else {
						hssfCell.setCellType(CellType.STRING);
						sum += Double.parseDouble(hssfCell.getStringCellValue());
						count++;
					}
				}
				score = sum/count;
				sql = "insert into scoreandduration (num,score,duration) values('"+string[0]+"','"+score+"','"
						+averageDuration[0]+"')";
				jt.update(sql);
				//重置sum与count，进行下一行的累加
				count = 0;
				sum = 0;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
