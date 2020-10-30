import java.util.HashMap;
import java.util.Set;

/**
 * Class Room - a room in an adventure game.
 * <p>
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 * <p>
 * A "Room" represents one location in the scenery of the game.  It is
 * connected to other rooms via exits.  The exits are labelled north,
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Room {
    public String description;
    private HashMap<String, Room> exits;
    private HashMap<String, Item> items;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     *
     * @param description The room's description.
     */
    public Room(String description) {

        this.description = description;
        exits = new HashMap<>();
        items = new HashMap<>();
    }


    /**
     * Define an nexit from this room.
     *
     * @param direction The direction of the exit.
     * @param neighbor  The room in the given direction.
     */
    public void setExit(String direction, Room neighbor) {
        if (!exits.containsKey(direction))
            exits.put(direction, neighbor);
    }

    /**
     * Find an exit based on the direction.
     *
     * @param direction The direction to look for an exit.
     * @return The exit.
     */
    public Room getExit(String direction) {
        return exits.get(direction);
    }

    /**
     * Return all available exits.
     *
     * @return All exits.
     */
    public String getExitString() {
        String returnString = "Exits: ";
        Set<String> keys = exits.keySet();
        int index = 0;
        for (String exit : keys) {
            index++;
            if (index != keys.size())
                returnString += String.format("%s, ", exit);
            else returnString += String.format("%s.", exit);
        }
        return returnString;
    }

    /**
     * @return The description of the room.
     */
    public String getDescription() {
        return String.format("You are %s", description);
    }

    /**
     * @return Get the description of the room along with its exits.
     */
    public String getLongDescription() {
        return String.format("You are %s\n%s", description, getExitString());
    }

    /**
     * Add an item to the room.
     *
     * @param name        Name of the item.
     * @param description Description of the item.
     * @param weight      The weight of the item.
     */
    public void addItem(String name, String description, double weight) {
        if (!hasItem(name)) items.put(name, new Item(name, description, weight));
    }

    /**
     * Get an item from the room.
     *
     * @param name The name of the item.
     * @return The found item.
     */
    public Item getItem(String name) {
        return items.get(name);
    }

    /**
     * Return all available items in the room.
     *
     * @return All items.
     */
    public String getItemString() {
        String returnString = "Items: ";
        Set<String> keys = items.keySet();
        int index = 0;
        for (String item : keys) {
            index++;
            if (index != keys.size())
                returnString += String.format("%s, ", item);
            else returnString += String.format("%s.", item);
        }
        return returnString;
    }

    /**
     * Does the room have the item with the given name?
     *
     * @param name The name of the item.
     * @return Returns true if it has otherwise false.
     */
    public boolean hasItem(String name) {
        return items.containsKey(name);
    }

    /**
     * Remove the item with the given name.
     *
     * @param name The name of the item to remove.
     */
    public void removeItem(String name) {
        if (hasItem(name)) items.remove(name);
    }

    /**
     * Remove an item.
     *
     * @param item The item to remove.
     */
    public void removeItem(Item item) {
        removeItem(item.getName());
    }

}
