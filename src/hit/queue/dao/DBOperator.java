/**
 * 
 */
package hit.queue.dao;

import java.sql.*;

/**
 * @author 马天翼
 * 
 */
public class DBOperator {
	/**
	 * 从数据库中查询数据
	 * 
	 * @param sql
	 * @return
	 */
	public ResultSet query(String sql) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeResource(con, st, rs);
		}
		return rs;
	}

	/**
	 * 修改数据库中的数据
	 * 
	 * @param sql
	 * @return
	 */
	public boolean update(String sql) {// 修改数据库中数据
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			con = ConnectionManager.getConnection();
			st = con.createStatement();
			flag = st.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeResource(con, st, rs);
		}
		return flag;
	}
}
