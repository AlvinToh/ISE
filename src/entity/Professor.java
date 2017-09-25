package entity;

public class Professor {
    private String prof_smu_email;
    private String password;
    private int avatar_id;
	public Professor(String prof_smu_email, String password, int avatar_id) {
		this.prof_smu_email = prof_smu_email;
		this.password = password;
		this.avatar_id = avatar_id;
	}

	public String getProf_smu_email() {
		return prof_smu_email;
	}

	public String getPassword() {
		return password;
	}

	public int getAvatar_id() {
		return avatar_id;
	}
	

}
