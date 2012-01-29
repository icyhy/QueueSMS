/**
 * 
 */
package hit.queue.admin;

import hit.queue.business.Business;
import hit.queue.business.Window;
import hit.queue.exception.DeleteTheOnlyWindowException;
import hit.queue.exception.DispatchedWindowException;
import hit.queue.exception.ExistBusinessException;
import hit.queue.exception.WindowNotFoundException;

import java.util.ArrayList;

/**
 * @author 马天翼
 * 
 */
public class BusinessOperator {
	private static ArrayList<Business> businessList;
	private static ArrayList<Window> dispatchedWindows;
	private static ArrayList<Window> undispatchedWindows;

	public BusinessOperator() {
		init();
	}

	/**
	 * 初始化业务列表，已分配窗口列表，未分配窗口列表
	 */
	public void init() {
		businessList = new ArrayList<Business>();
		dispatchedWindows = new ArrayList<Window>();
		undispatchedWindows = new ArrayList<Window>();
		initUndispatchedWindows();
	}

	/**
	 * 初始化未分配列表
	 */
	private void initUndispatchedWindows() {
		for (int i = 1; i < 21; i++) {
			undispatchedWindows.add(new Window(i));
		}
		// System.out.println("未分配窗口数量：" + undispatchedWindows.size());
	}

	/**
	 * 获取未分配窗口中最小号码的窗口
	 * 
	 * @return
	 */
	public Window getMinNoWindowInUndispatchedWindow() {
		if (undispatchedWindows.isEmpty()) {// 无可用窗口
			return new Window();
		} else {// 有可用窗口，寻找最小元素
			Window firstWindow = undispatchedWindows.get(0);
			int min = firstWindow.getNo();
			Window returnWindow = firstWindow;
			for (int i = 1; i < undispatchedWindows.size(); i++) {
				Window tempWindow = undispatchedWindows.get(i);
				int temp = tempWindow.getNo();
				if (min > temp) {
					min = temp;
					returnWindow = tempWindow;
				}
			}
			return returnWindow;
		}
	}

	/**
	 * 分配窗口(多个窗口)
	 * 
	 * @param windows
	 */
	public void dispatchWindow(ArrayList<Window> windows) {
		for (int i = 0; i < windows.size(); i++) {
			dispatchWindow(windows.get(i).getNo());
		}
	}

	/**
	 * 分配窗口（单窗口）
	 * 
	 * @param no
	 */
	public void dispatchWindow(int no) {
		Window window = this.searchUndispatchedWindow(no);
		undispatchedWindows.remove(window);
		dispatchedWindows.add(window);
	}

	/**
	 * 搜索指定业务
	 * 
	 * @param name
	 * @return
	 */
	public Business searchBusiness(String name) {
		for (int i = 0; i < businessList.size(); i++) {
			Business business = businessList.get(i);
			if (name.equals(business.getName()))
				return business;
		}
		return new Business();
	}

	/**
	 * 搜索指定号码的未分配的窗口
	 * 
	 * @param no
	 * @return
	 */
	public Window searchUndispatchedWindow(int no) {
		for (int i = 0; i < undispatchedWindows.size(); i++) {
			Window window = undispatchedWindows.get(i);
			if (window.getNo() == no)
				return window;
		}
		return new Window(0);
	}

	/**
	 * 搜索指定号码的已分配的窗口
	 * 
	 * @param no
	 * @return
	 */
	public Window searchDispatchedWindow(int no) {
		for (int i = 0; i < dispatchedWindows.size(); i++) {
			Window window = dispatchedWindows.get(i);
			if (window.getNo() == no)
				return window;
		}
		return new Window(0);
	}

	/**
	 * 检查是否已有指定业务
	 * 
	 * @param name
	 * @throws ExistBusinessException
	 */
	public void validateBusiness(String name) throws ExistBusinessException {
		for (int i = 0; i < businessList.size(); i++) {
			Business business = businessList.get(i);
			if (name.equals(business.getName()))
				throw new ExistBusinessException("validateBusiness:'" + name
						+ "' " + "already has this business!");
		}
	}

	/**
	 * 检查是否已有指定窗口
	 * 
	 * @param no
	 * @throws DispatchedWindowException
	 */
	public void validateWindow(int no) throws DispatchedWindowException {
		for (int i = 0; i < dispatchedWindows.size(); i++) {
			Window window = dispatchedWindows.get(i);
			if (window.getNo() == no)
				throw new DispatchedWindowException("validateWindow: No." + no
						+ " window has been dispatched!");
		}
	}

	/**
	 * @return the businessList
	 */
	public static ArrayList<Business> getBusinessList() {
		return businessList;
	}

	/**
	 * @param businessList
	 *            the businessList to set
	 */
	public static void setBusinessList(ArrayList<Business> businessList) {
		BusinessOperator.businessList = businessList;
	}

	/**
	 * @return the dispatchedWindows
	 */
	public static ArrayList<Window> getDispatchedWindows() {
		return dispatchedWindows;
	}

	/**
	 * @param dispatchedWindows
	 *            the dispatchedWindows to set
	 */
	public static void setDispatchedWindows(ArrayList<Window> dispatchedWindows) {
		BusinessOperator.dispatchedWindows = dispatchedWindows;
	}

	/**
	 * @return the undispatchedWindows
	 */
	public static ArrayList<Window> getUndispatchedWindows() {
		return undispatchedWindows;
	}

	/**
	 * @param undispatchedWindows
	 *            the undispatchedWindows to set
	 */
	public static void setUndispatchedWindows(
			ArrayList<Window> undispatchedWindows) {
		BusinessOperator.undispatchedWindows = undispatchedWindows;
	}

	/**
	 * 添加业务
	 * 
	 * @param name
	 * @param windows
	 * @throws ExistBusinessException
	 * @throws DispatchedWindowException
	 */
	public void addBusiness(String name, ArrayList<Window> windows)
			throws ExistBusinessException, DispatchedWindowException {
		Business bus = this.searchBusiness(name);
		if (bus.getName().equals("")) {// 未重复
			Window window = new Window();
			boolean result = this.isWindowsDispatched(windows);
			if (result)// 分配过了
				throw new DispatchedWindowException("addBusiness('" + name
						+ "'," + windows.toString() + ") has dispatched window");
			if (windows.size() > 1) {// 指定窗口

			} else {// 默认窗口
				if (0 == windows.get(0).getNo()) {
					windows.remove(0);
					window = this.getMinNoWindowInUndispatchedWindow();
					windows.add(window);
				}
			}
			Business business = new Business(name, windows);
			businessList.add(business);
			dispatchWindow(windows);
		} else
			throw new ExistBusinessException("addBusiness('" + name + ")");
	}

	/**
	 * 判断窗口是否已被分配
	 * 
	 * @param windows
	 * @return
	 */
	public boolean isWindowsDispatched(ArrayList<Window> windows) {
		for (int i = 0; i < windows.size(); i++) {
			Window window = windows.get(i);
			window = this.searchDispatchedWindow(window.getNo());
			if (window.getNo() != 0)// 已分配
				return true;
		}
		return false;
	}

	/**
	 * 为业务分配新窗口
	 * 
	 * @param window
	 * @param business
	 * @throws DispatchedWindowException
	 */
	public void addWindowToBusiness(Window window, Business business)
			throws DispatchedWindowException {
		if (0 == window.getNo())// 默认窗口号
			window = this.getMinNoWindowInUndispatchedWindow();
		else {// 指定窗口号
			Window win2 = this.searchDispatchedWindow(window.getNo());
			if (win2.getNo() != 0)// 已分配
				throw new DispatchedWindowException(
						"addWindowToBusiness: this window has dispatched");
		}
		business.addWindow(window);
		ArrayList<Window> list = new ArrayList<Window>();
		list.add(window);
		this.dispatchWindow(list);
	}

	/**
	 * 从业务中删除窗口
	 * 
	 * @param business
	 * @param i
	 * @throws WindowNotFoundException
	 * @throws DeleteTheOnlyWindowException
	 */
	public void deleteWindowFromBusiness(Business business, int i)
			throws WindowNotFoundException, DeleteTheOnlyWindowException {
		Window window = this.searchWindowFromBusiness(business, i);
		// System.out.println(window.getNo());
		if (0 == window.getNo())// 业务中无该窗口
			throw new WindowNotFoundException(
					"deleteWindowFromBusiness: No such a window in business "
							+ business.getName());
		else if (-1 == window.getNo()) {// 唯一的窗口，不允许删除
			throw new DeleteTheOnlyWindowException(
					"deleteWindowFromBusiness: No such a window in business "
							+ business.getName());
		} else {// 业务中有该窗口，删掉
			ArrayList<Window> windows = business.getWindows();
			windows.remove(window);
			this.undispatchWindow(window.getNo());
		}
	}

	/**
	 * 从业务中查找窗口
	 * 
	 * @param business
	 * @param no
	 * @return
	 */
	public Window searchWindowFromBusiness(Business business, int no) {
		ArrayList<Window> windows = business.getWindows();
		if (windows.size() > 1) {
			for (int i = 0; i < windows.size(); i++) {
				if (no == windows.get(i).getNo())// 找到，返回
					return windows.get(i);
			}
			return new Window(0);// 未找到
		}
		return new Window(-1);// 唯一的窗口
	}

	/**
	 * 将窗口从已分配队列中移除
	 * 
	 * @param no
	 */
	public void undispatchWindow(int no) {
		Window window = this.searchDispatchedWindow(no);
		getDispatchedWindows().remove(window);
		getUndispatchedWindows().add(window);
	}

	/**
	 * 修改指定号码的窗口号
	 * 
	 * @param business
	 * @param sourceNo
	 * @param destNo
	 * @throws DispatchedWindowException
	 */
	public void modifyWindow(Business business, int sourceNo, int destNo)
			throws DispatchedWindowException {
		Window destWindow = this.searchDispatchedWindow(destNo);
		if (destWindow.getNo() != 0)// 不在
			throw new DispatchedWindowException();
		else {// 在
			undispatchWindow(sourceNo);
			dispatchWindow(destNo);
			Window window = this.searchWindowFromBusiness(business, sourceNo);
			window.setNo(destNo);
		}
	}

	/**
	 * 互换两个窗口
	 * 
	 * @param first
	 * @param second
	 */
	public void exchangeTwoDispatchedWindow(int first, int second) {
		Business business1 = getBusinessByWindow(first);
		System.out.println(business1.getName());

		Business business2 = getBusinessByWindow(second);
		System.out.println(business2.getName());
		System.out.println(second);
		Window window1 = this.searchWindowFromBusiness(business1, first);
		Window window2 = this.searchWindowFromBusiness(business2, second);
		swapTwoWindows(window1, window2);
	}

	// 交换两个窗口的属性
	public void swapTwoWindows(Window first, Window second) {
		Window temp = new Window();
		temp.setNo(first.getNo());
		first.setNo(second.getNo());
		second.setNo(temp.getNo());
	}

	/**
	 * 查找窗口的业务
	 * 
	 * @param no
	 * @return
	 */
	public Business getBusinessByWindow(int no) {
		ArrayList<Business> business = getBusinessList();
		for (int i = 0; i < business.size(); i++) {
			Window window = this.searchWindowFromBusiness(business.get(i), no);
			if (window.getNo() != 0)// 找到
				return business.get(i);
		}
		return new Business();
	}
}
