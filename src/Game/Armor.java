package Game;

public class Armor {
    private String name;
    private String type; // "helma", "chestplate", "kalhoty"
    private int mageBoost;
    private int warriorBoost;

    public Armor(String name, String type, int mageBoost, int warriorBoost) {
        this.name = name;
        this.type = type;
        this.mageBoost = mageBoost;
        this.warriorBoost = warriorBoost;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getMageBoost() {
        return mageBoost;
    }

    public int getWarriorBoost() {
        return warriorBoost;
    }
}