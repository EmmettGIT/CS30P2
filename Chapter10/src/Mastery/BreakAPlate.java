/*

Program: BreakAPlate.java          Last Date of this Revision: October 2, 2026

Purpose: The purpose of this code is to create a Java Swing game where the user clicks a Play button
to randomly determine the outcome of breaking plates. It displays different broken-plate images and awards 
either a tiger plush, sticker, or no prize based on the random result.

Author: Emmett_Stransky 
School: CHHS
Course: CSE 3010 - Computer Science 3
 

*/

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
import java.awt.Color;

public class BreakAPlate implements ActionListener {

	ImageIcon Plates = new ImageIcon("../Chapter10/src/Mastery/plates.gif");
	ImageIcon BrokenPlates = new ImageIcon("../Chapter10/src/Mastery/plates_all_broken.gif");
	ImageIcon TwoBrokenPlates = new ImageIcon("../Chapter10/src/Mastery/plates_two_broken.gif");
	ImageIcon TigerPlush = new ImageIcon("../Chapter10/src/Mastery/tiger_plush.gif");
	ImageIcon Sticker = new ImageIcon("../Chapter10/src/Mastery/sticker.gif");
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
		frame.setBounds(100, 100, 307, 352);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 0, 0));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel displayplate = new JLabel("");
		displayplate.setBackground(new Color(169, 169, 169));
		displayplate.setBounds(10, 11, 271, 108);
		panel.add(displayplate);
		
		JLabel displayprize = new JLabel("");
		displayprize.setBounds(98, 202, 142, 89);
		panel.add(displayprize);
		
		JButton PlayButton = new JButton("Play");
		PlayButton.setBackground(new Color(250, 240, 230));
		PlayButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				
				int newGame = 0;
				
				newGame = (int)(3 * Math.random() + 1);
				
				if(newGame == 1)
				{
					displayplate.setIcon(BrokenPlates);
					displayprize.setIcon(TigerPlush);
				}
				else if(newGame == 2)
				{
					displayplate.setIcon(TwoBrokenPlates);
					displayprize.setIcon(Sticker);
				}
				else if(newGame == 3)
				{
					displayplate.setIcon(Plates);
					displayprize.setIcon(null);
				}
				
			}
			
			
			
		});
		
		
		PlayButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		PlayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		PlayButton.setBounds(77, 130, 142, 56);
		panel.add(PlayButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
