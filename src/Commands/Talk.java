package Commands;

import Game.*;

import java.util.Scanner;


/**
 * The Talk command lets the player talk to NPCs in the current location.
 * If the character is a merchant, the player can trade with them.
 * This includes buying items, selling equipped items, or buying helpful info.
 * If it's an enemy, the enemy responds with a dialogue.
 */

public class Talk implements Command {
    private final World world;
    private final Scanner scanner;
    private final Player player;


    public Talk(World world, Player player) {
        this.world = world;
        this.player = player;
        this.scanner = new Scanner(System.in);
    }

    /**
     * This command allows the player to talk to a character in the current location.
     * If the player talks to an ally, they can trade, get info, or just talk.
     * If the player talks to an enemy, it shows the enemy’s dialogue.
     * The player needs to type the name of the character they want to talk to.
     */
    @Override
    public String execute() {
        Location currentLocation = world.getCurrentPosition();
        if (currentLocation == null) {
            return "Nejsi v zadne lokaci.";
        }

        System.out.print("S kym chces mluvit? ");
        String npcName = scanner.nextLine();

        Ally ally = currentLocation.getAlly();
        if (ally != null && ally.getName().equalsIgnoreCase(npcName)) {
            String greeting = ally.getGreetDialog();

            if (ally.isDealer()) {
                System.out.print("Chces obchodovat? (ano/ne) ");
                String tradeAnswer = scanner.nextLine().toLowerCase();

                if (tradeAnswer.equals("ano")) {
                    System.out.print("Vyber: koupit, prodat, info: ");
                    String tradeOption = scanner.nextLine().toLowerCase();

                    switch (tradeOption) {
                        case "koupit":
                            return doBuy(ally);
                        case "prodat":
                            return doSell(ally);
                        case "info":
                            return doInfo(ally);
                        default:
                            return "Neplatna volba obchodovani.";
                    }
                } else {
                    return greeting;
                }
            } else {
                return greeting;
            }
        }

        Enemy enemy = currentLocation.getEnemy();
        if (enemy != null && enemy.getName().equalsIgnoreCase(npcName)) {
            return enemy.getGreetDialog();
        }

        return "Tata postava tu neni.";
    }



    /**
     * Allows the player to buy an item from the ally.
     * It shows a list of items for sale, asks the player to choose one,
     * checks if the player has enough money and free inventory slot,
     * and then makes the purchase.
     */
    private String doBuy(Ally ally) {
        if (ally.getItemsForSale() == null || ally.getItemsForSale().isEmpty()) {
            return "Tento obchodnik nic neprodava.";
        }

        System.out.println("Obchodnik nabizi tyto predmety:");
        for (int i = 0; i < ally.getItemsForSale().size(); i++) {
            Object obj = ally.getItemsForSale().get(i);
            String itemName = "";
            int itemPrice = 0;

            if (obj.getClass().equals(Weapon.class)) {
                Weapon w = (Weapon) obj;
                itemName = w.getName();
                itemPrice = w.getPrice();
            } else if (obj.getClass().equals(Armor.class)) {
                Armor a = (Armor) obj;
                itemName = a.getName();
                itemPrice = a.getPrice();
            }

            System.out.println((i + 1) + ") " + itemName + " (cena: " + itemPrice + ")");
        }

        System.out.print("Vyber cislo predmetu, ktery chces koupit: ");
        String input = scanner.nextLine();
        int choice;
        try {
            choice = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            return "Neplatna volba.";
        }
        if (choice < 0 || choice >= ally.getItemsForSale().size()) {
            return "Neplatna volba.";
        }

        Object selectedObj = ally.getItemsForSale().get(choice);

        String selectedName = "";
        int selectedPrice = 0;

        if (selectedObj.getClass().equals(Weapon.class)) {
            Weapon w = (Weapon) selectedObj;
            selectedName = w.getName();
            selectedPrice = w.getPrice();
        } else if (selectedObj.getClass().equals(Armor.class)) {
            Armor a = (Armor) selectedObj;
            selectedName = a.getName();
            selectedPrice = a.getPrice();
        } else {
            return "Tento predmet se neda koupit.";
        }

        if (player.getMoney() < selectedPrice) {
            return "Nemas dost penez.";
        }

        if (selectedObj.getClass().equals(Weapon.class)) {
            Weapon w = (Weapon) selectedObj;
            boolean added = player.getEquipment().addWeapon(w);
            if (added) {
                player.setMoney(player.getMoney() - selectedPrice);
                ally.getItemsForSale().remove(choice);
                return "Koupil jsi " + w.getName() + ". Mas " + player.getMoney() + " penez.";
            } else {
                return "Nemas volny slot na zbrane.";
            }
        } else if (selectedObj.getClass().equals(Armor.class)) {
            Armor a = (Armor) selectedObj;
            String type = a.getType();
            boolean added = false;
            if (type.equalsIgnoreCase("helma")) {
                added = player.getEquipment().addHelmet(a);
            } else if (type.equalsIgnoreCase("chestplate")) {
                added = player.getEquipment().addChestplate(a);
            } else if (type.equalsIgnoreCase("kalhoty")) {
                added = player.getEquipment().addPants(a);
            } else {
                return "Tento typ brneni neni podporovan.";
            }
            if (added) {
                player.setMoney(player.getMoney() - selectedPrice);
                ally.getItemsForSale().remove(choice);
                return "Koupil jsi " + a.getName() + ". Mas " + player.getMoney() + " penez.";
            } else {
                return "Nemas volny slot na " + type + ".";
            }
        } else {
            return "Tento predmet se neda koupit.";
        }
    }


    /**
     * Allows the player to sell currently equipped items.
     * The player can sell a weapon or armor part if they have a replacement.
     * If the sale is successful, the item is removed and the player receives money.
     */
    private String doSell(Ally ally) {
        System.out.print("Co chces prodat? (zbran, helma, chestplate, kalhoty) ");
        String sellOption = scanner.nextLine().toLowerCase();

        switch (sellOption) {
            case "zbran":
                if (player.getEquipment().getWeaponsCount() > 1 && player.getEquipment().getEquippedWeapon() != null) {
                    Weapon weapon = player.getEquipment().getEquippedWeapon();
                    int price = weapon.getPrice();
                    System.out.print("Chces prodat " + weapon.getName() + " za " + price + " ? (ano/ne) ");
                    String sellAnswer = scanner.nextLine().toLowerCase();
                    if (sellAnswer.equals("ano")) {
                        boolean sold = player.getEquipment().sellActiveWeapon();
                        if (sold) {
                            player.setMoney(player.getMoney() + price);
                            player.setEquippedWeapon(null);
                          ally.addItemForSale(weapon);
                            return "Zbran byla prodana. Ted mas " + player.getMoney() + " penez.";
                        } else {
                            return "Nepodarilo se prodat aktivni zbran.";
                        }
                    } else {
                        return "Prodej zbrane zrusen.";
                    }
                } else {
                    return "Nemuzes prodat zbran, protoze nemas zadnou nahradni zbran v inventari.";
                }

            case "helma":
                if (player.getEquipment().getHelmetsCount() > 1 && player.getEquipment().getEquippedHelmet() != null) {
                    Armor helmet = player.getEquipment().getEquippedHelmet();
                    int price = helmet.getPrice();
                    System.out.print("Chces prodat " + helmet.getName() + " za " + price + " ? (ano/ne) ");
                    String sellAnswer = scanner.nextLine().toLowerCase();
                    if (sellAnswer.equals("ano")) {
                        boolean sold = player.getEquipment().sellActiveHelmet();
                        if (sold) {
                            player.setMoney(player.getMoney() + price);
                            ally.addItemForSale(helmet);
                            return "Helma byla prodana. Ted mas " + player.getMoney() + " penez.";
                        } else {
                            return "Nepodarilo se prodat aktivni helmu.";
                        }
                    } else {
                        return "Prodej helmy zrusen.";
                    }
                } else {
                    return "Nemuzes prodat helmu, protoze nemas zadnou nahradni helmu v inventari.";
                }

            case "chestplate":
                if (player.getEquipment().getChestplatesCount() > 1 && player.getEquipment().getEquippedChestplate() != null) {
                    Armor chestplate = player.getEquipment().getEquippedChestplate();
                    int price = chestplate.getPrice();
                    System.out.print("Chces prodat " + chestplate.getName() + " za " + price + " ? (ano/ne) ");
                    String sellAnswer = scanner.nextLine().toLowerCase();
                    if (sellAnswer.equals("ano")) {
                        boolean sold = player.getEquipment().sellActiveChestplate();
                        if (sold) {
                            player.setMoney(player.getMoney() + price);
                            ally.addItemForSale(chestplate);
                            return "Chestplate byl prodan. Ted mas " + player.getMoney() + " penez.";
                        } else {
                            return "Nepodarilo se prodat aktivni chestplate.";
                        }
                    } else {
                        return "Prodej chestplate zrusen.";
                    }
                } else {
                    return "Nemuzes prodat chestplate, protoze nemas zadny nahradni chestplate v inventari.";
                }

            case "kalhoty":
                if (player.getEquipment().getPantsCount() > 1 && player.getEquipment().getEquippedPants() != null) {
                    Armor pants = player.getEquipment().getEquippedPants();
                    int price = pants.getPrice();
                    System.out.print("Chces prodat " + pants.getName() + " za " + price + " ? (ano/ne) ");
                    String sellAnswer = scanner.nextLine().toLowerCase();
                    if (sellAnswer.equals("ano")) {
                        boolean sold = player.getEquipment().sellActivePants();
                        if (sold) {
                            player.setMoney(player.getMoney() + price);
                            ally.addItemForSale(pants);
                            return "Kalhoty byly prodany. Ted mas " + player.getMoney() + " penez.";
                        } else {
                            return "Nepodarilo se prodat aktivni kalhoty.";
                        }
                    } else {
                        return "Prodej kalhot zrusen.";
                    }
                } else {
                    return "Nemuzes prodat kalhoty, protoze nemas zadne nahradni kalhoty v inventari.";
                }

            default:
                return "Neplatna volba. Zkus znovu.";
        }
    }


    /**
     * Lets the player buy a hint from an ally.
     * If the player agrees, they pay money and receive the info dialog of the Ally.
     */
    private String doInfo(Ally ally) {
        System.out.print("Cena napovedy je 20. Chces koupit info? (ano/ne) ");
        String infoAnswer = scanner.nextLine().toLowerCase();
        if (infoAnswer.equals("ano")) {
            return "Info: " + ally.getInfoDialog();
        } else {
            return "Info nekoupeno.";
        }
    }

    @Override
    public boolean exit() {
        return false;
    }
}
