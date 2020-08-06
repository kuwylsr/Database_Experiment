package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class AdvanceQuery extends JFrame {

	private JPanel contentPane;

	public void getSqlputTextArea(ResultSet rset, JTextArea textArea) throws SQLException {
		ResultSetMetaData rsmd = rset.getMetaData();
		textArea.setText("");
		if (!rset.next()) {
			textArea.append("未找到相关内容！");
		} else {
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
	public AdvanceQuery(String operate, JTextArea textArea) {
		Connection conn = GUI.conn;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(650, 350, 533, 274);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("有两门以上优秀课程同学的学号及平均成绩");
		label.setFont(new Font("宋体", Font.PLAIN, 18));
		label.setBounds(125, 16, 376, 18);
		contentPane.add(label);

		JButton button = new JButton("查询1：");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "SELECT SNO , AVG(GRADE) FROM SC WHERE SNO IN ( SELECT SNO FROM SC WHERE GRADE < 90 GROUP BY SNO HAVING COUNT(*) >=2 )GROUP BY SNO;";
				try {
					PreparedStatement ptmt = conn.prepareStatement(sql);
					ResultSet rset = ptmt.executeQuery();
					System.out.println(sql);
					getSqlputTextArea(rset, textArea);
					setVisible(false);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button.setFont(new Font("宋体", Font.PLAIN, 17));
		button.setBounds(14, 13, 108, 27);
		contentPane.add(button);

		JButton button_1 = new JButton("查询2：");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "SELECT DISTINCT S1.SNO ,SNAME FROM SC S1,student WHERE S1.SNO = student.SNO AND NOT EXISTS (SELECT * FROM SC WHERE SC.CNO = '002' AND SC.SNO = S1.SNO);";
				try {
					PreparedStatement ptmt = conn.prepareStatement(sql);
					ResultSet rset = ptmt.executeQuery();
					System.out.println(sql);
					getSqlputTextArea(rset, textArea);
					setVisible(false);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_1.setFont(new Font("宋体", Font.PLAIN, 17));
		button_1.setBounds(14, 53, 108, 27);
		contentPane.add(button_1);

		JLabel label_1 = new JLabel("没学过002号课程的学生信息");
		label_1.setFont(new Font("宋体", Font.PLAIN, 18));
		label_1.setBounds(125, 56, 291, 18);
		contentPane.add(label_1);

		JButton button_2 = new JButton("查询3：");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "SELECT student.SNO , SNAME , AVG(SC.GRADE) FROM student , SC WHERE student.SNO = SC.SNO GROUP BY SNO HAVING  AVG(SC.GRADE)>= ALL(SELECT AVG(SC1.GRADE) FROM SC SC1 GROUP BY SNO);";
				try {
					PreparedStatement ptmt = conn.prepareStatement(sql);
					ResultSet rset = ptmt.executeQuery();
					System.out.println(sql);
					getSqlputTextArea(rset, textArea);
					setVisible(false);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_2.setFont(new Font("宋体", Font.PLAIN, 17));
		button_2.setBounds(14, 93, 108, 27);
		contentPane.add(button_2);

		JLabel label_2 = new JLabel("找出平均成绩最高的学生");
		label_2.setFont(new Font("宋体", Font.PLAIN, 18));
		label_2.setBounds(125, 96, 291, 18);
		contentPane.add(label_2);

		JButton button_3 = new JButton("查询4：");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "SELECT S.SNAME FROM student S WHERE NOT EXISTS ( SELECT * FROM course C1, teacher T1 WHERE C1.TNO = T1.TNO AND T1.TNAME = '李老师' AND NOT EXISTS ( SELECT * FROM SC SC1 WHERE SC1.CNO = C1.CNO AND SC1.SNO = S.SNO ));";
				try {
					PreparedStatement ptmt = conn.prepareStatement(sql);
					ResultSet rset = ptmt.executeQuery();
					System.out.println(sql);
					getSqlputTextArea(rset, textArea);
					setVisible(false);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_3.setFont(new Font("宋体", Font.PLAIN, 17));
		button_3.setBounds(14, 133, 108, 27);
		contentPane.add(button_3);

		JLabel label_3 = new JLabel("找出学过001号老师主讲的所有课程的学生");
		label_3.setFont(new Font("宋体", Font.PLAIN, 18));
		label_3.setBounds(125, 136, 365, 18);
		contentPane.add(label_3);

		JButton button_4 = new JButton("查询5：");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "SELECT SNAME FROM student WHERE NOT EXISTS(SELECT * FROM course ,teacher,sc WHERE course.TNO = teacher.TNO AND teacher.TNAME = '李老师'  AND SC.CNO = course.CNO AND SC.SNO = student.SNO);";
				try {
					PreparedStatement ptmt = conn.prepareStatement(sql);
					ResultSet rset = ptmt.executeQuery();
					System.out.println(sql);
					getSqlputTextArea(rset, textArea);
					setVisible(false);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_4.setFont(new Font("宋体", Font.PLAIN, 17));
		button_4.setBounds(14, 173, 108, 27);
		contentPane.add(button_4);

		JLabel label_4 = new JLabel("列出没学过李老师讲授课程的所有同学");
		label_4.setFont(new Font("宋体", Font.PLAIN, 18));
		label_4.setBounds(125, 176, 343, 18);
		contentPane.add(label_4);
	}

}
