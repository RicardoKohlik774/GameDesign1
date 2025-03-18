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
       getLocation(1).addAlly(new Ally("Mira", "Wassup", true, ""));
    }


}
