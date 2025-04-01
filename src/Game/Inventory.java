package Game;

import java.util.ArrayList;

/**
 * Represents the player's inventory.
 * Stores weapons, armor pieces, main quest items and keys.
 * Also has methods for equipping, discarding, and managing items.
 */

public class Inventory {
    private ArrayList<Weapon> weapons;
    private ArrayList<Armor> helmets;
    private ArrayList<Armor> chestplates;
    private ArrayList<Armor> pants;
    private ArrayList<Item> Items = new ArrayList<>();
    private Key key;

    public Inventory() {
        weapons = new ArrayList<>();
        helmets = new ArrayList<>();
        chestplates = new ArrayList<>();
        pants = new ArrayList<>();
        key = null;
    }

    public ArrayList<Item> getItems() {
        return Items;
    }

    public boolean addItem(Item item) {
        Items.add(item);
        return true;
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public ArrayList<Armor> getHelmets() {
        return helmets;
    }

    public ArrayList<Armor> getChestplates() {
        return chestplates;
    }

    public ArrayList<Armor> getPants() {
        return pants;
    }



    public int getWeaponsCount() {
        return weapons.size();
    }

    public int getHelmetsCount() {
        return helmets.size();
    }

    public int getChestplatesCount() {
        return chestplates.size();
    }

    public int getPantsCount() {
        return pants.size();
    }




    public boolean discardWeapon() {
        if (weapons.size() > 1) {
            weapons.remove(1);

            return true;
        } else {

            return false;
        }
    }

    public boolean discardHelmet() {
        if (helmets.size() > 1) {
            helmets.remove(1);

            return true;
        } else {

            return false;
        }
    }

    public boolean discardChestplate() {
        if (chestplates.size() > 1) {
            chestplates.remove(1);

            return true;
        } else {

            return false;
        }
    }

    public boolean discardPants() {
        if (pants.size() > 1) {
            pants.remove(1);

            return true;
        } else {

            return false;
        }
    }

    public boolean sellActiveWeapon() {
        if (!weapons.isEmpty()) {
            weapons.remove(0);
            return true;
        }
        return false;
    }

    public boolean sellActiveHelmet() {
        if (!helmets.isEmpty()) {
            helmets.remove(0);
            return true;
        }
        return false;
    }

    public boolean sellActiveChestplate() {
        if (!chestplates.isEmpty()) {
            chestplates.remove(0);
            return true;
        }
        return false;
    }

    public boolean sellActivePants() {
        if (!pants.isEmpty()) {
            pants.remove(0);
            return true;
        }
        return false;
    }




    public Weapon getEquippedWeapon() {
        if (weapons.isEmpty()) {
            return null;
        } else {
            return weapons.get(0);
        }
    }

    public Armor getEquippedHelmet() {
        if (helmets.isEmpty()) {
            return null;
        } else {
            return helmets.get(0);
        }
    }

    public Armor getEquippedChestplate() {
        if (chestplates.isEmpty()) {
            return null;
        } else {
            return chestplates.get(0);
        }
    }

    public Armor getEquippedPants() {
        if (pants.isEmpty()) {
            return null;
        } else {
            return pants.get(0);
        }
    }



    public boolean addWeapon(Weapon weapon) {
        if (weapons.size() < 2) {
            weapons.add(weapon);
            return true;
        } else {

            return false;
        }
    }

    public boolean addHelmet(Armor helmet) {
        if (helmets.size() < 2) {
            helmets.add(helmet);
            return true;
        } else {

            return false;
        }
    }

    public boolean addChestplate(Armor chestplate) {
        if (chestplates.size() < 2) {
            chestplates.add(chestplate);
            return true;
        } else {

            return false;
        }
    }

    public boolean addPants(Armor pant) {
        if (pants.size() < 2) {
            pants.add(pant);
            return true;
        } else {

            return false;
        }
    }

    public boolean addKey(Key newKey) {
        if (key == null) {
            key = newKey;
            return true;
        } else {

            return false;
        }
    }

    public void removeKey() {
        key = null;
    }

    public Key getKey() {
        return key;
    }

    public boolean equipWeapon() {
        if (weapons.size() < 2) {

            return false;
        } else {
            Weapon temp = weapons.get(0);
            weapons.set(0, weapons.get(1));
            weapons.set(1, temp);

            return true;
        }
    }

    public boolean equipHelmet() {
        if (helmets.size() < 2) {

            return false;
        } else {
            Armor temp = helmets.get(0);
            helmets.set(0, helmets.get(1));
            helmets.set(1, temp);

            return true;
        }
    }

    public boolean equipChestplate() {
        if (chestplates.size() < 2) {

            return false;
        } else {
            Armor temp = chestplates.get(0);
            chestplates.set(0, chestplates.get(1));
            chestplates.set(1, temp);

            return true;
        }
    }

    public boolean equipPants() {
        if (pants.size() < 2) {

            return false;
        } else {
            Armor temp = pants.get(0);
            pants.set(0, pants.get(1));
            pants.set(1, temp);

            return true;
        }
    }
}