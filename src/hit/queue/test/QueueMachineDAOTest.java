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
 * @author ������
 * 
 */
public class QueueMachineDAOTest {

	@Test
	public void loadBusinessListTest() throws SQLException {
		QueueMachineDAO qmd = new QueueMachineDAOImp();
		ArrayList<String> list = qmd.getBusinessList();
		Assert.assertEquals("ҵ��1", list.get(0));
		Assert.assertEquals("ҵ��2", list.get(1));
		Assert.assertEquals("ҵ��3", list.get(2));
		Assert.assertEquals("ҵ��4", list.get(3));
	}

}
