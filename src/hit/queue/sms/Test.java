package hit.queue.sms;

import montnets.mondem;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		mondem gsm = new mondem();
		GsmModemOperator gmo = new GsmModemOperator(gsm);
		if (gmo.init())
			// gmo.displayMessage();
			// gmo.sendSms("13945622065", "≤‚ ‘–≈œ¢£¨«ÎŒªÿ∏¥£°");
			gmo.closeGsm();
		// gmo.isSended();
		// gmo.headerDealer("0,8,2011-12-13 11:36:29,13936879788,0,0,0,13936879788,0,0");
		// gmo.headerDealer("6,8,2011-12-13 11:45:06,13936879788,0,0,0,,0,18");

	}

}
