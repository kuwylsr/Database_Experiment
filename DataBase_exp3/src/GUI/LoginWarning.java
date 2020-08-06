package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class LoginWarning extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public LoginWarning(int identify,boolean flag,String type) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 100, 410, 265);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(type);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 24));
		lblNewLabel.setBounds(62, 13, 264, 102);
		contentPane.add(lblNewLabel);
		
		JButton button = new JButton("确认");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				get().setVisible(false);
				if(flag) {
					try {
						if(identify == 1) {					
							StudentFrame frame = new StudentFrame();			
							frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
							frame.setVisible(true);			
						}else if(identify == 2) {
							CreatorFrame frame = new CreatorFrame();
							frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
							frame.setVisible(true);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
			}
		});
		button.setFont(new Font("宋体", Font.PLAIN, 30));
		button.setBounds(116, 117, 163, 59);
		contentPane.add(button);
	}
	
	public LoginWarning get() {
		return this;
	}
}
