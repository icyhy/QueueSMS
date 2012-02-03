/**
 * 
 */
package hit.queue.service;

import hit.queue.dao.QueueMachineDAO;
import hit.queue.dao.QueueMachineDAOImp;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author ¬ÌÃÏ“Ì
 *
 */
public class QueueMachineOperator implements QueueMachineServcies {

	/* (non-Javadoc)
	 * @see hit.queue.service.QueueMachineServcies#getBusinessList()
	 */
	
	public ArrayList<String> getBusinessList() {
		QueueMachineDAO qmd = new QueueMachineDAOImp();
		ArrayList<String> list = new ArrayList<String>();
		try {
			list = qmd.getBusinessList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
