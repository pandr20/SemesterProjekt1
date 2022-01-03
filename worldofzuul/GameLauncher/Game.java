package GameLauncher;

import java.util.Scanner;
import Commands.*;
import Room.*;
import TaskSystem.*;
import Parser.*;
import PointSystem.*;
import InventorySystem.*;
import NPC.*;
import MusicPlayer.*;

public class Game
{
    private Parser parser;
    private Room currentRoom;
    private TaskSystem ts;
    private PointSystem ps;
    private Inventory inv;
    private NPC npc;
    private PlayMusic musicObject;
    private PlayMusic fishStoreMusicObject;
    private String mainMusic = "MusicPlayer/MusicFileVictor.wav";
    private String fishStoreMusic = "FishStoreMusic.wav";


    protected Room town_square, garbage_disposal, shopping_street, fish_store, harbour_east, harbour_west, beach, pier_1, pier_2, reef;


    public Game()
    {
        musicObject = new PlayMusic();
        fishStoreMusicObject = new PlayMusic();
        musicObject.playMusic(mainMusic);
        ts = new TaskSystem(10);
        createRooms();
        parser = new Parser();
        ps = new PointSystem();
        inv = new Inventory(10);
        npc = new NPC();
    }

    private void createRooms()
    {
        Item apple, bottle, freezer;

        apple = new Item("Apple", 1, ItemType.BIOLOGICAL);
        freezer = new Item("Freezer", 10, ItemType.METAL);
        bottle = new Item("Bottle", 1, ItemType.PLASTIC);

        NPC victor, sigurd, kenneth;

        victor = new NPC("Victor", false); //Shopping_street
        sigurd = new NPC("Sigurd", true); // Pier_1
        kenneth = new NPC("Kenneth",false); // Fish_store

        town_square = new Room("in the Town Square");
        garbage_disposal = new Room("at the Garbage disposal");
        shopping_street = new Room("in the shopping street");
        fish_store = new Room("in the fish store");
        harbour_east = new Room("at Harbour east");
        harbour_west = new Room("at Harbour west");
        beach = new Room("on the Beach");
        pier_1 = new Room("at Pier 1");
        pier_2 = new Room("at Pier 2");
        reef = new Room("on the reef");

        town_square.setExit("east", shopping_street);
        town_square.setExit("south", harbour_west);
        town_square.setExit("west", garbage_disposal);
        town_square.addItem("Apple", apple);

        garbage_disposal.setExit("east", town_square);

        shopping_street.setExit("west", town_square);
        shopping_street.setExit("east", fish_store);
        shopping_street.setExit("south", harbour_east);


        fish_store.setExit("west", shopping_street);
        fish_store.addItem("Freezer", freezer);
        fish_store.addNPC("Kenneth", kenneth);

        harbour_west.setExit("north", town_square);
        harbour_west.setExit("east", harbour_east);
        harbour_west.setExit("south", pier_1);
        harbour_west.addTask(ts.mainTask.getTaskName(), ts.mainTask);
        harbour_west.addNPC("Victor", victor);

        harbour_east.setExit("north", shopping_street);
        harbour_east.setExit("east", beach);
        harbour_east.setExit("south", pier_2);
        harbour_east.setExit("west", harbour_west);

        beach.setExit("west", harbour_east);
        beach.addItem("Bottle", bottle);

        pier_1.setExit("north", harbour_west);
        pier_1.addTask(ts.testTrack2.getTaskName(),ts.testTrack2);


        pier_2.setExit("north", harbour_east);
        pier_2.setExit("east", reef);
        pier_2.addNPC("Sigurd", sigurd);

        //assign Task Steps
        ts.assignStepRoom(ts.mainTask, 0, fish_store);
        ts.assignStepRoom(ts.mainTask, 1, pier_2);
        ts.assignStepRoom(ts.mainTask, 2, fish_store);
        ts.assignStepRoom(ts.mainTask,3,harbour_west);
        ts.assignStepRoom(ts.mainTask, 4,pier_2);

        ts.assignStepRoom(ts.testTrack2,0, beach);
        ts.assignStepRoom(ts.testTrack2, 1, pier_1);
        ts.assignBadStepRoom(ts.testTrack2, 0, pier_2);

        currentRoom = town_square;
    }

    public void play() 
    {            
        printWelcome();

                
        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            checkTasks();
            if(currentRoom == fish_store)
            {
                musicObject.playMusic(mainMusic);
                //fishStoreMusicObject.playMusic(fishStoreMusic);
            }
            if(currentRoom == reef)
            {
                endGame();
            }
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    private void endGame()
    {
        System.out.println("You ended the game with " + ps.getPoint() + " points");
        int points = ps.getPoint();

        if(points <= -100)
        {
            System.out.println("The reef is dead, there is no life anywhere to be seen.");
            System.out.println("There is nothing to be done, the world as we know it is going to die this way.");
        }
        else if(points > -100 && points < 100)
        {
            System.out.println("The reef is in a bad state, if nothing is done the reef will die in the near future.");
            System.out.println("We need to do something right now.");
        }
        else if(points >= 100)
        {
            System.out.println("Its a miracle, the reef is healing");
            System.out.println("You did it, the fish are returning to the reef!");
        }
    }

    private void checkTasks()
    {
        if(ts.getActiveTask() != null)
        {
            Task activeTask = ts.getActiveTask();
            if(activeTask.getRoomStep() == currentRoom) //Checks if a task step requires you to be in the current room, and if true, then the task step is marked as completed
            {
                activeTask.completedStep();
            }
            else
            {
                try
                {
                    activeTask.completedBadStep();
                }
                catch (NullPointerException e)
                {
                }
            }
            if(activeTask.isCompleted())
            {
                System.out.println("Congratulations task: " + activeTask.getTaskName() + " has been completed!");
                ts.moveCompletedTask(activeTask);
                ps.addPoint(activeTask.getRewardPoints());
            }
            else if(activeTask.isCompletedBad())
            {
                System.out.println("Wow really? You just made the oceans health worse. Good job!");
                ts.moveCompletedTask(activeTask);
                ps.addPoint(activeTask.getRewardPoints());
            }
        }
    }

    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is an incredibly exciting adventure game.");
        System.out.println("It has been a very hard night.");
        System.out.println("You wake up after a night full of drinking. You do not know where you are.");
        System.out.println("A government official comes up to you;");
        System.out.println( " \"Hey you are finally awake, you are the famous marine biologist, right? We need your help. "
                +"\n" + "  The world is in trouble, but you can help us save it!\"");
        System.out.println("You: \"No it is already too late, the world is going to end. Leave me alone!\"");
        System.out.println("Government official: \"You know that isn't true! Do me a favor, go south to the harbour, "
                + "\n" + "  there is someone who needs your help. We can still make it!");
        System.out.println();

        System.out.println("Type '" + CommandWord.HELP + "' if you need help with the controls.");
        System.out.println();

        System.out.println(currentRoom.getLongDescription());
    }

    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if(commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        switch (commandWord) {
            case HELP -> printHelp();
            case GO -> goRoom(command);
            case SHOW -> show(command);
            case ACCEPT -> accept(command);
            case TAKE -> take(command);
            case DROP -> drop(command);
            case INV -> printInventory();
            case QUIT -> wantToQuit = quit(command);
            case INSPECT -> inspect(command);
            case HELLO -> hello(command);
            case MAP -> map(command);
        }
        return wantToQuit;
    }
    private void map(Command command) {
        System.out.println("#############################################################################");
        System.out.println("#                  #                  #                  #                  #");
        System.out.println("# Garbage Disposal     Town Square      Shopping Street       Fish Store    #");
        System.out.println("#                  #                  #                  #                  #");
        System.out.println("############################  #################  ############################");
        System.out.println("                   #                  #                  #                  #");
        System.out.println("                   #   Harbour West       Harbour East          Beach       #");
        System.out.println("                   #                  #                  #                  #");
        System.out.println("                   #########  #################  ############################");
        System.out.println("                   #                  #                  #                  #");
        System.out.println("                   #     Pier 1       #      Pier 2      >      Reef        #");
        System.out.println("                   #                  #                  #                  #");
        System.out.println("                   ##########################################################");
    }

    private void inspect(Command command) {
        if(command.hasSecondWord() == false){
            return;
        }
        String item_name = command.getSecondWord().toLowerCase();
        Item[] inventory = inv.getInventory();
        if (inventory.length == 0) {
            System.out.println("Inventory is empty.");
            return;
        }
        for(Item item : inventory){
            if(item.getName().equals(item_name)) {
                System.out.println("Item " + item.getName() + " is of size " + item.getSize() + " and type " + item.getType().toString());
                return;
            }
        }
        System.out.println("Item not found.");
    }

    private void take(Command command){
        if(command.hasSecondWord() == false){
            return;
        }
        String secondWord = command.getSecondWord();
        Item item = currentRoom.getItem(secondWord);
        if (item == null){
            System.out.println("Item not found.");
            return;
        }


        if (inv.addItem(item)){
            System.out.println("Took" + " " + item.getName());
            currentRoom.removeItem(secondWord);
        } else {
            System.out.println("Not enough space in inventory.");
        }
    }

    private void drop(Command command){
        if(command.hasSecondWord() == false){
            return;
        }
        String secondWord = command.getSecondWord();
        Item item = inv.removeItem(secondWord);
        if (item == null){
            System.out.println("Item not found.");
            return;
        }
        currentRoom.addItem(secondWord, item);
        System.out.println("Dropped" + " " + item.getName());
    }

    private void printInventory(){
        Item[] inventory = inv.getInventory();
        if (inventory.length == 0){
            System.out.println("Inventory is empty.");
            return;
        }
        for(Item item : inventory){
            System.out.println(item.getName());
        }
        System.out.println("Total weight" + " " + inv.getTotalWeight());
    }

    private void printHelp()
    {
        System.out.println("So you need help with the controls, huh?");
        System.out.println("Well, your command words are:");
        parser.showCommands(); // Udskriver alle tilgængelige kommandoer for input, på nær UNKNOWN, da den bliver sorteret fra.
        System.out.println();
        System.out.println("To see a map of the game, simply write 'map'");
        System.out.println("To walk between rooms, write 'go' followed by which direction to go"
                            + "\n" + "  Example: 'go east' - lets you go east!");
        System.out.println("To accept tasks, write 'accept' followed by the task name"
                            + "\n" + "  Example: 'accept main' - lets you accept the main task!");
        System.out.println("To talk with an NPC, write 'hello' followed by the NPC's name"
                            + "\n" + "  Example: 'hello Victor' - lets you talk to Victor!");
        System.out.println("To show which tasks are currently accepted, write 'show task'");
        System.out.println("To show what the next step in your task is, write 'show task' followed by the task name!"
                            + "\n" + "  Example: 'show task main' - to see the next step of the main task!");
        System.out.println("To pick up an item, write 'take' followed by the item name");
        System.out.println("To view your inventory, simply write 'inventory'");
        System.out.println("To inspect an item, write 'inspect' followed by the item name");
        System.out.println("To drop an item, write 'drop' followed by the item name");
        System.out.println("To quit the game, simply write 'quit'");

    }

    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }
        String direction = command.getSecondWord().toLowerCase();

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else if(nextRoom == reef)
        {
            System.out.println("You are about to go to the reef, are you sure you wanna go there?");
            System.out.println("Yes - Go to the reef");
            System.out.println("No - Stay at the pier");

            Scanner in = new Scanner(System.in);
            String temp = in.nextLine();
            if(temp.equalsIgnoreCase("yes"))
            {
                currentRoom = reef;
                System.out.println(currentRoom.getLongDescription());
            }
            else if(temp.equalsIgnoreCase("no"))
            {
                currentRoom = pier_2;
                System.out.println(currentRoom.getLongDescription());
            }
        }

        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    private void hello(Command command)
    {
        if(!command.hasSecondWord())
        {
            System.out.println("Who do you want to talk to?");
            return;
        }

        String talkTo = command.getSecondWord().toLowerCase();

        switch (talkTo) {

            case "victor": {
                if (currentRoom == harbour_west) {
                    System.out.println(npc.victor());
                } else {
                    System.out.println("Victor is not in this room ...");
                }
                break;
            }
            case "sigurd": {
                if (currentRoom == pier_2) {
                    System.out.println(npc.sigurd());
                } else {
                    System.out.println("Sigurd is not in this room ...");
                }
                break;
            }
            case "kenneth": {
                if (currentRoom == fish_store) {
                    System.out.println(npc.kenneth());
                } else {
                    System.out.println("Kenneth is not in this room ...");
                }
                break;
            }
            default: {
                System.out.println("Don't know who " + talkTo + " is.");
            }
        }
    }

    private void show(Command command)
    {
        if(!command.hasSecondWord())
        {
            System.out.println("Show what?");
            return;
        }

        String showing = command.getSecondWord().toLowerCase();

        switch (showing)
        {
            case "point":
                System.out.println(ps.toString());
                break;

            case "task":
                String task = command.getThirdWord();
                if(!command.hasThirdWord())
                {
                    System.out.println(ts.toString());
                    break;
                }
                else if(ts.getActiveTaskCounter() > 0)
                {
                    try
                    {
                        if(ts.isATask(task))
                        {
                            Task temp = ts.getTask(task);
                            System.out.println(ts.showTaskStep(temp));
                            break;
                        }
                    }
                    catch (NullPointerException ex)
                    {
                        System.out.println(task + " is not a valid task");
                        break;
                    }
                }
                break;

            case "completed":
                System.out.println("Completed tasks: " + ts.getCompletedTask());
                break;
            default:
                System.out.println(showing + " is not a valid command for show.");
                break;
        }
    }

    private void accept(Command command)
    {
        if(!command.hasSecondWord())
        {
            System.out.println("Accept what?");
            return;
        }
        String task = command.getSecondWord().toLowerCase();
        System.out.println("Accepting " + task);

        if(currentRoom.getTask(task) != null)
        {
            Task temp = currentRoom.getTask(task);
            ts.addTask(temp);
            currentRoom.removeTaskFromRoom(task);
        }
    }

    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;
        }
    }
}
