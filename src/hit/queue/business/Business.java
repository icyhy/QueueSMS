/**
 * 
 */
package hit.queue.business;

import hit.queue.business.Window;
import java.util.ArrayList;

/**
 * @author ¬ÌÃÏ“Ì
 * 
 */
public class Business {
	private String name;
	private ArrayList<Window> windows;

	/**
	 * @param name
	 * @param window
	 */
	public Business(String name, ArrayList<Window> windows) {
		this.name = name;
		this.windows = windows;
	}

	public Business(String name, int no) {
		this.name = name;
		windows = new ArrayList<Window>();
		windows.add(new Window(no));
	}

	public Business() {
		this.name = "";
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the window
	 */
	public ArrayList<Window> getWindows() {
		return windows;
	}

	/**
	 * @param window
	 *            the window to set
	 */
	public void setWindows(ArrayList<Window> windows) {
		this.windows = windows;
	}

	public void addWindow(Window window) {
		ArrayList<Window> windows = this.getWindows();
		windows.add(window);
	}
}
