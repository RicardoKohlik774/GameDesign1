package Game;

import Commands.Console;

public class Main {
    public static void main(String[] args) {
        World world = new World();
        Console console = new Console(world);
        world.assignNPCs();
        console.startConsole();

    }
}