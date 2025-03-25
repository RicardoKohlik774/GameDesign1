package Commands;

import Game.*;

import java.util.Scanner;

public class Search implements Command {
    private final World world;
    private final Inventory inventory;
    private final Scanner scanner;
    private final Player player;


    public Search(World world, Inventory inventory, Player player) {
        this.world = world;
        this.inventory = inventory;
        this.player = player;
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

        if (found.getClass().equals(Item.class)) {
            Item immovable = (Item) found;
            if (!immovable.isMovable()) {
                System.out.println("Nalezl jsi: " + immovable.getName() + " (nelze prenest).");
                System.out.print("Chces se napit? (ano/ne) ");
                String drinkAnswer = scanner.nextLine();
                if (drinkAnswer.equalsIgnoreCase("ano")) {
                    player.setAttack(player.getAttack() + 5);
                    player.setHealth(player.getHealth() + 10);
                    currentLocation.setItem(null);
                    world.updateLocks(player);
                    return "Napil jsi se a ziskal jsi + 5 k utoku a +10 k zivotum.";
                } else {
                    return "Nenapil jsi se.";
                }
            }
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
                }
            } else if (found.getClass().equals(Weapon.class)) {
                Weapon weapon = (Weapon) found;
                if (inventory.addWeapon(weapon)) {
                    success = true;
                }
            } else if (found.getClass().equals(Armor.class)) {
                Armor armor = (Armor) found;
                String type = armor.getType();
                if (type.equalsIgnoreCase("helma")) {
                    if (inventory.addHelmet(armor)) {
                        success = true;
                    }
                } else if (type.equalsIgnoreCase("chestplate")) {
                    if (inventory.addChestplate(armor)) {
                        success = true;
                    }
                } else if (type.equalsIgnoreCase("kalhoty")) {
                    if (inventory.addPants(armor)) {
                        success = true;
                    }
                } else {
                    return "Tento typ zbroje neni podporovan.";
                }
            } else if (found.getClass().equals(Item.class)) {
                Item movable = (Item) found;
                if (movable.isMovable()) {
                    if (inventory.addItem(movable)) {
                        success = true;
                    }
                } else {
                    return "Tento predmet se neda prenest.";
                }
            } else {
                return "Tento predmet se neda sebrat.";
            }

            if (success) {
                currentLocation.setItem(null);
                world.updateLocks(player);
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