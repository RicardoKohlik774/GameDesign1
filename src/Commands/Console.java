package Commands;

import Game.*;
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
        world.loadMap();
        world.assignLocks();
        playerCreate();
        registerCommands();
    }


    public boolean playerCreate(){
        this.inventory = new Inventory();
        Weapon mec = new Weapon("Mec", 0, 1, 100);
        Weapon hulka = new Weapon("Hulka", 4, 0, 120);
        this.inventory.addWeapon(mec);
        this.inventory.addWeapon(hulka);
        Armor helma = new Armor("Zakladni helma", "helma", 0, 1);
        Armor chestplate = new Armor("Zakladni chestplate", "chestplate", 0, 2);
        Armor kalhoty = new Armor("Zakladni kalhoty", "kalhoty", 0, 1);
        this.inventory.addHelmet(helma);
        this.inventory.addChestplate(chestplate);
        this.inventory.addPants(kalhoty);
        this.player = new Player("Lucian", 25, 2, this.inventory);
        this.player.setEquippedWeapon(mec);
        this.player.setCurrentLocation(world.getCurrentPosition());
        return true;
    }

    private void registerCommands() {
        commands.put("jdi", new Move(world, player));
        commands.put("mluv", new Talk(world, player));
        commands.put("zahod", new Discard(inventory));
        commands.put("vybav", new Equip(inventory));
        commands.put("pouzij", new Use(inventory, world));
        commands.put("prohledej", new Search(world, inventory, player));
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
            System.out.println("------------------------------------------------------------------------------------------------------------------");
            System.out.println("Nachazis se v " + player.getCurrentLocation());
            System.out.println("Staty: " + player.getHealth() + "hp," + player.getAttack() + "atk.");
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
