package sample;

import InventorySystem.Coords;
import InventorySystem.Inventory;
import InventorySystem.Item;
import InventorySystem.ItemType;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;


import Room.*;
import Pollution.*;
import MusicPlayer.PlayMusic;
import NPC.*;
import TaskSystem.TaskSystem;

public class Controller implements Initializable {
    public double width,height;
    public boolean toggleHelpPane, toggleMapPane, toggleNPCPane;
    public ImageView playerIcon;
    private String musicFile1 = "GameMusic.wav";
    private String musicFile2 = "MusicFileVictor.wav";

    public Label playerLabel;
    public Button myButton;
    public Button taskButton;
    public ProgressBar progressbar_1;
    public AnchorPane myAnchorPane;
    public ImageView backgroundImage;
    public Pane pane1;
    public Pane helpPane;
    public Pane mapPane;
    public Pane npcPane;
    public Label helpPaneText;
    public Label npcPaneText;
    private Room currentRoom;
    private NPC sigurd, victor, kenneth;
    private Room town_square,harbor_east, harbor_west, shopping_street, fish_store, garbage_disposal, beach, pier_1, pier_2, reef;
    private PlayMusic musicPlayer;
    private Pollution ps;
    public Pane bottle_1, bottle_2, fridge, plasticStraw, gin;
    private Player player;
    private TaskSystem ts;
    private Image[] playerImage;
    private int playerIconCounter;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        musicPlayer = new PlayMusic(musicFile1, musicFile2);
        player = new Player(playerLabel, playerIcon);
        ts = new TaskSystem(1);
        createNPC();
        createRooms();
        createHelpPane();
        changeMusic();
        width = backgroundImage.getFitWidth();
        height = backgroundImage.getFitHeight();
        ps = new Pollution(50);
        progressbar_1.setProgress(ps.getPollution()/100);
        createItem();
        playerImage = new Image[21];
        assignPlayerImageToList();
    }
    public void assignPlayerImageToList()
    {
        for (int i = 0; i < 21; i++)
        {
            playerImage[i] = new Image("Player_png/Player_" + (i+1) + ".png");
        }
    }

    public void changePlayerIcon(int interval)
    {
        if (interval == 1)
        {
            if (playerIconCounter > 6)
            {
                playerIconCounter = 0;
            }
            playerIcon.setImage(playerImage[playerIconCounter]);
            playerIconCounter++;

            if (playerIconCounter > 6)
            {
                playerIconCounter = 0;
            }
        }
        if (interval == 2)
        {
            if (playerIconCounter < 7 & playerIconCounter < 13)
            {
                playerIconCounter = 7;
            }
            playerIcon.setImage(playerImage[playerIconCounter]);
            playerIconCounter++;

            if (playerIconCounter > 13)
            {
                playerIconCounter = 7;
            }
        }
        if (interval == 3)
        {
            if (playerIconCounter < 14)
            {
                playerIconCounter = 14;
            }
            playerIcon.setImage(playerImage[playerIconCounter]);
            playerIconCounter++;

            if (playerIconCounter > 20)
            {
                playerIconCounter = 14;
            }
        }
        if (interval == 4)
        {

            playerIcon.setImage(playerImage[playerIconCounter]);
            playerIconCounter++;

            if (playerIconCounter > 8)
            {
                playerIconCounter = 5;
            }
        }

    }

    private void createNPC()
    {
        sigurd = new NPC("Sigurd");
        sigurd.setPosition(192,432);
        sigurd.setPosition(192,540);


        victor = new NPC("Victor");
        victor.setPosition(576,864);
        victor.setPosition(576,756);
        victor.assignTask(ts.mainTask);

        kenneth = new NPC("Kenneth");
        kenneth.setPosition(1152,540);
        kenneth.setPosition(1152,432);

        //Assign Task steps to NPCs
        ts.assignStepNPC(ts.mainTask,0,kenneth);
        ts.assignStepNPC(ts.mainTask,1,sigurd);
        ts.assignStepNPC(ts.mainTask,2,kenneth);
        ts.assignStepNPC(ts.mainTask,3,victor);
    }

    public void changeEndGameScene()
    {
        if (25 > ps.getPollution())
        { reef.setRoomImage("reef_perfekt.png"); }
        else if (25 <= ps.getPollution() & ps.getPollution() < 75)
        { reef.setRoomImage("reef_fine.png"); }
        else if (75 <= ps.getPollution())
        { reef.setRoomImage("reef_bad.png"); }
    }

    public void createItem()
    {
        Coords coordBottle1 = new Coords(384,540);
        Item bottle1 = new Item("bottle1", 1, ItemType.PLASTIC, coordBottle1, bottle_1);
        harbor_east.addItem("bottle1", bottle1);
        bottle_1.setLayoutX(coordBottle1.getX());
        bottle_1.setLayoutY(coordBottle1.getY());

        Coords coordBottle2 = new Coords(960,216);
        Item bottle2 = new Item("bottle2", 1, ItemType.PLASTIC, coordBottle2, bottle_2);
        beach.addItem("bottle2", bottle2);
        bottle_2.setLayoutX(coordBottle2.getX());
        bottle_2.setLayoutY(coordBottle2.getY());


        Coords coordfridge = new Coords(192,540);
        Item frigde1 = new Item("fridge", 10, ItemType.PLASTIC, coordfridge, fridge);
        fish_store.addItem("fridge", frigde1);
        fridge.setLayoutX(coordfridge.getX());
        fridge.setLayoutY(coordfridge.getY());

        Coords coordplasticStraw = new Coords(576,540);
        Item plasticStraw1 = new Item("plasticStraw", 1, ItemType.PLASTIC, coordplasticStraw, plasticStraw);
        beach.addItem("plasticStraw", plasticStraw1);
        plasticStraw.setLayoutX(coordplasticStraw.getX());
        plasticStraw.setLayoutY(coordplasticStraw.getY());

        Coords coordgin = new Coords(768,648);
        Item gin1 = new Item("gin", 1, ItemType.PLASTIC, coordgin, gin);
        pier_1.addItem("gin", gin1);
        gin.setLayoutX(coordgin.getX());
        gin.setLayoutY(coordgin.getY());
    }

    public void manageItems(Room newRoom) {
        Item[] lastRoomItems = currentRoom.getAllItemsInRoom();
        for (Item item : lastRoomItems){
            item.disable();
        }

        Item[] newRoomItems = newRoom.getAllItemsInRoom();
        for (Item item : newRoomItems){
            item.enable();
        }
    }

    private void changeRoom(Room room)
    {
        changeEndGameScene();
        backgroundImage.setImage(room.getRoomImage());
        if(player.getPlayerTileX() == 0)
        {
            player.setPlayerX(width-(2*width/10));
            System.out.println("Set x:" + player.getPlayerX());
        }
        else if(player.getPlayerTileX() == width-(width/10))
        {
            player.setPlayerX(width/10);
            System.out.println("Set x:" + player.getPlayerX());
        }
        else if(player.getPlayerTileY() == 0)
        {
            player.setPlayerY(height - (2*height/10));
            System.out.println("Set y:" + player.getPlayerY());
        }
        else if(player.getPlayerTileY() == height-(height/10))
        {
            player.setPlayerY(height/10);
            System.out.println("Set y:" + player.getPlayerY());
        }

        manageItems(room);
        currentRoom = room;
        //progressbar.setProgress(ps.getPoint()/100);

        changeMusic();
    }

    private void changeMusic()
    {
        if(currentRoom == fish_store)
        {
            musicPlayer.stopMusicFile2();
            musicPlayer.startMusicFile1();
        }
        else
        {
            if(!musicPlayer.isPlaying2())
            {
                musicPlayer.stopMusicFile1();
                musicPlayer.startMusicFile2();
            }
        }
    }

    private void createRooms()
    {
        town_square = new Room(20);
        harbor_east = new Room(8);
        harbor_west = new Room(4);
        shopping_street = new Room(4);
        fish_store = new Room(2);
        garbage_disposal = new Room(1);
        beach = new Room(2);
        pier_1 = new Room(2);
        pier_2 = new Room(3);
        reef = new Room(1);


        town_square.setRoomImage("town_square.png");
        town_square.setRoomExit(768,972);
        town_square.setRoomExit(960,972);
        town_square.setRoomExit(0,432);
        town_square.setRoomExit(0,540);
        town_square.setRoomExit(1728,432);
        town_square.setRoomExit(1728,540);
        town_square.setRoomNeighbour(0,harbor_west);
        town_square.setRoomNeighbour(1,harbor_west);
        town_square.setRoomNeighbour(2,garbage_disposal);
        town_square.setRoomNeighbour(3,garbage_disposal);
        town_square.setRoomNeighbour(4, shopping_street);
        town_square.setRoomNeighbour(5, shopping_street);

        
        harbor_west.setRoomImage("HarborWest.png");
        harbor_west.assignNPC(victor);
        harbor_west.setRoomExit(768,0);
        harbor_west.setRoomExit(960,0);
        harbor_west.setRoomExit(1728,108);
        harbor_west.setRoomExit(1728,216);
        harbor_west.setRoomExit(1728,324);
        harbor_west.setRoomExit(1728,432);
        harbor_west.setRoomExit(768,972);
        harbor_west.setRoomExit(960,972);
        harbor_west.setRoomNeighbour(0,town_square);
        harbor_west.setRoomNeighbour(1,town_square);
        harbor_west.setRoomNeighbour(2,harbor_east);
        harbor_west.setRoomNeighbour(3,harbor_east);
        harbor_west.setRoomNeighbour(4,harbor_east);
        harbor_west.setRoomNeighbour(5,harbor_east);
        harbor_west.setRoomNeighbour(6,pier_1);
        harbor_west.setRoomNeighbour(7,pier_1);
        harbor_west.addBoundary(192,432);
        harbor_west.addBoundary(384,324);
        harbor_west.addBoundary(384,216);
        harbor_west.addBoundary(192,864);
        harbor_west.addBoundary(1728,756);
        harbor_west.addBoundary(1728,648);
        harbor_west.addBoundary(1728,540);
        harbor_west.addBoundary(1728,432);


        harbor_east.setRoomImage("HarborEast.png");
        harbor_east.setRoomExit(0,108);
        harbor_east.setRoomExit(0,216);
        harbor_east.setRoomExit(0,324);
        harbor_east.setRoomExit(0,432);
        harbor_east.setRoomExit(768,0);
        harbor_east.setRoomExit(960,0);
        harbor_east.setRoomExit(1728,216);
        harbor_east.setRoomExit(1728,324);
        harbor_east.setRoomExit(1728,432);
        harbor_east.setRoomExit(1728,540);
        harbor_east.setRoomExit(768,972);
        harbor_east.setRoomExit(960,972);
        harbor_east.setRoomNeighbour(0,harbor_west);
        harbor_east.setRoomNeighbour(1,harbor_west);
        harbor_east.setRoomNeighbour(2,harbor_west);
        harbor_east.setRoomNeighbour(3,harbor_west);
        harbor_east.setRoomNeighbour(4,shopping_street);
        harbor_east.setRoomNeighbour(5,shopping_street);
        harbor_east.setRoomNeighbour(6,beach);
        harbor_east.setRoomNeighbour(7,beach);
        harbor_east.setRoomNeighbour(8,beach);
        harbor_east.setRoomNeighbour(9,beach);
        harbor_east.setRoomNeighbour(10,pier_2);
        harbor_east.setRoomNeighbour(11,pier_2);


        shopping_street.setRoomImage("ShoppingStreet.png");
        shopping_street.setRoomExit(0,432);
        shopping_street.setRoomExit(0,540);
        shopping_street.setRoomExit(768,972);
        shopping_street.setRoomExit(960,972);
        shopping_street.setRoomExit(1728,432);
        shopping_street.setRoomExit(1728,540);
        shopping_street.setRoomNeighbour(0,town_square);
        shopping_street.setRoomNeighbour(1,town_square);
        shopping_street.setRoomNeighbour(2,harbor_east);
        shopping_street.setRoomNeighbour(3,harbor_east);
        shopping_street.setRoomNeighbour(4,fish_store);
        shopping_street.setRoomNeighbour(5,fish_store);

        fish_store.setRoomImage("fiskebod.png");
        fish_store.assignNPC(kenneth);
        fish_store.setRoomExit(0,216);
        fish_store.setRoomExit(0,324);
        fish_store.setRoomExit(0,432);
        fish_store.setRoomExit(0,540);
        fish_store.setRoomNeighbour(0,shopping_street);
        fish_store.setRoomNeighbour(1,shopping_street);
        fish_store.setRoomNeighbour(2,shopping_street);
        fish_store.setRoomNeighbour(3,shopping_street);
        fish_store.addBoundary(192,756);
        fish_store.addBoundary(384,756);
        fish_store.addBoundary(576,756);
        fish_store.addBoundary(768,756);
        fish_store.addBoundary(960,756);
        fish_store.addBoundary(1152,756);
        fish_store.addBoundary(1344,756);
        fish_store.addBoundary(1536,756);
        fish_store.addBoundary(1536,648);
        fish_store.addBoundary(1536,540);
        fish_store.addBoundary(1536,432);
        fish_store.addBoundary(1536,324);
        fish_store.addBoundary(1536,216);
        fish_store.addBoundary(1536,108);

        garbage_disposal.setRoomImage("garbage_disposal.png");
        garbage_disposal.setRoomExit(1728,432);
        garbage_disposal.setRoomExit(1728, 540);
        garbage_disposal.setRoomNeighbour(0,town_square);
        garbage_disposal.setRoomNeighbour(1,town_square);

        beach.setRoomImage("Beach.done.png");
        beach.setRoomExit(0,216);
        beach.setRoomExit(0,324);
        beach.setRoomExit(0,432);
        beach.setRoomExit(0,540);
        beach.setRoomNeighbour(0,harbor_east);
        beach.setRoomNeighbour(1,harbor_east);
        beach.setRoomNeighbour(2,harbor_east);
        beach.setRoomNeighbour(3,harbor_east);
        beach.addBoundary(192,756);
        beach.addBoundary(384,756);
        beach.addBoundary(576,756);
        beach.addBoundary(768,756);
        beach.addBoundary(960,756);
        beach.addBoundary(1152,756);
        beach.addBoundary(1344,756);
        beach.addBoundary(1536,756);

        pier_1.setRoomImage("pier_1.png");
        pier_1.setRoomExit(768,0);
        pier_1.setRoomExit(960,0);
        pier_1.setRoomNeighbour(0,harbor_west);
        pier_1.setRoomNeighbour(1,harbor_west);
        pier_1.addBoundary(192,756);
        pier_1.addBoundary(384,756);
        pier_1.addBoundary(576,756);
        pier_1.addBoundary(768,756);
        pier_1.addBoundary(960,756);
        pier_1.addBoundary(1152,756);
        pier_1.addBoundary(1344,756);
        pier_1.addBoundary(1536,756);


        pier_2.setRoomImage("pier_2.png");
        pier_2.assignNPC(sigurd);
        pier_2.setRoomExit(768,0);
        pier_2.setRoomExit(960,0);
        pier_2.setRoomExit(1728,216);
        pier_2.setRoomExit(1728,324);
        pier_2.setRoomExit(1728,432);
        pier_2.setRoomExit(1728,540);
        pier_2.setRoomNeighbour(0,harbor_east);
        pier_2.setRoomNeighbour(1,harbor_east);
        pier_2.setRoomNeighbour(2,reef);
        pier_2.setRoomNeighbour(3,reef);
        pier_2.setRoomNeighbour(4,reef);
        pier_2.setRoomNeighbour(5,reef);
        pier_2.addBoundary(192,756);
        pier_2.addBoundary(384,756);
        pier_2.addBoundary(576,756);
        pier_2.addBoundary(768,756);
        pier_2.addBoundary(960,756);
        pier_2.addBoundary(1152,756);
        pier_2.addBoundary(1344,756);
        pier_2.addBoundary(1536,756);

        currentRoom = town_square;
    }

    private void createHelpPane()
    {
        String helpPaneTextFieldText = "So you need help with the controls, huh?" + "\n\n" +
                "To see a map of the game, simply press 'M'" + "\n\n" +
                "To walk between rooms, use 'WASD'" + "\n\n" +
                "To accept tasks, simply go up to an NPC and press 'T' to talk with them and see their tasks" + "\n\n" +
                "Press 'Q' for dancing" + "\n\n" +
                "To pick up an item, walk over the tile with the item on it and press 'E'" + "\n\n" +
                "To drop an item, press '1, 2, 3 ... 0'" + "\n\n" +
                "To hide/show this help window, press 'H'";
        helpPaneText.setText(helpPaneTextFieldText);
        toggleHelpPane = false;
    }

    public void changeText(ActionEvent actionEvent) {
        //playerLabel.setText("TESTY McTest");
    }

    public void handle(javafx.scene.input.KeyEvent keyEvent) {
        var key = keyEvent.getCode();
        switch (key)
        {
            case W: moveUp(currentRoom);   break;
            case A: moveLeft(currentRoom); break;
            case S: moveDown(currentRoom); break;
            case D: moveRight(currentRoom);break;
            case H: help(); break;
            case M: map(); break;
            case T: talkNPC(currentRoom); break;
            case E: pickUp(); break;
            case Q: Dance(currentRoom); break;
            case DIGIT1:
            case DIGIT2:
            case DIGIT3:
            case DIGIT4:
            case DIGIT5:
            case DIGIT6:
            case DIGIT7:
            case DIGIT8:
            case DIGIT9:
            case DIGIT0: drop(key.ordinal()-25); break;
        }
    }

    private void drop(int index){
        if (index == -1) index = 10;
        Inventory inv = player.getPlayerInventory();
        Item item = inv.removeItem(index);
        if (item == null) return;
        item.setCoords(player.getCoords());
        currentRoom.addItem(item.getName(), item);
        item.enable();
        checkItemDropRoom(item);
    }

    private void pickUp() {
        int x = (int) player.getPlayerTileX();
        int y = (int) player.getPlayerTileY();
        Coords coords = new Coords(x, y);
        Item item = currentRoom.getItemByCoordinates(coords);
        if (item == null) return;
        if(player.getPlayerInventory().addItem(item)) {
            currentRoom.removeItem(item.getName());
        }
    }

    private void checkItemDropRoom(Item item)
    {
        if(item.getName().equals("fridge"))
        {
            if(currentRoom == pier_2 || currentRoom == beach || currentRoom == pier_1)
            {
                ps.addPollution(25);
            }
            else if(currentRoom == garbage_disposal)
            {
                ps.addPollution(-24);
            }
        }
        else if(item.getName().equals("bottle1") || item.getName().equals("bottle2") || item.getName().equals("plasticStraw"))
        {
            if(currentRoom == garbage_disposal)
            {
                ps.addPollution(-6);
            }
        }
    }

    public void Dance(Room currentRoom)
    {
        int x = (int) player.getPlayerTileX();
        int y = (int) player.getPlayerTileY();
        if(!currentRoom.isWall(x,y + (int) height/10))
        {
            player.setPlayerY(player.getPlayerY() + height/40000);
            System.out.println(player.getPlayerX() + ", " + player.getPlayerY());
        }

        changePlayerIcon(4);
    }

    public void moveUp(Room currentRoom)
    {
        int x = (int) player.getPlayerTileX();
        int y = (int) player.getPlayerTileY();
        if(!currentRoom.isWall(x,y - (int) height/10))
        {
            player.setPlayerY(player.getPlayerY() - height/40);
            System.out.println(player.getPlayerX() + ", " + player.getPlayerY());
        }
        if(toggleNPCPane)
        {
            NPC npc = currentRoom.getNPC();
            if(!npc.isPlayerInRange(x , y))
            {
                toggleNPCPane = false;
                npcPane.setVisible(toggleNPCPane);
            }
        }
        if(currentRoom.isExit(x, y))
        {
            System.out.println("You are on an exit");
            int exitNumber = currentRoom.getExitNumber(x , y);
            Room nextRoom = currentRoom.getRoomFromExitNumber(exitNumber);
            changeRoom(nextRoom);
        }
        changePlayerIcon(3);
        progressbar_1.setProgress(ps.getPollution()/100);
        System.out.println("x .  " + player.getPlayerTileX());
        System.out.println("y  .  " + player.getPlayerTileY());
    }
    public void moveDown(Room currentRoom)
    {
        int x = (int) player.getPlayerTileX();
        int y = (int) player.getPlayerTileY();
        if(!currentRoom.isWall(x,y + (int) height/10))
        {
            player.setPlayerY(player.getPlayerY() + height/40);
            System.out.println(player.getPlayerX() + ", " + player.getPlayerY());
        }
        if(toggleNPCPane)
        {
            NPC npc = currentRoom.getNPC();
            if(!npc.isPlayerInRange(x ,y))
            {
                toggleNPCPane = false;
                npcPane.setVisible(toggleNPCPane);
            }
        }
        if(currentRoom.isExit(x,y))
        {
            System.out.println("You are on an exit");
            int exitNumber = currentRoom.getExitNumber(x , y);
            Room nextRoom = currentRoom.getRoomFromExitNumber(exitNumber);
            changeRoom(nextRoom);
        }
        changePlayerIcon(1);
        progressbar_1.setProgress(ps.getPollution()/100);
    }
    public void moveLeft(Room currentRoom)
    {
        int x = (int) player.getPlayerTileX();
        int y = (int) player.getPlayerTileY();
        if(!currentRoom.isWall(x - (int) width/10, y))
        {
            player.setPlayerX(player.getPlayerX() - width/40);
            System.out.println(player.getPlayerX() + ", " + player.getPlayerY());
        }
        if(toggleNPCPane)
        {
            NPC npc = currentRoom.getNPC();
            if(!npc.isPlayerInRange(x , y))
            {
                toggleNPCPane = false;
                npcPane.setVisible(toggleNPCPane);
            }
        }
        if(currentRoom.isExit(x ,y))
        {
            System.out.println("You are on an exit");
            int exitNumber = currentRoom.getExitNumber(x, y);
            Room nextRoom = currentRoom.getRoomFromExitNumber(exitNumber);
            changeRoom(nextRoom);
        }
        changePlayerIcon(2);
        progressbar_1.setProgress(ps.getPollution()/100);
    }
    public void moveRight(Room currentRoom)
    {
        int x = (int) player.getPlayerTileX();
        int y = (int) player.getPlayerTileY();
        if(!currentRoom.isWall(x + (int) width/10, y)) //This line checks if we are trying to move into a boundary, and only runs the codeblock if we are not entering a boundary
        {
            player.setPlayerX(player.getPlayerX() + width/40);
            System.out.println(player.getPlayerX() + ", " + player.getPlayerY());
        }
        if(toggleNPCPane)
        {
            NPC npc = currentRoom.getNPC();
            if(!npc.isPlayerInRange(x , y))
            {
                toggleNPCPane = false;
                npcPane.setVisible(toggleNPCPane);
            }
        }
        if(currentRoom.isExit(x ,y)) //This line checks if we are moving into an active exit and if so we change the room to the room corresponding with the exit
        {
            System.out.println("You are on an exit");
            int exitNumber = currentRoom.getExitNumber(x , y);
            Room nextRoom = currentRoom.getRoomFromExitNumber(exitNumber);
            changeRoom(nextRoom);
        }
        changePlayerIcon(1);
        progressbar_1.setProgress(ps.getPollution()/100);
    }

    private void help()
    {
        toggleHelpPane = !toggleHelpPane;
        helpPane.setVisible(toggleHelpPane);
    }

    private void map()
    {
        toggleMapPane = !toggleMapPane;
        mapPane.setVisible(toggleMapPane);
    }

    private void checkTask()
    {
        if(ts.getActiveTask().isCompleted())
        {
            ps.addPollution(ts.getActiveTask().getRewardPoints());
            ts.moveCompletedTask(ts.getActiveTask());
        }
    }

    private void talkNPC(Room currentRoom)
    {
        if(currentRoom.hasNPC())
        {

            NPC npcInTheRoom = currentRoom.getNPC();
            if(npcInTheRoom.isPlayerInRange((int)player.getPlayerTileX(),(int)player.getPlayerTileY()))
            {
                toggleNPCPane = !toggleNPCPane;
                if(toggleNPCPane)
                {
                    if(npcInTheRoom.hasTask())
                    {
                        npcInTheRoom.setSpeech("Hello, I'm " + npcInTheRoom.getName() + " and I have a task for you");
                        npcPaneText.setText(npcInTheRoom.getSpeech());
                        taskButton.setText("Accept task");
                        taskButton.setVisible(true);
                    }
                    else if(ts.getActiveTask() == null)
                    {
                        npcInTheRoom.setSpeech("Hello, I'm " + npcInTheRoom.getName() + ". Did you know that if you pick up some trash and throw it out at the garbage disposal it helps the environment.");
                        npcPaneText.setText(npcInTheRoom.getSpeech());
                        taskButton.setVisible(false);
                        taskButton.setText("I don't have a task");
                    }
                    try
                    {
                        if(ts.getActiveTask().isNPCInTask(npcInTheRoom) && !npcInTheRoom.hasTask())
                        {
                            if(!ts.getActiveTask().isTaskGiver(npcInTheRoom))
                            {
                                npcInTheRoom.setSpeech(ts.getActiveTask().getStep(npcInTheRoom));

                                if(ts.getActiveTask().getCompletedStepsCounter() > 0)
                                {
                                    if(ts.getActiveTask().isNPCLastStep(npcInTheRoom))
                                    {
                                        taskButton.setVisible(false);
                                    }
                                    else
                                    {
                                        taskButton.setVisible(true);
                                    }
                                }
                                else if(ts.getActiveTask().getCompletedStepsCounter() < 1)
                                {
                                    taskButton.setVisible(true);
                                }

                            }
                            else if(ts.getActiveTask().isTaskGiver(npcInTheRoom))
                            {
                                if(npcInTheRoom == ts.getActiveTask().getNPCSteps(npcInTheRoom))
                                {
                                    npcInTheRoom.setSpeech(ts.getActiveTask().getStep(npcInTheRoom));
                                    taskButton.setVisible(true);
                                }
                                else
                                {
                                    npcInTheRoom.setSpeech(ts.getActiveTask().getTaskDescription());
                                    taskButton.setVisible(false);
                                }
                            }
                            npcPaneText.setText(npcInTheRoom.getSpeech());

                        }
                    }
                    catch (NullPointerException ex)
                    {

                    }
                }
                npcPane.setVisible(toggleNPCPane);
            }
        }
    }

    public void taskButtonPress(ActionEvent actionEvent)
    {
        NPC npcInTheRoom = currentRoom.getNPC();
        if(npcInTheRoom.hasTask())
        {
            ts.addTask(ts.mainTask);
            npcInTheRoom.removeTask();
            taskButton.setVisible(false);
            npcPaneText.setText(ts.mainTask.taskStart());
        }
        else if(ts.getActiveTask().isNPCInTask(npcInTheRoom))
        {
            ts.getActiveTask().completedStep();
            taskButton.setVisible(false);
            checkTask();
        }
    }
}
