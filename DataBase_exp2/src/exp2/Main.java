package exp2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	private static Scanner in;

	public static void printTips() {
		System.out.println("**************************************************");
		System.out.println("| please input the instructions like:             |");
		System.out.println("|==> company_query -q <Number> -p [Parameters] <==|");
		System.out.println("| if want to refer to Parameters ,please input:P  |");
		System.out.println("| if want to exit system ,please input:exit       |");
		System.out.println("**************************************************");
	}

	public static void printParameters() {
		System.out.println("| 1. 项目编号%PNO%");
		System.out.println("| 2. 项目名%PNAME%");
		System.out.println("| 3. 部门名%DNAME%");
		System.out.println("| 4. 工资%SALARY%、部门名%DNAME%");
		System.out.println("| 5. 项目编号%PNO%");
		System.out.println("| 6. 领导名%ENAME%");
		System.out.println("| 7. 项目编号%PNO1%和%PNO2%");
		System.out.println("| 8. 工资%SALARY%");
		System.out.println("| 9. 项目数%N%、工作总时间%HOURS%");
	}

	public static Connection connectDataBase(String url, String user, String passwd) {
		String driverClass = "com.mysql.cj.jdbc.Driver";
		try {
			Class.forName(driverClass);

			Connection conn = DriverManager.getConnection(url, user, passwd);
			return conn;

		} catch (ClassNotFoundException | SQLException sqle) {
			System.out.println("SQLException:" + sqle);
			System.exit(1);
			return null;
		}
	}

	public static void RequestQuery(int num, Connection conn, String parameters) throws SQLException {
		String[] parametersTemp = parameters.split("\t");
		int len = parametersTemp.length;
		String sql = "";
		if (num == 1) {
			sql = "SELECT ESSN FROM works_on W WHERE PNO = ?";
		} else if (num == 2) {
			sql = "SELECT ENAME FROM project P, works_on W, employee E "
					+ "WHERE P.PNO = W.PNO AND W.ESSN = E.ESSN AND P.PNAME = ?";
		} else if (num == 3) {
			sql = "SELECT ENAME, ADDRESS FROM department D, employee E "
					+ "WHERE D.DNAME = ? AND D.DNO = E.DNO";
		} else if (num == 4) {
			sql = "SELECT ENAME, ADDRESS FROM department D, employee E "
					+ "WHERE D.DNO = E.DNO AND SALARY < ? AND DNAME = ?";
		} else if (num == 5) {
			sql = "SELECT ENAME FROM project P, employee E, works_on W "
					+ "WHERE W.PNO = P.PNO AND W.ESSN = E.ESSN AND W.ESSN NOT IN "
					+ "(SELECT W1.ESSN FROM employee E1, project P1, works_on W1 "
					+ "WHERE W1.PNO = P1.PNO AND W1.ESSN = E1.ESSN AND P1.PNO = ?)";
		} else if (num == 6) {
			sql = "SELECT E1.ENAME, DNAME FROM employee E1, employee E2, department D "
					+ "WHERE E1.SUPERSSN = E2.ESSN AND E2.ENAME = ? AND E1.DNO = D.DNO";
		} else if (num == 7) {
			sql = "SELECT W1.ESSN FROM works_on W1, works_on W2 "
					+ "WHERE W1.ESSN = W2.ESSN AND W1.PNO = ? AND W2.PNO = ?";
		} else if (num == 8) {
			sql = "SELECT DNAME FROM employee E,department D "
					+ "WHERE E.DNO = D.DNO GROUP BY E.DNO HAVING AVG(SALARY) < ?";
		} else if (num == 9) {
			sql = "SELECT ENAME FROM works_on W,employee E "
					+ "WHERE W.ESSN = E.ESSN GROUP BY W.ESSN HAVING COUNT(*) >= ? AND SUM(HOURS) <= ?";
		}

		PreparedStatement ptmt = conn.prepareStatement(sql);
		for (int i = 1; i <= len; i++) {
			ptmt.setString(i, parametersTemp[i - 1]);
		}

		ResultSet rset = ptmt.executeQuery();
		ResultSetMetaData rsmd = rset.getMetaData();

		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			String columnName = rsmd.getColumnName(i);
			System.out.print(columnName + "\t");
		}
		System.out.println();

		while (rset.next()) {
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				System.out.print(rset.getString(i) + "\t");
			}
			System.out.println();
		}
	}

	public static boolean IfMatch(String query) {
		String pattern = "company_query -q \\<[0-9]\\> -p \\[[\u4e00-\u9fa5,\\w,\\t]+\\]";
		Pattern p = Pattern.compile(pattern);
		Matcher mr = p.matcher(query);
		return mr.find();
	}

	public static void main(String[] args) throws SQLException {
		String url = "jdbc:mysql://localhost/company?user=soft&password=soft1234&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT";
		String user = "root";
		String password = "woyaojiayou";
		Connection conn = connectDataBase(url, user, password);
		in = new Scanner(System.in);
		while (true) {
			printTips();
			String query = in.nextLine();

			if (query.equals("exit")) { // 退出程序
				conn.close();
				System.exit(0);
			} else if (query.equals("P")) { // 显示参数菜单
				printParameters();
			} else if (IfMatch(query)) { // matcher
				int Number = Integer.parseInt((query.split("[<,>]")[1]));
				String Parameters = query.split("[\\[,\\]]")[1];
				System.out.println("The result of query is :");
				if (Number >= 1 && Number <= 9 && (int) Number == Number) {
					RequestQuery(Number, conn, Parameters);
				} else {
					System.out.println("Input error!");
					continue;
				}
			} else {
				System.out.println("Input error!");
				continue;
			}
		}
	}

}
