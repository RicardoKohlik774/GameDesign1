package Game;

import java.util.ArrayList;

public class Player {
    private String name;
    private int health;
    private int attack;
    private ArrayList<Item> inventory;
    private Weapon equippedWeapon;
    private Inventory equipment;
    private Location currentLocation;

    private int money = 200;



    public Player(String name, int health, int attack, Inventory equipment) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.inventory = new ArrayList<>();
        this.equipment = equipment;
        this.currentLocation = null;
    }

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


    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
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

    public void fight(Enemy enemy) {
        while (this.health > 0 && enemy.getHealth() > 0) {
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
                        armorBonus = armorBonus + helm.getWarriorBoost();
                    } else if (this.equippedWeapon != null && this.equippedWeapon.getMageBoost() != 0) {
                        armorBonus = armorBonus + helm.getMageBoost();
                    }
                }
                if (this.equipment.getEquippedChestplate() != null) {
                    Armor chest = this.equipment.getEquippedChestplate();
                    if (this.equippedWeapon != null && this.equippedWeapon.getWarriorBoost() != 0) {
                        armorBonus = armorBonus + chest.getWarriorBoost();
                    } else if (this.equippedWeapon != null && this.equippedWeapon.getMageBoost() != 0) {
                        armorBonus = armorBonus + chest.getMageBoost();
                    }
                }
                if (this.equipment.getEquippedPants() != null) {
                    Armor pants = this.equipment.getEquippedPants();
                    if (this.equippedWeapon != null && this.equippedWeapon.getWarriorBoost() != 0) {
                        armorBonus = armorBonus + pants.getWarriorBoost();
                    } else if (this.equippedWeapon != null && this.equippedWeapon.getMageBoost() != 0) {
                        armorBonus = armorBonus + pants.getMageBoost();
                    }
                }
            }

            int totalBonus = weaponBonus + armorBonus;
            int playerDamage = this.attack + totalBonus;
            enemy.setHealth(enemy.getHealth() - playerDamage);
            System.out.println("Hrac zautocil a ubral " + playerDamage + " hp neprateli.");

            if (enemy.getHealth() <= 0) {
                break;
            }

            int enemyDamage = enemy.getStrength();
            this.health = this.health - enemyDamage;
            System.out.println("Nepritel zautocil a ubral " + enemyDamage + " hp hraci.");

            if (this.health <= 0) {
                break;
            }
        }
    }



    public void setAttack(int attack) {
        this.attack = attack;
    }
}