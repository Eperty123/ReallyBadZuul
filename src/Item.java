/**
 * The item class.
 *
 * @author Carlo De Leon
 * @version 2020.10.30
 */

public class Item {
    private String name;
    private String description;
    private double weight;

    /**
     * Create a new instance of the Item class.
     */
    public Item() {

    }

    public Item(String name) {
        this.name = name;
        weight = 1.0f;
    }

    public Item(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public Item(String name, String description, double weight) {
        this.name = name;
        this.description = description;
        this.weight = weight;
    }

    /**
     * @return Get the item name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Get the item description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return Get the item weifght.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Set the item name.
     *
     * @param name Name of the item.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the item description.
     *
     * @param description The description of the item.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set the item weight.
     *
     * @param weight The weight of the item.
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * @return Get the item's full description.
     */
    public String getItemFullDescription() {
        return String.format("%s - %s", getName(), getDescription());
    }
}
