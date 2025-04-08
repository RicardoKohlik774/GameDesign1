package Commands;

import Game.Inventory;
import Game.World;
import Game.Location;
import Game.Key;

import java.util.Scanner;

/**
 * This command allows the player to use a key to unlock specific locations.
 * The player must be in the right place and have the correct key to unlock the target location.
 */
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
        System.out.println("Co chces pouzit?");
        String userInput = scanner.nextLine();

        Key key = inventory.getKey();
        if (key == null) {
            return "Nemas zadny klic.";
        }

        Location currentLoc = world.getCurrentPosition();
        if (currentLoc == null) {
            return "Nejsi v zadne lokaci.";
        }


        if (currentLoc.getId() == 3) {
            Location target = world.getLocation(4);
            if (target == null) {
                return "Lokace Sklepeni neexistuje.";
            }
            if (!target.isLocked()) {
                return "Sklepeni jiz neni zamceno.";
            }
            if (userInput.equalsIgnoreCase("Klic od sklepeni")) {
                target.setLocked(false);
                inventory.removeKey();
                return "Odemkl jsi Sklepeni. Nyni muzes vstoupit do sklepeni.";
            } else {
                return "Tento klic se zde neda pouzit.";
            }
        }

        else if (currentLoc.getName().equalsIgnoreCase("Horni namesti")) {
            Location target = world.getLocation(9);
            if (target == null) {
                return "Lokace Rozborena vez neexistuje.";
            }
            if (!target.isLocked()) {
                return "Rozborena vez jiz neni zamcena.";
            }
            if (userInput.equalsIgnoreCase("Klic od veze")) {
                target.setLocked(false);
                inventory.removeKey();
                return "Odemkl jsi Rozborenou vez. Nyni muzes vstoupit do veze.";
            } else {
                return "Tento klic se zde neda pouzit.";
            }
        } else {
            return "Tady tento klic nemuzes pouzit.";
        }
    }

    @Override
    public boolean exit() {
        return false;
    }
}