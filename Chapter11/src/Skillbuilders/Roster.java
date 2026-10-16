package Skillbuilders;

import java.io.*;
import java.util.Scanner;

public class Roster 
{

	public static void main(String[] args) 
	{
		
		File textFile;
		String fileName;
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter file name: ");
		
		fileName = input.next();
		
		textFile = new File(fileName);
		
		if(textFile.exists())
		{
			System.out.println("Wow! That file DOES exist! ");
			System.out.println("How many students in the class?");
		}
		else
		{
			System.out.println("Ruh-roh. That file definetely does NOT exist. "
					+ "\nMaybe you spelt the file name wrong or something. ");
		}
	}

}
