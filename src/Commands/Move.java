package Commands;

import Game.Location;
import Game.Player;
import Game.World;
import java.util.Scanner;

public class Move implements Command {
    private final World world;
    private final Player player;
    private final Scanner scanner;

    public Move(World world, Player player) {
        this.world = world;
        this.player = player;
        this.scanner = new Scanner(System.in);
    }

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
