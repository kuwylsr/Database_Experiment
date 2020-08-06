package system;

public class Administrator {

	private final String name;
	private final String password;
	
	public Administrator(String name , String password) {
		this.name = name;
		this.password = password;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getPasswd() {
		return this.password;
	}
}
