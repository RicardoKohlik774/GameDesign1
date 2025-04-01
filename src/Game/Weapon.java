package Game;

public class Weapon {
 private String name;
 private int mageBoost;
 private int warriorBoost;
 private int price;

 /**
  * This class represents a weapon that the player can equip.
  * Each weapon gives a mage or warrior attack boost and has a price.
  */

 public Weapon(String name, int mageBoost, int warriorBoost, int price) {
  this.name = name;
  this.mageBoost = mageBoost;
  this.warriorBoost = warriorBoost;
  this.price = price;
 }

 public String getName() {
  return name;
 }

 public int getMageBoost() {
  return mageBoost;
 }

 public int getWarriorBoost() {
  return warriorBoost;
 }

 public int getPrice() {
  return price;
 }

 @Override
 public String toString() {
  return "Weapon{" +
          "name='" + name + '\'' +
          ", mageBoost=" + mageBoost +
          ", warriorBoost=" + warriorBoost +
          ", price=" + price +
          '}';
 }
}