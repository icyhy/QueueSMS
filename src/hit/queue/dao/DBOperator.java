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
	Connection con;
	Statement st;
	ResultSet rs;

	/**
	 * �����ݿ��в�ѯ����
	 * 
	 * @param sql
	 * @return
	 */
	public ResultSet query(String sql) {
		try {
			con = ConnectionManager.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
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
		boolean flag = false;
		try {
			con = ConnectionManager.getConnection();
			st = con.createStatement();
			flag = st.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public void close() {
		ConnectionManager.closeResource(con, st, rs);
	}
}
