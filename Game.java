import java.util.Stack;
import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class is the main class of the "World of Zuul" application.go
 * "World of Zuul" is a very simple, text based adventure game.  Users
 * can walk around some scenery. That's all. It should really be extended
 * to make it more interesting!
 * <p>
 * To play this game, create an instance of this class and call the "play"
 * method.
 * <p>
 * This main class creates and initialises all the others: it creates all
 * rooms, creates the parser and starts the game.  It also evaluates and
 * executes the commands that the parser returns.
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game {
    private Parser parser;
    //private Room currentRoom;
    private Stack<Room> roomsAccessed;
    private Player player;
    private int commandsCounter;
    private ArrayList<Room> roomsList;
    //private ArrayList<Characters> characters;
    private ArrayList<Item> itemsAll;
    private Random random = new Random();


    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        roomsAccessed = new Stack<>();
        createPlayer();
        createRooms();
        parser = new Parser();

    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        roomsList = new ArrayList<>();
        //characters = new ArrayList<Characters>();
        itemsAll = new ArrayList<>();

        Room outside, theater, pub, lab, office, trapRoom, transporterRoom;
        // create the rooms
        Characters hero = Characters.CAPTAINAMERICA;
        outside = new Room("outside the main entrance of the university", new Item("lamp", "an ancient lamp", 10), hero);
        Item roomItem = new Item("cookie", " a magic cookie", 1);
        roomsList.add(outside);
        //characters.add(hero);
        itemsAll.add(roomItem);
        outside.addItem(roomItem);


        hero = Characters.PONADIN;
        roomItem = new Item("key", "a magic key to unlock the door", 1);
        Item roomItem2 = new Item("chair", " a very comfortable chair made of leather", 15);
        pub = new Room("in the campus pub", roomItem, hero);
        pub.addItem(roomItem);
        roomsList.add(pub);
       // characters.add(hero);
        itemsAll.add(roomItem);
        itemsAll.add(roomItem2);

        hero = Characters.SAILORMOON;
        roomItem = new Item("table", "a small but luxurious table", 80);
        theater = new Room("in a lecture theater", roomItem, hero);
        roomsList.add(theater);
       // characters.add(hero);
        itemsAll.add(roomItem);

        hero = Characters.THEWITCHER;
        roomItem = new Item("beamer", "a useful beamer", 1);
        lab = new Room("in a computing lab", roomItem, hero);
        roomsList.add(lab);
        //characters.add(hero);
        itemsAll.add(roomItem);

        hero = Characters.SUPERMAN;
        roomItem = new Item("phone", "a newly bought smartphone", 1);
        office = new Room("in the computing admin office", roomItem, hero);
        roomsList.add(office);
        //characters.add(hero);
        itemsAll.add(roomItem);

        hero = Characters.HOMERSIMPSON;
        roomItem = new Item("phone", "a dead phone", 3);
        trapRoom = new Room("in the traproom", roomItem, hero);
        roomsList.add(trapRoom);
        //characters.add(hero);
        itemsAll.add(roomItem);

        hero = Characters.BATMAN;
        roomItem = new Item("pillow", "an old pillow", 7);
        transporterRoom = new Room("in the transporter room", roomItem, hero);
        roomsList.add(transporterRoom);
        //characters.add(hero);
        itemsAll.add(roomItem);

        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("north", trapRoom);
        outside.setExit("west", transporterRoom);

        theater.setExit("west", outside);
        theater.setExit("north", trapRoom);
        theater.setExit("south", lab);
        theater.setExit("east", office);

        pub.setExit("east", outside);
        pub.setExit("north", theater);
        pub.setExit("south", trapRoom);
        pub.setExit("west", lab);

        lab.setExit("north", outside);
        lab.setExit("east", office);
        lab.setExit("south", pub);

        office.setExit("west", lab);
        office.setExit("north", theater);
        office.setExit("south", pub);
        office.setExit("east", outside);

        transporterRoom.setExit("south", office);

        // start game outside
        //currentRoom = outside
        roomsAccessed.push(outside);
        player.setRoom(roomsAccessed.peek());
        setCharactersItems2();
    }

    /**
     *
     */
    private void createPlayer() {
        player = new Player("Sabrina", 62);
    }

    /**
     * Main play routine.  Loops until end of play.
     */
    public void play() {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(roomsAccessed.peek().getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     *
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        } else if (commandWord.equals("go")) {
            goRoom(command);
        } else if (commandWord.equals("back")) {
            back();
        } else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        } else if (commandWord.equals("take")) {
            take(command);
        } else if (commandWord.equals("drop")) {
            drop(command);
        } else if (commandWord.equals("items")) {
            showItems();
        } else if (commandWord.equals("eat")) {
            eatCookie(command);
        } else if (commandWord.equals("charge")) {
            charge(command);
        } else if (commandWord.equals("fire")) {
            fire(command);
        }
        commandsCounter++;
        if (commandsCounter < 20) {
            return wantToQuit;
        } else {
            System.out.println("GAME OVER!!!");
            commandsCounter = 0;
            return !wantToQuit;
        }
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the
     * command words.
     */
    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /**
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }
        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = roomsAccessed.peek().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }

        //if you access the locked doors
        else if (roomsAccessed.peek().getShortDescription().contains("in the computing admin office")
                && direction.contains("east") && player.itemTaken("key") == false) {
            System.out.println("This door is locked. Take a key to unlock it.");

        } else if (roomsAccessed.peek().getShortDescription().contains("in a lecture theater") && direction.contains("south") && player.itemTaken("key") == false) {
            System.out.println("This door is locked. Take a key to unlock it.");

        }

        //if you enter the transporter room you get randomly transported into one of the other rooms
        else if (nextRoom.getShortDescription().contains("in the transporter room")) {
            while (nextRoom.getShortDescription().contains("in the transporter room") ||
                    nextRoom.getShortDescription().equals(roomsAccessed.peek().getShortDescription())) {
                nextRoom = randomRoom();
            }
            roomsAccessed.add(nextRoom);
            player.setRoom(roomsAccessed.peek());
            System.out.println(roomsAccessed.peek().getLongDescription());
            salutation();
        } else {
            roomsAccessed.add(nextRoom);
            player.setRoom(roomsAccessed.peek());
            System.out.println(roomsAccessed.peek().getLongDescription());
            salutation();
        }
    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     *
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * Implement a back command so that using it repeatedly takes you back several rooms, all the way to the beginning of the game
     * if used often enough. Use a Stack to do this. This command does not have a second word.
     * Also, implement a trapdoor somewhere (or some other form of door that you can only cross one way).
     */
    private void back() {
        if (roomsAccessed.size() > 1) {
            if (roomsAccessed.peek().getShortDescription().equals("in the traproom")) {
                System.out.println("You're trapped. You can't move to another room!");
            } else {
                roomsAccessed.pop();
                Room top = roomsAccessed.peek();
                System.out.println(top.getLongDescription());
                salutation();
            }
        } else {
            System.out.println("You can't go back any further.");
        }
    }

    /**
     * the player takes an item
     */
    private void take(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        } else {
            String itemToTake = command.getSecondWord();
            player.takeMultipleItems(itemToTake);
        }
    }

    /**
     * the player drops the item
     */
    private void drop(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        } else {
            String itemToDrop = command.getSecondWord();
            player.dropItem(itemToDrop);
            if (itemToDrop.equals(roomsAccessed.peek().getCharacter().getItemNeeded().getName())){
                System.out.println("Thank you. Here's my help for you: bla bla bla");
            }
        }
    }

    /**
     * Implement an items command that prints out all items currently carried and their total weight
     */
    private void showItems() {
        player.showItemsTaken();
        System.out.println("The total weight you are carrying is ");
        System.out.println(player.itemsCarriedWeight());

    }

    /**
     * Add a magic cookie item to a room. Add an eat cookie command. If a player finds and eats the magic cookie,
     * it increases the weight that the player can carry.
     */
    private void eatCookie(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Eat what?");
            return;
        } else {
            String cookie = command.getSecondWord();
            player.eatCookie(cookie);
        }
    }

    /**
     * When you charge the beamer, it memorizes the current room
     */
    private void charge(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Charge what?");
            return;
        } else {
            String beamer = command.getSecondWord();
            player.chargeBeamer(beamer);
        }
    }

    /**
     * When you fire the beamer, it transports you immediately back to the room it was charged in.
     */
    private void fire(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Fire what?");
            return;
        } else {
            String beamer = command.getSecondWord();
            player.fire(beamer);
        }
    }

    /**
     * Returns a random room
     */
    private Room randomRoom() {
        Random rand = new Random();
        return roomsList.get(rand.nextInt(roomsList.size()));
    }

    /**
     * returns true if the room contains a character, false otherwise
     */
    private void salutation() {
        if (!roomsAccessed.peek().getCharacter().getName().equals("")) {
            System.out.println("Hello there! I am " + roomsAccessed.peek().getCharacter().getName());
            System.out.println(
                    "I'll be able to help you if you give me the item I need first: a "
                            + roomsAccessed.peek().getCharacter().getItemNeeded().getName());
        }
    }

    /**
     * returns a random Item contained in any room
     */
    private Item randomItem() {
        int index = random.nextInt(itemsAll.size());
        Item item = itemsAll.get(index);
        return item;
    }

    /**
     * assigns a random item to each character
     */
    //private void setCharactersItems() {
      //  Iterator<Characters> it = characters.iterator();
        //while (it.hasNext()) {
          //  Characters charac = it.next();
            //charac.setItemNeeded(randomItem());
        //}
    //}

    private void setCharactersItems2(){
        for(Characters characters: Characters.values()){
           characters.setItemNeeded(randomItem());
        }

    }}
       





        
