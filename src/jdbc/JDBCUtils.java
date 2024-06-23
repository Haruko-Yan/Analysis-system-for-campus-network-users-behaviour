package jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataConsolidateFunction;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import online_info.OnlineInfo;
/*
 * JDBC工具类
 */
public class JDBCUtils {
	
	private static DataSource dsToOnlineInfo;
	private static DataSource dsToTeacherInfo;
	static {
		try {
			Properties pro1 = new Properties();
			pro1.load(JDBCUtils.class.getClassLoader().getResourceAsStream("druidToOnline_info.properties"));
			dsToOnlineInfo = DruidDataSourceFactory.createDataSource(pro1);
			Properties pro2 = new Properties();
			pro2.load(JDBCUtils.class.getClassLoader().getResourceAsStream("druidToTeacher_info.properties"));
			dsToTeacherInfo = DruidDataSourceFactory.createDataSource(pro2);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static DataSource getDataSourceToOnlineInfo() {
		return dsToOnlineInfo;
	}
	public static DataSource getDataSourceToTeacherInfo() {
		return dsToTeacherInfo;
	}
	/*
	 * 将用户上传的Excel数据存入数据库
	 */
	public static void writeFromExcel(String path) {
		File file = new File(path);
		if (!file.exists()) {
			System.out.println("文件不存在！");
		}
		POIFSFileSystem poifsFileSystem;
		try {
			JdbcTemplate jt = new JdbcTemplate(dsToOnlineInfo);
			String sql = "insert into userinfo(num,start_time,end_time,duration,data_usage,ip_address,"
					+ "international_upstream,international_downstream,domestic_upstream,domestic_downstream) values";
			String cleanSql = "delete from userinfo where duration='0'";
			StringBuilder sb = new StringBuilder(sql);
			poifsFileSystem = new POIFSFileSystem(new FileInputStream(file));
			HSSFWorkbook workbook = new HSSFWorkbook(poifsFileSystem);
			HSSFSheet hssfSheet = workbook.getSheetAt(0);
			int rowLength = hssfSheet.getLastRowNum();
			HSSFRow hssfRow = hssfSheet.getRow(0);
			int colLength = hssfRow.getLastCellNum();
			String info[] = new String[10];
			HSSFCell hssfCell;
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (int i = 1; i < rowLength; i++) {
				//从Excel中提取数据
				hssfRow = hssfSheet.getRow(i);
				for (int j = 0; j < colLength; j++) {
					hssfCell = hssfRow.getCell(j);
					if (hssfCell!=null) {
						hssfCell.setCellType(CellType.STRING);
					}
					//转换Excel里日期的格式
					if(j==1 || j==2) {
						info[j] = sdf2.format(sdf1.parse(hssfCell.getStringCellValue()));
					}
					else {
						info[j] = hssfCell.getStringCellValue();
					}
				}
				//拼接sql语句
				sb.append("('"+info[0]+"','"+info[1]+"','"+info[2]+"','"+info[3]+"','"+info[4]+"','"
				+info[5]+"','"+info[6]+"','"+info[7]+"','"+info[8]+"','"+info[9]+"'),");
			}
			//将拼接好的字符串去掉最后一个逗号
			sql = sb.toString().substring(0,sb.toString().length()-1);
			jt.update(sql);
			jt.update(cleanSql);
			workbook.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * 将数据从数据库取出
	 */
	
	public static List<OnlineInfo> readInfos(){
		JdbcTemplate jt = new JdbcTemplate(dsToOnlineInfo);
		String sql = "select * from test limit 10";
		List<OnlineInfo> list = jt.query(sql, new BeanPropertyRowMapper(OnlineInfo.class));
		return list;
	}
	
	public static Connection getConnection() throws SQLException {
		return dsToOnlineInfo.getConnection();
	}
	
	public void close(Statement sta,Connection con) {
		close(null,sta,con);
	}
	
	public void close(ResultSet rs,Statement sta,Connection con) {
		if (rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (sta!=null) {
			try {
				sta.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	

}
