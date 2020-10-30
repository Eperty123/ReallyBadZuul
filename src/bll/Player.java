package bll;

/**
 * The player class.
 *
 * @author Carlo De Leon
 * @version 2020.10.30
 */
public class Player {
    private String name;
    private int age;

    private Inventory inventory;
    private Room currentRoom;
    private double maxCarryWeight;

    /**
     * Initialize a new bll.Player instance.
     */
    public Player() {
        initialize();
    }

    /**
     * Initialize a new bll.Player instance with the given name.
     *
     * @param name The name for the player.
     */
    public Player(String name) {

        initialize();
        this.name = name;
    }

    /**
     * Initialize a new bll.Player instance with the given name and age.
     *
     * @param name The name for the player.
     * @param age  The age for the player.
     */
    public Player(String name, int age) {
        initialize();
        this.name = name;
        this.age = age;
    }

    /**
     * Initialize a new bll.Player instance with the given name and location (room).
     *
     * @param name The name for the player.
     * @param room The location (room) the player resides in.
     */
    public Player(String name, Room room) {
        initialize();
        this.name = name;
        this.currentRoom = room;
    }

    /**
     * Initialize a new bll.Player instance with the given name, location (room) and max carry weight.
     *
     * @param name           The name for the player.
     * @param room           The location (room) the player resides in.
     * @param maxCarryWeight The maximum carry weight.
     */
    public Player(String name, Room room, double maxCarryWeight) {
        initialize();
        this.name = name;
        this.currentRoom = room;
        this.maxCarryWeight = maxCarryWeight;
    }

    /**
     * The initialize method. Put all initialization code here.
     */
    private void initialize() {
        inventory = new Inventory(maxCarryWeight);
    }

    /**
     * Get the player's name.
     *
     * @return The player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the player's age.
     *
     * @return The player's age.
     */
    public int getAge() {
        return age;
    }

    /**
     * Get the player's current room.
     *
     * @return The player's current room.
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Get the bll.Inventory class instance.
     *
     * @return The bll.Inventory class instance.
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Can the player carry more items?
     *
     * @return Returns true if yes otherwise false.
     */
    public boolean canCarryItems() {
        return inventory.canCarryItems();
    }

    /**
     * Get the player's max carry weight.
     *
     * @return The player's max carry weight.
     */
    public double getMaxCarryWeight() {
        return maxCarryWeight;
    }

    /**
     * Set the player's name.
     *
     * @param name The new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the player's age.
     *
     * @param age The age.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Assign the player's location.
     *
     * @param room The location of the player.
     */
    public void setCurrentRoom(Room room) {
        currentRoom = room;
    }

    /**
     * Set the player's max carry weight.
     *
     * @param weight The maximum weight.
     */
    public void setMaxCarryWeight(double weight) {
        inventory.setMaxCarryWeight(weight);
    }

    /**
     * Carry an item.
     *
     * @param item The item to carry.
     */
    public void carryItem(Item item) {
        inventory.carryItem(item);
    }

    /**
     * Drop an item.
     *
     * @param item The item to drop.
     */
    public void dropItem(Item item) {
        inventory.removeItem(item);
    }

    /**
     * Drop an item with a given name.
     *
     * @param itemName The item name.
     */
    public void dropItem(String itemName) {
        inventory.removeItem(itemName);
    }

    @Override
    public String toString() {
        return String.format("bll.Player Information:\nName: %s\nAge: %d\n%s", getName(), getAge(),
                getInventory().getInventoryItemsString());
    }
}
