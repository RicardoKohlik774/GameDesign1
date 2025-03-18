package Commands;

import Game.Inventory;
import java.util.Scanner;

public class Discard implements Command {
    private final Inventory inventory;
    private final Scanner scanner;

    public Discard(Inventory inventory) {
        this.inventory = inventory;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String execute() {
        System.out.println("Co chces zahodit? (zbran/helma/chestplace/kalhoty)");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("zbran")) {
            if (inventory.discardWeapon()) {
                return "Zbran byla zahozena.";
            } else {
                return "Nemuzes zahodit posledni zbran.";
            }
        } else if (choice.equalsIgnoreCase("helma")) {
            if (inventory.discardHelmet()) {
                return "Helma byla zahozena.";
            } else {
                return "Nemuzes zahodit posledni helmu.";
            }
        } else if (choice.equalsIgnoreCase("chestplate")) {
            if (inventory.discardChestplate()) {
                return "Chestplate byl zahozen.";
            } else {
                return "Nemuzes zahodit posledni chestplate.";
            }
        } else if (choice.equalsIgnoreCase("kalhoty")) {
            if (inventory.discardPants()) {
                return "Kalhoty byly zahozeny.";
            } else {
                return "Nemuzes zahodit posledni kalhoty.";
            }
        }  else {
            return "Spatna volba, zkus to znovu. (zbran/helma/chestplace/kalhoty)";
        }
    }

    @Override
    public boolean exit() {
        return false;
    }
}
