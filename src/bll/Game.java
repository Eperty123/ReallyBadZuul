package bll;

import java.util.HashMap;

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
    private Inventory inventory;
    private HashMap<String, Player> players;
    private Player currentPlayer;
    private int maxPlayers = 2;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        parser = new Parser();
        inventory = new Inventory();
        players = new HashMap<>();
        createRooms();
        createPlayers();
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
     * Create all players.
     */
    private void createPlayers() {
        // You can change the naming implementation.
        // This is just for demonstration.
        int index = 1;
        for (int i = 0; i < maxPlayers; i++) {
            String name = String.format("bll.Player %d", index);
            var player = new Player(name, currentRoom, 2);
            players.put(name, player);
            index++;
        }

        // Assign the first player element in our player list as the current player.
        // It is required to be set or you'll break the game!
        currentPlayer = players.get("bll.Player 1");
    }

    /**
     * Change player.
     *
     * @param command The change player command.
     */
    private void changePlayer(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            print("What player?");
            return;
        }
        String playerName = command.getRestWord();
        Player newPlayer = players.get(playerName);
        if (newPlayer == null) print(String.format("Failed to change player to: %s.", playerName));
        else {
            if (currentPlayer != newPlayer) {
                currentPlayer = newPlayer;
                print(String.format("Changed player to: %s.", playerName));
                currentRoom = currentPlayer.getCurrentRoom();
                printLocation();
            } else print("You are already THAT player.");
        }
    }

    /**
     * Get all players.
     *
     * @return Returns all the available players.
     */
    public String getPlayersString() {
        String returnString = "";
        int index = 0;
        if (players.values().size() > 0) {
            for (Player player : players.values()) {
                index++;
                if (index != players.values().size())
                    returnString += String.format("%s\n\n", player.toString());
                else returnString += String.format("%s", player.toString());
            }
        } else returnString += "none";
        return returnString;
    }

    /**
     * Main play routine. Loops until end of play.
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
        print(String.format("Thank you for playing, %s. Good bye.", currentPlayer.getName()));
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

    private void printLocation() {
        print(String.format("[%s]: You are %s", currentPlayer.getName(), currentRoom.getDescription()));
        print("");
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
            print(String.format("[%s]: Bro what the fuck?", currentPlayer.getName()));
            return false;
        }

        var commandWord = CommandWord.Parse(command.getCommandWord());
        switch (commandWord) {
            case CHANGEPLAYER:
                changePlayer(command);
                break;
            case GO:
                goRoom(command);
                break;

            case INVENTORY:
                print(inventory.getInventoryItemsString());
                break;

            case LOOK:
                look();
                break;

            case HELP:
                printHelp();
                break;

            case PICKUP:
                pickupItem(command);
                break;

            case PLAYERS:
                print(getPlayersString());
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
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
            currentPlayer.setCurrentRoom(nextRoom);
            printLocation();
        }
    }

    /**
     * Try to look around in the given direction.
     */
    private void look() {
        if (currentRoom != null)
            print(String.format("[%s]: You look around and find...\n%s\n%s", currentPlayer.getName(), currentRoom.getExitString(), currentRoom.getItemString()));
        else
            print(String.format("[%s]: You're surrounded by darkness and need to try your way out of it.", currentPlayer.getName()));
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

        String item = command.getRestWord();

        // Try to pickup the item.
        Item foundItem = currentRoom.getItem(item);

        if (foundItem == null) {
            print("That item doesn't exit!");
        } else {
            handleItemPickup(foundItem);
        }
    }

    /**
     * bll.Item pickup logic.
     *
     * @param item The item to pickup.
     */
    private void handleItemPickup(Item item) {

        // Add the item to our carry list.
        if (currentPlayer.canCarryItems()) {
            currentPlayer.carryItem(item);
            print(String.format("[%s]: You picked up: %s", currentPlayer.getName(), item.getItemFullDescription()));

        } else print(String.format("[%s]: I can't carry any more items!", currentPlayer.getName()));

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