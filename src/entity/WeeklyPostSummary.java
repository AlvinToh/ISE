package entity;

public class WeeklyPostSummary {
	private String smu_email_id;
	private int week; 
	private float mark;
	
	
	public WeeklyPostSummary(String smu_email_id, int week, float mark) {
		this.smu_email_id = smu_email_id;
		this.week = week;
		this.mark = mark;
	}


	public String getSmu_email_id() {
		return smu_email_id;
	}


	public int getWeek() {
		return week;
	}


	public float getMark() {
		return mark;
	}
	
	
	
	

}
