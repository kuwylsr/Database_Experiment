package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import system.DatabaseSystem;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.awt.event.ItemEvent;

public class GUI extends JFrame {

	private JPanel contentPane;
	private JTextField accountField;
	private JPasswordField passwordField;
	
	private int identify = 0;
	public boolean Login = false;
	public static String id = "";
	
	public static Connection conn = null;

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
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
//				try {
//		            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//		                if ("Nimbus".equals(info.getName())) {
//		                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//		                    break;
//		                }
//		            }
//		        } catch (ClassNotFoundException ex) {
//		            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		        } catch (InstantiationException ex) {
//		            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		        } catch (IllegalAccessException ex) {
//		            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//		            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		        }
				try {

					BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;

					org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();

					UIManager.put("RootPane.setupButtonVisible", false);

				} catch (Exception e) {

					// TODO Auto-generated catch block

					e.printStackTrace();

				}
				DatabaseSystem databaseSystem = new DatabaseSystem();
				
				String url = "jdbc:mysql://localhost/college?user=soft&password=soft1234&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT";
				String user = "root";
				String password = "woyaojiayou";
				conn = connectDataBase(url, user, password);
				
				try {
					databaseSystem.init(databaseSystem);
					GUI frame = new GUI(databaseSystem);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI(DatabaseSystem databaseSystem) {
		setFont(new Font("Dialog", Font.PLAIN, 40));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(650, 280, 621, 523);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 603, 476);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("工大数据库系统");
		lblNewLabel.setFont(new Font("宋体", Font.BOLD, 40));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(162, 13, 301, 145);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("账号：");
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(147, 155, 72, 18);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("密码：");
		lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(147, 223, 72, 18);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("登录身份");
		lblNewLabel_3.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel_3.setBounds(142, 299, 99, 18);
		panel.add(lblNewLabel_3);
		
		accountField = new JTextField();
		accountField.setFont(new Font("宋体", Font.BOLD, 17));
		accountField.setBounds(233, 154, 189, 24);
		panel.add(accountField);
		accountField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("宋体", Font.PLAIN, 15));
		passwordField.setBounds(233, 222, 189, 24);
		panel.add(passwordField);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("宋体", Font.PLAIN, 17));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"请选择", "学生身份", "教务人员"}));//
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					if(comboBox.getSelectedItem().equals("学生身份")) {
						identify = 1;
					}else if(comboBox.getSelectedItem().equals("教务人员")) {
						identify = 2;
					}
				}
			}
		});
		comboBox.setBounds(233, 298, 136, 24);
		panel.add(comboBox);
		
		JButton btnNewButton = new JButton("确认登录");
		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 17));
		btnNewButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				String Name = accountField.getText();
				id = Name;
				String Passwd = new String(passwordField.getPassword());
				if((identify == 1) && (databaseSystem.StudentLogin(Name, Passwd) != null)) {
					Login = true;
					LoginWarning dialog = new LoginWarning(identify,Login,"学生登录成功！");
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setLocation(500, 500);
					dialog.setVisible(true);
					
				}else if((identify == 2) && (databaseSystem.AdministratorLogin(Name, Passwd) != null)) {
					Login = true;
					LoginWarning dialog = new LoginWarning(identify,Login,"教务人员登录成功！");
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setLocation(500, 500);
					dialog.setVisible(true);
					
				}else {
					Login = false;
					LoginWarning dialog = new LoginWarning(identify,Login,"登陆失败，请重新登录！");
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setLocation(500, 500);
					dialog.setVisible(true);
				}
			}
		});
		btnNewButton.setBounds(233, 355, 170, 56);
		panel.add(btnNewButton);
	}
}
