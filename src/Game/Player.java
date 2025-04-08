package Game;

import java.util.ArrayList;

/**
 * This class represents the player character.
 * The player has health, base attack, inventory, equipment, and a location.
 * It also handles logic for fighting enemies and checking if the player has all key items.
 */

public class Player {
    private String name;
    private int health;
    private int attack;
    private ArrayList<Item> inventory;
    private Weapon equippedWeapon;
    private Armor equippedHelmet;
    private Armor equippedChestplate;
    private Armor equippedPants;
    private Inventory equipment;
    private Location currentLocation;

    private int money = 200;




    public void setEquippedHelmet(Armor helmet) {
        this.equippedHelmet = helmet;
    }

    public void setEquippedChestplate(Armor chestplate) {
        this.equippedChestplate = chestplate;
    }

    public void setEquippedPants(Armor pants) {
        this.equippedPants = pants;
    }


    public Player(String name, int health, int attack, Inventory equipment, int money) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.inventory = new ArrayList<>();
        this.equipment = equipment;
        this.currentLocation = null;
        this.money = money;
    }

    /**
     * Checks if the player has all three main quest items.
     */
    public boolean hasAllMainItems() {
        boolean hasDagger = false;
        boolean hasBook = false;
        boolean hasMask = false;

        for (Item i : this.equipment.getItems()) {
            String name = i.getName();
            if (name.equalsIgnoreCase("Dyka smrti")) {
                hasDagger = true;
            } else if (name.equalsIgnoreCase("Kniha kouzel")) {
                hasBook = true;
            } else if (name.equalsIgnoreCase("Maska zapomneni")) {
                hasMask = true;
            }
        }

        return hasDagger && hasBook && hasMask;
    }

    /**
     * Calculates total attack based on the base attack,
     * equipped weapon and armor boosts (warrior or mage).
     */
    public int getCurrentAttack() {
        int weaponBonus = 0;
        if (this.equippedWeapon != null) {
            if (this.equippedWeapon.getWarriorBoost() != 0) {
                weaponBonus = this.equippedWeapon.getWarriorBoost();
            } else if (this.equippedWeapon.getMageBoost() != 0) {
                weaponBonus = this.equippedWeapon.getMageBoost();
            }
        }

        int armorBonus = 0;
        if (this.equipment != null) {
            if (this.equipment.getEquippedHelmet() != null) {
                Armor helm = this.equipment.getEquippedHelmet();
                if (this.equippedWeapon != null && this.equippedWeapon.getWarriorBoost() != 0) {
                    armorBonus += helm.getWarriorBoost();
                } else if (this.equippedWeapon != null && this.equippedWeapon.getMageBoost() != 0) {
                    armorBonus += helm.getMageBoost();
                }
            }
            if (this.equipment.getEquippedChestplate() != null) {
                Armor chest = this.equipment.getEquippedChestplate();
                if (this.equippedWeapon != null && this.equippedWeapon.getWarriorBoost() != 0) {
                    armorBonus += chest.getWarriorBoost();
                } else if (this.equippedWeapon != null && this.equippedWeapon.getMageBoost() != 0) {
                    armorBonus += chest.getMageBoost();
                }
            }
            if (this.equipment.getEquippedPants() != null) {
                Armor pants = this.equipment.getEquippedPants();
                if (this.equippedWeapon != null && this.equippedWeapon.getWarriorBoost() != 0) {
                    armorBonus += pants.getWarriorBoost();
                } else if (this.equippedWeapon != null && this.equippedWeapon.getMageBoost() != 0) {
                    armorBonus += pants.getMageBoost();
                }
            }
        }
        return this.attack + weaponBonus + armorBonus;
    }


    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }




    public void setEquippedWeapon(Weapon weapon) {
        this.equippedWeapon = weapon;
    }

    public Inventory getEquipment() {
        return equipment;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location location) {
        this.currentLocation = location;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }



    /**
     * Starts a fight between the player and enemy.
     * If the player wins, their health is increased.
     * If the player loses, their health and the enemy's health are reset to the original values.
     */
    public void fight(Enemy enemy) {
        System.out.println("Souboj zacal.");
        int originalEnemyHP = enemy.getHealth();
        int originalPlayerHP = this.health;

        while (this.health > 0 && enemy.getHealth() > 0) {
            int playerDamage = getCurrentAttack();
            enemy.setHealth(enemy.getHealth() - playerDamage);
            System.out.println("Hrac zautocil a ubral " + playerDamage + " hp neprateli.");

            if (enemy.getHealth() <= 0) {
                System.out.println("Vyhral jsi souboj.");
                setHealth(originalPlayerHP + 10);
                break;
            }

            int enemyDamage = enemy.getStrength();
            this.health = this.health - enemyDamage;
            System.out.println("Nepritel zautocil a ubral " + enemyDamage + " hp hraci.");

            if (this.health <= 0) {
                System.out.println("Prohral jsi.");
                setHealth(originalPlayerHP);
                enemy.setHealth(originalEnemyHP);
                break;
            }
        }
    }




    public void setAttack(int attack) {
        this.attack = attack;
    }
}