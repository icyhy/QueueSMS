/**
 * ���ŷ��ͽӿ�
 */
package hit.queue.sms;

/**
 * @author ������
 * 
 */
public interface SmsSender {
	public boolean init();

	public boolean sendSms(String phone, String message);
}
