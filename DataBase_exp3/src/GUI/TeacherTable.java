package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.jar.Attributes.Name;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TeacherTable extends JFrame {

	private JPanel contentPane;
	private JTextField IDField;
	private JTextField TNAMEField_1;
	private JTextField SalaryField_2;
	private JTextField AgeField_3;
	private JTextField GreField_4;
	private JTextField DegField_5;
	private JTextField DateField_6;
	private JTextField DnoField_7;


	public void getSqlputTextArea(ResultSet rset,JTextArea textArea) throws SQLException {
		ResultSetMetaData rsmd = rset.getMetaData();
		textArea.setText("");
		if(!rset.next()) {
			textArea.append("未找到相关内容！");
		}else {
			rset.previous();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				String columnName = rsmd.getColumnName(i);
				textArea.append(columnName + "\t");
			}
			textArea.append("\n");

			while (rset.next()) {
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					textArea.append(rset.getString(i) + "\t");
				}
				textArea.append("\n");
			}
		}
	}
	
	/**
	 * Create the frame.
	 */
	public TeacherTable(String operate,JTextArea textArea) {
		Connection conn = GUI.conn;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(650, 280, 474, 340);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("职工号：");
		label.setFont(new Font("宋体", Font.PLAIN, 17));
		label.setBounds(33, 38, 80, 18);
		contentPane.add(label);
		
		IDField = new JTextField();
		IDField.setColumns(10);
		IDField.setBounds(117, 32, 86, 24);
		contentPane.add(IDField);
		
		JLabel label_1 = new JLabel("姓名：");
		label_1.setFont(new Font("宋体", Font.PLAIN, 17));
		label_1.setBounds(252, 34, 51, 18);
		contentPane.add(label_1);
		
		TNAMEField_1 = new JTextField();
		TNAMEField_1.setColumns(10);
		TNAMEField_1.setBounds(301, 32, 86, 24);
		contentPane.add(TNAMEField_1);
		
		JLabel label_2 = new JLabel("工资：");
		label_2.setFont(new Font("宋体", Font.PLAIN, 17));
		label_2.setBounds(33, 83, 64, 18);
		contentPane.add(label_2);
		
		SalaryField_2 = new JTextField();
		SalaryField_2.setColumns(10);
		SalaryField_2.setBounds(117, 77, 86, 24);
		contentPane.add(SalaryField_2);
		
		JLabel label_3 = new JLabel("年龄：");
		label_3.setFont(new Font("宋体", Font.PLAIN, 17));
		label_3.setBounds(252, 79, 51, 18);
		contentPane.add(label_3);
		
		AgeField_3 = new JTextField();
		AgeField_3.setColumns(10);
		AgeField_3.setBounds(301, 77, 86, 24);
		contentPane.add(AgeField_3);
		
		JLabel label_4 = new JLabel("毕业院校：");
		label_4.setFont(new Font("宋体", Font.PLAIN, 17));
		label_4.setBounds(33, 138, 86, 18);
		contentPane.add(label_4);
		
		GreField_4 = new JTextField();
		GreField_4.setColumns(10);
		GreField_4.setBounds(117, 132, 86, 24);
		contentPane.add(GreField_4);
		
		JLabel label_5 = new JLabel("学位：");
		label_5.setFont(new Font("宋体", Font.PLAIN, 17));
		label_5.setBounds(252, 134, 51, 18);
		contentPane.add(label_5);
		
		DegField_5 = new JTextField();
		DegField_5.setColumns(10);
		DegField_5.setBounds(301, 132, 86, 24);
		contentPane.add(DegField_5);
		
		DateField_6 = new JTextField();
		DateField_6.setColumns(10);
		DateField_6.setBounds(117, 189, 86, 24);
		contentPane.add(DateField_6);
		
		JLabel label_7 = new JLabel("系号：");
		label_7.setFont(new Font("宋体", Font.PLAIN, 17));
		label_7.setBounds(252, 191, 51, 18);
		contentPane.add(label_7);
		
		DnoField_7 = new JTextField();
		DnoField_7.setColumns(10);
		DnoField_7.setBounds(301, 189, 86, 24);
		contentPane.add(DnoField_7);
		
		if(operate.equals("删除数据")) {
			TNAMEField_1.setEditable(false);
			SalaryField_2.setEditable(false);
			AgeField_3.setEditable(false);
			GreField_4.setEditable(false);
			DegField_5.setEditable(false);
			DateField_6.setEditable(false);
			DnoField_7.setEditable(false);
		}
		
		JButton button = new JButton("确认");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(operate.equals("删除数据")) {
					textArea.setText("");
					textArea.setSelectedTextColor(Color.RED);
					String id = IDField.getText();
					String sql = "DELETE FROM teacher WHERE TNO = ?;";
					try {
						PreparedStatement ptmt = conn.prepareStatement(sql);
						ptmt.setString(1, id);
						ptmt.execute();
						System.out.println(sql);
						if(ptmt.getUpdateCount() != 0 ) {
							textArea.append("删除教师数据成功！\n");
						}else {
							textArea.append("所要删除教师不存在！\n");
						}
					} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
						textArea.setSelectedTextColor(Color.RED);
						textArea.setText("删除操作不满足数据库的完整性约束！\n");
					}catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
				}else if(operate.equals("插入数据")) {
					textArea.setText("");
					String id = IDField.getText();
					String name = TNAMEField_1.getText();
					String salary = SalaryField_2.getText();
					String age = AgeField_3.getText();
					String greduate = GreField_4.getText();
					String degree = DegField_5.getText();
					String date = DateField_6.getText();
					String dno = DnoField_7.getText();
					String sql = "INSERT INTO teacher VALUES(?,?,?,?,?,?,?,?);";
					try {
						PreparedStatement ptmt = conn.prepareStatement(sql);
						ptmt.setString(1, id);
						ptmt.setString(2, name);
						ptmt.setString(3, salary);
						ptmt.setString(4, age);
						ptmt.setString(5, greduate);
						ptmt.setString(6, degree);
						ptmt.setString(7, date);
						ptmt.setString(8, dno);
						textArea.setSelectedTextColor(Color.RED);
						if(id.equals("")) {
							ptmt.setObject(1, null);
							textArea.append("插入教师职工号为NULL！\n");
						}
						if(name.equals("")) {
							ptmt.setObject(2, null);
							textArea.append("插入教师姓名为NULL！\n");
						}
						if(salary.equals("")) {
							ptmt.setObject(3, null);
							textArea.append("插入教师工资为NULL！\n");
						}
						if(age.equals("")) {
							ptmt.setObject(4, null);
							textArea.append("插入教师年龄为NULL！\n");
						}
						if(greduate.equals("")) {
							ptmt.setObject(5, null);
							textArea.append("插入教师毕业院校为NULL！\n");
						}
						if(degree.equals("")) {
							ptmt.setObject(6, null);
							textArea.append("插入教师学位为NULL！\n");
						}
						if(date.equals("")) {
							ptmt.setObject(7, null);
							textArea.append("插入教师毕业日期为NULL！\n");
						}
						if(dno.equals("")) {
							ptmt.setObject(8, null);
							textArea.append("插入教师所属系为NULL！\n");
						}
						ptmt.execute();
						System.out.println(sql);
						textArea.append("插入教师数据成功！\n");
					} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
						textArea.setSelectedTextColor(Color.RED);
						textArea.setText("插入操作不满足数据库的完整性约束！\n");
					} catch (SQLException e1) {
						textArea.setSelectedTextColor(Color.RED);
						textArea.setText("触发器触发:"+e1.getMessage());
					}	
				}else if(operate.equals("查询数据")) {
					textArea.setText("");
					String id = IDField.getText();
					String name = TNAMEField_1.getText();
					String salary = SalaryField_2.getText();
					String age = AgeField_3.getText();
					String greduate = GreField_4.getText();
					String degree = DegField_5.getText();
					String date = DateField_6.getText();
					String dno = DnoField_7.getText();
					StringBuilder sql = new StringBuilder();
					sql.append("SELECT * FROM teacher WHERE ");
					if(!id.equals("")) {
						sql.append("TNO = ").append("'").append(id).append("'").append(" AND ");
					}
					if(!name.equals("")) {
						sql.append("TNAME = ").append("'").append(name).append("'").append(" AND ");
					}
					if(!salary.equals("")) {
						sql.append("SALARY = ").append("'").append(salary).append("'").append(" AND ");
					}
					if(!age.equals("")) {
						sql.append("TAGE = ").append("'").append(age).append("'").append(" AND ");
					}
					if(!greduate.equals("")) {
						sql.append("TGRA = ").append("'").append(greduate).append("'").append(" AND ");
					}
					if(!degree.equals("")) {
						sql.append("GEGREE = ").append("'").append(degree).append("'").append(" AND ");
					}
					if(!date.equals("")) {
						sql.append("EMPDATE = ").append("'").append(date).append("'").append(" AND ");
					}
					if(!dno.equals("")) {
						sql.append("DNO = ").append("'").append(dno).append("'").append(" AND ");
					}
					sql.delete(sql.length()-4, sql.length());
					sql.append(";");
					try {
						PreparedStatement ptmt = conn.prepareStatement(sql.toString());
						ResultSet rset = ptmt.executeQuery();
						System.out.println(sql);
						getSqlputTextArea(rset, textArea);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				setVisible(false);
			}
		});
		button.setFont(new Font("宋体", Font.PLAIN, 17));
		button.setBounds(170, 246, 113, 27);
		contentPane.add(button);
		
		JLabel label_6 = new JLabel("聘用日期：");
		label_6.setFont(new Font("宋体", Font.PLAIN, 17));
		label_6.setBounds(33, 195, 86, 18);
		contentPane.add(label_6);
		
		
	}

}
