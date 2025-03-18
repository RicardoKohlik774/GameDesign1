package Commands;

import Game.World;
import Game.Player;
import Game.Location;

public class Pray implements Command {
    private final Player player;
    private final World world;

    public Pray(Player player, World world) {
        this.player = player;
        this.world = world;
    }

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