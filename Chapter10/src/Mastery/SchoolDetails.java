/*

Program: SchoolDetails.java          Last Date of this Revision: October 6, 2026

Purpose: The purpose of this program is to create a GUI that allows the user to enter their name, grade, and school. 
When the user clicks Submit, the program displays their school details and the corresponding school logo.

Author: Emmett_Stransky 
School: CHHS
Course: CSE 3010 - Computer Science 3
 

*/

package Mastery;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

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
	private void initialize() 
	{
		int logoWidth = 180;
		int logoHeight = 150;

		final ImageIcon crescentImg = new ImageIcon(
			    new ImageIcon("../Chapter10/src/Mastery/Crescent.png")
			    .getImage().getScaledInstance(180, 150, java.awt.Image.SCALE_SMOOTH));

			final ImageIcon fowlerImg = new ImageIcon(
			    new ImageIcon("../Chapter10/src/Mastery/Fowler.png")
			    .getImage().getScaledInstance(180, 150, java.awt.Image.SCALE_SMOOTH));

			final ImageIcon queeneImg = new ImageIcon(
			    new ImageIcon("../Chapter10/src/Mastery/QueenE.png")
			    .getImage().getScaledInstance(180, 150, java.awt.Image.SCALE_SMOOTH));

			final ImageIcon westernImg = new ImageIcon(
			    new ImageIcon("../Chapter10/src/Mastery/Western.png")
			    .getImage().getScaledInstance(180, 150, java.awt.Image.SCALE_SMOOTH));

			final ImageIcon abeImg = new ImageIcon(
			    new ImageIcon("../Chapter10/src/Mastery/Abe.png")
			    .getImage().getScaledInstance(180, 150, java.awt.Image.SCALE_SMOOTH));
		
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
		
		JTextArea Details = new JTextArea();
		Details.setFont(new Font("Monospaced", Font.PLAIN, 11));
		Details.setLineWrap(true);
		Details.setBounds(210, 89, 156, 161);
		Details.setWrapStyleWord(true);
		panel.add(Details);
		
		JLabel SchoolLogo = new JLabel("");
		SchoolLogo.setBackground(new Color(255, 255, 255));
		SchoolLogo.setBounds(10, 89, 190, 161);
		panel.add(SchoolLogo);
		
		JButton btnNewButton = new JButton("SUBMIT");		
		btnNewButton.setBackground(new Color(255, 215, 0));
		btnNewButton.setBounds(253, 11, 113, 67);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String firstname = FirstName.getText();
				String lastname = LastName.getText();
				String grade = (String) Grade.getSelectedItem();
				String school = (String) School.getSelectedItem();
				
				Details.setText("\n" + "\n"
						+ firstname + " " + lastname
				        + "\nis in grade: " + grade
				        + "\nand attends " + school
				        + " high school.");
				
				if (school.equals("Crescent Heights")) {
					SchoolLogo.setIcon(crescentImg);
				} else if (school.equals("James Fowler")) {
					SchoolLogo.setIcon(fowlerImg);
				} else if (school.equals("Queen Elizabeth")) {
					SchoolLogo.setIcon(queeneImg);
				} else if (school.equals("Western Canada")) {
					SchoolLogo.setIcon(westernImg);
				} else if (school.equals("William Aberhart")) {
					SchoolLogo.setIcon(abeImg);
			
				
				}
			}
		});
		
		panel.add(btnNewButton);
		
		
		
		
		
		
		

	}
}
