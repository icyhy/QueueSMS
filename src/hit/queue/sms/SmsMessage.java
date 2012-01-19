/**
 * 
 */
package hit.queue.sms;

/**
 * @author ¬ÌÃÏ“Ì
 * 
 */
public class SmsMessage {
	private String sendPhoneNo;
	private String recvTime;
	private String message;

	/**
	 * @param sendPhoneNo
	 * @param recvTime
	 * @param message
	 */
	public SmsMessage(String sendPhoneNo, String recvTime, String message) {
		this.sendPhoneNo = sendPhoneNo;
		this.recvTime = recvTime;
		this.message = message;
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
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format(
				"SmsMessage [sendPhoneNo=%s, recvTime=%s, message=%s]",
				sendPhoneNo, recvTime, message);
	}

}
