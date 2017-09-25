package entity;

public class Avatar {
	private int avatar_id;
	private String avatar_name;
	private String icon;
	private float avatar_qa_coin;
	private float thoughtfulness_score;
	private String section_id;
	private boolean is_bot;
	public Avatar(int avatar_id, String avatar_name, String icon, float avatar_qa_coin, float thoughtfulness_score,
			String section_id, boolean is_bot) {
		super();
		this.avatar_id = avatar_id;
		this.avatar_name = avatar_name;
		this.icon = icon;
		this.avatar_qa_coin = avatar_qa_coin;
		this.thoughtfulness_score = thoughtfulness_score;
		this.section_id = section_id;
		this.is_bot = is_bot;
	}
	public int getAvatar_id() {
		return avatar_id;
	}
	public String getAvatar_name() {
		return avatar_name;
	}
	public String getIcon() {
		return icon;
	}
	public float getAvatar_qa_coin() {
		return avatar_qa_coin;
	}
	public float getThoughtfulness_score() {
		return thoughtfulness_score;
	}
	public String getSection_id() {
		return section_id;
	}
	public boolean isIs_bot() {
		return is_bot;
	}
	
	
}
