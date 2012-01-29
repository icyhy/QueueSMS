/**
 * 
 */
package hit.queue.dao;

import java.sql.*;

/**
 * @author ������
 * 
 */
public class ConnectionManager {
	private static String username = "root";
	private static String password = "6383395";
	private static String driver = "org.gjt.mm.mysql.Driver";
	private static String url = "jdbc:mysql://localhost/";
	private static String database = "queue";

	public static Connection getConnection() throws Exception {// �������ݿ�
		Class.forName(driver).newInstance();
		String wholeUrl = url + database;
		return DriverManager.getConnection(wholeUrl, username, password);
	}

	public static void closeResource(Connection con, Statement st, ResultSet rs) {// �ر�����
		try {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
