package Commands;

import Game.World;
import Game.Inventory;
import Game.Location;
import Game.Key;
import Game.Weapon;
import Game.Armor;
import java.util.Scanner;

public class Search implements Command {
    private final World world;
    private final Inventory inventory;
    private final Scanner scanner;

    public Search(World world, Inventory inventory) {
        this.world = world;
        this.inventory = inventory;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String execute() {
        Location currentLocation = world.getCurrentPosition();
        if (currentLocation == null) {
            return "Nejsi v zadne lokaci.";
        }

        Object found = currentLocation.getItem();
        if (found == null) {
            return "V teto lokaci nebyl nalezen zadny predmet.";
        }

        System.out.println("Nalezl jsi: " + found);
        System.out.println("Chces ho sebrat? (ano/ne)");
        String answer = scanner.nextLine();

        if (answer.equalsIgnoreCase("ano")) {
            boolean success = false;

            if (found.getClass().equals(Key.class)) {
                Key key = (Key) found;
                if (inventory.addKey(key)) {
                    success = true;
                } else {
                    success = false;
                }
            } else if (found.getClass().equals(Weapon.class)) {
                Weapon weapon = (Weapon) found;
                if (inventory.addWeapon(weapon)) {
                    success = true;
                } else {
                    success = false;
                }
            } else if (found.getClass().equals(Armor.class)) {
                Armor armor = (Armor) found;
                String type = armor.getType();
                if (type.equalsIgnoreCase("helma")) {
                    if (inventory.addHelmet(armor)) {
                        success = true;
                    } else {
                        success = false;
                    }
                } else if (type.equalsIgnoreCase("chestplate")) {
                    if (inventory.addChestplate(armor)) {
                        success = true;
                    } else {
                        success = false;
                    }
                } else if (type.equalsIgnoreCase("kalhoty")) {
                    if (inventory.addPants(armor)) {
                        success = true;
                    } else {
                        success = false;
                    }
                } else {
                    return "Tento typ zbroje neni podporovan.";
                }
            } else {
                return "Tento predmet se neda sebrat.";
            }

            if (success) {
                currentLocation.setItem(null);
                return "Predmet byl sebran.";
            } else {
                return "Nemuzes predmet sebrat, protoze mas plne misto.";
            }
        } else {
            return "Nesebral jsi predmet.";
        }
    }

    @Override
    public boolean exit() {
        return false;
    }
}