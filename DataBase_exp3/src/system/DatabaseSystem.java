package system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;



public class DatabaseSystem {
	
	private Set<Student> SetStudent = new HashSet<>();
	private Set<Administrator> SetAdministrator = new HashSet<>();
	

	public DatabaseSystem init(DatabaseSystem system) throws IOException {
		
		File file = new File("src/system/passwd.txt");
		FileReader in = new FileReader(file);
		BufferedReader reader = new BufferedReader(in);
		String line = null;
		int flag = 0;
		while((line = reader.readLine()) != null) {
			if(line.equals("学生")) {
				flag = 1;
				continue;
			}else if(line.equals("教务人员")) {
				flag = 2;
				continue;
			}
			if((flag == 1)&&(!line.equals(""))) {
				String[] content = line.split("\t");
				Student u = new Student(content[0].split(":")[1], content[1].split(":")[1]);
				system.SetStudent.add(u);
			}else if((flag == 2)&&(!line.equals(""))) {
				String[] content = line.split("\t");
				Administrator w = new Administrator(content[0].split(":")[1], content[1].split(":")[1]);
				system.SetAdministrator.add(w);
			}
		}
		return system;
	}
	
	public Student StudentLogin(String name,String password) {
		Iterator<Student> it = SetStudent.iterator(); 
		while(it.hasNext()) {
			Student s = it.next();
			if(s.getName().equals(name)&&s.getPasswd().equals(password)) {
				return s;
			}
		}
		return null;
	}
	
	public Administrator AdministratorLogin(String name,String password) {
		Iterator<Administrator> it = SetAdministrator.iterator(); 
		while(it.hasNext()) {
			Administrator a = it.next();
			if(a.getName().equals(name)&&a.getPasswd().equals(password)) {
				return a;
			}
		}
		return null;
	}
	
	public Set<Student> getUsers(){
		return this.SetStudent;
	}
	public Set<Administrator> getMoneySystem() {
		return this.SetAdministrator;
	}
	
	public static void main(String[] args) throws IOException {
		DatabaseSystem bank = new DatabaseSystem();
		bank.init(bank);
		System.out.println(bank.getMoneySystem());
	}
}
