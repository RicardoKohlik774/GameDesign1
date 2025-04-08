package Game;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * The World class handles the map, locations, NPCs, items, and player's movement between locations.
 * It also manages location locking and unlocking, and interactions with the map file.
 */
public class World {
    private Map<Integer, Location> world = new HashMap<>();
    private int start = 1;
    private int currentPosition = start;

    /**
     * Loads map data from a text file and creates locations.
     */
    public boolean loadMap() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/Game/Mapa.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] rozdeleniRadku = line.split(";");
                int id = Integer.parseInt(rozdeleniRadku[0]);
                String name = rozdeleniRadku[1];
                String[] neighbors = Arrays.copyOfRange(rozdeleniRadku, 2, rozdeleniRadku.length);
                Location location = new Location(id, name, neighbors);
                world.put(id, location);
            }
            return true;
        } catch (IOException e) {
            System.out.println("Chyba nacteni souboru mapy");
            return false;
        }
    }


    /**
     * Loads items from a text file and places them into locations.
     */
    public boolean loadItems() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/Game/Items.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] rozdeleniRadku = line.split(";");
                int locationId = Integer.parseInt(rozdeleniRadku[0]);
                String type = rozdeleniRadku[1];
                switch (type) {
                    case "Weapon":
                        String weaponName = rozdeleniRadku[2];
                        int mageBoost = Integer.parseInt(rozdeleniRadku[3]);
                        int warriorBoost = Integer.parseInt(rozdeleniRadku[4]);
                        int weaponPrice = Integer.parseInt(rozdeleniRadku[5]);
                        getLocation(locationId).setItem(new Weapon(weaponName, mageBoost, warriorBoost, weaponPrice));
                        break;
                    case "Armor":
                        String armorName = rozdeleniRadku[2];
                        String armorType = rozdeleniRadku[3];
                        int armorMageBoost = Integer.parseInt(rozdeleniRadku[4]);
                        int armorWarriorBoost = Integer.parseInt(rozdeleniRadku[5]);
                        int armorPrice = Integer.parseInt(rozdeleniRadku[6]);
                        getLocation(locationId).setItem(new Armor(armorName, armorType, armorMageBoost, armorWarriorBoost, armorPrice));
                        break;
                    case "Key":
                        String keyName = rozdeleniRadku[2];
                        getLocation(locationId).setItem(new Key(keyName));
                        break;
                    case "Item":
                        String itemName = rozdeleniRadku[2];
                        boolean movable = Boolean.parseBoolean(rozdeleniRadku[3]);
                        boolean isMain = Boolean.parseBoolean(rozdeleniRadku[4]);
                        getLocation(locationId).setItem(new Item(itemName, movable, isMain));
                        break;
                    default:
                        System.out.println("Neznamy typ itemu: " + type);
                }
            }
            return true;
        } catch (IOException e) {
            System.out.println("Chyba nacteni itemu ze souboru.");
            return false;
        }
    }

    /**
     * Loads NPCs (Allies and Enemies) from a text file and adds them to locations.
     * Some allies have items for sale.
     */
    public void loadNPCs() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/Game/NPCs.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                String type = parts[0];
                int locationId = Integer.parseInt(parts[1]);
                Location location = getLocation(locationId);

                if (type.equals("ALLY")) {
                    String name = parts[2];
                    String greet = parts[3];
                    boolean isDealer = Boolean.parseBoolean(parts[4]);
                    int infoPrice = Integer.parseInt(parts[5]);
                    String info = parts[6];
                    Ally ally = new Ally(name, greet, isDealer, infoPrice, info);

                    if (isDealer && parts.length > 7) {
                        String[] items = parts[7].split(",");
                        for (String itemStr : items) {
                            String[] itemParts = itemStr.split("\\|");
                            if (itemParts.length == 5) {    //Armor
                                String itemName = itemParts[0];
                                String typeName = itemParts[1];
                                int mageBoost = Integer.parseInt(itemParts[2]);
                                int warriorBoost = Integer.parseInt(itemParts[3]);
                                int price = Integer.parseInt(itemParts[4]);
                                Armor armor = new Armor(itemName, typeName, mageBoost, warriorBoost, price);
                                ally.addItemForSale(armor);
                            } else if (itemParts.length == 4) {   //Weapon
                                String itemName = itemParts[0];
                                int mageBoost = Integer.parseInt(itemParts[1]);
                                int warriorBoost = Integer.parseInt(itemParts[2]);
                                int price = Integer.parseInt(itemParts[3]);
                                Weapon weapon = new Weapon(itemName, mageBoost, warriorBoost, price);
                                ally.addItemForSale(weapon);
                            }
                        }
                    }

                    location.addAlly(ally);
                } else if (type.equals("ENEMY")) {
                    String name = parts[2];
                    String greet = parts[3];
                    String fight = parts[4];
                    int health = Integer.parseInt(parts[5]);
                    int strength = Integer.parseInt(parts[6]);
                    Enemy enemy = new Enemy(name, greet, fight, health, strength);
                    location.addEnemy(enemy);
                }
            }
        } catch (IOException e) {
            System.out.println("Chyba nacitani NPC ze souboru");
        }
    }



    /**
     * Locks selected locations at the start of the game.
     */
    public void assignLocks() {

        Location sklep = getLocation(4);
        if (sklep != null) {
            sklep.setLocked(true);
        }
        Location vez = getLocation(9);
        if (vez != null) {
            vez.setLocked(true);
        }
            }

    /**
     * Updates locked or unlocked status of specific locations based on game progress.
     * Unlocks "Horni namesti" if location "Sklpeni" has no enemy.
     * Unlocks "Komnata smrti" if player has all three main items.
     */
    public void updateLocks(Player player) {
        Location sklep = getLocation(4);
        Location horniNamesti = getLocation(5);
        Location komnata = getLocation(8);
        Location vez = getLocation(7);
        Location soudniSin = getLocation(9);
        Location dolniNamesti = getLocation(2);

        boolean sklepEnemy = sklep != null && !sklep.getEnemies().isEmpty();
        boolean dolniNamestiEnemy = dolniNamesti != null && !dolniNamesti.getEnemies().isEmpty();
        boolean vezEnemy = vez != null && !vez.getEnemies().isEmpty();
        boolean soudniSinEnemy = soudniSin != null && !soudniSin.getEnemies().isEmpty();
        boolean maMainItemy = player.hasAllMainItems();

        if (sklepEnemy || dolniNamestiEnemy) {
            horniNamesti.setLocked(true);
        } else {
            horniNamesti.setLocked(false);
        }

        if (maMainItemy && !vezEnemy && !soudniSinEnemy) {
            komnata.setLocked(false);
        } else {
            komnata.setLocked(true);
        }
    }

    public void addLocation(int id, Location location) {
        world.put(id, location);
    }

    public void setCurrentPosition(int id) {
        currentPosition = id;
    }



    public World() {
    }

    public Location getCurrentPosition() {
        return world.get(currentPosition);
    }


    /**
     * Checks if the player is able to move to a new location based on the given ID.
     * Only allows movement if the target location is a neighbor and is not locked.
     */
    public boolean moveTo(int newLocation) {
        Location current = world.get(currentPosition);
        if (current == null) {
            return false;
        }
        for (String neighbor : current.getNeighbors()) {
            try {
                int neighborId = Integer.parseInt(neighbor);
                if (neighborId == newLocation) {
                    Location target = world.get(newLocation);
                    if (target == null) {
                        return false;
                    }
                    if (target.isLocked()) {
                        System.out.println("Tato lokace je zamcena.");
                        return false;
                    }

                    currentPosition = newLocation;
                    return true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Soused neni cislo: " + neighbor);
            }
        }
        return false;
    }

    /**
     * Moves the player to a new location using the ID of the location.
     * Confirms movement if moveTo method was successful.
     */
    public boolean movePlayer(int direction) {
        int newLocation = direction;
        return moveTo(newLocation);
    }

    public Location getLocation(int id) {
        return world.get(id);
    }


}
