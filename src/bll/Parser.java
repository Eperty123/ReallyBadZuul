package bll;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 * <p>
 * This parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a two word command. It returns the command
 * as an object of class bll.Command.
 * <p>
 * The parser has a set of known command words. It checks user input against
 * the known commands, and if the input is not one of the known commands, it
 * returns a command object that is marked as an unknown command.
 *
 * @author Carlo De Leon, Michael Kölling and David J. Barnes
 * @version 2020.10.30
 */
public class Parser {
    private Scanner reader;         // source of command input

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() {
        reader = new Scanner(System.in);
    }

    /**
     * @return The next command from the user.
     */
    public Command getCommand() {
        String inputLine;   // will hold the full input line
        String word1 = null;
        String word2 = null;

        System.out.print("> ");     // print prompt

        inputLine = reader.nextLine();
        ArrayList<String> restWords = new ArrayList<>();
        // Find up to two words on the line.
        Scanner tokenizer = new Scanner(inputLine);

        // We use the index to determine where we are
        // in the word array.
        int index = 0;

        // Loop through all the words.
        while (tokenizer.hasNext()) {
            // Get the current word.
            String word = tokenizer.next();

            //Get the command words from the index 0 (first word).
            if (index == 0) word1 = word;
                // then the next (second word).
            else if (index == 1) word2 = word;

            // Get the rest of the input once we get past
            // the command word.
            if (index > 0) restWords.add(word);

            // We must have the index increment at the bottom
            // to ensure the command words get registered properly.
            index++;
        }

        // Now check whether this word is known. If so, create a command
        // with it. If not, create a "null" command (for unknown command).
        if (CommandWord.Parse(word1) != CommandWord.UNKNOWN) {
            return new Command(word1, word2, String.join(" ", restWords));
        } else {
            return new Command(null, word2);
        }

    }

    public String getCommands() {
        return CommandWord.getAllCommands();
    }
}
