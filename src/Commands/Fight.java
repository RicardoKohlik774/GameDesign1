package Commands;

import Game.World;
import Game.Player;
import Game.Enemy;
import Game.Location;

/**
 * This class allows the player to fight an enemy in the current location.
 * If the player or the enemy loses all health, the fight ends.
 */
public class Fight implements Command {
    private Player player;
    private World world;

    /**
     * Creates the Fight command using the player and world objects.
     */
    public Fight(Player player, World world) {
        this.player = player;
        this.world = world;
    }

    /**
     * Starts a fight with an enemy in the current location.
     * If the enemy is defeated, it is removed from the location.
     */
    @Override
    public String execute() {
        Location currentLocation = world.getCurrentPosition();
        if (currentLocation == null) {
            return "Nejsi v zadne lokaci.";
        }
        Enemy enemy = currentLocation.getEnemy();
        if (enemy == null) {
            return "V teto lokaci neni nepritel.";
        }
        System.out.println("Jdes bojovat s " + enemy.getName() + ".");
        player.fight(enemy);

        if (player.getHealth() <= 0) {
            return "Prohral jsi...";
        } else if (enemy.getHealth() <= 0) {
            if (enemy.getName().equalsIgnoreCase("Thorneus")) {
                System.out.println("Porazil jsi Thorneuse a tim jsi zlomil kletbu uvalenou na mesto! GG!");
                System.exit(0);
            }
            currentLocation.removeNPC();
            return "Vyhral jsi!";
        } else {
            return "Souboj je u konce.";
        }
    }

    @Override
    public boolean exit() {
        return false;
    }
}