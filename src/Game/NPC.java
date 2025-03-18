package Game;

public abstract class NPC {
    protected String name;
    protected String greetDialog;

    public NPC(String name, String greetDialog) {
        this.name = name;
        this.greetDialog = greetDialog;
    }

    public String getName() {
        return name;
    }

    public String getGreetDialog() {
        return greetDialog;
    }
}
