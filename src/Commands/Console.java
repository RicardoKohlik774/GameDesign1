package Commands;

import Game.Player;
import Game.World;
import Game.Inventory;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Console {
    private Map<String, Command> commands = new HashMap<>();
    private World world;
    private Inventory inventory;
    private Player player;

    public Console(World world) {
        this.world = world;
        this.inventory = inventory;
        this.player = player;
        world.loadMap();
        registerCommands();
    }

    private void registerCommands() {
        commands.put("jdi", new Move(world));
        commands.put("mluv", new Talk(world));
        commands.put("discard", new Discard(inventory));
        commands.put("vybav", new Equip(inventory));
        commands.put("pouzij", new Use(inventory, world));
        commands.put("prohledej", new Search(world, inventory));
        commands.put("boj", new Fight(player, world));
        commands.put("pomodlit", new Pray(player, world));
    }

    public void executeCommand(String name) {
        Command command = commands.get(name);
        if (command != null) {
            System.out.println(command.execute());
        } else {
            System.out.println("Neznamy prikaz: " + name);
        }
    }

    public void startConsole() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if ("exit".equalsIgnoreCase(input)) {
                break;
            }
            executeCommand(input);
        }
        scanner.close();
    }
}