package Mastery;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

public class WordGuess {

    public static void main(String[] args) throws IOException {

        String[] words = new String[100];
        int count = 0;

        Scanner fileInput = new Scanner(new File("../Chapter11/src/Mastery/words.txt"));

        while (fileInput.hasNextLine()) {
            words[count] = fileInput.nextLine();
            count++;
        }

        fileInput.close();

        Random generator = new Random();
        int number = generator.nextInt(count+1);

        String secretWord = words[number];

        Scanner input = new Scanner(System.in);
        String guess = "";

        System.out.println("Guess the secret word!");

        while (!guess.equals(secretWord)) {
            System.out.print("Enter your guess: ");
            guess = input.nextLine();

            if (!guess.equals(secretWord)) {
                System.out.println("Incorrect. Try again.");
            }
        }

        System.out.println("Correct! You guessed the word!");

        input.close();
    }
}

/* SCREEN DUMP
Guess the secret word!
Enter your guess: candy
Incorrect. Try again.
Enter your guess: earth
Incorrect. Try again.
Enter your guess: train
Correct! You guessed the word!
*/