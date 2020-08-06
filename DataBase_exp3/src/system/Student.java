package system;

public class Student {
	
	private final String name;
	private final String password;
	
	public Student(String name , String password) {
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
