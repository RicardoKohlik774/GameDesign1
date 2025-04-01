package Game;

/**
 * Represents an item in the game.
 * Items can be movable or immovable, and some are required to finish the game.
 */
public class Item {
    private String name;
    private boolean isMovable;
    private boolean isMain;


    public Item(String name, boolean isMovable, boolean isMain) {
        this.name = name;
        this.isMovable = isMovable;
        this.isMain = isMain;

    }

    public String getName() {
        return name;
    }

    public boolean isMovable() {
        return isMovable;
    }

    public boolean isMain() {
        return isMain;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", isMovable=" + isMovable +
                '}';
    }
}