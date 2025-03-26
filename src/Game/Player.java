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
            if (equipment.getEquippedWeapon() != null) {
                if (equipment.getEquippedWeapon() .getWarriorBoost() != 0) {
                    weaponBonus = equipment.getEquippedWeapon() .getWarriorBoost();
                } else if (equipment.getEquippedWeapon() .getMageBoost() != 0) {
                    weaponBonus = equipment.getEquippedWeapon() .getMageBoost();
                }
            }

            int armorBonus = 0;
            if (equipment.getEquippedHelmet() != null) {
                if (equipment.getEquippedHelmet() != null) {
                    Armor helm = equipment.getEquippedHelmet();
                    if (equipment.getEquippedHelmet() != null && equipment.getEquippedHelmet().getWarriorBoost() != 0) {
                        armorBonus = armorBonus + helm.getWarriorBoost();
                    } else if (equipment.getEquippedHelmet() != null && equipment.getEquippedHelmet().getMageBoost() != 0) {
                        armorBonus = armorBonus + helm.getMageBoost();
                    }
                }
                if (equipment.getEquippedChestplate() != null) {
                    Armor chest = equipment.getEquippedChestplate();
                    if (equipment.getEquippedChestplate() != null && equipment.getEquippedChestplate().getWarriorBoost() != 0) {
                        armorBonus = armorBonus + chest.getWarriorBoost();
                    } else if (equipment.getEquippedChestplate() != null && equipment.getEquippedChestplate().getMageBoost() != 0) {
                        armorBonus = armorBonus + chest.getMageBoost();
                    }
                }
                if (equipment.getEquippedPants() != null) {
                    Armor pants = equipment.getEquippedPants();
                    if (equipment.getEquippedPants() != null && equipment.getEquippedPants().getWarriorBoost() != 0) {
                        armorBonus = armorBonus + pants.getWarriorBoost();
                    } else if (equipment.getEquippedPants() != null && equipment.getEquippedPants().getMageBoost() != 0) {
                        armorBonus = armorBonus + pants.getMageBoost();
                    }
                }
            }

            int totalBonus = weaponBonus + armorBonus;
            int playerDamage = this.attack + totalBonus;
            enemy.setHealth(enemy.getHealth() - playerDamage);
            System.out.println("Hrac zautocil a ubral " + playerDamage + " hp neprateli.");
            System.out.println("Nepriteli zbyva " + enemy.getHealth() + " hp.");;

            if (enemy.getHealth() <= 0) {
                break;
            }

            int enemyDamage = enemy.getStrength();
            this.health = this.health - enemyDamage;
            System.out.println("Nepritel zautocil a ubral " + enemyDamage + " hp hraci.");
            System.out.println("Hracovi zbyva " + getHealth() + " hp.");

            if (this.health <= 0) {
                break;
            }
        }
    }



    public void setAttack(int attack) {
        this.attack = attack;
    }
}