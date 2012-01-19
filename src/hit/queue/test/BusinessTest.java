/**
 * 
 */
package hit.queue.test;

import static org.junit.Assert.*;
import hit.queue.admin.BusinessOperator;
import hit.queue.business.Business;

import hit.queue.business.Window;
import hit.queue.exception.DeleteTheOnlyWindowException;
import hit.queue.exception.DispatchedWindowException;
import hit.queue.exception.ExistBusinessException;
import hit.queue.exception.WindowNotFoundException;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author 马天翼
 * 
 */
public class BusinessTest {
	private static BusinessOperator operator;

	@BeforeClass
	public static void init() {
		operator = new BusinessOperator();
	}
	
	@Test
	public void firstBusinessWithDefaultWindowTest() throws ExistBusinessException {
		addBusinessWithSingleWindowTest("开户",0,1);
	}
	
	
	@Test
	public void addBusinessWithDefaultWindowTest() throws ExistBusinessException {
		addBusinessWithSingleWindowTest("取款",0,2);
	}
	
	/*
	 * 添加单窗口业务
	 */
	public void addBusinessWithSingleWindowTest(String name,int no,int expectedNo) throws ExistBusinessException{
		ArrayList<Window> windows = new ArrayList<Window>();
		try {
			windows.add(new Window(no));
			operator.addBusiness(name,windows);
		} catch (DispatchedWindowException e) {
			e.printStackTrace();
			fail();
			//Assert.assertEquals(expected, actual)
		}
		Business business = operator.searchBusiness(name);
		Window window = business.getWindows().get(0);
		Assert.assertEquals(name,business.getName());
		Assert.assertEquals(expectedNo, window.getNo());
	}
	
	
	@Test
	public void addBusinessWithOneWindowTest() throws ExistBusinessException {
		addBusinessWithSingleWindowTest("存款",4,4);
	}
	
	@Test
	public void addBusinessWithExistedNameTest() throws DispatchedWindowException {
		ArrayList<Window> windows = new ArrayList<Window>();
 		try {
 			windows.add(new Window(0));
			operator.addBusiness("开户", windows);
			fail();
		} catch (ExistBusinessException e) {
			//e.printStackTrace();
			Assert.assertTrue(true);
		} 
	}
	
	@Test
	public void addBusinessWithExistedWindowTest() throws ExistBusinessException {
		ArrayList<Window> windows = new ArrayList<Window>();
		try{
			windows.add(new Window(2));
			operator.addBusiness("外汇", windows);
			fail();
		}catch(DispatchedWindowException e){
			//e.printStackTrace();
			Assert.assertTrue(true);
		}
	}
	
	@Test
	public void addBusinessWithMultipleWindowsTest() throws ExistBusinessException, DispatchedWindowException{
		ArrayList<Window> wins = new ArrayList<Window>();
		Window five = new Window(5);
		Window six = new Window(6);
		Window seven = new Window(7);
		wins.add(five);
		wins.add(six);
		wins.add(seven);
		operator.addBusiness("外币结算",wins);
		Business business = BusinessOperator.getBusinessList().get(BusinessOperator.getBusinessList().size()-1);
		Assert.assertEquals("外币结算", business.getName());
		Assert.assertEquals(5, business.getWindows().get(0).getNo());
		Assert.assertEquals(6, business.getWindows().get(1).getNo());
		Assert.assertEquals(7, business.getWindows().get(2).getNo());
	}
	
	
	//以下为添加窗口测试用例
	@Test
	public void addWindowWithDefaultWindowNoTest() throws DispatchedWindowException{
		Business business = operator.searchBusiness("开户");
		operator.addWindowToBusiness(new Window(0),business);
		ArrayList<Window> wins = business.getWindows();
		Assert.assertEquals(1, wins.get(0).getNo());
		Assert.assertEquals(3, wins.get(1).getNo());
	}
	
	@Test
	public void addWindowWithNewNoTest(){
		Business business = operator.searchBusiness("开户");
		Window win = new Window(8);
		ArrayList<Window> wins = business.getWindows();
		business.getWindows().add(win);
		Assert.assertEquals(1, wins.get(0).getNo());
		Assert.assertEquals(3, wins.get(1).getNo());
		Assert.assertEquals(8, wins.get(2).getNo());
	}
	
	@Test
	public void addWindowWithExistNoTest(){
		Business business = operator.searchBusiness("开户");
		Window win = new Window(1);
		try {
			operator.addWindowToBusiness(win, business);
			fail();
		} catch (DispatchedWindowException e) {
			e.printStackTrace();
			Assert.assertTrue(true);
		}
	}
	
	@Test
	public void deleteWindowByExistNoTest() throws WindowNotFoundException, DeleteTheOnlyWindowException{
		Business business = operator.searchBusiness("开户");
		operator.deleteWindowFromBusiness(business, 8);
		ArrayList<Window> windows = business.getWindows();
		Assert.assertEquals(1, windows.get(0).getNo());
		Assert.assertEquals(3, windows.get(1).getNo());
	}
	
	@Test
	public void deleteTheOnlyWindowOfBusinessTest() throws WindowNotFoundException{
		Business business = operator.searchBusiness("取款");
		try{
			operator.deleteWindowFromBusiness(business, 2);
			fail();
		}catch(DeleteTheOnlyWindowException e){
			e.printStackTrace();
			Assert.assertTrue(true);
		}
	}
	
	@Test
	public void modifyWindowTest() throws DispatchedWindowException{
		Business business = operator.searchBusiness("开户");
		operator.modifyWindow(business,3,9);
		Assert.assertEquals(1,business.getWindows().get(0).getNo());
		Assert.assertEquals(9,business.getWindows().get(1).getNo());
	}
	
	@Test
	public void modifyWindowToExitsedWindowTest(){
		Business business = operator.searchBusiness("开户");
		try {
			operator.modifyWindow(business, 9, 5);
			fail();
		} catch (DispatchedWindowException e) {
			e.printStackTrace();
			Assert.assertTrue(true);
		}
	}
	
	@Test
	public void exchangeTwoDispatchedWindowTest(){
		operator.exchangeTwoDispatchedWindow(1,5);
		Business business1 = operator.searchBusiness("开户");
		Assert.assertEquals("开户", business1.getName());
		Assert.assertEquals(5, business1.getWindows().get(0).getNo());
		Assert.assertEquals(9, business1.getWindows().get(1).getNo());
		Business business2 = operator.searchBusiness("外币结算");
		Assert.assertEquals("外币结算", business2.getName());
		Assert.assertEquals(1, business2.getWindows().get(0).getNo());
		Assert.assertEquals(6, business2.getWindows().get(1).getNo());
		Assert.assertEquals(7, business2.getWindows().get(2).getNo());
	}
}
