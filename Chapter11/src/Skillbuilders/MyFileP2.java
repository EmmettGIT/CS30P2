package Skillbuilders;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MyFileP2 {

	public static void main(String[] args) 
	{
		// DO NOT USE SCANNER TO ACCESS FILES!!! IMPORTANT!!!
		File textFile;
		String response;
		
		Scanner input = new Scanner(System.in);
		
		//create a file
		textFile = new File("C:\\Users\\49254008\\git\\CS30P2\\Chapter11\\src\\Skillbuilders\\zzz.txt");

		//check if file exists
		if(textFile.exists()) 
		{
		System.out.println("zzz.txt exists");
		}
		else 
		{
			try {
					textFile.createNewFile();
					System.out.println("zzz.txt file created. good job little fella");
				}
			catch(IOException e) 
			{
				System.out.println("File could not be created. nice try homie.");
				System.err.println("IOException: " + e.getMessage());
				
				
			}
		}
		//delete if user chooses to delete the silly little file
		System.out.println("Would you like to (Y)keep the file or (N)nah? ");
		response = input.next();
		
		if(response.equalsIgnoreCase("N"))
		{
			//delete file
			if(textFile.delete())
			{
				System.out.println("Say goodbye to the file! it's gone.");
			}
		}
		else
		{
			System.out.println("File has NOT been deleted. dunno why you would keep it though. ");
		}
		
	}

}
