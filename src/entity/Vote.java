package entity;

public class Vote {
	private int post_id;
	private int avatar_id;
	private boolean  upvote;
	private boolean downvote;
	private String timestamp;
	
	public Vote(int post_id, int avatar_id, boolean upvote, boolean downvote, String timestamp) {
		this.post_id = post_id;
		this.avatar_id = avatar_id;
		this.upvote = upvote;
		this.downvote = downvote;
		this.timestamp = timestamp;
	}
	
	

	public int getPost_id() {
		return post_id;
	}
	public int getAvatar_id() {
		return avatar_id;
	}
	public boolean isUpvote() {
		return upvote;
	}
	public boolean isDownvote() {
		return downvote;
	}
	public String getTimestamp() {
		return timestamp;
	}
	
	

}
