package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.JSplitPane;
import javax.swing.JInternalFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StudentFrame extends JFrame {

	private JPanel contentPane;
	private JTextField StudentThemeField;
	private JTextField NameField;
	private JTextField idField_1;
	private JTextField classField_2;
	private JTextField updateField;
	private JTextField ageField;
	private JTextField sexField_1;
	private JTextField instituteField_2;
	private JTextField deptField_3;
	private JTextField courseField_4;
	private JTextField assnField_4;
	private JTextField cgradeField_5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentFrame frame = new StudentFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void getSqlputTextArea(ResultSet rset,JTextArea textArea) throws SQLException {
		ResultSetMetaData rsmd = rset.getMetaData();
		textArea.setText("");
		if(!rset.next()) {
			textArea.append("未查询到相关内容！");
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
	 * @throws SQLException 
	 */
	public StudentFrame() throws SQLException {
		Connection conn = GUI.conn;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(550, 200, 978, 736);
		setResizable(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		StudentThemeField = new JTextField();
		StudentThemeField.setEditable(false);
		StudentThemeField.setBounds(218, 13, 501, 68);
		StudentThemeField.setFont(new Font("宋体", Font.BOLD, 22));
		StudentThemeField.setHorizontalAlignment(SwingConstants.CENTER);
		StudentThemeField.setColumns(10);
		contentPane.add(StudentThemeField);
		
		
		JLabel basicImformationLabel = new JLabel("学生基本信息");
		basicImformationLabel.setBounds(14, 105, 126, 29);
		basicImformationLabel.setFont(new Font("宋体", Font.BOLD, 20));
		contentPane.add(basicImformationLabel);
		
		JLabel label = new JLabel("姓名：");
		label.setBounds(14, 138, 72, 18);
		label.setFont(new Font("宋体", Font.PLAIN, 20));
		contentPane.add(label);
		
		NameField = new JTextField();
		NameField.setFont(new Font("宋体", Font.PLAIN, 16));
		NameField.setBounds(75, 137, 86, 24);
		contentPane.add(NameField);
		NameField.setColumns(10);
		
		JLabel label_1 = new JLabel("学号：");
		label_1.setBounds(198, 138, 72, 18);
		label_1.setFont(new Font("宋体", Font.PLAIN, 20));
		contentPane.add(label_1);
		
		idField_1 = new JTextField();
		idField_1.setFont(new Font("宋体", Font.PLAIN, 16));
		idField_1.setBounds(260, 137, 86, 24);
		contentPane.add(idField_1);
		idField_1.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("班级：");
		lblNewLabel.setBounds(660, 139, 72, 18);
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 20));
		contentPane.add(lblNewLabel);
		
		classField_2 = new JTextField();
		classField_2.setFont(new Font("宋体", Font.PLAIN, 16));
		classField_2.setBounds(734, 138, 86, 24);
		contentPane.add(classField_2);
		classField_2.setColumns(10);
		
		JLabel label_2 = new JLabel("入学日期：");
		label_2.setBounds(378, 138, 100, 18);
		label_2.setFont(new Font("宋体", Font.PLAIN, 20));
		contentPane.add(label_2);
		
		updateField = new JTextField();
		updateField.setFont(new Font("宋体", Font.PLAIN, 16));
		updateField.setBounds(477, 137, 132, 24);
		contentPane.add(updateField);
		updateField.setColumns(10);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalStrut_1.setBounds(14, 214, 946, 18);
		contentPane.add(horizontalStrut_1);
		
		JLabel label_3 = new JLabel("学生基本查询");
		label_3.setBounds(17, 231, 126, 29);
		label_3.setFont(new Font("宋体", Font.BOLD, 20));
		contentPane.add(label_3);
		
		ageField = new JTextField();
		ageField.setFont(new Font("宋体", Font.PLAIN, 16));
		ageField.setBounds(75, 177, 86, 24);
		ageField.setColumns(10);
		contentPane.add(ageField);
		
		JLabel label_4 = new JLabel("年龄：");
		label_4.setBounds(14, 178, 72, 18);
		label_4.setFont(new Font("宋体", Font.PLAIN, 20));
		contentPane.add(label_4);
		
		sexField_1 = new JTextField();
		sexField_1.setFont(new Font("宋体", Font.PLAIN, 16));
		sexField_1.setBounds(259, 174, 87, 24);
		sexField_1.setColumns(10);
		contentPane.add(sexField_1);
		
		JLabel label_5 = new JLabel("性别：");
		label_5.setBounds(198, 175, 72, 18);
		label_5.setFont(new Font("宋体", Font.PLAIN, 20));
		contentPane.add(label_5);
		
		instituteField_2 = new JTextField();
		instituteField_2.setFont(new Font("宋体", Font.PLAIN, 16));
		instituteField_2.setBounds(439, 174, 170, 24);
		instituteField_2.setColumns(10);
		contentPane.add(instituteField_2);
		
		JLabel label_6 = new JLabel("学院：");
		label_6.setBounds(378, 178, 60, 18);
		label_6.setFont(new Font("宋体", Font.PLAIN, 20));
		contentPane.add(label_6);
		
		JLabel label_7 = new JLabel("系别：");
		label_7.setBounds(660, 178, 72, 18);
		label_7.setFont(new Font("宋体", Font.PLAIN, 20));
		contentPane.add(label_7);
		
		deptField_3 = new JTextField();
		deptField_3.setFont(new Font("宋体", Font.PLAIN, 16));
		deptField_3.setBounds(734, 177, 86, 24);
		deptField_3.setColumns(10);
		contentPane.add(deptField_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(359, 244, 587, 420);
		contentPane.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("宋体", Font.PLAIN, 17));
		scrollPane.setViewportView(textArea);
		
		JButton btnNewButton = new JButton("查询所有课程信息");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String sql = "SELECT * FROM course;";
				try {
					PreparedStatement ptmt = conn.prepareStatement(sql);
					ResultSet rset = ptmt.executeQuery();
					System.out.println(sql);
					getSqlputTextArea(rset, textArea);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		});
		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 16));
		btnNewButton.setBounds(14, 316, 175, 40);
		contentPane.add(btnNewButton);
		
		
		
		JButton button = new JButton("查询已选课程");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String id = idField_1.getText();
				String sql = "SELECT C.CNAME FROM SC, course C WHERE SC.SNO = ? AND SC.CNO = C.CNO;";
				try {
					PreparedStatement ptmt = conn.prepareStatement(sql);
					ptmt.setString(1, id);
					ResultSet rset = ptmt.executeQuery();
					System.out.println(sql);
					getSqlputTextArea(rset, textArea);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		});
		button.setFont(new Font("宋体", Font.PLAIN, 16));
		button.setBounds(214, 316, 132, 40);
		contentPane.add(button);
		
		JButton button_1 = new JButton("查询所有社团信息");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "SELECT * FROM assn;";
				try {
					PreparedStatement ptmt = conn.prepareStatement(sql);
					ResultSet rset = ptmt.executeQuery();
					System.out.println(sql);
					getSqlputTextArea(rset, textArea);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}
		});
		button_1.setFont(new Font("宋体", Font.PLAIN, 16));
		button_1.setBounds(14, 444, 175, 40);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("查询参与社团");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = idField_1.getText();
				String sql = "SELECT A.ANAME FROM joinassn J, student S, assn A WHERE J.ANO = A.ANO AND S.SNO = J.SNO AND S.SNO = ?;";
				try {
					PreparedStatement ptmt = conn.prepareStatement(sql);
					ptmt.setString(1, id);
					ResultSet rset = ptmt.executeQuery();
					System.out.println(sql);
					getSqlputTextArea(rset, textArea);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}
		});
		button_2.setFont(new Font("宋体", Font.PLAIN, 16));
		button_2.setBounds(214, 444, 132, 40);
		contentPane.add(button_2);
		
		JLabel label_8 = new JLabel("输入课程名：");
		label_8.setFont(new Font("宋体", Font.PLAIN, 17));
		label_8.setBounds(17, 265, 111, 38);
		contentPane.add(label_8);
		
		courseField_4 = new JTextField();
		courseField_4.setFont(new Font("宋体", Font.PLAIN, 16));
		courseField_4.setBounds(120, 273, 86, 24);
		contentPane.add(courseField_4);
		courseField_4.setColumns(10);
		
		JButton button_3 = new JButton("查询");
		button_3.setFont(new Font("宋体", Font.PLAIN, 17));
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String courseName = courseField_4.getText();
				String sql = "SELECT * FROM course WHERE CNAME = ?;";
				try {
					PreparedStatement ptmt = conn.prepareStatement(sql);
					ptmt.setString(1, courseName);
					ResultSet rset = ptmt.executeQuery();
					System.out.println(sql);
					getSqlputTextArea(rset, textArea);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			}
		});
		button_3.setBounds(221, 273, 125, 27);
		contentPane.add(button_3);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		horizontalGlue.setBounds(14, 369, 304, 18);
		contentPane.add(horizontalGlue);
		
		JLabel label_9 = new JLabel("输入社团名：");
		label_9.setFont(new Font("宋体", Font.PLAIN, 17));
		label_9.setBounds(20, 393, 111, 38);
		contentPane.add(label_9);
		
		assnField_4 = new JTextField();
		assnField_4.setFont(new Font("宋体", Font.PLAIN, 16));
		assnField_4.setColumns(10);
		assnField_4.setBounds(123, 401, 86, 24);
		contentPane.add(assnField_4);
		
		JButton button_4 = new JButton("查询");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String assn = assnField_4.getText();
				String sql = "SELECT * FROM assn A WHERE A.ANAME = ?;";
				try {
					PreparedStatement ptmt = conn.prepareStatement(sql);
					ptmt.setString(1, assn);
					ResultSet rset = ptmt.executeQuery();
					System.out.println(sql);
					getSqlputTextArea(rset, textArea);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}
		});
		button_4.setFont(new Font("宋体", Font.PLAIN, 17));
		button_4.setBounds(224, 401, 122, 27);
		contentPane.add(button_4);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		horizontalGlue_1.setBounds(14, 507, 301, 18);
		contentPane.add(horizontalGlue_1);
		
		JLabel label_10 = new JLabel("输入课程名：");
		label_10.setFont(new Font("宋体", Font.PLAIN, 17));
		label_10.setBounds(17, 528, 111, 38);
		contentPane.add(label_10);
		
		cgradeField_5 = new JTextField();
		cgradeField_5.setFont(new Font("宋体", Font.PLAIN, 16));
		cgradeField_5.setColumns(10);
		cgradeField_5.setBounds(120, 536, 86, 24);
		contentPane.add(cgradeField_5);
		
		JButton button_5 = new JButton("查询成绩");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = idField_1.getText();
				String cgrade = cgradeField_5.getText();
				String sql = "SELECT SC.GRADE FROM sc, course C WHERE SC.SNO = ? AND SC.CNO = C.CNO AND C.CNAME = ?;";
				try {
					PreparedStatement ptmt = conn.prepareStatement(sql);
					ptmt.setString(1, id);
					ptmt.setString(2, cgrade);
					ResultSet rset = ptmt.executeQuery();
					System.out.println(sql);
					getSqlputTextArea(rset, textArea);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}
		});
		button_5.setFont(new Font("宋体", Font.PLAIN, 17));
		button_5.setBounds(221, 536, 125, 27);
		contentPane.add(button_5);
		
		JButton button_6 = new JButton("查询所有已选课程的成绩");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = idField_1.getText();
				String sql = "SELECT CNAME, GRADE FROM sc, course C WHERE SC.CNO = C.CNO AND SC.SNO = ?;";
				try {
					PreparedStatement ptmt = conn.prepareStatement(sql);
					ptmt.setString(1, id);
					ResultSet rset = ptmt.executeQuery();
					System.out.println(sql);
					getSqlputTextArea(rset, textArea);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}
		});
		button_6.setFont(new Font("宋体", Font.PLAIN, 16));
		button_6.setBounds(15, 579, 215, 34);
		contentPane.add(button_6);
		
		JButton btnNewButton_1 = new JButton("最高分的课程");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = idField_1.getText();
				String sql = "SELECT CNAME, SC.GRADE FROM SC, course C WHERE SC.SNO = ? AND SC.CNO = C.CNO AND SC.GRADE >= ALL (SELECT S.GRADE FROM SC S WHERE S.SNO = SC.SNO);";
				try {
					PreparedStatement ptmt = conn.prepareStatement(sql);
					ptmt.setString(1, id);
					ResultSet rset = ptmt.executeQuery();
					System.out.println(sql);
					getSqlputTextArea(rset, textArea);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}
		});
		btnNewButton_1.setFont(new Font("宋体", Font.PLAIN, 16));
		btnNewButton_1.setBounds(14, 626, 164, 38);
		contentPane.add(btnNewButton_1);
		
		JButton button_7 = new JButton("最低分的课程");
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = idField_1.getText();
				String sql = "SELECT CNAME, SC.GRADE FROM SC, course C WHERE SC.SNO = ? AND SC.CNO = C.CNO AND SC.GRADE <= ALL (SELECT S.GRADE FROM SC S WHERE S.SNO = SC.SNO);";
				try {
					PreparedStatement ptmt = conn.prepareStatement(sql);
					ptmt.setString(1, id);
					ResultSet rset = ptmt.executeQuery();
					System.out.println(sql);
					getSqlputTextArea(rset, textArea);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}
		});
		button_7.setFont(new Font("宋体", Font.PLAIN, 16));
		button_7.setBounds(193, 624, 153, 40);
		contentPane.add(button_7);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setBounds(14, 82, 940, 18);
		contentPane.add(horizontalStrut);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{StudentThemeField}));
		String sql = "SELECT S.SNAME, S.SNO, S.SSEX, S.SAGE, S.CLANO, T.TNAME, D.DNAME, C.BDATE, I.INAME from student S, class C, dept D, institute I, teacher T WHERE S.CLANO = C.CLANO AND C.DNO = D.DNO AND D.INO = I.INO AND T.TNO = S.TNO AND SNO = ?;";
		PreparedStatement ptmt = conn.prepareStatement(sql);
		ptmt.setString(1, GUI.id);
		ResultSet rset = ptmt.executeQuery();
		ResultSetMetaData rsmd = rset.getMetaData();
		while(rset.next()) {
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				if(i==1) NameField.setText(rset.getString(i));
				else if(i==2) idField_1.setText(rset.getString(i));
				else if(i==3) sexField_1.setText(rset.getString(i));
				else if(i==4) ageField.setText(rset.getString(i));
				else if(i==5) classField_2.setText(rset.getString(i));
				else if(i==7) deptField_3.setText(rset.getString(i));
				else if(i==8) updateField.setText(rset.getString(i));
				else if(i==9) instituteField_2.setText(rset.getString(i));
			}
			StudentThemeField.setText("欢迎"+rset.getString(1)+"来到学生数据库系统");
		}
	}
}
