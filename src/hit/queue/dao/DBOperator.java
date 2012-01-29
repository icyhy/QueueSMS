/**
 * 
 */
package hit.queue.dao;

import java.sql.*;

/**
 * @author ������
 * 
 */
public class DBOperator {
	/**
	 * �����ݿ��в�ѯ����
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
	 * �޸����ݿ��е�����
	 * 
	 * @param sql
	 * @return
	 */
	public boolean update(String sql) {// �޸����ݿ�������
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
