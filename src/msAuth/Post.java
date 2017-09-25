package msAuth;

public class Post {
	private int avatar_id;
	private int parent_id;
	private int level;
	private int post_id;
	private String post_title;
	private String post_content;
	private boolean is_question;
	private boolean is_bot;
	private boolean is_qa_bountiful;
	private String timestamp;
	private int time_limit_qa;
	private int time_limit_bot;
	private float qa_coin_basic;
	private float qa_coin_bounty;
	private float thoughfulness_score;
	private boolean no_show;
	private int previous_version;
	private int number_of_upvotes;
	private int number_of_downvotes;
	
	public Post(int avatar_id, int parent_id, int level, int post_id, String post_title, String post_content,
			boolean is_question, boolean is_bot, boolean is_qa_bountiful, String timestamp, int time_limit_qa,
			int time_limit_bot, float qa_coin_basic, float qa_coin_bounty, float thoughfulness_score, boolean no_show,
			int previous_version, int number_of_upvotes, int number_of_downvotes) {
	
		this.avatar_id = avatar_id;
		this.parent_id = parent_id;
		this.level = level;
		this.post_id = post_id;
		this.post_title = post_title;
		this.post_content = post_content;
		this.is_question = is_question;
		this.is_bot = is_bot;
		this.is_qa_bountiful = is_qa_bountiful;
		this.timestamp = timestamp;
		this.time_limit_qa = time_limit_qa;
		this.time_limit_bot = time_limit_bot;
		this.qa_coin_basic = qa_coin_basic;
		this.qa_coin_bounty = qa_coin_bounty;
		this.thoughfulness_score = thoughfulness_score;
		this.no_show = no_show;
		this.previous_version = previous_version;
		this.number_of_upvotes = number_of_upvotes;
		this.number_of_downvotes = number_of_downvotes;
	}

	public int getAvatar_id() {
		return avatar_id;
	}

	public int getParent_id() {
		return parent_id;
	}

	public int getLevel() {
		return level;
	}

	public int getPost_id() {
		return post_id;
	}

	public String getPost_title() {
		return post_title;
	}

	public String getPost_content() {
		return post_content;
	}

	public boolean isIs_question() {
		return is_question;
	}

	public boolean isIs_bot() {
		return is_bot;
	}

	public boolean isIs_qa_bountiful() {
		return is_qa_bountiful;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public int getTime_limit_qa() {
		return time_limit_qa;
	}

	public int getTime_limit_bot() {
		return time_limit_bot;
	}

	public float getQa_coin_basic() {
		return qa_coin_basic;
	}

	public float getQa_coin_bounty() {
		return qa_coin_bounty;
	}

	public float getThoughfulness_score() {
		return thoughfulness_score;
	}

	public boolean isNo_show() {
		return no_show;
	}

	public int getPrevious_version() {
		return previous_version;
	}

	public int getNumber_of_upvotes() {
		return number_of_upvotes;
	}

	public int getNumber_of_downvotes() {
		return number_of_downvotes;
	} 
	
	
	
	


}
