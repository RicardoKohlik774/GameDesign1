package Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a location in the game.
 * A location can have allies, enemies, items, and be locked or unlocked.
 */

public class Location {
    private int id;
    private String name;
    private String[] neighbors;
    private List<Ally> allies;
    private List<Enemy> enemies;
    private Object item;
    private boolean locked;


    public Location(int id, String name, String[] neighbors) {
        this.id = id;
        this.name = name;
        this.neighbors = neighbors;
        this.allies = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.item = null;

        if (name.equalsIgnoreCase("Sklepeni")) {
            this.locked = true;
        } else {
            this.locked = false;
        }
    }

    public void addAlly(Ally ally) {
        allies.add(ally);
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void removeNPC() {
        allies.clear();
        enemies.clear();
    }

    public List<Ally> getAllies() {
        return allies;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String[] getNeighbors() {
        return neighbors;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public String toString() {
        String npcInfo;
        if (allies.size() > 0) {
            npcInfo = "Allies: ";
            for (int i = 0; i < allies.size(); i++) {
                npcInfo = npcInfo + allies.get(i).getName();
                if (i < allies.size() - 1) {
                    npcInfo = npcInfo + ", ";
                }
            }
        } else if (enemies.size() > 0) {
            npcInfo = "Enemies: ";
            for (int i = 0; i < enemies.size(); i++) {
                npcInfo = npcInfo + enemies.get(i).getName();
                if (i < enemies.size() - 1) {
                    npcInfo = npcInfo + ", ";
                }
            }
        } else {
            npcInfo = "None";
        }

        String itemInfo;
        if (item != null) {
            itemInfo = item.toString();
        } else {
            itemInfo = "Zadny predmet";
        }

        return "Location{id=" + id
                + ", name='" + name + "', neighbors=" + String.join(", ", neighbors)
                + ", npc=" + npcInfo
                + ", item=" + itemInfo + "}";
    }


    public Ally getAlly() {
        if (allies.isEmpty()) {
            return null;
        } else {
            return allies.get(0);
        }
    }

    public Enemy getEnemy() {
        if (enemies.isEmpty()) {
            return null;
        } else {
            return enemies.get(0);
        }
    }

}