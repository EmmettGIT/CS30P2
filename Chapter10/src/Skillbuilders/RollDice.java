package Skillbuilders;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

public class RollDice {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RollDice window = new RollDice();
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
	public RollDice() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		ImageIcon die1 = new ImageIcon("../Chapter10/src/Skillbuilders/die1.gif");
		ImageIcon die2 = new ImageIcon("../Chapter10/src/Skillbuilders/die2.gif");
		ImageIcon die3 = new ImageIcon("../Chapter10/src/Skillbuilders/die3.gif");
		ImageIcon die4 = new ImageIcon("../Chapter10/src/Skillbuilders/die4.gif");
		ImageIcon die5 = new ImageIcon("../Chapter10/src/Skillbuilders/die5.gif");
		ImageIcon die6 = new ImageIcon("../Chapter10/src/Skillbuilders/die6.gif");
		
		frame = new JFrame();
		frame.setBounds(100, 100, 215, 222);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(204, 153, 153));
		panel.setBounds(0, 0, 199, 183);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel DieFace2 = new JLabel("");
		DieFace2.setBounds(111, 11, 88, 79);
		panel.add(DieFace2);
		
		JLabel DieFace = new JLabel("");
		DieFace.setBounds(13, 11, 88, 79);
		panel.add(DieFace);
		
		JButton Roll = new JButton("Roll Die");
		Roll.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				
				int newRoll, newRoll2 = 0;
				
				newRoll = (int)(6 * Math.random() + 1);
				
				if(newRoll == 1)
				{
					DieFace.setIcon(die1);
				}
				else if(newRoll == 2)
				{
					DieFace.setIcon(die2);
				}
				else if(newRoll == 3)
				{
					DieFace.setIcon(die3);
				}
				else if(newRoll == 4)
				{
					DieFace.setIcon(die4);
				}
				else if(newRoll == 5)
				{
					DieFace.setIcon(die5);
				}
				else if(newRoll == 6)
				{
					DieFace.setIcon(die6);
				}
				
				newRoll2 = (int)(6 * Math.random() + 1);
				
				if(newRoll2 == 1) 
				{
					DieFace2.setIcon(die1);
				}
				else if(newRoll2 == 2) 
				{
					DieFace2.setIcon(die2);
				}
				else if(newRoll2 == 3) 
				{
					DieFace2.setIcon(die3);
				}
				else if(newRoll2 == 4) 
				{
					DieFace2.setIcon(die4);
				}
				else if(newRoll2 == 5) 
				{
					DieFace2.setIcon(die5);
				}
				else if(newRoll2 == 6) 
				{
					DieFace2.setIcon(die6);
				}
			}
		});
		Roll.setForeground(new Color(0, 0, 0));
		Roll.setBackground(SystemColor.activeCaption);
		Roll.setFont(new Font("Tahoma", Font.BOLD, 30));
		Roll.setBounds(10, 101, 179, 71);
		panel.add(Roll);
		
		
		
	}
}
