package Commands;

import Game.Location;
import Game.Player;
import Game.World;
import java.util.Scanner;

/**
 * This command allows the player to move from one location to another.
 * It checks if the location exists, and if it's locked.
 */
public class Move implements Command {
    private final World world;
    private final Player player;
    private final Scanner scanner;

    /**
     * Creates the Move command using the world and the player.
     */
    public Move(World world, Player player) {
        this.world = world;
        this.player = player;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Moves the player to a new location.
     * Location must exist, be a neighbor, and cannot not be locked.
     */
    @Override
    public String execute() {
        System.out.println("Kam chces jit? (cislo lokace)");
        String input = scanner.nextLine();
        int newLocationId;
        try {
            newLocationId = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return "Neplatna volba.";
        }
        Location target = world.getLocation(newLocationId);
        if (target == null) {
            return "Tato lokace neexistuje.";
        }
        if (target.isLocked()) {
            return "Tato lokace je zamcena.";
        }
        boolean success = world.movePlayer(newLocationId);
        if (success) {
            player.setCurrentLocation(target);
            world.updateLocks(player);
            return "Presunul jsi se do lokace: " + newLocationId;
        } else {
            return "Tam se nemuzes presunout.";
        }
    }

    @Override
    public boolean exit() {
        return false;
    }
}