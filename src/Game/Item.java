package Game;

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
}