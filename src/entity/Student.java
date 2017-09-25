package entity;

/**
 * @author User
 *
 */
public class Student {
    private String smu_email_id;
    private String smu_email;
    private String tele_username; 
    private String group_id;
    private String password;
    private int chat_id;
    private String veri_code;
    private String temp_smu_email_address;
    private int avatar_id;
    
	public Student(String smu_email_id, String smu_email, String tele_username, String group_id, String password,
			int chat_id, String veri_code, String temp_smu_email_address,int avatar_id) {
		super();
		this.smu_email_id = smu_email_id;
		this.smu_email = smu_email;
		this.tele_username = tele_username;
		this.group_id = group_id;
		this.password = password;
		this.chat_id = chat_id;
		this.veri_code = veri_code;
		this.temp_smu_email_address = temp_smu_email_address;
		this.avatar_id = avatar_id;
	}

	public String getSmu_email_id() {
		return smu_email_id;
	}

	public String getSmu_email() {
		return smu_email;
	}

	public String getTele_username() {
		return tele_username;
	}

	public String getGroup_id() {
		return group_id;
	}

	public String getPassword() {
		return password;
	}

	public int getChat_id() {
		return chat_id;
	}

	public String getVeri_code() {
		return veri_code;
	}

	public String getTemp_smu_email_address() {
		return temp_smu_email_address;
	}
    
	public int getAvatar_id() {
		return avatar_id;
	}

	


}
