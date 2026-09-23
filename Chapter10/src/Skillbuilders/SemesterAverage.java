package Skillbuilders;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.SystemColor;

public class SemesterAverage {

    private JFrame frame;
    private JTextField grd1;
    private JTextField grd2;
    private JTextField grd3;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SemesterAverage window = new SemesterAverage();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public SemesterAverage() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 486, 292);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 215, 0));
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        grd1 = new JTextField();
        grd1.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyTyped(KeyEvent e) 
        	
        	{
        		if(grd1.getText().equals("Enter first grade"))
        		{
        			grd1.setText("");
        		}
        	}
        });
        grd1.setText("Enter first grade");
        grd1.setBounds(10, 11, 138, 49);
        panel.add(grd1);
        grd1.setColumns(10);

        grd2 = new JTextField();
        grd2.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyTyped(KeyEvent e) 
        	{
        		if(grd2.getText().equals("Enter second grade"))
        		{
        			grd2.setText("");
        		}
        	}
        });
        grd2.setText("Enter second grade");
        grd2.setBounds(10, 71, 138, 49);
        panel.add(grd2);
        grd2.setColumns(10);

        grd3 = new JTextField();
        grd3.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyTyped(KeyEvent e) 
        	{
        		if(grd3.getText().equals("Enter third grade"))
        		{
        			grd3.setText("");
        		}
        	}
        });
        grd3.setText("Enter third grade");
        grd3.setBounds(10, 131, 138, 49);
        panel.add(grd3);
        grd3.setColumns(10);

        JLabel displayavg = new JLabel("");
        displayavg.setBackground(SystemColor.text);
        displayavg.setForeground(SystemColor.desktop);
        displayavg.setFont(new Font("Tahoma", Font.PLAIN, 20));
        displayavg.setBounds(10, 191, 449, 49);
        panel.add(displayavg);

        JButton btnNewButton = new JButton("SUBMIT");

        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double g1 = Double.parseDouble(grd1.getText());
                    double g2 = Double.parseDouble(grd2.getText());
                    double g3 = Double.parseDouble(grd3.getText());

                    double avgGrade = (g1 + g2 + g3) / 3;

                    displayavg.setText("Average: " + avgGrade);
                } catch (NumberFormatException ex) {
                    displayavg.setText("Enter numbers only");
                }
            }
        });

        btnNewButton.setBackground(new Color(154, 205, 50));
        btnNewButton.setForeground(new Color(0, 100, 0));
        btnNewButton.setFont(new Font("Verdana", Font.BOLD, 50));
        btnNewButton.setBounds(167, 11, 292, 169);
        panel.add(btnNewButton);
    }
}
