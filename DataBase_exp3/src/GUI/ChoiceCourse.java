package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

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

public class ChoiceCourse extends JFrame {

	private JPanel contentPane;
	private JTextField IDField;
	private JTextField CIDField_1;
	private JTextField GradeField_2;
	private JButton button;


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
	public ChoiceCourse(String operate,JTextArea textArea) {
		Connection conn = GUI.conn;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(650, 280, 403, 216);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		IDField = new JTextField();
		IDField.setColumns(10);
		IDField.setBounds(63, 29, 86, 24);
		contentPane.add(IDField);
		
		JLabel label = new JLabel("学号：");
		label.setFont(new Font("宋体", Font.PLAIN, 17));
		label.setBounds(14, 31, 51, 18);
		contentPane.add(label);
		
		CIDField_1 = new JTextField();
		CIDField_1.setColumns(10);
		CIDField_1.setBounds(256, 29, 86, 24);
		contentPane.add(CIDField_1);
		
		JLabel label_1 = new JLabel("课程编号：");
		label_1.setFont(new Font("宋体", Font.PLAIN, 17));
		label_1.setBounds(176, 31, 95, 18);
		contentPane.add(label_1);
		
		GradeField_2 = new JTextField();
		GradeField_2.setColumns(10);
		GradeField_2.setBounds(63, 79, 86, 24);
		contentPane.add(GradeField_2);
		
		JLabel label_2 = new JLabel("成绩：");
		label_2.setFont(new Font("宋体", Font.PLAIN, 17));
		label_2.setBounds(14, 81, 51, 18);
		contentPane.add(label_2);
		
		if(operate.equals("删除数据")) {
			GradeField_2.setEditable(false);
		}
		
		button = new JButton("确认");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(operate.equals("删除数据")) {
				textArea.setText("");
				textArea.setSelectedTextColor(Color.RED);
				String id = IDField.getText();
				String cid = CIDField_1.getText();
				String sql = "DELETE FROM sc WHERE SNO = ? AND CNO = ?;";
				try {
					PreparedStatement ptmt = conn.prepareStatement(sql);
					ptmt.setString(1, id);
					ptmt.setString(2, cid);
					ptmt.execute();
					System.out.println(sql);
					if(ptmt.getUpdateCount() != 0 ) {
						textArea.append("删除选课数据成功！\n");
					}else {
						textArea.append("所要删除的选课数据不存在！\n");
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
					String cid = CIDField_1.getText();
					String grade = GradeField_2.getText();
					String sql = "INSERT INTO sc VALUES(?,?,?);";
					try {
						PreparedStatement ptmt = conn.prepareStatement(sql);
						ptmt.setString(1, id);
						ptmt.setString(2, cid);
						ptmt.setString(3, grade);
						textArea.setSelectedTextColor(Color.RED);
						if(id.equals("")) {
							ptmt.setObject(1, null);
							textArea.append("插入学生学号为NULL！\n");
						}
						if(cid.equals("")) {
							ptmt.setObject(2, null);
							textArea.append("插入课程编号为NULL！\n");
						}
						if(grade.equals("")) {
							ptmt.setObject(3, null);
							textArea.append("插入学生成绩为NULL！\n");
						}
						ptmt.execute();
						System.out.println(sql);
						textArea.append("插入学生选课数据成功！\n");
					} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
						textArea.setSelectedTextColor(Color.RED);
						textArea.setText("插入操作不满足数据库的完整性约束！\n");
					} catch(SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else if(operate.equals("查询数据")) {
					textArea.setText("");
					String id = IDField.getText();
					String cid = CIDField_1.getText();
					String grade = GradeField_2.getText();
					StringBuilder sql = new StringBuilder();
					sql.append("SELECT * FROM sc WHERE ");
					if(!id.equals("")) {
						sql.append("SNO = ").append("'").append(id).append("'").append(" AND ");
					}
					if(!cid.equals("")) {
						sql.append("CNO = ").append("'").append(cid).append("'").append(" AND ");
					}
					if(!grade.equals("")) {
						sql.append("GRADE = ").append("'").append(grade).append("'").append(" AND ");
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
		button.setBounds(136, 129, 113, 27);
		contentPane.add(button);
	}

}
