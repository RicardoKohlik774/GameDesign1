package Game;

public class Ally extends NPC {
    private boolean isDealer;

    private String infoDialog;

    public Ally(String name, String greetDialog, boolean isDealer, String infoDialog) {
        super(name, greetDialog);
        this.isDealer = isDealer;
        this.infoDialog = infoDialog;
    }

    public boolean isDealer() {
        return isDealer;
    }

    public String getInfoDialog() {
        return infoDialog;
    }

    public String dealing() {
       return "";
    }

    public String info() {
        return infoDialog;
    }
}
