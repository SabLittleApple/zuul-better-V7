import java.util.ArrayList;
import java.util.Iterator;

/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player {
    private String name;
    private int maxWeight;
    private Room currentRoom;
    //private Item itemTaken;  //to be uncommented
    private ArrayList<Item> itemsTaken = new ArrayList<>();
    private Room beamerRoom;

    /**
     * Constructor for objects of class Player
     */
    public Player(String name, int maxWeight) {
        this.name = name;
        this.maxWeight = maxWeight;
    }

    /**
     *
     */
    public void setMaxWeight(int weight) {
        maxWeight = weight;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param y a sample parameter for a method
     * @return the sum of x and y
     */
    public int getMaxWeight() {
        return maxWeight;
    }

    /**
     *
     */
    //public void takeOneItem(String name)
    //{
    //  Iterator <Item> it = currentRoom.getItems().iterator();
    //  while(it.hasNext()){
    //      Item item = it.next();
    //      if(item.getName().equals(name)){
    //          itemTaken = item;
    //          it.remove();
    //          System.out.println("The item " + name + " has been taken");
    //          return;
    //      }
    //  }
    //  System.out.println("You can't take this item. The item " + name + " is not present in the room.");
    //}

    /**
     * Extend your implementation to allow the player to carry any number of items.
     */
    public void takeMultipleItems(String name) {
        Iterator<Item> it = currentRoom.getItems().iterator();
        while (it.hasNext()) {
            Item item = it.next();
            if (item.getName().equals(name)) {
                if (item.getWeight() <= maxWeight) {
                    it.remove();
                    itemsTaken.add(item);
                    System.out.println("item " + item.getName() + " taken");
                    return;
                } else {
                    System.out.println("This item is too heavy for you. You can't carry it.");
                    return;
                }
            }
        }
        System.out.println("There is no item " + name + " in the room.");
    }

    /**
     *
     */
    public void setRoom(Room room) {
        currentRoom = room;
    }

    /**
     *
     */
    //  public void dropOneItem(String name)
    //{  
    //    if(itemTaken.getName().equals(name)) {
    //        currentRoom.getItems().add(itemTaken);
    //        System.out.println("item " + itemTaken.getName() + " dropped");
    //        itemTaken = null;
    //    }  
    //    else {
    //        System.out.println("You need to take the item before dropping it down");
    //    }
    //}

    /**
     *
     */
    public void dropItem(String name) {
        Iterator<Item> it = itemsTaken.iterator();
        while (it.hasNext()) {
            Item item = it.next();
            if (item.getName().equals(name)) {
                currentRoom.addItem(item);
                it.remove();
                System.out.println("Item " + item.getName() + " dropped");
                return;
            }
        }
        System.out.println("You need to take the item before dropping it down");
    }

    /**
     *
     */
    public void showItemsTaken() {
        System.out.println("Items you are currently carrying: ");
        for (Item items : itemsTaken) {
            System.out.println(items.getName() + " ");
        }
    }

    /**
     * Returns the total weight of the items carried
     */
    public int itemsCarriedWeight() {
        int totWeight = 0;
        for (Item items : itemsTaken) {
            totWeight += items.getWeight();
        }
        return totWeight;
    }

    /**
     * Add a magic cookie item to a room. Add an eat cookie command. If a player finds and eats the magic cookie, 
     * it increases the weight that the player can carry. Note you need to take the cookie before eating it.
     */
    public void eatCookie(String name)
    {
        Iterator<Item> it = itemsTaken.iterator();
        while (it.hasNext()) {
            Item item = it.next();
            if (item.getName().equals("cookie")) {
                it.remove();
            }
        }
        maxWeight += 10;
        System.out.println("Magic cookie eaten! Congratulations, you're stronger now!");
        System.out.println("The maximum weight you can now carry is " + maxWeight);
    }

    /**
     * When you charge the beamer, it memorizes the current room. 
     */
    public void chargeBeamer(String name)
    {
        Iterator<Item> it = itemsTaken.iterator();
        while(it.hasNext()){
            Item item = it.next();
            if(item.getName().equals("beamer")){
                beamerRoom = currentRoom;
                System.out.println("Beamer has been successfully charged!");
                return;
            }
        }
        System.out.println("You have to take the beamer before charging it");
    }

    /**
     * When you charge the beamer, it memorizes the current room. When you fire the beamer, it transports you immediately back to the 
     * room it was charged in. 
     */
    public void fire(String name)
    {
        if(beamerRoom == null){
            System.out.println("Make sure you have taken the beamer and charged it before firing it");
        }
        else{
            currentRoom = beamerRoom;
            System.out.println("Beamer has been successfully fired!");
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /**
     * returns if an item has been taken by the player or not 
     */
    public boolean itemTaken(String name)
    {
        boolean taken = false;
        Iterator<Item> it = itemsTaken.iterator();
        while(it.hasNext()){
            Item item = it.next();
            if(item.getName().equals(name)){
                return !taken;
            }
        }
        return taken;
    }
}

