package application;

public class User {
	private String username,fullname,mdp;

	public User(String username, String fullname, String mdp) {
		super();
		this.username = username;
		this.fullname = fullname;
		this.mdp = mdp;
	}

	public String getUsername() {
		return username;
	}

	public void setusername(String username) {
		this.username = username;
	}

	public User() {
		super();
	}

	public String getFullname() {
		return fullname;
	}

	public void setfullname(String fullname) {
		this.fullname = fullname;
	}

	public String getMdp() {
		return mdp;
	}

	public void setmdp(String mdp) {
		this.mdp = mdp;
	}
	
	public String toString(){
		return username+" "+fullname+" "+mdp;
	}
	
}
