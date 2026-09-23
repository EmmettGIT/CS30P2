package Skillbuilders;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;

public class MetricConvertor {

	private JFrame frame;
	private JTextField fc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MetricConvertor window = new MetricConvertor();
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
	public MetricConvertor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(51, 153, 0));
		frame.setBounds(100, 100, 464, 270);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel Display = new JLabel("");
		Display.setBounds(10, 151, 428, 69);
		Display.setFont(new Font("Tahoma", Font.PLAIN, 15));
		frame.getContentPane().add(Display);
		
		JLabel Label = new JLabel("Select a conversion type:");
		Label.setBounds(37, 0, 378, 69);
		Label.setFont(new Font("Tahoma", Font.BOLD, 30));
		frame.getContentPane().add(Label);
		
		JComboBox ComboBox = new JComboBox();
		ComboBox.setBounds(37, 85, 378, 55);
		ComboBox.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
			
				if(ComboBox.getSelectedItem().equals("1 pound = 0.4536 kilograms"))
				{
					String n = fc.getText();
					double Num = Double.parseDouble(n);
					double answer = Num * 0.4536;
					
					Display.setText(Num + " pounds converted to kilograms is: "
							+ answer + " kg");
				}
				
				if(ComboBox.getSelectedItem().equals("1 gallon = 3.78541 liters"))
				{
					String n = fc.getText();
					double Num = Double.parseDouble(n);
					double answer = Num * 3.78541;
					
					Display.setText(Num + " gallons converted to litres is: "
							+ answer + " L");
				}
				
				if(ComboBox.getSelectedItem().equals("1 foot = 0.3048 meters"))
				{
					String n = fc.getText();
					double Num = Double.parseDouble(n);
					double answer = Num * 0.3048;
					
					Display.setText(Num + " feet converted to meters is: "
							+ answer + " m");
				}
				
				if(ComboBox.getSelectedItem().equals("1 inch = 2.54 centimeters"))
				{
					String n = fc.getText();
					double Num = Double.parseDouble(n);
					double answer = Num * 2.54;
					
					Display.setText(Num + " inches converted to centimeters is: "
										+ answer + " cm");
				}
				
			}
		});
		ComboBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ComboBox.setModel(new DefaultComboBoxModel(new String[] {"Click here to convert", "1 inch = 2.54 centimeters", "1 foot = 0.3048 meters", "1 gallon = 3.78541 liters", "1 pound = 0.4536 kilograms"}));
		frame.getContentPane().add(ComboBox);
		
		fc = new JTextField();
		fc.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{
				if(fc.getText().equals("Enter your number here"))
				{
					fc.setText("");
				}
			}
		});
		fc.setText("Enter your number here");
		fc.setBounds(146, 58, 139, 20);
		frame.getContentPane().add(fc);
		fc.setColumns(10);
		
		
	}
}
