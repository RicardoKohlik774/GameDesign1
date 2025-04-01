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
          if (sklep.getEnemy() != null) {
                horniNamesti.setLocked(false);
            } else {
                horniNamesti.setLocked(true);
           }
            if (player.hasAllMainItems()) {
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


    public void assignNPCs() {
       getLocation(1).addAlly(new Ally("Mira", "O můj bože, ty jsi naživu!", false, 30, "Bez do dolniho namesti fightit Nyssu"));
       Ally baldric = new Ally("Baldric", "Co tu pohledáváš?", true, 50, "Bez do sklepeni zbit Garraka");
        getLocation(3).addAlly(baldric);
        baldric.addItemForSale(new Armor("zelezna helma", "helma",0,3, 50));
        getLocation(2).addAlly(new Ally("Renzo", "Ahoj, mám nějaké zboží, které by se ti mohlo hodit.", true, 50, "Bez do opusteneho domu a pokecej s Baldricem"));
        getLocation(5).addAlly(new Ally("Korin", "Vítej, je hezké vidět dalšího člověka a ne monstrum.", false, 100, "Bez do kostela a tam najdes klic k rozborene vezi"));
        getLocation(6).addAlly(new Ally("Felmir", "Zdař bůh!", true, 150, "Bez do veze zbit oko Chaosu"));
        getLocation(9).addAlly(new Ally("Kaplan", "Zdravím človeče...máš štěstí že jsi jestě na živu.", true, 200, "Kup si ode mne nebo Felmira lepsi zbroj a bez zbit Veynora"));
        getLocation(4).addEnemy(new Enemy("Garrak", "Sem jsi neměl chodit...", "Budeš další!", 50, 9));
        getLocation(9).addEnemy(new Enemy("oko Chaosu", "Vidím tvůj strach.", "Mě nedokážeš porazit!", 10, 1));
        getLocation(7).addEnemy(new Enemy("Veynor", "Myslíš, že máš na to porazit Thorneuse?", "Ukaž, co dovedeš!", 10, 1));
        getLocation(8).addEnemy(new Enemy("Thorneus", "Tak ty chceš zlomit kledbu? HA HA HA! Tak to zkus..", "Selžeš jako ostatní před tebou!", 10, 1));
        getLocation(2).addEnemy(new Enemy("Nyssa", "Nazdárek!, pojď blíž, nekoušu...hehe.", "Ty jsi tak naivní!", 35,8));
    }

    public void assignItems() {
        getLocation(1).setItem(new Weapon("Rezava dyka", 0,5,50));
        getLocation(2).setItem(new Armor("Kozene boty", "kalhoty",0,3, 40));
        getLocation(3).setItem(new Key("Klic od sklepeni"));
        getLocation(6).setItem(new Key("Klic od veze"));
        getLocation(4).setItem(new Item("Dyka smrti", true,true));
        getLocation(9).setItem(new Item("Kniha kouzel", true,true));
        getLocation(7).setItem(new Item("Maska zapomneni", true,true));
        getLocation(5).setItem(new Item("Fontana", false,false));

    }

}
