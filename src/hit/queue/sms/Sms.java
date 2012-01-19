/**
 * 
 */
package hit.queue.sms;

/**
 * @author ÂíÌìÒí
 * 
 */
public class Sms {
	private SmsHeader header;
	private SmsMessage message;

	/**
	 * @param header
	 * @param message
	 */
	public Sms(SmsHeader header, SmsMessage message) {
		this.header = header;
		this.message = message;
	}

	/**
	 * @return the header
	 */
	public SmsHeader getHeader() {
		return header;
	}

	/**
	 * @param header
	 *            the header to set
	 */
	public void setHeader(SmsHeader header) {
		this.header = header;
	}

	/**
	 * @return the message
	 */
	public SmsMessage getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(SmsMessage message) {
		this.message = message;
	}

}
