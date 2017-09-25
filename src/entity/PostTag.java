package entity;

public class PostTag {
	private int post_id;
	private int tag_id;
	private float association;
	
	public PostTag(int post_id, int tag_id, float association) {
		this.post_id = post_id;
		this.tag_id = tag_id;
		this.association = association;
	}

	public int getPost_id() {
		return post_id;
	}

	public int getTag_id() {
		return tag_id;
	}

	public float getAssociation() {
		return association;
	}
	
	

}
