/**
 * 
 */
package hit.queue.sms;

import java.util.Random;

/**
 * @author ������
 * 
 */
public class CodeGenerator {
	private static char ch[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
			'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
			'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
			'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
			'z', '0', '1' };// ������ظ�����0��1����Ϊ��Ҫ�������鳤��Ϊ64

	private static Random random = new Random();

	// ����ָ�����ȵ�����ַ���
	public static String createRandomString(int length) {
		if (length > 0) {
			int index = 0;
			char[] temp = new char[length];
			int num = random.nextInt();
			for (int i = 0; i < length % 5; i++) {
				temp[index++] = ch[num & 63];// ȡ������λ���ǵö�Ӧ�Ķ��������Բ�����ʽ���ڵġ�
				num >>= 6;// 63�Ķ�����Ϊ:111111
				// ΪʲôҪ����6λ����Ϊ��������һ����64����Ч�ַ���ΪʲôҪ��5ȡ�ࣿ��Ϊһ��int��Ҫ��4���ֽڱ�ʾ��Ҳ����32λ��
			}
			for (int i = 0; i < length / 5; i++) {
				num = random.nextInt();
				for (int j = 0; j < 5; j++) {
					temp[index++] = ch[num & 63];
					num >>= 6;
				}
			}
			return new String(temp, 0, length);
		} else if (length == 0) {
			return "";
		} else {
			throw new IllegalArgumentException();
		}
	}
}
