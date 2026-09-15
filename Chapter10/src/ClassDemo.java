import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class ClassDemo {

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
					ClassDemo window = new ClassDemo();
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
	public ClassDemo() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 791, 342);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		FirstName = new JTextField();
		FirstName.setText("Enter first name");
		FirstName.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{
				if(FirstName.getText().equals("Enter first name"))
				{
					FirstName.setText("");
				}
			}
		});
		FirstName.setBounds(10, 11, 324, 27);
		panel.add(FirstName);
		FirstName.setColumns(10);
		
		LastName = new JTextField();
		LastName.setText("Enter last name");
		LastName.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{
				if(LastName.getText().equals("Enter last name"))
				{
					LastName.setText("");
				}
			}
		});
		LastName.setColumns(10);
		LastName.setBounds(10, 49, 324, 27);
		panel.add(LastName);
		
		JLabel Display = new JLabel("");
		Display.setBounds(10, 87, 324, 205);
		panel.add(Display);
		
		JButton Submit = new JButton("SUBMIT");
		Submit.setBackground(new Color(144, 238, 144));
		Submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String fN = FirstName.getText();
				String lN = LastName.getText();
				
				Display.setText("Your full name is: " + fN + " " + lN);
			}
		});
		Submit.setForeground(new Color(0, 128, 0));
		Submit.setFont(new Font("Rockwell", Font.BOLD, 50));
		Submit.setBounds(344, 11, 421, 281);
		panel.add(Submit);
		
		
	}
}
