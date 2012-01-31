/**
 * 
 */
package hit.queue.dao;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author ÂíÌìÒí
 * 
 */
public interface QueueMachineDAO {

	public ArrayList<String> getBusinessList() throws SQLException;

}
