/**
 * 短信发送接口
 */
package hit.queue.sms;

/**
 * @author 马天翼
 * 
 */
public interface SmsSender {
	public boolean init();

	public boolean sendSms(String phone, String message);
}
