import java.io.BufferedReader;
import java.io.FileReader;

public class World {





    public Boolean mapLoad() {
        try (BufferedReader br = new BufferedReader(new FileReader("src\\Mapa.txt"))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                   //do
            }
           //output
        } catch (Exception e) {
                  return false;
        }
        return true;
    }

}
