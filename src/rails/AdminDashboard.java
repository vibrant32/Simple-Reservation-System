package rails;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.*;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class AdminDashboard extends JFrame {
	
	JFrame jFrame;
	private JPanel contentPane;
	Vector<String> resDetails;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminDashboard frame = new AdminDashboard();
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
	public AdminDashboard() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 511);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("REQUESTED RESERVATIONS");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBounds(133, 11, 369, 37);
		contentPane.add(lblNewLabel);
		
		DefaultTableModel model = new DefaultTableModel(new String[] {"USERNAME","FROM","TO","DATE","STATUS"},0);
		
		JTable table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		table.setRowHeight(22);
		table.setFillsViewportHeight(true);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 79, 666, 345);
		scrollPane.setVisible(true);
		contentPane.add(scrollPane);
		
		try {
			String sql = "select uname,source,dest,resdate,status from railways";
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","admin");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String username = rs.getString("uname");
				String source = rs.getString("source");
				String destination = rs.getString("dest");
				String resDate = rs.getString("resdate");
				String status = rs.getString("status");
				model.addRow(new Object[] {username,source,destination,resDate,status});
			}
			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		table.setModel(model);
		JButton btnNewButton = new JButton("Reserve");
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				String status = "reserved";
				resDetails =  model.getDataVector().elementAt(table.getSelectedRow());
				String usrname = resDetails.get(0);
				String source = resDetails.get(1);
				String resdate = resDetails.get(3);
				
				try {
					String sql = "update railways set status=? where uname=? and source=? and resdate=?";
					Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","admin");
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, status);
					stmt.setString(2, usrname);
					stmt.setString(3, source);
					stmt.setString(4, resdate);
					int rowAffected = stmt.executeUpdate();
					System.out.println("row affected -> " + rowAffected);
					AdminDashboard frame = new AdminDashboard();
					JOptionPane.showMessageDialog(frame, "Entry reserved","Successful",JOptionPane.INFORMATION_MESSAGE);
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setBounds(165, 434, 103, 30);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Close");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);				
			}
		});
		
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_1.setBounds(399, 434, 103, 30);
		contentPane.add(btnNewButton_1);
	}
}
