package Commands;

import Game.World;
import java.util.Scanner;

public class Move implements Command {
    private final World world;
    private final Scanner scanner;

    public Move(World world) {
        this.world = world;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String execute() {
        System.out.print("Kam chces jit? (1-9)");
        String input = scanner.nextLine();
        try {
            int newLocation = Integer.parseInt(input);
            boolean success = world.movePlayer(newLocation);
            if (success) {
                return "Nyni jsi v lokaci: " + newLocation;
            } else {
                return "Nelze se premistit do teto lokace.";
            }
        } catch (NumberFormatException e) {
            return "Spatna volba, Zkus to znovu (1-9, mistnost musi byt sousedni a neuzamcena)";
        }
    }

    @Override
    public boolean exit() {
        return false;
    }
}