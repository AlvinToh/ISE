package entity;

public class StuPresetQuestion {
	private String stu_id;
	private int question_id;
	private String answer;
	private int QA_coins;
	
	public StuPresetQuestion(String stu_id, int question_id, String answer, int qaCoins) {
		this.stu_id = stu_id;
		this.question_id = question_id;
		this.answer = answer;
		QA_coins = qaCoins;
	}

	public String getStu_id() {
		return stu_id;
	}

	public int getQuestion_id() {
		return question_id;
	}

	public String getAnswer() {
		return answer;
	}

	public int getQA_coins() {
		return QA_coins;
	}

	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}

	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public void setQA_coins(int qaCoins) {
		QA_coins = qaCoins;
	}
	
	
	
	
	

}
