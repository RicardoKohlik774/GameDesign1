package Commands;

import Game.World;
import Game.Player;
import Game.Enemy;
import Game.Location;

public class Fight implements Command {
    private Player player;
    private World world;

    public Fight(Player player, World world) {
        this.player = this.player;
        this.world = this.world;
    }

    @Override
    public String execute() {
        Location currentLocation = world.getCurrentPosition();

        Enemy enemy = currentLocation.getEnemy();
        if (enemy == null) {
            return "V teto lokaci neni nepritel.";
        }

        System.out.println("Jdes bojovat s " + enemy.getName() + ".");
        player.fight(enemy);

        if (player.getHealth() <= 0) {
            return "Prohral jsi...";
        } else if (enemy.getHealth() <= 0) {
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