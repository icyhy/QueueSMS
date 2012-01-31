/**
 * 
 */
package hit.queue.test;

import java.sql.SQLException;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;
import hit.queue.dao.*;

/**
 * @author 马天翼
 * 
 */
public class QueueMachineDAOTest {

	@Test
	public void loadBusinessListTest() throws SQLException {
		QueueMachineDAO qmd = new QueueMachineDAOImp();
		ArrayList<String> list = qmd.getBusinessList();
		Assert.assertEquals("业务1", list.get(0));
		Assert.assertEquals("业务2", list.get(1));
		Assert.assertEquals("业务3", list.get(2));
		Assert.assertEquals("业务4", list.get(3));
	}

}
