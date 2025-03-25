package Game;

public class Armor {
    private String name;
    private String type;
    private int mageBoost;
    private int warriorBoost;
    private int price;

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

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Armor{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", mageBoost=" + mageBoost +
                ", warriorBoost=" + warriorBoost +
                ", price=" + price +
                '}';
    }
}