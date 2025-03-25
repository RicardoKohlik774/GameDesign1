package Commands;

import Game.*;

import java.util.Scanner;

public class Talk implements Command {
    private final World world;
    private final Scanner scanner;
    private final Player player;

    public Talk(World world, Player player) {
        this.world = world;
        this.player = player;
        this.scanner = new Scanner(System.in);
    }

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
                    System.out.print("Vyber: kupit, prodat, info: ");
                    String tradeOption = scanner.nextLine().toLowerCase();
                    if (tradeOption.equals("kupit")) {
                        return doBuy(ally);
                    } else if (tradeOption.equals("prodat")) {
                        return doSell();
                    } else if (tradeOption.equals("info")) {
                        return doInfo(ally);
                    } else {
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

    private String doBuy(Ally ally) {
        if (ally.getItemsForSale() == null || ally.getItemsForSale().isEmpty()) {
            return "Tento obchodnik nic neprodava.";
        }
        System.out.println("Obchodnik nabizi tyto predmety:");
        for (int i = 0; i < ally.getItemsForSale().size(); i++) {
            Object obj = ally.getItemsForSale().get(i);
            String itemName = "";
            int itemPrice = 0;

            // Rozliseni podle getClass()
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

        // Ziskame vybrany objekt
        Object selectedObj = ally.getItemsForSale().get(choice);
        // Zjistime jmeno a cenu
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

        // Ted pridame do inventory
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
            // Key, cokoliv jineho, nekupujeme
            return "Tento predmet se neda koupit.";
        }
    }



    private String doSell() {
        System.out.print("Co chces prodat? (zbran, helma, chestplate, kalhoty) ");
        String sellOption = scanner.nextLine().toLowerCase();
        if (sellOption.equals("zbran")) {
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
        } else if (sellOption.equals("helma")) {
            if (player.getEquipment().getHelmetsCount() > 1 && player.getEquipment().getEquippedHelmet() != null) {
                Armor helmet = player.getEquipment().getEquippedHelmet();
                int price = helmet.getPrice();
                System.out.print("Chces prodat " + helmet.getName() + " za " + price + " ? (ano/ne) ");
                String sellAnswer = scanner.nextLine().toLowerCase();
                if (sellAnswer.equals("ano")) {
                    boolean sold = player.getEquipment().sellActiveHelmet();
                    if (sold) {
                        player.setMoney(player.getMoney() + price);
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
        } else if (sellOption.equals("chestplate")) {
            if (player.getEquipment().getChestplatesCount() > 1 && player.getEquipment().getEquippedChestplate() != null) {
                Armor chest = player.getEquipment().getEquippedChestplate();
                int price = chest.getPrice();
                System.out.print("Chces prodat " + chest.getName() + " za " + price + " ? (ano/ne) ");
                String sellAnswer = scanner.nextLine().toLowerCase();
                if (sellAnswer.equals("ano")) {
                    boolean sold = player.getEquipment().sellActiveChestplate();
                    if (sold) {
                        player.setMoney(player.getMoney() + price);
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
        } else if (sellOption.equals("kalhoty")) {
            if (player.getEquipment().getPantsCount() > 1 && player.getEquipment().getEquippedPants() != null) {
                Armor pants = player.getEquipment().getEquippedPants();
                int price = pants.getPrice();
                System.out.print("Chces prodat " + pants.getName() + " za " + price + " ? (ano/ne) ");
                String sellAnswer = scanner.nextLine().toLowerCase();
                if (sellAnswer.equals("ano")) {
                    boolean sold = player.getEquipment().sellActivePants();
                    if (sold) {
                        player.setMoney(player.getMoney() + price);
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
        } else {
            return "Neplatna volba. Zkus znovu.";
        }
    }

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
