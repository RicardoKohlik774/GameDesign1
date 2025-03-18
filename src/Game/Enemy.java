package Game;

public class Enemy extends NPC {
    private String fightDialog;
    private int health;
    private int strength;

    public Enemy(String name, String greetDialog, String fightDialog, int health, int strength) {
        super(name, greetDialog);
        this.fightDialog = fightDialog;
        this.health = health;
        this.strength = strength;
    }

    public String getFightDialog() {
        return fightDialog;
    }

    public int getHealth() {
        return health;
    }

    public int getStrength() {
        return strength;
    }

    public String duel() {
        return "";
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
