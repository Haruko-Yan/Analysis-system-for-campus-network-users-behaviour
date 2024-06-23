package find_users_info;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import jdbc.JDBCUtils;
import online_info.OnlineInfo;

public class UserDaoImpl implements UserDao {
	JdbcTemplate jt = new JdbcTemplate(JDBCUtils.getDataSourceToOnlineInfo());
	//查询数据库userinfo中的总记录数
	@Override
	public int findTotalCount(Map<String, String[]> condition) {
		String sql = "select count(*) from userinfo where 1=1";
		StringBuilder sb = new StringBuilder(sql);
		Set<String> keySet = condition.keySet();
		List<Object> params = new ArrayList<Object>();
		//遍历条件集合，将条件加到SQL语句中
		for (String key : keySet) {
			String value = condition.get(key)[0];
			if ("num".equals(key) && value!=null && !"".equals(value)) {
				sb.append(" and num = ? ");
				params.add(value);
			}
			if ("start_time".equals(key) && value!=null && !"".equals(value)) {
				sb.append(" and start_time >= ? ");
				params.add(value);
			}
			if ("end_time".equals(key) && value!=null && !"".equals(value)) {
				sb.append(" and start_time <= ? ");
				params.add(value);
			}
		}
		return jt.queryForObject(sb.toString(), Integer.class, params.toArray());
	}
	//根据条件查找数据库userinfo中的记录，实现分页查询
	@Override
	public List<OnlineInfo> findByPage(int start, int rows, Map<String, String[]> condition) {
		String sql = "select * from userinfo where 1=1 ";
		StringBuilder sb = new StringBuilder(sql);
		Set<String> keySet = condition.keySet();
		List<Object> params = new ArrayList<Object>();
		for (String key : keySet) {
			String value = condition.get(key)[0];
			if ("num".equals(key) && value!=null && !"".equals(value)) {
				sb.append(" and num = ? ");
				params.add(value);
			}
			if ("start_time".equals(key) && value!=null && !"".equals(value)) {
				sb.append(" and start_time >= ? ");
				params.add(value);
			}
			if ("end_time".equals(key) && value!=null && !"".equals(value)) {
				sb.append(" and start_time <= ? ");
				params.add(value);
			}
		}
		sb.append(" order by num asc,start_time asc limit ? , ? ");
		params.add(start);
		params.add(rows);
		return jt.query(sb.toString(), new BeanPropertyRowMapper<OnlineInfo>(OnlineInfo.class),params.toArray());
	}
	//根据条件查找数据库userinfo中的记录
	@Override
	public List<OnlineInfo> findByNum(Map<String, String[]> condition) {
		String sql = "select * from userinfo where 1=1 ";
		StringBuilder sb = new StringBuilder(sql);
		Set<String> keySet = condition.keySet();
		List<Object> params = new ArrayList<Object>();
		for (String key : keySet) {
			String value = condition.get(key)[0];
			if ("num".equals(key) && value!=null && !"".equals(value)) {
				sb.append(" and num = ? ");
				params.add(value);
			}
		}
		sb.append(" order by num asc,start_time asc ");
		return jt.query(sb.toString(), new BeanPropertyRowMapper<OnlineInfo>(OnlineInfo.class),params.toArray());
	}
	//数据库userinfo中的所有记录
	@Override
	public List<OnlineInfo> findAll() {
		String sql = "select * from userinfo order by num asc,start_time asc ";
		return jt.query(sql, new BeanPropertyRowMapper<OnlineInfo>(OnlineInfo.class));
	}
	//数据库userinfo中的学生总数
	@Override
	public int findTotalStudentCount() {
		String sql = "select count(*) from (select * from userinfo group by num) userinfo";
		return jt.queryForObject(sql.toString(), Integer.class);
	}
	//数据库userinfo中的上网总时长
	@Override
	public Double findTotalDuration() {
		String sql = "select sum(duration) from userinfo";
		return jt.queryForObject(sql.toString(), Double.class);
	}
	//数据库userinfo中的单个学生上网总时长
	@Override
	public Double findTotalDuration(Map<String, String[]> condition) {
		String sql = "select sum(duration) from userinfo where 1=1 ";
		StringBuilder sb = new StringBuilder(sql);
		Set<String> keySet = condition.keySet();
		List<Object> params = new ArrayList<Object>();
		//遍历条件集合，将条件加到SQL语句中
		for (String key : keySet) {
			String value = condition.get(key)[0];
			if ("num".equals(key) && value!=null && !"".equals(value)) {
				sb.append(" and num = ? ");
				params.add(value);
			}
		}
		return jt.queryForObject(sb.toString(), Double.class, params.toArray());
	}
	//数据库userinfo中的上网总流量
	@Override
	public Double findTotalData() {
		String sql = "select sum(data_usage) from userinfo ";
		return jt.queryForObject(sql, Double.class);
	}
	//数据库userinfo中的单个学生上网总流量
	@Override
	public Double findTotalData(Map<String, String[]> condition) {
		String sql = "select sum(data_usage) from userinfo where 1=1 ";
		StringBuilder sb = new StringBuilder(sql);
		Set<String> keySet = condition.keySet();
		List<Object> params = new ArrayList<Object>();
		//遍历条件集合，将条件加到SQL语句中
		for (String key : keySet) {
			String value = condition.get(key)[0];
			if ("num".equals(key) && value!=null && !"".equals(value)) {
				sb.append(" and num = ? ");
				params.add(value);
			}
		}
		return jt.queryForObject(sb.toString(), Double.class, params.toArray());
	}
	//数据库中所有学生上网总天数
	@Override
	public Double findCountByNumAndDate() {
		String sql = "select count(*) from (select * from userinfo  group by TO_DAYS(start_time),num) userinfo";
		return jt.queryForObject(sql.toString(), Double.class);
	}
	//数据库中单个学生上网总天数
	@Override
	public Double findCountByNumAndDate(Map<String, String[]> condition) {
		String sql = "select count(*) from (select * from userinfo where 1=1 ";
		StringBuilder sb = new StringBuilder(sql);
		Set<String> keySet = condition.keySet();
		List<Object> params = new ArrayList<Object>();
		//遍历条件集合，将条件加到SQL语句中
		for (String key : keySet) {
			String value = condition.get(key)[0];
			if ("num".equals(key) && value!=null && !"".equals(value)) {
				sb.append(" and num = ? ");
				params.add(value);
			}
		}
		//按日期分组
		sb.append(" group by TO_DAYS(start_time)) userinfo ");
		return jt.queryForObject(sb.toString(), Double.class, params.toArray());
	}
	@Override
	public List<OnlineInfo> findAllNum() {
		String sql = "select * from userinfo group by num";
		return jt.query(sql, new BeanPropertyRowMapper<OnlineInfo>(OnlineInfo.class));
	}
	
	
	
}
