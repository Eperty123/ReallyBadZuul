/**
 * This class is the main class of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.  Users
 * can walk around some scenery. That's all. It should really be extended
 * to make it more interesting!
 * <p>
 * To play this game, create an instance of this class and call the "play"
 * method.
 * <p>
 * This main class creates and initialises all the others: it creates all
 * rooms, creates the parser and starts the game.  It also evaluates and
 * executes the commands that the parser returns.
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game {
    private Parser parser;
    private Room currentRoom;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        Room outside, theater, pub, lab, office;

        // create the rooms
        outside = new Room("outside the main entrance of the university.");
        theater = new Room("in a lecture theater.");
        pub = new Room("in the campus pub.");
        lab = new Room("in a computing lab.");
        office = new Room("in the computing admin office.");

        lab.setExit("west", outside);
        outside.setExit("south", lab);

        lab.addItem("iPhone X", "Who leaves behind an expensive phone like this?", 1.5);
        outside.addItem("Note", "\"You will never escape this hell hole, bro.\"", 1.5);


        currentRoom = outside;  // start game outside
    }

    /**
     * Main play routine.  Loops until end of play.
     */
    public void play() {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        print("Thank you for playing. Good bye.");
    }

    private void printLocation() {
        print(currentRoom.getDescription());
        print("");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        print("");
        print("Welcome to the World of Zuul!");
        print("World of Zuul is a new, incredibly boring adventure game.");
        print("Type 'help' if you need help.");
        print("");

        // Get the current location and print it.
        printLocation();
    }

    /**
     * Given a command, process (that is: execute) the command.
     *
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            print("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        } else if (commandWord.equals("go")) {
            goRoom(command);
        } else if (commandWord.equals("look")) {
            look();
        } else if (commandWord.equals("pickup")) {
            pickupItem(command);
        } else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the
     * command words.
     */
    private void printHelp() {
        print("You are lost. You are alone. You wander around");
        print("around at the university.");
        print("");
        print("Your command words are:");
        print(parser.getCommands());
    }

    /**
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            print("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        currentRoom = nextRoom;

        if (nextRoom == null) {
            print("There is no door!");
        } else {
            printLocation();
        }
    }

    /**
     * Try to look in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void look() {

        print(String.format("You look around and find...\n%s\n%s", currentRoom.getExitString(), currentRoom.getItemString()));
    }

    /**
     * Handle the pickup command.
     */
    private void pickupItem(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            print("Pickup what?");
            return;
        }

        String item = command.getSecondWord();

        // Try to leave current room.
        Item foundItem = currentRoom.getItem(item);

        if (foundItem == null) {
            print("That item doesn't exit!");
        } else {
            handleItemPickup(foundItem);
        }
    }

    /**
     * Item pickup logic.
     *
     * @param item The item to pickup.
     */
    private void handleItemPickup(Item item) {
        print(String.format("You picked up: %s", item.getItemFullDescription()));
        // Remove the item from the room.
        currentRoom.removeItem(item);
    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     *
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            print("Quit what?");
            return false;
        } else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * A helper method to print lines.
     *
     * @param text The text to print.
     */
    public static void print(String text) {
        System.out.println(text);
    }
}