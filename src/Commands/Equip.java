package Commands;

import Game.Inventory;
import java.util.Scanner;

public class Equip implements Command {
    private final Inventory inventory;
    private final Scanner scanner;

    public Equip(Inventory inventory) {
        this.inventory = inventory;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String execute() {
        System.out.println("Co chces vybavit? (zbran/helma/chestplate/kalhoty)");
        String choice = scanner.nextLine();

        if (choice.equals("zbran")) {
            if (inventory.equipWeapon()) {
                return "Zbran byla vybavena.";
            } else {
                return "Nemuzes vybavit jinou zbran, protoze nemas zadnou v rezerve.";
            }
        } else if (choice.equals("helma")) {
            if (inventory.equipHelmet()) {
                return "Helma byla vybavena.";
            } else {
                return "Nemuzes vybavit jinou helmu, protoze nemas zadnou v rezerve.";
            }
        } else if (choice.equals("chestplate")) {
            if (inventory.equipChestplate()) {
                return "Chestplate byl vybaven.";
            } else {
                return "Nemuzes vybavit jiny chestplate, protoze nemas zadny v rezerve.";
            }
        } else if (choice.equals("kalhoty")) {
            if (inventory.equipPants()) {
                return "Kalhoty byly vybaveny.";
            } else {
                return "Nemuzes vybavit jine kalhoty, protoze nemas zadne v rezerve.";
            }
        } else {
            return "Neplatna volba. Zkus znovu.";
        }
    }

    @Override
    public boolean exit() {
        return false;
    }
}
