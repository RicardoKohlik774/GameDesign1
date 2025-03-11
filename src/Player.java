import java.util.ArrayList;

public class Player {

    private String name;
    private int health;
    private int attack;
    private ArrayList<Item> inventory;
    private Weapon equippedWeapon;
    private Location currentLocation;
    private boolean hasMainItems;

    public void moveTo(Location location) {

    }

    public void talkTo(Ally ally) {

    }

    public void fight(Enemy enemy) {

    }

    public void trade(Ally ally) {

    }

    public void searchLocation() {

    }

    public void pickUpItem(Item item) {

    }

    public void dropItem(Item item) {

    }

    public void useItem(Item item) {

    }

    public void pray() {

    }

    public boolean hasMainItems() {
        return false;
    }


}
