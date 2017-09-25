package utility;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class RandomString extends TimerTask  {
/***
	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.schedule(new RandomString(), 0, 5000); 
	}
	
***/
	public static String getCurrentTime() {
		// Date object
		Date date = new Date();
		// getTime() returns current time in milliseconds
		long time = date.getTime();
		// Passed the milliseconds to constructor of Timestamp class
		Timestamp ts = new Timestamp(time);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(ts);

		return dateStr;
	}

	public static String generateRandomString(int length) {
		final String AB = "abcdefghijklmnopqrstuvwxyz";
		SecureRandom rnd = new SecureRandom();

		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// System.out.println(generateRandomString(8)+ " generated at " + getCurrentTime()); 
		String passcode =generateRandomString(8);	
		QRcode.generateQRcode(passcode);
		System.out.println(getCurrentTime());
		
	}

}
