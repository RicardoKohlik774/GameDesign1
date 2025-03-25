package Game;

import java.util.ArrayList;

public class Ally {
    private String name;
    private String greetDialog;
    public boolean isDealer;
    private ArrayList<Object> itemsForSale;
    private int infoPrice;
    private String infoDialog;

    public Ally(String name, String greetDialog, boolean isDealer, int infoPrice, String infoDialog) {
        this.name = name;
        this.greetDialog = greetDialog;
        this.isDealer = isDealer;
        this.infoPrice = infoPrice;
        this.infoDialog = infoDialog;
        if (isDealer) {
            itemsForSale = new ArrayList<>();
        }
    }

    public String getName() {
        return name;
    }

    public String getGreetDialog() {
        return greetDialog;
    }

    public ArrayList<Object> getItemsForSale() {
        return itemsForSale;
    }

    public void addItemForSale(Object obj) {
        if (itemsForSale != null) {
            itemsForSale.add(obj);
        }
    }

    public boolean isDealer() {
        return isDealer;
    }

    public int getInfoPrice() {
        return infoPrice;
    }

    public String getInfoDialog() {
        return infoDialog;
    }

}
