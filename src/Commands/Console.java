package Commands;

import Game.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The Console class handles all user interaction with the command-line interface.
 * It manages player creation, command registration, and the main input loop where the player types commands.
 */
public class Console {
    private Map<String, Command> commands = new HashMap<>();
    private World world;
    private Inventory inventory;
    private Player player;

    /**
     * Constructor for the console that sets up the world, locks, player, and commands.
     */
    public Console(World world) {
        this.world = world;
        world.loadMap();
        world.assignLocks();
        playerCreate();
        registerCommands();
    }

    /**
     * Creates the  player with basic stats and equipment.
     */
    public boolean playerCreate(){
        this.inventory = new Inventory();
        Weapon mec = new Weapon("Mec", 0, 1, 100);
        Weapon hulka = new Weapon("Hulka", 4, 0, 120);
        this.inventory.addWeapon(hulka);
        this.inventory.addWeapon(mec);
        Armor helma = new Armor("Zakladni helma", "helma", 0, 1,30);
        Armor chestplate = new Armor("Zakladni chestplate", "chestplate", 0, 2,25);
        Armor kalhoty = new Armor("Zakladni kalhoty", "kalhoty", 0, 1,20);
        this.inventory.addHelmet(helma);
        this.inventory.addChestplate(chestplate);
        this.inventory.addPants(kalhoty);
        this.player = new Player("Lucian", 25, 3, this.inventory);
        this.player.setEquippedWeapon(hulka);
        this.player.setCurrentLocation(world.getCurrentPosition());
        return true;
    }

    /**
     * Registers all game commands and sets them to their each keyword.
     */
    private void registerCommands() {
        commands.put("jdi", new Move(world, player));
        commands.put("mluv", new Talk(world, player));
        commands.put("zahod", new Discard(inventory));
        commands.put("vybav", new Equip(inventory, player));
        commands.put("pouzij", new Use(inventory, world));
        commands.put("prohledej", new Search(world, inventory, player));
        commands.put("boj", new Fight(player, world));
        commands.put("pomodlit", new Pray(player, world));
    }

    /**
     * Executes the command entered by the player if it exists.
     */
    public void executeCommand(String name) {
        Command command = commands.get(name);
        if (command != null) {
            System.out.println(command.execute());
        } else {
            System.out.println("Neznamy prikaz: " + name);
        }
    }

    /**
     * Starts the game console loop, displaying the current location and player stats,
     * and waits for player input to execute commands.
     */
    public void startConsole() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("------------------------------------------------------------------------------------------------------------------");
            System.out.println("Nachazis se v " + player.getCurrentLocation());
            System.out.println("Staty: " + player.getHealth() + "hp," + player.getCurrentAttack() + "atk.");
            System.out.println("Co chces delat? (jdi, mluv, zahod, vybav, pouzij, prohledej, boj, pomodlit)");
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