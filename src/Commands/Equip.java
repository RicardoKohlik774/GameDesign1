package Commands;

import Game.Inventory;
import Game.Player;
import Game.Weapon;
import Game.Armor;
import java.util.Scanner;

/**
 * The Equip class allows the player to equip a different item from their inventory,
 * such as a weapon or a piece of armor.
 * The player can only equip an item if they have at least one spare item of that type.
 */
public class Equip implements Command {
    private final Inventory inventory;
    private final Player player;
    private final Scanner scanner;

    /**
     * Creates the Equip command with references to the player's inventory and the player object.
     */
    public Equip(Inventory inventory, Player player) {
        this.inventory = inventory;
        this.player = player;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Executes the equip command, allowing the player to choose which type of equipment to change.
     * Allows switching only if a spare item exists in the corresponding inventory slot.
     */
    @Override
    public String execute() {
        System.out.println("Co chces vybavit? (zbran/helma/chestplate/kalhoty)");
        String choice = scanner.nextLine().toLowerCase();

        switch (choice) {
            case "zbran":
                if (inventory.getWeaponsCount() >= 2) {
                    Weapon newWeapon = inventory.getWeapons().get(1);
                    boolean success = inventory.equipWeapon();
                    if (success) {
                        player.setEquippedWeapon(newWeapon);
                        return "Vybavil jsi " + inventory.getEquippedWeapon() + ".";
                    } else {
                        return "Nemuzes vybavit jiny zbran, protoze nemas zadnou v rezerve.";
                    }
                } else {
                    return "Nemuzes vybavit jiny zbran, protoze nemas zadnou v rezerve.";
                }

            case "helma":
                if (inventory.getHelmetsCount() >= 2) {
                    Armor newHelmet = inventory.getHelmets().get(1);
                    boolean success = inventory.equipHelmet();
                    if (success) {
                        player.setEquippedHelmet(newHelmet);
                        return "Vybavil jsi " + inventory.getEquippedHelmet() + ".";
                    } else {
                        return "Nemuzes vybavit jiny helmu, protoze nemas zadnou v rezerve.";
                    }
                } else {
                    return "Nemuzes vybavit jiny helmu, protoze nemas zadnou v rezerve.";
                }

            case "chestplate":
                if (inventory.getChestplatesCount() >= 2) {
                    Armor newChestplate = inventory.getChestplates().get(1);
                    boolean success = inventory.equipChestplate();
                    if (success) {
                        player.setEquippedChestplate(newChestplate);
                        return "Vybavil jsi " + inventory.getEquippedChestplate() + ".";
                    } else {
                        return "Nemuzes vybavit jiny chestplate, protoze nemas zadny v rezerve.";
                    }
                } else {
                    return "Nemuzes vybavit jiny chestplate, protoze nemas zadny v rezerve.";
                }

            case "kalhoty":
                if (inventory.getPantsCount() >= 2) {
                    Armor newPants = inventory.getPants().get(1);
                    boolean success = inventory.equipPants();
                    if (success) {
                        player.setEquippedPants(newPants);
                        return "Vybavil jsi " + inventory.getEquippedPants() + ".";
                    } else {
                        return "Nemuzes vybavit jiny kalhoty, protoze nemas zadne v rezerve.";
                    }
                } else {
                    return "Nemuzes vybavit jiny kalhoty, protoze nemas zadne v rezerve.";
                }

            default:
                return "Neplatna volba. Zkus znovu.";
        }
    }


    @Override
    public boolean exit() {
        return false;
    }
}
