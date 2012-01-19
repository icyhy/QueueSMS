/**
 * ����è�����࣬���ƶ���è�ĳ�ʼ���������շ��Ͷ��Ŵ������
 */
package hit.queue.sms;

import java.util.ArrayList;

import montnets.*;

/**
 * @author ������
 * 
 */
public class GsmModemOperator implements SmsSender {
	private mondem gsm;// ����è����
	private final String SENDERNUMBER = "13945622065";// Ĭ�Ϸ���è��SIM������
	private final String DEFAULTHEADER = ",0," + SENDERNUMBER + ",0,1";// ������Ϣ���������к�+�ѷ��ʹ���+�����ߺ���+��������+��������
	private final int COMNO = 3;// COM�˿ں�
	private final int WAITCOUNT = 24;// �ȴ���������Ĭ������12�Σ�ÿ��5�룬��1����
	// private final int RETRYCOUNT = 2;// ���Լ�������Ĭ���������2��
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
	 * ����è��ʼ��
	 * 
	 * @see hit.queue.sms.SmsSender#init()
	 */
	public boolean init() {
		// ���ö���è�߳�ģʽ�ɹ�
		if (setThreadModel()) {
			// ���õ���èģʽ
			gsm.SetModemType(COMNO, 0);
			System.out.println("��COM��" + COMNO + "�˿�����Ϊ����èģʽ");

			// ��ʼ������è
			if (0 == gsm.InitModem(-1)) {
				System.out.println("��ʼ���ɹ���");
				return true;
			} else {
				System.out.println("��ʼ��ʧ�ܣ�");
			}
		}
		// ���ö���è�߳�ģʽʧ��
		return false;
	}

	// �����߳�ģʽ
	private boolean setThreadModel() {
		// �����߳�ģʽ
		if (0 == gsm.SetThreadMode(1)) {// �����ɹ�
			System.out.println("�����߳�ģʽ�ɹ�");
			return true;
		} else {// ����ʧ��
			System.out.println("�����߳�ģʽʧ��");
			return false;
		}
	}

	/*
	 * ���Ͷ���
	 * 
	 * @see hit.queue.sms.SmsSender#sendSms(java.lang.String, java.lang.String)
	 */
	/*
	 * public boolean sendSms(String phone, String message) { // �ύ�ɹ� String
	 * sequenceNo = CodeGenerator.createRandomString(20); String header = phone
	 * + "," + sequenceNo + DEFAULTHEADER; System.out.println("Send Sms Header:"
	 * + header); int count = 1; while (gsm.SendMsg(-1, header, message) >= 0 &&
	 * count < RETRYCOUNT) {// ���Ͷ��� System.out.println("��Ϣ�ύ�ɹ���");
	 * waitFiveSecond(); if (isSended(sequenceNo))// ���ͳɹ� return true; else //
	 * ����ʧ�� count++; } // �ύʧ�� System.out.println("�������Դ������ƣ�ȡ�����ͣ�"); return
	 * false; }
	 */

	public boolean sendSms(String phone, String message) {
		// ��¼����ʶ����
		String sequenceNo = CodeGenerator.createRandomString(20);
		this.sequenceNoQueue.add(sequenceNo);
		String header = phone + "," + sequenceNo + DEFAULTHEADER;
		System.out.println("Send Sms Header:" + header + " Message:" + message);
		// ���Ͷ���
		if (gsm.SendMsg(-1, header, message) >= 0)
			return true;
		return false;
	}

	// ������Ϣ
	public int recvMessage() {
		String[] result = gsm.ReadMsgEx(-1);
		// SmsMessage message = null;
		if (!result[0].equals("-1")) {// �յ���Ϣ
			SmsHeader header = new SmsHeader(result[1].split(","));
			System.out.println(header.toString());
			if (header.getType().equals("0")) {// �յ���Ϣ
				recvSmsOpertion(header, result[2]);
				return 0;
			} else if (header.getType().equals("1")) {// ���ͳɹ���Ϣ
				sendSuccessOperation(header);
				return 1;
			} else if (header.getType().equals("6")) {// ��Ϣ���ﱨ��
				confirmSmsOperation(header);
				return 6;
			} else if (header.getType().equals("2")) {// ����ʧ��
				// TODO:
				return 2;
			}
		}
		return -1;
	}

	// �Խ��յ�����Ϣ�Ĵ���
	private void recvSmsOpertion(SmsHeader header, String message) {
		SmsMessage sms = new SmsMessage(header.getSendPhoneNo(),
				header.getRecvTime(), message);
		this.recvQueue.add(sms);
		System.out.println(sms.toString());
	}

	// �Է��ͳɹ���Ϣ��ִ�Ĵ���
	private void sendSuccessOperation(SmsHeader header) {
		if (isThisSmsInSequenceNoQueue(header)) {// ����Ϣ�Ƿ��ڷ��͵Ķ�����
			this.sequenceNoQueue.remove(header.getSendSequenceNo());
			this.confirmQueue.add(header);
		}
	}

	// ����Ϣ���ﱨ����Ϣ�Ĵ���
	private void confirmSmsOperation(SmsHeader header) {
		if (isThisSmsInConfirmQueue(header))// ����Ϣ�Ƿ��ڴ�ȷ�϶�����
			this.confirmQueue.remove(header);
	}

	// �ж϶����Ƿ�Ϊȷ�϶���
	private boolean isThisSmsInSequenceNoQueue(SmsHeader header) {
		if (this.sequenceNoQueue.contains(header.getSendSequenceNo()))
			return true;
		return false;
	}

	// �ж϶����Ƿ�Ϊ�ѷ��͵ĵ�����Ϣ
	private boolean isThisSmsInConfirmQueue(SmsHeader header) {
		int size = this.confirmQueue.size();
		if (size != 0) {// ����Ϊ��
			for (int i = 0; i < size - 1; i++)
				if (header.getSequenceNo().equals(
						confirmQueue.get(i).getSequenceNo()))// ����������к����
					return true;
		}
		return false;
	}

	// �������Ƿ��յ�
	public boolean isSended(String sequenceNo) {
		boolean flag = false;
		SmsHeader srcheader = new SmsHeader();
		SmsHeader destheader = new SmsHeader();

		int count = 1;// ������
		while (!flag && count < WAITCOUNT) {
			// ��ȡ���ջ����е���Ϣ
			String[] result = gsm.ReadMsgEx(-1);
			if (result[0].equals("-1")) {// δ�յ���Ϣ
				waitFiveSecond();
				count++;
			} else {// �յ��ظ���Ϣ
				String[] returnheader = result[1].split(",");// ���صĶ���HEADER��Ϣ
				SmsHeader tempheader = new SmsHeader(returnheader);
				if (tempheader.getType().equals("1")
						&& tempheader.getSequenceNo().equals(sequenceNo))// ��ȡ�Ķ����Ƿ�Ϊ���ͳɹ���Ϣ
				{
					srcheader = tempheader;
					System.out.println("Դ��ϢHEADER��" + srcheader.toString());
				} else if (tempheader.getType().equals("6")) // ��ȡ�Ķ����Ƿ�Ϊ���ͳɹ�ȷ����Ϣ
					destheader = tempheader;
				// �ж��Ƿ�Ϊͬһ����Ϣ��ȷ��
				flag = isTheSameMessage(srcheader, destheader);
				if (flag) {
					System.out.println("Ŀ���յ�ȷ��HEADER��" + destheader.toString());
					return flag;
				}
			}
		}
		return flag;
	}

	// �ж�Դȷ�Ϻ���Ŀ��ȷ�Ϻš�ԴĿ���ֻ�����Ŀ���ֻ����Ƿ���ͬ����ͬ��˵����ͬһ��Ϣ��ȷ��
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

	// �ȴ�����
	private void waitFiveSecond() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		} // ��ʱ�ȴ�����
	}

	// ��ʾ���յ���Ϣ
	/*
	 * public void displayMessage() { SmsMessage message; while (true) {
	 * //message = recvMessage(); //if (message != null) {
	 * System.out.println("���ͺ��룺" + message.getSendPhoneNo());
	 * System.out.println("����ʱ�䣺" + message.getRecvTime());
	 * System.out.println("������Ϣ��" + message.getMessage()); }
	 * this.waitFiveSecond(); } }
	 */

	// �ر�GSM MODEM���ͷŶ˿�
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
