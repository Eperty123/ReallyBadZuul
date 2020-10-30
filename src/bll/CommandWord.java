package bll;

/**
 * Representations for all the valid command words for the game
 * along with a string in a particular language.
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2011.08.10
 */
public enum CommandWord {
    // A value for each command word along with its
    // corresponding user interface string.

    CHANGEPLAYER("changeplayer"),
    GO("go"),
    HELP("help"),
    LOOK("look"),
    INVENTORY("inventory"),
    PICKUP("pickup"),
    PLAYERS("players"),
    QUIT("quit"),
    UNKNOWN("?");

    // The command string.
    private String commandString;

    /**
     * Initialize with the corresponding command string.
     *
     * @param commandString The command string.
     */
    CommandWord(String commandString) {
        this.commandString = commandString;
    }

    /**
     * @return The command word as a string.
     */
    public String toString() {
        return commandString;
    }

    /**
     * Parse the command string as a bll.Command Word.
     *
     * @param commandString The command string to parse.
     * @return Returns the appropriate enum equivelant of the command string.
     */
    public static CommandWord Parse(String commandString) {
        for (CommandWord cmd : CommandWord.values()) {
            if (cmd.toString().equalsIgnoreCase(commandString))
                return cmd;
        }
        return CommandWord.UNKNOWN;
    }

    /**
     * Get all available commands.
     *
     * @return All the commands.
     */
    public static String getAllCommands() {
        String returnString = "";
        int index = 0;
        for (CommandWord command : CommandWord.values()) {
            index++;
            if (index != CommandWord.values().length)
                returnString += String.format("%s, ", command);
            else returnString += String.format("%s.", command);
        }
        return returnString;
    }
}