package Testing;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import Game.Player;
import Game.Weapon;
import Game.Armor;
import Game.Inventory;
import Game.Enemy;
import Game.World;
import Game.Location;
import Game.Key;
import Game.Item;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * This class contains unit tests for gameplay mechanics such as combat, movement,
 * and unlocking locations using keys or witch other conditions.
 */
public class AbsoluteUnit {
    private Player player;
    private Inventory inventory;
    private World world;
    private Weapon mec;
    private Weapon hulka;
    private Armor helma;
    private Armor chestplate;
    private Armor kalhoty;
    private Enemy enemy;
    private Location loc1;
    private Location loc2;
    private Location loc3;
    private Location loc8;
    private Location loc4;

    /**
     * Sets up the test objects before each test.
     * Creates test player, inventory, world, locations, and an enemy.
     */

    @Before
    public void prepUp() {
        inventory = new Inventory();
        mec = new Weapon("Mec", 5, 0, 100);
        hulka = new Weapon("Hulka", 0, 5, 120);
        inventory.addWeapon(mec);
        inventory.addWeapon(hulka);
        helma = new Armor("Zakladni helma", "helma", 2, 1, 50);
        chestplate = new Armor("Zakladni chestplate", "chestplate", 3, 2, 60);
        kalhoty = new Armor("Zakladni kalhoty", "kalhoty", 2, 1, 40);
        inventory.addHelmet(helma);
        inventory.addChestplate(chestplate);
        inventory.addPants(kalhoty);

        player = new Player("Lucian", 100, 10, inventory);
        player.setEquippedWeapon(mec);
        player.setEquippedHelmet(helma);
        player.setEquippedChestplate(chestplate);
        player.setEquippedPants(kalhoty);
        player.setMoney(100);

        world = new World();
        loc1 = new Location(1, "Start", new String[]{"2"});
        loc2 = new Location(2, "Opusteny dum", new String[]{"1","3"});
        loc3 = new Location(3, "Sklepeni", new String[]{"2"});
        loc4 = new Location(4, "Rozborena vez", new String[]{"5"});
        loc8 = new Location(8, "Komnata smrti", new String[]{"2"});

        loc3.setLocked(true);
        loc4.setLocked(true);
        loc8.setLocked(true);

        world.addLocation(1, loc1);
        world.addLocation(2, loc2);
        world.addLocation(3, loc3);
        world.addLocation(4, loc4);
        world.addLocation(8, loc8);
        world.setCurrentPosition(1);
        player.setCurrentLocation(loc1);

        enemy = new Enemy("Thorneus", "Pozdrav", "Utok", 1, 5);
        enemy.setHealth(30);
        loc2.addEnemy(enemy);
    }

    /**
     * Tests the total attack when using a warrior weapon.
     */
    @Test
    public void testAttackWithWarrior() {
        int totalattack = player.getCurrentAttack();
        assertEquals(22, totalattack);
    }

    /**
     * Tests the total attack when using a mage weapon.
     */
    @Test
    public void testAttackWithMage() {
        player.setEquippedWeapon(hulka);
        int totalattack = player.getCurrentAttack();
        assertEquals(19, totalattack);
    }

    /**
     * Tests that the player wins a fight and gains health after the battle.
     */
    @Test
    public void testFightWin() {
        enemy.setHealth(20);
        player.fight(enemy);
        assertEquals(110, player.getHealth());
        assertTrue(enemy.getHealth() <= 0);
    }

    /**
     * Tests that the player loses a fight and their health is reset.
     */
    @Test
    public void testFightLose() {
        enemy.setStrength(200);
        player.fight(enemy);
        assertEquals(100, player.getHealth());
    }

    /**
     * Tests that moving to a locked location is not possible.
     */
    @Test
    public void testMoveToLockedLocation() {
        loc2.setLocked(true);
        boolean moved = world.movePlayer(2);
        assertFalse(moved);
    }

    /**
     * Tests that moving to an unlocked location is possible.
     */
    @Test
    public void testMoveToUnlockedLocation() {
        loc2.setLocked(false);
        boolean moved = world.movePlayer(2);
        assertTrue(moved);
        player.setCurrentLocation(world.getLocation(2));
        assertEquals(loc2, player.getCurrentLocation());
    }

    /**
     * Tests that the correct key can unlock the "Sklepeni" location.
     * Most of this method is AI generated.
     */
    @Test
    public void testKeyUnlocksSklepeni() {
        loc3.setLocked(true);
        world.setCurrentPosition(3);
        Key key = new Key("Klic od sklepeni");
        inventory.addKey(key);
        String Input = "Klic od sklepeni\n";
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(Input.getBytes()));
        Commands.Use useCommand = new Commands.Use(inventory, world);
        String result = useCommand.execute();
        System.setIn(originalIn);
        assertFalse(loc3.isLocked());
        assertTrue(result.contains("Odemkl"));
    }

    /**
     * Tests that "Komnata smrti" becomes unlocked when the player has all required main items.
     */
    @Test
    public void testUnlockKomnataSmrti() {
        Location loc5 = new Location(5, "Horni namesti", new String[]{"8"});
        world.addLocation(5, loc5);
        Item dagger = new Item("Dyka smrti", true, true);
        Item book = new Item("Kniha kouzel", true, true);
        Item mask = new Item("Maska zapomneni", true, true);
        inventory.addItem(dagger);
        inventory.addItem(book);
        inventory.addItem(mask);
        world.updateLocks(player);
        assertFalse(loc8.isLocked());
    }

}