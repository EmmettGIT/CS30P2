package Skillbuilders;

import java.io.*;
import java.text.NumberFormat;
import java.util.Scanner;

public class StatsP2 {

	public static void main(String[] args) 
	{
		File dataFile;
		
		FileReader in;
		BufferedReader readFile;
		
		FileWriter out;
		BufferedWriter writeFile;
		
		Scanner input = new Scanner(System.in);
		
		String fileName;
		String stuName, score;
		
		int numStu;
		int numScores = 0;
		
		double scoreValue;
		double lowScore = 100;
		double highScore = 0;
		double avgScore;
		double totalScore = 0;
		
		NumberFormat percent = NumberFormat.getPercentInstance();
		
		//write data to a file
		
		System.out.println("Enter file name: ");
		fileName = input.next();
		
		System.out.println("How many students? ");
		numStu = input.nextInt();
		
		try
		{
			dataFile = new File(fileName);
			
			out = new FileWriter(dataFile);
			writeFile = new BufferedWriter(out);
			
			for(int i=0; i < numStu; i++)
			{
				System.out.println("Enter student name: ");
				stuName = input.next();
				
				System.out.println("Enter test score: ");
				score = input.next();
				
				writeFile.write(stuName);
				writeFile.newLine();
				writeFile.write(score);
				writeFile.newLine();
				
			} //end for loop
			
			writeFile.close();
			out.close();
			System.out.println("Data written to file. ");
			
			
		} //closes try
		catch(IOException e)
		{
			System.out.println("Ruh-roh. There was a problem reading the file. ");
			System.err.println("IOException" + e.getMessage());
		}
		
		
		//read data from file and process
		try 
		{
			
			dataFile = new File(fileName);
			
			in = new FileReader(dataFile);
			readFile = new BufferedReader(in);
			
			while((stuName = readFile.readLine()) != null)
			{
				score = readFile.readLine();
				System.out.println(stuName + " " + score);
				
				numScores += 1;
				
				scoreValue = Double.parseDouble(score);
				
				totalScore += scoreValue;
				
				if(scoreValue < lowScore)
				{
					lowScore = scoreValue;
				}
				if(scoreValue > highScore)
				{
					highScore = scoreValue;
				}
			} //end of while loop
			
			avgScore = totalScore / numScores;
			System.out.println("Low score = " + percent.format(lowScore/100));
			System.out.println("High score = " + percent.format(highScore/100));
			System.out.println("Average score = " + percent.format(avgScore/100));
			
			readFile.close();
			in.close();
			
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File does not exist or could not be found. damn bro. that's too bad.");
			System.err.println("FileNotFoundException" + e.getMessage());
		}
		catch(IOException e)
		{
			System.out.println("Ruh-roh. There was a problem reading the file. ");
			System.err.println("IOException" + e.getMessage());
		}
		
	}

}
