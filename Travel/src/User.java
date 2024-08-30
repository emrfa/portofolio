public class User {

	private String userId;
	private String username;
	private String userEmail;
	private String userPassword;
	private String userGender;

	
	public User(String userId, String username, String userEmail, String userPassword, String userGender) {
		// TODO Auto-generated constructor stub
		this.userId = userId;
		this.username = username;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userGender = userGender;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	} 
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserGender() {
		return userGender;
	}
	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

}
