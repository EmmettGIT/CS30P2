package Mastery;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

public class StudentSemesterAverage {

	private JFrame frame;
	private JTextField stuName;
	private JTextField grdLvl;
	private JTextField semNum;
	private JTextField grd1;
	private JTextField grd2;
	private JTextField grd3;
	private JTextField grd4;
	private JLabel avgGrade;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentSemesterAverage window = new StudentSemesterAverage();
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
	public StudentSemesterAverage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 516, 525);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 153));
		panel.setBounds(0, 0, 500, 486);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		stuName = new JTextField();
		stuName.setBounds(249, 11, 241, 20);
		panel.add(stuName);
		stuName.setColumns(10);
		
		grdLvl = new JTextField();
		grdLvl.setColumns(10);
		grdLvl.setBounds(249, 42, 241, 20);
		panel.add(grdLvl);
		
		semNum = new JTextField();
		semNum.setColumns(10);
		semNum.setBounds(249, 73, 241, 20);
		panel.add(semNum);
		
		grd1 = new JTextField();
		grd1.setColumns(10);
		grd1.setBounds(249, 104, 241, 20);
		panel.add(grd1);
		
		grd2 = new JTextField();
		grd2.setColumns(10);
		grd2.setBounds(249, 135, 241, 20);
		panel.add(grd2);
		
		grd3 = new JTextField();
		grd3.setColumns(10);
		grd3.setBounds(249, 166, 241, 20);
		panel.add(grd3);
		
		grd4 = new JTextField();
		grd4.setColumns(10);
		grd4.setBounds(249, 197, 241, 20);
		panel.add(grd4);
		
		JLabel stuNametxt = new JLabel("Student Name:");
		stuNametxt.setFont(new Font("Tahoma", Font.BOLD, 11));
		stuNametxt.setForeground(new Color(255, 255, 255));
		stuNametxt.setBounds(10, 11, 224, 20);
		panel.add(stuNametxt);
		
		JLabel grdLvltxt = new JLabel("Grade Level:");
		grdLvltxt.setFont(new Font("Tahoma", Font.BOLD, 11));
		grdLvltxt.setForeground(new Color(255, 255, 255));
		grdLvltxt.setBounds(10, 42, 224, 20);
		panel.add(grdLvltxt);
		
		JLabel semNumtxt = new JLabel("Semester Number:");
		semNumtxt.setFont(new Font("Tahoma", Font.BOLD, 11));
		semNumtxt.setBackground(new Color(240, 240, 240));
		semNumtxt.setForeground(new Color(255, 255, 255));
		semNumtxt.setBounds(10, 73, 224, 20);
		panel.add(semNumtxt);
		
		JLabel grd1txt = new JLabel("Grade 1:");
		grd1txt.setFont(new Font("Tahoma", Font.BOLD, 11));
		grd1txt.setForeground(new Color(255, 255, 255));
		grd1txt.setBounds(10, 104, 224, 20);
		panel.add(grd1txt);
		
		JLabel grd2txt = new JLabel("Grade 2:");
		grd2txt.setFont(new Font("Tahoma", Font.BOLD, 11));
		grd2txt.setForeground(new Color(255, 255, 255));
		grd2txt.setBounds(10, 135, 224, 20);
		panel.add(grd2txt);
		
		JLabel grd3txt = new JLabel("Grade 3:");
		grd3txt.setFont(new Font("Tahoma", Font.BOLD, 11));
		grd3txt.setForeground(new Color(255, 255, 255));
		grd3txt.setBounds(10, 166, 224, 20);
		panel.add(grd3txt);
		
		JLabel grd4txt = new JLabel("Grade 4:");
		grd4txt.setFont(new Font("Tahoma", Font.BOLD, 11));
		grd4txt.setForeground(new Color(255, 255, 255));
		grd4txt.setBounds(10, 197, 224, 20);
		panel.add(grd4txt);
		
		avgGrade = new JLabel("Average:");
		avgGrade.setFont(new Font("Tahoma", Font.BOLD, 11));
		avgGrade.setForeground(new Color(255, 255, 255));
		avgGrade.setBounds(10, 228, 51, 20);
		panel.add(avgGrade);
		
		JLabel percentAvg = new JLabel("");
		percentAvg.setForeground(new Color(255, 255, 255));
		percentAvg.setFont(new Font("Tahoma", Font.BOLD, 11));
		percentAvg.setBounds(65, 230, 169, 17);
		panel.add(percentAvg);
		
		JTextArea displayStuAvgs = new JTextArea();
		displayStuAvgs.setBounds(10, 259, 480, 166);
		panel.add(displayStuAvgs);
		
		JButton saveBtn = new JButton("Save to File");
		saveBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
		saveBtn.setBackground(new Color(255, 204, 51));
		saveBtn.setBounds(99, 436, 141, 39);
		panel.add(saveBtn);
		
		JButton filecontentsBtn = new JButton("View File Contents");
		filecontentsBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
		filecontentsBtn.setBackground(new Color(255, 204, 51));
		filecontentsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		filecontentsBtn.setBounds(260, 436, 141, 39);
		panel.add(filecontentsBtn);
	}
}
