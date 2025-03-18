package Game;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Weapon> weapons;
    private ArrayList<Armor> helmets;
    private ArrayList<Armor> chestplates;
    private ArrayList<Armor> pants;
    private Key key;

    public Inventory() {
        weapons = new ArrayList<>();
        helmets = new ArrayList<>();
        chestplates = new ArrayList<>();
        pants = new ArrayList<>();
        key = null;
    }

    public boolean discardWeapon() {
        if (weapons.size() > 1) {
            weapons.remove(1);
            System.out.println("Zbran byla zahozena.");
            return true;
        } else {
            System.out.println("Nemuzes zahodit posledni zbran.");
            return false;
        }
    }

    public boolean discardHelmet() {
        if (helmets.size() > 1) {
            helmets.remove(1);
            System.out.println("Helma byla zahozena.");
            return true;
        } else {
            System.out.println("Nemuzes zahodit posledni helmu.");
            return false;
        }
    }

    public boolean discardChestplate() {
        if (chestplates.size() > 1) {
            chestplates.remove(1);
            System.out.println("Chestplate byl zahozen.");
            return true;
        } else {
            System.out.println("Nemuzes zahodit posledni chestplate.");
            return false;
        }
    }

    public boolean discardPants() {
        if (pants.size() > 1) {
            pants.remove(1);
            System.out.println("Kalhoty byly zahozeny.");
            return true;
        } else {
            System.out.println("Nemuzes zahodit posledni kalhoty.");
            return false;
        }
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
            System.out.println("Mas plne sloty na zbrane.");
            return false;
        }
    }

    public boolean addHelmet(Armor helmet) {
        if (helmets.size() < 2) {
            helmets.add(helmet);
            return true;
        } else {
            System.out.println("Mas plne sloty na helmy.");
            return false;
        }
    }

    public boolean addChestplate(Armor chestplate) {
        if (chestplates.size() < 2) {
            chestplates.add(chestplate);
            return true;
        } else {
            System.out.println("Mas plne sloty na chestplaty.");
            return false;
        }
    }

    public boolean addPants(Armor pant) {
        if (pants.size() < 2) {
            pants.add(pant);
            return true;
        } else {
            System.out.println("Mas plne sloty na kalhoty.");
            return false;
        }
    }

    public boolean addKey(Key newKey) {
        if (key == null) {
            key = newKey;
            return true;
        } else {
            System.out.println("Uz mas klic. Nelze nest vice klicu.");
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
            System.out.println("Nemuzes vybavit jinou zbran, protoze nemas zadnou v rezerve.");
            return false;
        } else {
            Weapon temp = weapons.get(0);
            weapons.set(0, weapons.get(1));
            weapons.set(1, temp);
            System.out.println("Vybavil jsi: " + weapons.get(0).getName());
            return true;
        }
    }

    public boolean equipHelmet() {
        if (helmets.size() < 2) {
            System.out.println("Nemuzes vybavit jinou helmu, protoze nemas zadnou v rezerve.");
            return false;
        } else {
            Armor temp = helmets.get(0);
            helmets.set(0, helmets.get(1));
            helmets.set(1, temp);
            System.out.println("Vybavil jsi: " + helmets.get(0).getName());
            return true;
        }
    }

    public boolean equipChestplate() {
        if (chestplates.size() < 2) {
            System.out.println("Nemuzes vybavit jiny chestplate, protoze nemas zadny v rezerve.");
            return false;
        } else {
            Armor temp = chestplates.get(0);
            chestplates.set(0, chestplates.get(1));
            chestplates.set(1, temp);
            System.out.println("Vybavil jsi: " + chestplates.get(0).getName());
            return true;
        }
    }

    public boolean equipPants() {
        if (pants.size() < 2) {
            System.out.println("Nemuzes vybavit jine kalhoty, protoze nemas zadne v rezerve.");
            return false;
        } else {
            Armor temp = pants.get(0);
            pants.set(0, pants.get(1));
            pants.set(1, temp);
            System.out.println("Vybavil jsi: " + pants.get(0).getName());
            return true;
        }
    }
}