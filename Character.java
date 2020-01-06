import java.util.ArrayList;

/**
 * Write a description of class Character here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

enum Characters {

    CAPTAINAMERICA("Captain America"), PONADIN("Ponadin"), SAILORMOON("Sailor Moon"), THEWITCHER("The Witcher"),
    SUPERMAN("Superman"), HOMERSIMPSON("Homer Simpson"), BATMAN("Batman");
    private String name;
    private Item itemNeeded;


    Characters(String name) {
        this.name = name;
    }

    /**
     * returns the name of the character
     */
    public String getName() {
        return name;
    }

    /**
     * sets the item needed
     */
    public void setItemNeeded(Item item) {
        this.itemNeeded = item;
    }

    /**
     * returns the item needed
     */
    public Item getItemNeeded() {
        return itemNeeded;
    }

    public void printHelp() {
        switch (name) {
            CASE CAPTAINAMERICA :
            System.out.println("Go always to the left. You'll find your way");
            break;
            CASE PONADIN:
            System.out.println("Be strong and take teh magic item");
            break;
            CASE SAILORMOON:
            System.out.println("Never go back!");
            break;
            CASE THEWITCHER:
            System.out.println("It's closer than you think!");
            break;
            CASE SUPERMAN:
            System.out.println("Eat more and take the next item");
            break;
            CASE HOMERSIMPSON:
            System.out.println("Are there no burgers over here? In that case, eat the magic cookie!");
            break;
            CASE BATMAN:
            System.out.println("Playing at night makes you luckier");
        }
    }
}
