package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClassTable extends JFrame {

	private JPanel contentPane;
	private JTextField clanoField;
	private JTextField dnoField_1;
	private JTextField stunoField_2;
	private JTextField buDateField_3;

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
	public ClassTable(String operate,JTextArea textArea) {
		Connection conn = GUI.conn;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(650, 280, 447, 223);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		clanoField = new JTextField();
		clanoField.setColumns(10);
		clanoField.setBounds(98, 24, 86, 24);
		contentPane.add(clanoField);
		
		JLabel label = new JLabel("班号：");
		label.setFont(new Font("宋体", Font.PLAIN, 17));
		label.setBounds(49, 26, 51, 18);
		contentPane.add(label);
		
		dnoField_1 = new JTextField();
		dnoField_1.setColumns(10);
		dnoField_1.setBounds(291, 24, 86, 24);
		contentPane.add(dnoField_1);
		
		JLabel label_1 = new JLabel("系编号：");
		label_1.setFont(new Font("宋体", Font.PLAIN, 17));
		label_1.setBounds(211, 26, 95, 18);
		contentPane.add(label_1);
		
		stunoField_2 = new JTextField();
		stunoField_2.setColumns(10);
		stunoField_2.setBounds(98, 74, 86, 24);
		contentPane.add(stunoField_2);
		
		JLabel label_2 = new JLabel("人数：");
		label_2.setFont(new Font("宋体", Font.PLAIN, 17));
		label_2.setBounds(49, 76, 51, 18);
		contentPane.add(label_2);
		
		buDateField_3 = new JTextField();
		buDateField_3.setColumns(10);
		buDateField_3.setBounds(291, 74, 86, 24);
		contentPane.add(buDateField_3);
		
		JLabel label_3 = new JLabel("组建日期：");
		label_3.setFont(new Font("宋体", Font.PLAIN, 17));
		label_3.setBounds(211, 76, 95, 18);
		contentPane.add(label_3);
		
		if(operate.equals("删除数据")) {
			dnoField_1.setEditable(false);
			buDateField_3.setEditable(false);
			stunoField_2.setEditable(false);
		}
		
		JButton button = new JButton("确认");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(operate.equals("删除数据")) {
					textArea.setText("");
					textArea.setSelectedTextColor(Color.RED);
					String clano = clanoField.getText();
					String sql = "DELETE FROM class WHERE CLANO = ?;";
					try {
						PreparedStatement ptmt = conn.prepareStatement(sql);
						ptmt.setString(1, clano);
						ptmt.execute();
						System.out.println(sql);
						if(ptmt.getUpdateCount() != 0 ) {
							textArea.append("删除班级数据成功！\n");
						}else {
							textArea.append("所要删除的班级数据不存在！\n");
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
						String clano = clanoField.getText();
						String dno = dnoField_1.getText();
						String budate = buDateField_3.getText();
						String stuno = stunoField_2.getText();
						String sql = "INSERT INTO class VALUES(?,?,?,?);";
						try {
							PreparedStatement ptmt = conn.prepareStatement(sql);
							ptmt.setString(1, clano);
							ptmt.setString(2, dno);
							ptmt.setString(3, budate);
							ptmt.setString(4, stuno);
							textArea.setSelectedTextColor(Color.RED);
							if(clano.equals("")) {
								ptmt.setObject(1, null);
								textArea.append("插入班级班号为NULL！\n");
							}
							if(dno.equals("")) {
								ptmt.setObject(2, null);
								textArea.append("插入系别编号为NULL！\n");
							}
							if(budate.equals("")) {
								Calendar cal=Calendar.getInstance();  
								ptmt.setDate(3, java.sql.Date.valueOf(cal.get(Calendar.YEAR)+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.HOUR_OF_DAY)));
								textArea.append("插入组建日期为NULL！自动填为当前日期\n");
							}
							if(stuno.equals("")) {
								ptmt.setInt(4, 0);
								textArea.append("插入学生人数为NULL！自动填为0\n");
							}
							ptmt.execute();
							System.out.println(sql);
							textArea.append("插入班级数据成功！\n");
						} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
							textArea.setSelectedTextColor(Color.RED);
							textArea.setText("插入操作不满足数据库的完整性约束！\n");
						} catch(SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}else if(operate.equals("查询数据")) {
						textArea.setText("");
						String clano = clanoField.getText();
						String dno = dnoField_1.getText();
						String budate = buDateField_3.getText();
						String stuno = stunoField_2.getText();
						StringBuilder sql = new StringBuilder();
						sql.append("SELECT * FROM class WHERE ");
						if(!clano.equals("")) {
							sql.append("CLANO = ").append("'").append(clano).append("'").append(" AND ");
						}
						if(!dno.equals("")) {
							sql.append("DNO = ").append("'").append(dno).append("'").append(" AND ");
						}
						if(!budate.equals("")) {
							sql.append("BDATE = ").append("'").append(budate).append("'").append(" AND ");
						}
						if(!stuno.equals("")) {
							sql.append("STUNUM = ").append("'").append(stuno).append("'").append(" AND ");
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
		button.setBounds(161, 125, 113, 27);
		contentPane.add(button);
		
		
	}
}
