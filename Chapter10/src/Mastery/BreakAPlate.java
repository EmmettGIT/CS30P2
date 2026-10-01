package Mastery;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class BreakAPlate implements ActionListener {

	ImageIcon Plates = new ImageIcon("../Chapter10/src/Mastery/plates.gif");
	ImageIcon BrokenPlates = new ImageIcon("../Chapter10/src/Mastery/plates_all_broken.gif");
	ImageIcon TwoBrokenPlates = new ImageIcon("../Chapter10/src/Mastery/plates_two_broken.gif");
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BreakAPlate window = new BreakAPlate();
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
	public BreakAPlate() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 352);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel display = new JLabel("");
		display.setBounds(10, 11, 414, 108);
		panel.add(display);
		
		JLabel Prizes = new JLabel("");
		Prizes.setBounds(147, 213, 142, 89);
		panel.add(Prizes);
		
		JButton PlayButton = new JButton("Play");
		PlayButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				
				int newGame = 0;
				
				newGame = (int)(3 * Math.random() + 1);
				
				if(newGame == 1)
				{
					display.setIcon(BrokenPlates);
				}
				else if(newGame == 2)
				{
					display.setIcon(TwoBrokenPlates);
				}
				else if(newGame == 3)
				{
					display.setIcon(Plates);
				}
				
			}
			
			
			
		});
		
		
		PlayButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		PlayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		PlayButton.setBounds(147, 146, 142, 56);
		panel.add(PlayButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
