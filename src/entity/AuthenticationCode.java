package entity;

public class AuthenticationCode {
	private String week;
	private String teachingGrp;
	private String timestamp;
	private String qrcode;

	public AuthenticationCode(String week, String teachingGrp, String timestamp, String qrcode) {
		this.week = week;
		this.teachingGrp = teachingGrp;
		this.timestamp = timestamp;
		this.qrcode = qrcode;
	}

	public String getWeek() {
		return week;
	}

	public String getTeachingGrp() {
		return teachingGrp;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getQRCode() {
		return qrcode;
	}

}
