package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Student extends JFrame {

	private JPanel contentPane;
	private JTextField IDtextField;
	private JTextField NAMEtextField_1;
	private JTextField SEXtextField_2;
	private JTextField AGEtextField_3;
	private JTextField CLAIDtextField_4;
	private JTextField TIDtextField_5;

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
	public Student(String operate,JTextArea textArea) {
		Connection conn = GUI.conn;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(650, 280, 403, 283);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("学号：");
		label.setFont(new Font("宋体", Font.PLAIN, 17));
		label.setBounds(27, 29, 51, 18);
		contentPane.add(label);
		
		IDtextField = new JTextField();
		IDtextField.setBounds(76, 27, 86, 24);
		contentPane.add(IDtextField);
		IDtextField.setColumns(10);
		
		JLabel label_1 = new JLabel("姓名：");
		label_1.setFont(new Font("宋体", Font.PLAIN, 17));
		label_1.setBounds(197, 31, 51, 18);
		contentPane.add(label_1);
		
		NAMEtextField_1 = new JTextField();
		NAMEtextField_1.setColumns(10);
		NAMEtextField_1.setBounds(246, 29, 86, 24);
		contentPane.add(NAMEtextField_1);
		
		JLabel label_2 = new JLabel("性别：");
		label_2.setFont(new Font("宋体", Font.PLAIN, 17));
		label_2.setBounds(27, 82, 51, 18);
		contentPane.add(label_2);
		
		SEXtextField_2 = new JTextField();
		SEXtextField_2.setColumns(10);
		SEXtextField_2.setBounds(76, 80, 86, 24);
		contentPane.add(SEXtextField_2);
		
		JLabel label_3 = new JLabel("年龄：");
		label_3.setFont(new Font("宋体", Font.PLAIN, 17));
		label_3.setBounds(197, 78, 51, 18);
		contentPane.add(label_3);
		
		AGEtextField_3 = new JTextField();
		AGEtextField_3.setColumns(10);
		AGEtextField_3.setBounds(246, 76, 86, 24);
		contentPane.add(AGEtextField_3);
		
		JLabel label_4 = new JLabel("班号：");
		label_4.setFont(new Font("宋体", Font.PLAIN, 17));
		label_4.setBounds(27, 137, 51, 18);
		contentPane.add(label_4);
		
		CLAIDtextField_4 = new JTextField();
		CLAIDtextField_4.setColumns(10);
		CLAIDtextField_4.setBounds(76, 135, 86, 24);
		contentPane.add(CLAIDtextField_4);
		
		JLabel label_5 = new JLabel("导师：");
		label_5.setFont(new Font("宋体", Font.PLAIN, 17));
		label_5.setBounds(197, 139, 51, 18);
		contentPane.add(label_5);
		
		TIDtextField_5 = new JTextField();
		TIDtextField_5.setColumns(10);
		TIDtextField_5.setBounds(246, 137, 86, 24);
		contentPane.add(TIDtextField_5);
		
		if(operate.equals("删除数据")) {
			NAMEtextField_1.setEditable(false);
			SEXtextField_2.setEditable(false);
			AGEtextField_3.setEditable(false);
			CLAIDtextField_4.setEditable(false);
			TIDtextField_5.setEditable(false);
		}
		JButton button = new JButton("确认");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(operate.equals("删除数据")) {
					textArea.setText("");
					textArea.setSelectedTextColor(Color.RED);
					String id = IDtextField.getText();
					String sql = "DELETE FROM student WHERE SNO = ?;";
					try {
						PreparedStatement ptmt = conn.prepareStatement(sql);
						ptmt.setString(1, id);
						ptmt.execute();
						System.out.println(sql);
						if(ptmt.getUpdateCount() != 0 ) {
							textArea.append("删除学生数据成功！\n");
						}else {
							textArea.append("所要删除学生不存在！\n");
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
					String id = IDtextField.getText();
					String name = NAMEtextField_1.getText();
					String sex = SEXtextField_2.getText();
					String age = AGEtextField_3.getText();
					String claid = CLAIDtextField_4.getText();
					String tid = TIDtextField_5.getText();
					String sql = "INSERT INTO student VALUES(?,?,?,?,?,?);";
					try {
						PreparedStatement ptmt = conn.prepareStatement(sql);
						ptmt.setString(1, id);
						ptmt.setString(2, name);
						ptmt.setString(3, sex);
						ptmt.setString(4, age);
						ptmt.setString(5, claid);
						ptmt.setString(6, tid);
						textArea.setSelectedTextColor(Color.RED);
						if(id.equals("")) {
							ptmt.setObject(1, null);
							textArea.append("插入学生学号为NULL！\n");
						}
						if(name.equals("")) {
							ptmt.setObject(2, null);
							textArea.append("插入学生姓名为NULL！\n");
						}
						if(sex.equals("")) {
							ptmt.setObject(3, null);
							textArea.append("插入学生性别为NULL！\n");
						}
						if(age.equals("")) {
							ptmt.setObject(4, null);
							textArea.append("插入学生年龄为NULL！\n");
						}
						if(claid.equals("")) {
							ptmt.setObject(5, null);
							textArea.append("插入学生班级为NULL！\n");
						}
						if(tid.equals("")) {
							ptmt.setObject(6, null);
							textArea.append("插入学生导师为NULL！\n");
						}
						ptmt.execute();
						System.out.println(sql);
						textArea.append("插入学生数据成功！\n");
					} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
						textArea.setSelectedTextColor(Color.RED);
						textArea.setText("插入操作不满足数据库的完整性约束！\n");
					} catch (SQLException e1) {
						textArea.setSelectedTextColor(Color.RED);
						textArea.setText("触发器触发:"+e1.getMessage());
					}	
				}else if(operate.equals("查询数据")) {
					textArea.setText("");
					String id = IDtextField.getText();
					String name = NAMEtextField_1.getText();
					String sex = SEXtextField_2.getText();
					String age = AGEtextField_3.getText();
					String claid = CLAIDtextField_4.getText();
					String tid = TIDtextField_5.getText();
					StringBuilder sql = new StringBuilder();
					sql.append("SELECT * FROM student WHERE ");
					if(!id.equals("")) {
						sql.append("SNO = ").append("'").append(id).append("'").append(" AND ");
					}
					if(!name.equals("")) {
						sql.append("SNAME = ").append("'").append(name).append("'").append(" AND ");
					}
					if(!sex.equals("")) {
						sql.append("SSEX = ").append("'").append(sex).append("'").append(" AND ");
					}
					if(!age.equals("")) {
						sql.append("SAGE = ").append("'").append(age).append("'").append(" AND ");
					}
					if(!claid.equals("")) {
						sql.append("CLANO = ").append("'").append(claid).append("'").append(" AND ");
					}
					if(!tid.equals("")) {
						sql.append("TNO = ").append("'").append(tid).append("'").append(" AND ");
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
				get().setVisible(false);
			}
		});
		button.setFont(new Font("宋体", Font.PLAIN, 17));
		button.setBounds(135, 196, 113, 27);
		contentPane.add(button);
	}
	public Student get() {
		return this;
	}

}
