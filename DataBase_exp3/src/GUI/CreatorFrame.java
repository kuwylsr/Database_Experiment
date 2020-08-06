package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JInternalFrame;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.Color;

public class CreatorFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

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
	public CreatorFrame() {
		Connection conn = GUI.conn;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(550, 200, 873, 736);
		setResizable(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setBounds(14, 106, 827, 6);
		contentPane.add(horizontalStrut);
		
		JLabel label = new JLabel("管理员基本操作");
		label.setForeground(Color.RED);
		label.setFont(new Font("宋体", Font.BOLD, 20));
		label.setBounds(14, 117, 164, 29);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("操作对象");
		label_1.setFont(new Font("宋体", Font.BOLD, 20));
		label_1.setBounds(14, 191, 96, 29);
		contentPane.add(label_1);
		
		JRadioButton StudentRadioButton_1 = new JRadioButton("学生表");		
		StudentRadioButton_1.setFont(new Font("宋体", Font.PLAIN, 17));
		StudentRadioButton_1.setBounds(14, 229, 96, 27);
		contentPane.add(StudentRadioButton_1);
		
		JRadioButton TeacherradioButton_1 = new JRadioButton("教师表");
		TeacherradioButton_1.setFont(new Font("宋体", Font.PLAIN, 17));
		TeacherradioButton_1.setBounds(147, 229, 96, 27);
		contentPane.add(TeacherradioButton_1);
		
		JRadioButton ClassradioButton_2 = new JRadioButton("班级表");
		ClassradioButton_2.setFont(new Font("宋体", Font.PLAIN, 17));
		ClassradioButton_2.setBounds(14, 261, 96, 27);
		contentPane.add(ClassradioButton_2);
		
		JRadioButton ScRadioButton_2 = new JRadioButton("选课表");
		ScRadioButton_2.setFont(new Font("宋体", Font.PLAIN, 17));
		ScRadioButton_2.setBounds(147, 261, 110, 27);
		contentPane.add(ScRadioButton_2);
		
		ButtonGroup group=new ButtonGroup();
		group.add(StudentRadioButton_1);
		group.add(TeacherradioButton_1);
		group.add(ClassradioButton_2);
		group.add(ScRadioButton_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(267, 218, 574, 458);
		contentPane.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("宋体", Font.PLAIN, 17));
		scrollPane.setViewportView(textArea);
		
		JButton btnNewButton = new JButton("删除数据");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(StudentRadioButton_1.isSelected()) {
					Student frame = new Student("删除数据",textArea);
					frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				}else if(TeacherradioButton_1.isSelected()) {
					TeacherTable frame = new TeacherTable("删除数据",textArea);
					frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				}else if(ClassradioButton_2.isSelected()) {
					ClassTable frame = new ClassTable("删除数据",textArea);
					frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				}
				else if(ScRadioButton_2.isSelected()) {
					ChoiceCourse frame = new ChoiceCourse("删除数据",textArea);
					frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				}
			}
		});
		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 17));
		btnNewButton.setBounds(14, 157, 110, 29);
		contentPane.add(btnNewButton);
		
		JButton button = new JButton("插入数据");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(StudentRadioButton_1.isSelected()) {
					Student frame = new Student("插入数据",textArea);
					frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				}else if(TeacherradioButton_1.isSelected()) {
					TeacherTable frame = new TeacherTable("插入数据",textArea);
					frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				}else if(ClassradioButton_2.isSelected()) {
					ClassTable frame = new ClassTable("插入数据",textArea);
					frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				}
				else if(ScRadioButton_2.isSelected()) {
					ChoiceCourse frame = new ChoiceCourse("插入数据",textArea);
					frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				}
			}
		});
		button.setFont(new Font("宋体", Font.PLAIN, 17));
		button.setBounds(147, 157, 110, 29);
		contentPane.add(button);
		
		JButton button_1 = new JButton("查询数据");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(StudentRadioButton_1.isSelected()) {
					Student frame = new Student("查询数据",textArea);
					frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				}else if(TeacherradioButton_1.isSelected()) {
					TeacherTable frame = new TeacherTable("查询数据",textArea);
					frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				}else if(ClassradioButton_2.isSelected()) {
					ClassTable frame = new ClassTable("查询数据",textArea);
					frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				}
				else if(ScRadioButton_2.isSelected()) {
					ChoiceCourse frame = new ChoiceCourse("查询数据",textArea);
					frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				}
			}
		});
		button_1.setFont(new Font("宋体", Font.PLAIN, 17));
		button_1.setBounds(282, 157, 110, 29);
		contentPane.add(button_1);
		
		
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalStrut_1.setBounds(14, 298, 250, 18);
		contentPane.add(horizontalStrut_1);
		
		JLabel label_2 = new JLabel("管理员高级操作");
		label_2.setForeground(Color.RED);
		label_2.setFont(new Font("宋体", Font.BOLD, 20));
		label_2.setBounds(14, 319, 164, 29);
		contentPane.add(label_2);
		
		JButton btnNewButton_1 = new JButton("查看数据库E-R图");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new ImageViewerFrame();
				frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
			}
		});
		btnNewButton_1.setFont(new Font("宋体", Font.PLAIN, 17));
		btnNewButton_1.setBounds(14, 361, 229, 37);
		contentPane.add(btnNewButton_1);
		
		JButton button_2 = new JButton("查看各班成绩视图");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "SELECT * FROM avgmaxmingrade;";
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
		button_2.setFont(new Font("宋体", Font.PLAIN, 17));
		button_2.setBounds(14, 411, 229, 37);
		contentPane.add(button_2);
		
		JButton button_3 = new JButton("查看学生先修课视图");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "SELECT * FROM StuPreCourse;";
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
		button_3.setFont(new Font("宋体", Font.PLAIN, 17));
		button_3.setBounds(14, 461, 229, 37);
		contentPane.add(button_3);
		
		JButton button_4 = new JButton("查看各系领导视图");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String sql = "SELECT * FROM deptleader;";
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
		button_4.setFont(new Font("宋体", Font.PLAIN, 17));
		button_4.setBounds(14, 511, 229, 37);
		contentPane.add(button_4);
		
		JButton button_6 = new JButton("查看建立的触发器信息");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				textArea.append("触发器1：当插入一个新学生时，相应的班级人数+1\n");
				textArea.append("触发器2：当删除一个新学生时，相应的班级人数-1\n");
				textArea.append("触发器3：当一个班级的人数大于5人时，中断操作，发出异常信号\n");
				textArea.append("触发器4：教师的工资不得高于15000，否则触发器发出异常");
			}
		});
		button_6.setFont(new Font("宋体", Font.PLAIN, 17));
		button_6.setBounds(14, 561, 229, 37);
		contentPane.add(button_6);
		
		JButton button_7 = new JButton("查看建立的索引信息");
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
				textArea.append("索引1：建立学生表的学生姓名索引\n");
				textArea.append("索引2：建立教师表的教师姓名索引\n");
				textArea.append("索引3：建立社团表的社团名称索引\n");
			}
		});
		button_7.setFont(new Font("宋体", Font.PLAIN, 17));
		button_7.setBounds(14, 611, 229, 37);
		contentPane.add(button_7);
		
		JButton button_5 = new JButton("复杂查询");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdvanceQuery frame = new AdvanceQuery("复杂查询",textArea);
				frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				frame.setVisible(true);
			}
		});
		button_5.setFont(new Font("宋体", Font.PLAIN, 17));
		button_5.setBounds(420, 157, 110, 29);
		contentPane.add(button_5);
		
		JLabel lblNewLabel = new JLabel("欢迎来到管理员数据库系统");
		lblNewLabel.setFont(new Font("宋体", Font.BOLD, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(169, 28, 516, 37);
		contentPane.add(lblNewLabel);
		
		JLabel lblid = new JLabel("管理员ID：");
		lblid.setFont(new Font("宋体", Font.PLAIN, 17));
		lblid.setBounds(502, 80, 96, 18);
		contentPane.add(lblid);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(581, 78, 86, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.setText(GUI.id);
	}
}
