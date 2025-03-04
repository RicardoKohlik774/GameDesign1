
import java.util.Arrays;
import java.util.List;

public class Location {
    private int id;
    private String name;
    private List<String> neighbors;

    public Location(int id, String name, String[] neighbors) {
        this.id = id;
        this.name = name;
        this.neighbors = Arrays.asList(neighbors);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getNeighbors() {
        return neighbors;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", neighbors=" + neighbors +
                '}';
    }
}
