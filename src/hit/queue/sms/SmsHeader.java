/**
 * 
 */
package hit.queue.sms;

/**
 * @author 马天翼
 * 
 */
public class SmsHeader {
	private String type;// 信息类型
	private String portNo;// 端口号
	private String recvTime;// 接收时间
	private String recvPhoneNo;// 接收方手机号码
	private String sequenceNo;// 序列号
	private String sendedTime;// 已发送次数
	private String submitPort;// 提交端口
	private String sendPhoneNo;// 发送方手机号码
	private String sendtype;// 发送类型
	private String sendSequenceNo;// 发送序列号

	/**
	 * @param type
	 * @param portNo
	 * @param recvTime
	 * @param recvPhoneNo
	 * @param sequenceNo
	 * @param sendedTime
	 * @param submitPort
	 * @param sendPhoneNo
	 * @param sendtype
	 * @param sendSequenceNo
	 */
	public SmsHeader(String type, String portNo, String recvTime,
			String recvPhoneNo, String sequenceNo, String sendedTime,
			String submitPort, String sendPhoneNo, String sendtype,
			String sendSequenceNo) {
		this.type = type;
		this.portNo = portNo;
		this.recvTime = recvTime;
		this.recvPhoneNo = recvPhoneNo;
		this.sequenceNo = sequenceNo;
		this.sendedTime = sendedTime;
		this.submitPort = submitPort;
		this.sendPhoneNo = sendPhoneNo;
		this.sendtype = sendtype;
		this.sendSequenceNo = sendSequenceNo;
	}

	public SmsHeader() {
		// TODO Auto-generated constructor stub
	}

	public SmsHeader(String[] returnheader) {
		this.type = returnheader[0];
		this.portNo = returnheader[1];
		this.recvTime = returnheader[2];
		this.recvPhoneNo = returnheader[3];
		this.sequenceNo = returnheader[4];
		this.sendedTime = returnheader[5];
		this.submitPort = returnheader[6];
		this.sendPhoneNo = returnheader[7];
		this.sendtype = returnheader[8];
		this.sendSequenceNo = returnheader[9];
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the portNo
	 */
	public String getPortNo() {
		return portNo;
	}

	/**
	 * @param portNo
	 *            the portNo to set
	 */
	public void setPortNo(String portNo) {
		this.portNo = portNo;
	}

	/**
	 * @return the recvTime
	 */
	public String getRecvTime() {
		return recvTime;
	}

	/**
	 * @param recvTime
	 *            the recvTime to set
	 */
	public void setRecvTime(String recvTime) {
		this.recvTime = recvTime;
	}

	/**
	 * @return the recvPhoneNo
	 */
	public String getRecvPhoneNo() {
		return recvPhoneNo;
	}

	/**
	 * @param recvPhoneNo
	 *            the recvPhoneNo to set
	 */
	public void setRecvPhoneNo(String recvPhoneNo) {
		this.recvPhoneNo = recvPhoneNo;
	}

	/**
	 * @return the sequenceNo
	 */
	public String getSequenceNo() {
		return sequenceNo;
	}

	/**
	 * @param sequenceNo
	 *            the sequenceNo to set
	 */
	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	/**
	 * @return the sendedTime
	 */
	public String getSendedTime() {
		return sendedTime;
	}

	/**
	 * @param sendedTime
	 *            the sendedTime to set
	 */
	public void setSendedTime(String sendedTime) {
		this.sendedTime = sendedTime;
	}

	/**
	 * @return the submitPort
	 */
	public String getSubmitPort() {
		return submitPort;
	}

	/**
	 * @param submitPort
	 *            the submitPort to set
	 */
	public void setSubmitPort(String submitPort) {
		this.submitPort = submitPort;
	}

	/**
	 * @return the sendPhoneNo
	 */
	public String getSendPhoneNo() {
		return sendPhoneNo;
	}

	/**
	 * @param sendPhoneNo
	 *            the sendPhoneNo to set
	 */
	public void setSendPhoneNo(String sendPhoneNo) {
		this.sendPhoneNo = sendPhoneNo;
	}

	/**
	 * @return the sendtype
	 */
	public String getSendtype() {
		return sendtype;
	}

	/**
	 * @param sendtype
	 *            the sendtype to set
	 */
	public void setSendtype(String sendtype) {
		this.sendtype = sendtype;
	}

	/**
	 * @return the sendSequenceNo
	 */
	public String getSendSequenceNo() {
		return sendSequenceNo;
	}

	/**
	 * @param sendSequenceNo
	 *            the sendSequenceNo to set
	 */
	public void setSendSequenceNo(String sendSequenceNo) {
		this.sendSequenceNo = sendSequenceNo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String
				.format("SmsHeader [type=%s, portNo=%s, recvTime=%s, recvPhoneNo=%s, sequenceNo=%s, sendedTime=%s, submitPort=%s, sendPhoneNo=%s, sendtype=%s, sendSequenceNo=%s]",
						type, portNo, recvTime, recvPhoneNo, sequenceNo,
						sendedTime, submitPort, sendPhoneNo, sendtype,
						sendSequenceNo);
	}
}
