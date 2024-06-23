package login;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import jdbc.JDBCUtils;
/*
 * 与数据库交互，验证用户名与密码是否正确
 */
public class TeacherDao {
	private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSourceToTeacherInfo());
	
	public Teacher login(Teacher loginTeacher) {
		try {
			String sql = "select * from teacher where username=? and password=?";
			Teacher teacher = template.queryForObject(sql, 
					new BeanPropertyRowMapper<Teacher>(Teacher.class),
					loginTeacher.getUsername(),loginTeacher.getPassword());
			return teacher;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
