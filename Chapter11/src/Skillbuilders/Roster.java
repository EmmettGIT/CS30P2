package Skillbuilders;

import java.io.*;
import java.util.Scanner;

public class Roster 
{
    public static void main(String[] args) 
    {
        File dataFile;

        FileReader in;
        BufferedReader readFile;

        FileWriter out;
        BufferedWriter writeFile;

        Scanner input = new Scanner(System.in);

        String fileName;
        String firstName, lastName;

        int numStu;

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
                System.out.println("Enter student first name: ");
                firstName = input.next();

                System.out.println("Enter student last name: ");
                lastName = input.next();

                writeFile.write(firstName);

                writeFile.write(lastName);
                writeFile.newLine();
            }

            writeFile.close();
            out.close();

            System.out.println("Roster written to file. ");

        }
        catch(IOException e)
        {
        	System.out.println("There was a problem writing to the file. ");
        	System.err.println("IOException " + e.getMessage());
        }

        try 
        {
        	dataFile = new File(fileName);

        	in = new FileReader(dataFile);
        	readFile = new BufferedReader(in);

        	System.out.println("\n<---Class Roster--->");

        	while((firstName = readFile.readLine()) != null)
        	{
        		lastName = readFile.readLine();

        		if(lastName != null)
        		{
        			stuName student = new stuName(firstName, lastName);
        			System.out.println(student.toString());
                }
            }

            readFile.close();
            in.close();

        }
        catch(FileNotFoundException e)
        {
            System.out.println("File does not exist or could not be found.");
            System.err.println("FileNotFoundException " + e.getMessage());
        }
        catch(IOException e)
        {
            System.out.println("There was a problem reading the file.");
            System.err.println("IOException " + e.getMessage());
        }

        input.close();
    }
}

class stuName
{
    String firstName;
    String lastName;

    public stuName(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String toString()
    {
        return firstName + " " + lastName;
    }
}