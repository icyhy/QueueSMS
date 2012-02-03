/**
 * 
 */
package hit.queue.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 
 * @author ¬ÌÃÏ“Ì
 * 
 */
public class QueueMachineDAOImp implements QueueMachineDAO {

	public ArrayList<String> getBusinessList() throws SQLException {
		DBOperator dbo = new DBOperator();
		ResultSet result = dbo.query("select name from business");
		ArrayList<String> list = new ArrayList<String>();
		while (result.next()) {
			list.add(result.getString("name"));
		}
		System.out.println(list.toString());
		dbo.close();
		return list;
	}

}
