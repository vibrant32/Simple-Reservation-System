package rails;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.awt.Color;
import javax.swing.JPasswordField;
import com.toedter.calendar.JDateChooser;
import java.sql.*;
import java.util.Vector;

public class Logins {

	public JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;
	private JTextField regUsername;
	private JTextField fromPlace;
	private JTextField toPlace;
	private JPasswordField regPassword;
	Vector<String> userDetails; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Logins window = new Logins();
					window.frame.setVisible(true);
					Class.forName("oracle.jdbc.driver.OracleDriver");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public Logins() throws SQLException {
		initialize();
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 */
	private void initialize() {
		 
		frame = new JFrame();
		frame.setBounds(100, 100, 724, 518);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 10, 646, 450);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tabbedPane.addTab("Login", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(250, 31, 127, 54);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(177, 118, 87, 25);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(177, 176, 87, 25);
		panel.add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField.setBounds(264, 120, 113, 25);
		panel.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		passwordField.setBounds(264, 178, 113, 25);
		panel.add(passwordField);
		
		JButton btnNewButton_1 = new JButton("Submit");
		btnNewButton_1.addActionListener(new ActionListener() {
			//String pass = textField.getText();
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().equals("admin") && String.valueOf(passwordField.getPassword()).equals("admin")) {
					JOptionPane.showMessageDialog(frame, "Successfull");
					frame.dispose();
					AdminDashboard adminDashboard = new AdminDashboard();
					adminDashboard.setVisible(true);
				} else if(textField.getText().equals("") || String.valueOf(passwordField.getPassword()).equals("")) {
					JOptionPane.showMessageDialog(frame, "Please Enter correct details","Error",JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						String username = textField.getText();
						String passwordtxt = new String(passwordField.getPassword()); 
						String sql = "select uname,upass from railways where uname=? and upass=?"; 
						Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","admin");
						PreparedStatement stmt = conn.prepareStatement(sql);
						stmt.setString(1, username);
						stmt.setString(2, passwordtxt);
						ResultSet rs = stmt.executeQuery();
						String usernameTemp = "", passwordTemp = "";
						while(rs.next()) {
							usernameTemp = rs.getString(1);
							passwordTemp = rs.getString(2);
						}
						if(usernameTemp.equals(username) && passwordTemp.equals(passwordtxt)) {
							userDetails = new Vector<>();
							userDetails.add(usernameTemp);
							userDetails.add(passwordTemp);
							try {
								@SuppressWarnings("resource")
								PrintWriter out = new PrintWriter(new FileWriter("userDetails.txt"));
								for(int i=0; i<userDetails.size();i++) {
									out.println(userDetails.elementAt(i));
									out.flush();
								}
							} catch (IOException e1) {								
								e1.printStackTrace();
							}
							JOptionPane.showMessageDialog(frame, "Successfull");
							frame.dispose();
							UserDashboard userDashboard = new UserDashboard();
							userDashboard.setVisible(true);
							//tabbedPane.setSelectedIndex(1);
						} else {
							JOptionPane.showMessageDialog(frame, "Please Enter correct details","Error",JOptionPane.ERROR_MESSAGE);
						}
						conn.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					//System.out.println("Hello");
				}
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewButton_1.setBounds(220, 247, 102, 32);
		panel.add(btnNewButton_1);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Register", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("Register");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel_3.setBounds(237, 20, 183, 40);
		panel_1.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Username");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_4.setBounds(195, 82, 87, 25);
		panel_1.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Password");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_5.setBounds(195, 141, 87, 25);
		panel_1.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("From");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_6.setBounds(195, 200, 87, 25);
		panel_1.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("To");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_7.setBounds(195, 257, 87, 25);
		panel_1.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Date");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_8.setBounds(195, 313, 87, 25);
		panel_1.add(lblNewLabel_8);
		
		regUsername = new JTextField();
		regUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		regUsername.setBounds(306, 85, 114, 24);
		panel_1.add(regUsername);
		regUsername.setColumns(10);
		
		fromPlace = new JTextField();
		fromPlace.setFont(new Font("Tahoma", Font.PLAIN, 15));
		fromPlace.setBounds(308, 203, 112, 24);
		panel_1.add(fromPlace);
		fromPlace.setColumns(10);
		
		toPlace = new JTextField();
		toPlace.setFont(new Font("Tahoma", Font.PLAIN, 15));
		toPlace.setBounds(308, 260, 112, 24);
		panel_1.add(toPlace);
		toPlace.setColumns(10);
		
		regPassword = new JPasswordField();
		regPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		regPassword.setBounds(308, 144, 112, 24);
		panel_1.add(regPassword);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(306, 313, 114, 25);
		panel_1.add(dateChooser);
		
		JButton registerBtn = new JButton("Submit");
		registerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(regUsername.getText().equals("") || String.valueOf(regPassword.getPassword()).equals("") || fromPlace.getText().equals("") || toPlace.getText().equals("") || ((JTextField)dateChooser.getDateEditor().getUiComponent()).getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Please dont leave anything blank!","Error",JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						String date  = ((JTextField)dateChooser.getDateEditor().getUiComponent()).getText();
						Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","admin");
						PreparedStatement stmt = conn.prepareStatement("insert into railways(uname,upass,source,dest,resdate) values(?,?,?,?,?)");
						stmt.setString(1, regUsername.getText());
						stmt.setString(2, new String(regPassword.getPassword()));
						stmt.setString(3, fromPlace.getText());
						stmt.setString(4, toPlace.getText());
						stmt.setString(5, date);
						stmt.executeUpdate();
						conn.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}	
					JOptionPane.showMessageDialog(frame, "Successfull");
					tabbedPane.setSelectedIndex(0);
					regUsername.setText("");
					regPassword.setText("");
					fromPlace.setText("");
					toPlace.setText("");
					dateChooser.setDateFormatString("");
				}
			}
		});
		
		registerBtn.setFont(new Font("Tahoma", Font.BOLD, 15));
		registerBtn.setBounds(237, 365, 102, 32);
		panel_1.add(registerBtn);
	}
	
}
