package Commands;

import Game.World;
import Game.Player;
import Game.Location;

/**
 * This command lets the player pray at location "Stary kostel".
 * If the player is at that location, their stats are increased.
 */
public class Pray implements Command {
    private final Player player;
    private final World world;

    /**
     * Creates the Pray command with access to the player and the world.
     */
    public Pray(Player player, World world) {
        this.player = player;
        this.world = world;
    }

    /**
     * If the player is in "Stary kostel", it increases health and attack.
     * If not, praying is not possible.
     */
    @Override
    public String execute() {
        Location currentLocation = world.getCurrentPosition();
        if (currentLocation.getName().equalsIgnoreCase("Stary kostel")) {
            int oldHealth = player.getHealth();
            int oldAttack = player.getAttack();

            player.setHealth(oldHealth + 10);
            player.setAttack(oldAttack + 3);

            return "Pomodlil jsi se ke svemu bohu. Tvoje HP na dalsi boj se zvysilo o 10 a utok o 3.";
        } else {
            return "Tady se nemuzes modlit.";
        }
    }

    @Override
    public boolean exit() {
        return false;
    }
}