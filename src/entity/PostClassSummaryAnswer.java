package entity;

public class PostClassSummaryAnswer {
	private String smu_email_id;
	private int week; 
	private int question_id;
	private String question_hist;
	private String answer;
	private String group_id;
	
	public PostClassSummaryAnswer(String smu_email_id, int week, int question_id, String question_hist, String answer,
			String group_id) {
	
		this.smu_email_id = smu_email_id;
		this.week = week;
		this.question_id = question_id;
		this.question_hist = question_hist;
		this.answer = answer;
		this.group_id = group_id;
	}
	
	public PostClassSummaryAnswer(int question_id, String question_hist, String answer) {
	

		this.question_id = question_id;
		this.question_hist = question_hist;
		this.answer = answer;

	}

	public String getSmu_email_id() {
		return smu_email_id;
	}

	public int getWeek() {
		return week;
	}

	public int getQuestion_id() {
		return question_id;
	}

	public String getQuestion_hist() {
		return question_hist;
	}

	public String getAnswer() {
		return answer;
	}

	public String getGroup_id() {
		return group_id;
	}
	
	
	
	

}
