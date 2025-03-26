package Game;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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





    public World() {
    }

    public Location getCurrentPosition() {
        return world.get(currentPosition);
    }

    public boolean moveTo(int newLocation) {
        Location current = world.get(currentPosition);
        for (String neighbor : current.getNeighbors()) {
            try {
                if (Integer.parseInt(neighbor) == newLocation) {
                    currentPosition = newLocation;
                    return true;
                }
            } catch (NumberFormatException e) {
                System.out.println("soused neni cislo");
            }
        }
        return false;
    }


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
        baldric.addItemForSale(new Weapon("zelezny mec", 0,10,10));
        getLocation(2).addAlly(new Ally("Renzo", "Ahoj, mám nějaké zboží, které by se ti mohlo hodit.", true, 50, "Bez do opusteneho domu a pokecej s Baldricem"));
        getLocation(5).addAlly(new Ally("Korin", "Vítej, je hezké vidět dalšího člověka a ne monstrum.", false, 100, "Bez do kostela a tam najdes klic k rozborene vezi"));
        getLocation(6).addAlly(new Ally("Felmir", "Zdař bůh!", true, 150, "Bez do veze zbit oko Chaosu"));
        getLocation(9).addAlly(new Ally("Kaplan", "Zdravím človeče...máš štěstí že jsi jestě na živu.", true, 200, "Kup si ode mne nebo Felmira lepsi zbroj a bez zbit Veynora"));
        getLocation(4).addEnemy(new Enemy("Garrak", "Sem jsi neměl chodit...", "Budeš další!", 10, 1));
        getLocation(9).addEnemy(new Enemy("oko Chaosu", "Vidím tvůj strach.", "Mě nedokážeš porazit!", 10, 1));
        getLocation(7).addEnemy(new Enemy("Veynor", "Myslíš, že máš na to porazit Thorneuse?", "Ukaž, co dovedeš!", 10, 1));
        getLocation(8).addEnemy(new Enemy("Thorneus", "Tak ty chceš zlomit kledbu? HA HA HA! Tak to zkus..", "Selžeš jako ostatní před tebou!", 10, 1));
        getLocation(2).addEnemy(new Enemy("Nyssa", "Nazdárek!, pojď blíž, nekoušu...hehe.", "Ty jsi tak naivní!", 35,8));
    }

    public void assignItems() {
        getLocation(1).setItem(new Weapon("Rezava dyka", 0,5,50));
        getLocation(2).setItem(new Armor("Kozene boty", "kalhoty",0,3));
        getLocation(3).setItem(new Key("Klic od sklepeni"));
        getLocation(6).setItem(new Key("Klic od veze"));
        getLocation(4).setItem(new Item("Dyka smrti", true,true));
        getLocation(9).setItem(new Item("Kniha kouzel", true,true));
        getLocation(7).setItem(new Item("Maska zapomneni", true,true));
        getLocation(5).setItem(new Item("Fontana", false,false));

    }

}
