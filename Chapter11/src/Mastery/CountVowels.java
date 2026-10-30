package Mastery;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CountVowels {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter file name: ");
        String fileName = input.nextLine();

        int count = 0;

        try {
            File file = new File(fileName);
            Scanner fileInput = new Scanner(file);
            
            while (fileInput.hasNextLine()) {
                String line = fileInput.nextLine();

                for (int i = 0; i < line.length(); i++) {
                    char letter = Character.toLowerCase(line.charAt(i));

                    if (letter == 'a' || letter == 'e' || letter == 'i'
                            || letter == 'o' || letter == 'u') {
                        count++;
                    }
                }
            }

            fileInput.close();

            System.out.println("The number of vowels in " + fileName + " is " + count + ".");

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }

        input.close();
    }
}

/*
Enter file name: ../Chapter11/src/Mastery/Text.txt
The number of vowels in ../Chapter11/src/Mastery/Text.txt is 200.
*/