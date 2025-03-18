package Game;

public class Location {
    private int id;
    private String name;
    private String[] neighbors;
    private Ally ally;
    private Enemy enemy;
    private Object item; //Weapon, Armor, Key (zalezi jakou zbroj potrebuji v dany moment)

    public Location(int id, String name, String[] neighbors) {
        this.id = id;
        this.name = name;
        this.neighbors = neighbors;
        this.ally = null;
        this.enemy = null;
        this.item = null;
    }

    public boolean addAlly(Ally ally) {
        if (this.ally == null && this.enemy == null) {
            this.ally = ally;
            return true;
        } else {
            System.out.println("V teto lokaci uz je NPC.");
            return false;
        }
    }

    public boolean addEnemy(Enemy enemy) {
        if (this.ally == null && this.enemy == null) {
            this.enemy = enemy;
            return true;
        } else {
            System.out.println("V teto lokaci uz je NPC.");
            return false;
        }
    }

    public void removeNPC() {
        this.ally = null;
        this.enemy = null;
    }


    public Ally getAlly() {
        return ally;
    }

    public Enemy getEnemy() {
        return enemy;
    }


    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public String[] getNeighbors() {
        return neighbors;
    }

    @Override
    public String toString() {
        String npcInfo;
        if (ally != null) {
            npcInfo = "Ally: " + ally.getName();
        } else if (enemy != null) {
            npcInfo = "Enemy: " + enemy.getName();
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
}