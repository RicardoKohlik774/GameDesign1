package Commands;

import Game.World;
import Game.Location;
import Game.Ally;
import Game.Enemy;
import java.util.Scanner;

public class Talk implements Command {
    private final World world;
    private final Scanner scanner;

    public Talk(World world) {
        this.world = world;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String execute() {
        Location currentLocation = world.getCurrentPosition();

        System.out.print("S kym chces mluvit?");
        String npcName = scanner.nextLine();

        Ally ally = currentLocation.getAlly();
        if (ally != null && ally.getName().equalsIgnoreCase(npcName)) {
            return ally.getGreetDialog();
        }

        Enemy enemy = currentLocation.getEnemy();
        if (enemy != null && enemy.getName().equalsIgnoreCase(npcName)) {
            return enemy.getGreetDialog();
        }

        return "Tato postava tu neni.";
    }

    @Override
    public boolean exit() {
        return false;
    }
}
