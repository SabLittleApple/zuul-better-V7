import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;   

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;
    private ArrayList<Item> items;
    private Characters character;
    

    // stores exits of this room.
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     * @param person
     */
    public Room(String description, Item item, Characters person)
    {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>();
        items.add(item);
        this.character = person;
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString() + ".\n" + "Item(s) in the room: " + showItems();

    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }

    /**
     * Modify the project so that a room can hold any number of items. Use a collection to do this. 
     * Make sure the room has an addItem method that places an item into the room. Make sure all items get shown when a player enters a room.
     */
    public void addItem(Item item)
    {
        items.add(item);
    }

    /**

     */
    public String showItems()
    {
        String returnString = "";
        for (Item it : items){            
            returnString += " " + it.getDescription();
        }
        return returnString;
    }

    /**
     * 
     */
    public ArrayList<Item> getItems()
    {
        return items;
    }

    /**
     *
     */
    public Characters getCharacter()

    {
        return character;
    }
}

