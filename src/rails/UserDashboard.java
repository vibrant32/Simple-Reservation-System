package rails;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

@SuppressWarnings("serial")
public class UserDashboard extends JFrame {

	public Vector<String> userStrings = new Vector<String>();
	private JPanel contentPane;
	public String loggedUserStatus, usernameDisplay, sourceDisplay, destDisplay, dateDisplay, status;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserDashboard frame = new UserDashboard();
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
	public UserDashboard() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 570, 253);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setBounds(10, 10, 526, 52);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		contentPane.add(lblNewLabel);
		
		JLabel name = new JLabel("Name");
		name.setFont(new Font("Tahoma", Font.BOLD, 14));
		name.setBounds(10, 77, 50, 20);
		contentPane.add(name);
		
		JLabel source = new JLabel("From");
		source.setFont(new Font("Tahoma", Font.BOLD, 14));
		source.setBounds(10, 107, 76, 20);
		contentPane.add(source);
		
		JLabel destination = new JLabel("To");
		destination.setFont(new Font("Tahoma", Font.BOLD, 14));
		destination.setBounds(10, 138, 76, 20);
		contentPane.add(destination);
		
		JLabel dateofreservation = new JLabel("Date");
		dateofreservation.setFont(new Font("Tahoma", Font.BOLD, 14));
		dateofreservation.setBounds(10, 168, 76, 20);
		contentPane.add(dateofreservation);
		
		JLabel username_lbl = new JLabel("");
		username_lbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		username_lbl.setBounds(70, 77, 115, 20);
		contentPane.add(username_lbl);
		
		JLabel from_lbl = new JLabel("");
		from_lbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		from_lbl.setBounds(70, 107, 115, 20);
		contentPane.add(from_lbl);
		
		JLabel to_lbl = new JLabel("");
		to_lbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		to_lbl.setBounds(70, 137, 115, 20);
		contentPane.add(to_lbl);
		
		JLabel date_lbl = new JLabel("");
		date_lbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		date_lbl.setBounds(70, 168, 115, 20);
		contentPane.add(date_lbl);
	
		BufferedReader br = null;
		try {
			String currline;
			br = new BufferedReader(new FileReader("C:\\Users\\yashodeep\\eclipse-workspace\\ReservationRailway\\userDetails.txt"));
			while((currline = br.readLine()) != null) {
				userStrings.add(currline);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		//System.out.println(userStrings);
		
		if(userStrings.isEmpty()) {
			lblNewLabel.setText("SORRY NO TRAINS AVAILABLE");
			System.out.println("User Details are Null!");
		} else {
			try {
				String sql = "select uname,source,dest,resdate,status from railways where uname=? and upass=?";
				Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","admin");
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, userStrings.get(0));
				stmt.setString(2, userStrings.get(1));
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					System.out.println(rs.getString("status"));
					loggedUserStatus = rs.getString("status");
					usernameDisplay = rs.getString("uname");
					sourceDisplay = rs.getString("source");
					destDisplay = rs.getString("dest");
					dateDisplay = rs.getString("resdate");
				}
				
				if(loggedUserStatus.equals("reserved")) {
					lblNewLabel.setText("TICKET");
					username_lbl.setText(": "+usernameDisplay);
					from_lbl.setText(": "+sourceDisplay);
					to_lbl.setText(": "+destDisplay);
					date_lbl.setText(": "+dateDisplay);
				}
				
				} catch (SQLException e) {	
					e.printStackTrace();
				} catch (NullPointerException e) {
					lblNewLabel.setText("WILL BE CONFIRMED SOON...");
				}
		
		} 										
	}
}
