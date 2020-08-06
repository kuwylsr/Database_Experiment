package testSQL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {

	public static void JDBCexample(String dbid,String userid,String passwd) {
		try {
			String driverClass = "com.mysql.cj.jdbc.Driver";
			Class.forName(driverClass); //加载数据库驱动
			
			Connection conn = DriverManager.getConnection(dbid, userid, passwd);//加载数据库驱动，建立数据库连接
			
			Statement stmt = conn.createStatement();//创建一个语句对象
			
			//进行SQL语句的执行与处理工作
			String str1 = "2012-1-21";
			String str2 = "0000-01-21";
		    java.sql.Date sqlDate1 = java.sql.Date.valueOf(str1);
		    java.sql.Date sqlDate2 = java.sql.Date.valueOf(str2);
			String sql = "insert into test values (?)";
			
			PreparedStatement ptmt = conn.prepareStatement(sql);
			ptmt.setDate(1, sqlDate1);
//			ptmt.executeUpdate();
			ptmt.setDate(1, sqlDate2);
			//ptmt.executeUpdate();
			
			ptmt.close();
			
//			Date date = new Date(0011, 00, 11);
//			stmt.executeQuery("insert into test values ('0000-00-01')");
			
			ResultSet rset = stmt.executeQuery("SELECT ENAME FROM project P, works_on W, employee E WHERE P.PNO = W.PNO AND W.ESSN = E.ESSN AND P.PNAME = 'SQL Project'");
			while(rset.next()) {
				System.out.println(rset.getMetaData());
				System.out.println(rset.getMetaData().getColumnTypeName(1));
				System.out.println(rset.getMetaData().getColumnName(1));
				System.out.println(rset.getMetaData().getColumnLabel(1));

			}
			stmt.close();
			conn.close();
		} catch (SQLException | ClassNotFoundException sqle) {
			System.out.println("SQLException:" + sqle);
		}
	}
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost/company?user=soft&password=soft1234&useUnicode=true&characterEncoding=8859_1&serverTimezone=GMT";
		String user = "root";
		String password = "woyaojiayou";
		JDBCexample(url, user, password);
	}

}
