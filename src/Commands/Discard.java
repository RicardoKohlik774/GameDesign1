package Commands;

import Game.Inventory;
import java.util.Scanner;

/**
 * The Discard class implements the command that allows the player
 * to discard an item from their inventory, such as a weapon or armor piece.
 * It also ensures that last remaining items cannot be discarded.
 */
public class Discard implements Command {
    private final Inventory inventory;
    private final Scanner scanner;

    /**
     * Creates the Discard command with access to the player's inventory.
     */
    public Discard(Inventory inventory) {
        this.inventory = inventory;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Executes the discard command, prompting the user to choose an item type to discard.
     * Ensures that only unequipped can be removed.
     * Returns a message which displays the result of the discard attempt.
     */
    @Override
    public String execute() {
        System.out.println("Co chces zahodit? (zbran/helma/chestplace/kalhoty)");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("zbran")) {
            if (inventory.discardWeapon()) {
                return "Zahodil jsi " + inventory.getEquippedWeapon() + ".";
            } else {
                return "Nemuzes zahodit posledni zbran.";
            }
        } else if (choice.equalsIgnoreCase("helma")) {
            if (inventory.discardHelmet()) {
                return "Zahodil jsi " + inventory.getEquippedHelmet() + ".";
            } else {
                return "Nemuzes zahodit posledni helmu.";
            }
        } else if (choice.equalsIgnoreCase("chestplate")) {
            if (inventory.discardChestplate()) {
                return "Zahodil jsi " + inventory.getEquippedChestplate() + ".";
            } else {
                return "Nemuzes zahodit posledni chestplate.";
            }
        } else if (choice.equalsIgnoreCase("kalhoty")) {
            if (inventory.discardPants()) {
                return "Zahodil jsi " + inventory.getEquippedPants() + ".";
            } else {
                return "Nemuzes zahodit posledni kalhoty.";
            }
        } else {
            return "Spatna volba, zkus to znovu. (zbran/helma/chestplace/kalhoty)";
        }
    }

    @Override
    public boolean exit() {
        return false;
    }
}