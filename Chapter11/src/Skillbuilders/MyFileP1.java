package Skillbuilders;

import java.io.*;
import java.util.Scanner;

public class MyFileP1 
{

	public static void main(String[] args) 
	{
		// DO NOT USE SCANNER TO ACCESS FILES!!! IMPORTANT!!!
		File textFile;
		String fileName;
		Scanner input = new Scanner(System.in);
		
		//obtain file name from user
		System.out.println("Enter file name: ");
		
		//store file name in fileName
		fileName = input.next();
		
		//determine if file exists or not
		textFile = new File(fileName);
		
		if(textFile.exists())
		{
			System.out.println("Yay! File exists. ");
		}
		else
		{
			System.out.println("File does NOT exist buddy. Womp womp");
		}
	}

}
