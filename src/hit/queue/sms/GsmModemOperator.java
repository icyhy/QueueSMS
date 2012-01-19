/**
 * 短信猫操作类，控制短信猫的初始化、短信收发和短信处理操作
 */
package hit.queue.sms;

import java.util.ArrayList;

import montnets.*;

/**
 * @author 马天翼
 * 
 */
public class GsmModemOperator implements SmsSender {
	private mondem gsm;// 短信猫对象
	private final String SENDERNUMBER = "13945622065";// 默认发送猫的SIM卡号码
	private final String DEFAULTHEADER = ",0," + SENDERNUMBER + ",0,1";// 属性信息，包含序列号+已发送次数+发送者号码+发送类型+报告类型
	private final int COMNO = 3;// COM端口号
	private final int WAITCOUNT = 24;// 等待计数器，默认最多等12次，每次5秒，即1分钟
	// private final int RETRYCOUNT = 2;// 重试计数器，默认最多重试2次
	private ArrayList<String> sequenceNoQueue;
	private ArrayList<SmsMessage> recvQueue;
	private ArrayList<SmsHeader> confirmQueue;

	/**
	 * @param gsm
	 * 
	 */
	public GsmModemOperator(mondem gsm) {
		this.gsm = gsm;
		this.sequenceNoQueue = new ArrayList<String>();
		this.recvQueue = new ArrayList<SmsMessage>();
		this.confirmQueue = new ArrayList<SmsHeader>();
	}

	/*
	 * 短信猫初始化
	 * 
	 * @see hit.queue.sms.SmsSender#init()
	 */
	public boolean init() {
		// 设置短信猫线程模式成功
		if (setThreadModel()) {
			// 设置单口猫模式
			gsm.SetModemType(COMNO, 0);
			System.out.println("将COM：" + COMNO + "端口设置为单口猫模式");

			// 初始化短信猫
			if (0 == gsm.InitModem(-1)) {
				System.out.println("初始化成功！");
				return true;
			} else {
				System.out.println("初始化失败！");
			}
		}
		// 设置短信猫线程模式失败
		return false;
	}

	// 设置线程模式
	private boolean setThreadModel() {
		// 开启线程模式
		if (0 == gsm.SetThreadMode(1)) {// 开启成功
			System.out.println("设置线程模式成功");
			return true;
		} else {// 开启失败
			System.out.println("设置线程模式失败");
			return false;
		}
	}

	/*
	 * 发送短信
	 * 
	 * @see hit.queue.sms.SmsSender#sendSms(java.lang.String, java.lang.String)
	 */
	/*
	 * public boolean sendSms(String phone, String message) { // 提交成功 String
	 * sequenceNo = CodeGenerator.createRandomString(20); String header = phone
	 * + "," + sequenceNo + DEFAULTHEADER; System.out.println("Send Sms Header:"
	 * + header); int count = 1; while (gsm.SendMsg(-1, header, message) >= 0 &&
	 * count < RETRYCOUNT) {// 发送短信 System.out.println("信息提交成功！");
	 * waitFiveSecond(); if (isSended(sequenceNo))// 发送成功 return true; else //
	 * 发送失败 count++; } // 提交失败 System.out.println("超过重试次数限制！取消发送！"); return
	 * false; }
	 */

	public boolean sendSms(String phone, String message) {
		// 记录短信识别码
		String sequenceNo = CodeGenerator.createRandomString(20);
		this.sequenceNoQueue.add(sequenceNo);
		String header = phone + "," + sequenceNo + DEFAULTHEADER;
		System.out.println("Send Sms Header:" + header + " Message:" + message);
		// 发送短信
		if (gsm.SendMsg(-1, header, message) >= 0)
			return true;
		return false;
	}

	// 接收信息
	public int recvMessage() {
		String[] result = gsm.ReadMsgEx(-1);
		// SmsMessage message = null;
		if (!result[0].equals("-1")) {// 收到信息
			SmsHeader header = new SmsHeader(result[1].split(","));
			System.out.println(header.toString());
			if (header.getType().equals("0")) {// 收到信息
				recvSmsOpertion(header, result[2]);
				return 0;
			} else if (header.getType().equals("1")) {// 发送成功信息
				sendSuccessOperation(header);
				return 1;
			} else if (header.getType().equals("6")) {// 信息到达报告
				confirmSmsOperation(header);
				return 6;
			} else if (header.getType().equals("2")) {// 发送失败
				// TODO:
				return 2;
			}
		}
		return -1;
	}

	// 对接收到的信息的处理
	private void recvSmsOpertion(SmsHeader header, String message) {
		SmsMessage sms = new SmsMessage(header.getSendPhoneNo(),
				header.getRecvTime(), message);
		this.recvQueue.add(sms);
		System.out.println(sms.toString());
	}

	// 对发送成功信息回执的处理
	private void sendSuccessOperation(SmsHeader header) {
		if (isThisSmsInSequenceNoQueue(header)) {// 该信息是否在发送的队列中
			this.sequenceNoQueue.remove(header.getSendSequenceNo());
			this.confirmQueue.add(header);
		}
	}

	// 对信息到达报告信息的处理
	private void confirmSmsOperation(SmsHeader header) {
		if (isThisSmsInConfirmQueue(header))// 该信息是否在待确认队列中
			this.confirmQueue.remove(header);
	}

	// 判断短信是否为确认短信
	private boolean isThisSmsInSequenceNoQueue(SmsHeader header) {
		if (this.sequenceNoQueue.contains(header.getSendSequenceNo()))
			return true;
		return false;
	}

	// 判断短信是否为已发送的到达信息
	private boolean isThisSmsInConfirmQueue(SmsHeader header) {
		int size = this.confirmQueue.size();
		if (size != 0) {// 队列为空
			for (int i = 0; i < size - 1; i++)
				if (header.getSequenceNo().equals(
						confirmQueue.get(i).getSequenceNo()))// 与队列中序列号相等
					return true;
		}
		return false;
	}

	// 检查短信是否收到
	public boolean isSended(String sequenceNo) {
		boolean flag = false;
		SmsHeader srcheader = new SmsHeader();
		SmsHeader destheader = new SmsHeader();

		int count = 1;// 计数器
		while (!flag && count < WAITCOUNT) {
			// 读取接收缓存中的信息
			String[] result = gsm.ReadMsgEx(-1);
			if (result[0].equals("-1")) {// 未收到信息
				waitFiveSecond();
				count++;
			} else {// 收到回复信息
				String[] returnheader = result[1].split(",");// 返回的短信HEADER信息
				SmsHeader tempheader = new SmsHeader(returnheader);
				if (tempheader.getType().equals("1")
						&& tempheader.getSequenceNo().equals(sequenceNo))// 读取的短信是否为发送成功信息
				{
					srcheader = tempheader;
					System.out.println("源信息HEADER：" + srcheader.toString());
				} else if (tempheader.getType().equals("6")) // 读取的短信是否为发送成功确认信息
					destheader = tempheader;
				// 判断是否为同一条信息的确认
				flag = isTheSameMessage(srcheader, destheader);
				if (flag) {
					System.out.println("目的收到确认HEADER：" + destheader.toString());
					return flag;
				}
			}
		}
		return flag;
	}

	// 判断源确认号与目的确认号、源目的手机号与目的手机号是否相同，相同则说明是同一信息的确认
	private boolean isTheSameMessage(SmsHeader srcheader, SmsHeader destheader) {
		if (srcheader.getSendSequenceNo() != null
				&& destheader.getSendSequenceNo() != null
				&& srcheader.getSendSequenceNo().equals(
						destheader.getSendSequenceNo())) {
			if (srcheader.getRecvPhoneNo() != null
					&& destheader.getRecvPhoneNo() != null
					&& srcheader.getRecvPhoneNo().equals(
							destheader.getRecvPhoneNo()))
				return true;
		}
		return false;
	}

	// 等待五秒
	private void waitFiveSecond() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		} // 延时等待五秒
	}

	// 显示接收的信息
	/*
	 * public void displayMessage() { SmsMessage message; while (true) {
	 * //message = recvMessage(); //if (message != null) {
	 * System.out.println("发送号码：" + message.getSendPhoneNo());
	 * System.out.println("接收时间：" + message.getRecvTime());
	 * System.out.println("接收信息：" + message.getMessage()); }
	 * this.waitFiveSecond(); } }
	 */

	// 关闭GSM MODEM，释放端口
	public void closeGsm() {
		gsm.CloseModem(-1);
	}

	/**
	 * @return the gsm
	 */
	public mondem getGsm() {
		return gsm;
	}

	/**
	 * @param gsm
	 *            the gsm to set
	 */
	public void setGsm(mondem gsm) {
		this.gsm = gsm;
	}
}
