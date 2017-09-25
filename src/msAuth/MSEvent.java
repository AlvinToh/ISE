package msAuth;

public class MSEvent {
	private String Subject;
	private Start Start;
	private End End;
	
	public MSEvent(String Subject, String startDateTime, String endDateTime){
		this.Subject = Subject;
		this.Start = new Start(startDateTime);
		this.End = new End(endDateTime);
	}
	
	private class Start {
		private String DateTime;
		private String TimeZone;	
		
		private Start (String DateTime){
			this.setDateTime(DateTime);
			this.setTimeZone("Singapore Standard Time");
		}

		public String getDateTime() {
			return DateTime;
		}

		public void setDateTime(String dateTime) {
			DateTime = dateTime;
		}

		public String getTimeZone() {
			return TimeZone;
		}

		public void setTimeZone(String timeZone) {
			TimeZone = timeZone;
		}
	}
	
	public class End {
		private String DateTime;
		private String TimeZone;
		
		private End (String DateTime){
			this.setDateTime(DateTime);
			this.setTimeZone("Singapore Standard Time");
		}

		public String getDateTime() {
			return DateTime;
		}

		public void setDateTime(String dateTime) {
			DateTime = dateTime;
		}

		public String getTimeZone() {
			return TimeZone;
		}

		public void setTimeZone(String timeZone) {
			TimeZone = timeZone;
		}
	}

	public String getSubject() {
		return this.Subject;
	}

	public void setSubject(String subject) {
		this.Subject = subject;
	}

	public Start getStart() {
		return Start;
	}

	public void setStart(Start start) {
		Start = start;
	}

	public End getEnd() {
		return End;
	}

	public void setEnd(End end) {
		End = end;
	}
	
}
