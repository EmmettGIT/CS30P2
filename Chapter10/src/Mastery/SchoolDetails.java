package Mastery;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SchoolDetails {

	private JFrame frame;
	private JTextField FirstName;
	private JTextField LastName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SchoolDetails window = new SchoolDetails();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SchoolDetails() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 392, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 376, 261);
		panel.setBackground(new Color(128, 0, 0));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JComboBox Grade = new JComboBox();
		Grade.setBounds(10, 50, 98, 28);
		Grade.setModel(new DefaultComboBoxModel(new String[] {"Enter Grade", "10", "11", "12"}));
		panel.add(Grade);

		
		JComboBox School = new JComboBox();
		School.setBounds(118, 50, 125, 28);
		School.setModel(new DefaultComboBoxModel(new String[] {"Enter School", "Crescent Heights", "James Fowler", "Queen Elizabeth", "Western Canada", "William Aberhart"}));
		panel.add(School);
		
		FirstName = new JTextField();
		FirstName.setBounds(10, 11, 98, 28);
		FirstName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) 
			{
				if(FirstName.getText().equals("Enter first name"))
				{
					FirstName.setText("");
				}
			}
		});
		FirstName.setText("Enter first name");
		panel.add(FirstName);

		
		LastName = new JTextField();
		LastName.setBounds(118, 11, 125, 28);
		LastName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) 
			{
				if(LastName.getText().equals("Enter last name"))
				{
					LastName.setText("");
				}
			}
		});
		LastName.setText("Enter last name");
		LastName.setColumns(10);
		panel.add(LastName);
		
		JButton btnNewButton = new JButton("SUBMIT");
		btnNewButton.setBounds(253, 11, 113, 67);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel.add(btnNewButton);

		
		JLabel SchoolLogo = new JLabel("");
		SchoolLogo.setBounds(10, 89, 190, 161);
		panel.add(SchoolLogo);

	}
}
