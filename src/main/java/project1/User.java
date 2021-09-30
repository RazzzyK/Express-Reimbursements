package project1;

import java.io.Serializable;

public class User implements Serializable{
	
	private String id;
	private String username;
	private String password;
	
	public User() {
		
	}
	
//	public User(String firstname, String lastname, String id) {
//		
//		
//		this.setFirstname(firstname);
//		this.setLastname(lastname);
//		this.setId(id);
//	}
	public User(String username, String password) {
		
		this.setId("somerandomid");
		this.setUsername(username);
		this.setPassword(password);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String lastname) {
		this.password = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
