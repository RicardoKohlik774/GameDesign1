package Commands;

import Game.Inventory;
import Game.World;
import java.util.Scanner;

public class Use implements Command {
    private final Inventory inventory;
    private final World world;
    private final Scanner scanner;

    public Use(Inventory inventory, World world) {
        this.inventory = inventory;
        this.world = world;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String execute() {
        System.out.println("Co chces pouzit? (klic)");
        String choice = scanner.nextLine();

        if (choice.equals("klic")) {
            if (inventory.getKey() != null) {
                System.out.println("Pouzil jsi klic a odemkl jsi mistnost.");
                inventory.removeKey();
                return "Klic byl pouzit.";
            } else {
                return "Nemuzes pouzit klic, protoze zadny nemas.";
            }
        } else {
            return "Tento predmet nelze zde pouzit.";
        }
    }

    @Override
    public boolean exit() {
        return false;
    }
}
