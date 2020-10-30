package bll;

import java.util.HashMap;
import java.util.Set;

/**
 * The inventory for carrying items found in rooms.
 *
 * @author Carlo De Leon
 * @version 2020.10.30
 */
public class Inventory {

    private HashMap<String, Item> carriedItems;
    private double maxCarryWeight = 100;

    /**
     * Initialize a new bll.Item instance.
     */
    public Inventory() {
        initialize();
    }

    /**
     * Initialize a new bll.Inventory instance with the given max carry weight.
     *
     * @param maxCarryWeight The total weight the inventory can hold.
     */
    public Inventory(double maxCarryWeight) {
        initialize();
        this.maxCarryWeight = maxCarryWeight;
    }

    /**
     * The initialize method. Put all initialization code here.
     */
    private void initialize() {
        carriedItems = new HashMap<>();
    }

    /**
     * Carry the specified item.
     *
     * @param item The item to carry.
     */
    public void carryItem(Item item) {
        if (!carriedItems.containsKey(item.getName()) && canCarryItems()) {
            carriedItems.put(item.getName(), item);
        }
    }

    /**
     * Return all carried items.
     *
     * @return All carried items.
     */
    public String getInventoryItemsString() {
        String returnString = "bll.Inventory items: ";
        Set<String> keys = carriedItems.keySet();
        int index = 0;
        if (keys.size() > 0) {
            for (String item : keys) {
                index++;
                if (index != keys.size())
                    returnString += String.format("%s, ", item);
                else returnString += String.format("%s", item);
            }
        } else returnString += "none";
        return returnString;
    }

    /**
     * Are we already carrying such items?
     *
     * @param itemName The item name.
     * @return Returns true if we are otherwise false.
     */
    public boolean isCarryingItem(String itemName) {
        return carriedItems.containsKey(itemName);
    }

    /**
     * Are we already carrying such items?
     *
     * @param item The item.
     * @return Returns true if we are otherwise false.
     */
    public boolean isCarryingItem(Item item) {
        return isCarryingItem(item.getName());
    }

    /**
     * Remove the item from our inventory.
     *
     * @param itemName Name of the item.
     */
    public void removeItem(String itemName) {
        if (isCarryingItem(itemName))
            carriedItems.remove(itemName);
    }

    /**
     * Remove the item from our inventory.
     *
     * @param item The item.
     */
    public void removeItem(Item item) {
        removeItem(item.getName());
    }

    /**
     * Can we carry more items?
     *
     * @return returns true if we can otherwise false.
     */
    public boolean canCarryItems() {
        return getCurrentWeight() < maxCarryWeight;
    }

    /**
     * Get the current weight of carried items.
     *
     * @return Returns the total weight of all items.
     */
    public double getCurrentWeight() {
        double weight = 0;
        for (Item item : carriedItems.values()) {
            weight += item.getWeight();
        }
        return weight;
    }

    /**
     * @return Return the max carry weight.
     */
    public double getMaxCarryWeight() {
        return maxCarryWeight;
    }

    /**
     * Set the max carry weight.
     *
     * @param weight The maximum allowed weight.
     */
    public void setMaxCarryWeight(double weight) {
        maxCarryWeight = weight;
    }
}
