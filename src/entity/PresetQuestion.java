package entity;

public class PresetQuestion {
	private int Question_ID;
	private String term;
	private int week;
	private String topic;
	private String question;
	private String answer;
	private int QA_coins;
	private String prof_id;
	
	
	public PresetQuestion(int question_ID, String term, int week, String topic, String question, String answer,
			int qA_coins, String prof_id) {
		Question_ID = question_ID;
		this.term = term;
		this.week = week;
		this.topic = topic;
		this.question = question;
		this.answer = answer;
		QA_coins = qA_coins;
		this.prof_id = prof_id;
	}
	public int getQuestion_ID() {
		return Question_ID;
	}
	public String getTerm() {
		return term;
	}
	public int getWeek() {
		return week;
	}
	public String getTopic() {
		return topic;
	}
	public String getQuestion() {
		return question;
	}
	public String getAnswer() {
		return answer;
	}
	public int getQA_coins() {
		return QA_coins;
	}
	public String getProf_id() {
		return prof_id;
	}
	public void setQuestion_ID(int question_ID) {
		Question_ID = question_ID;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public void setQA_coins(int qA_coins) {
		QA_coins = qA_coins;
	}
	public void setProf_id(String prof_id) {
		this.prof_id = prof_id;
	}

}
