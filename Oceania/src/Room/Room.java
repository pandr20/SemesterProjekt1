package Room;

import InventorySystem.*;
import TaskSystem.*;
import NPC.*;
import javafx.scene.image.Image;
import java.io.File;
import java.util.Set;
import java.util.HashMap;



public class Room 
{
    private HashMap<Integer, Room> roomNeighbours;
    private HashMap<String, Task> tasksInRoom;
    private HashMap<String, Item> itemsInRoom;
    private HashMap<String, NPC> NPCsInRoom;
    private NPC npcInRoom;
    private int[][] boundaries;
    private int[][] extraBoundaries;
    private int extraBoundaryCounter = 0;
    private int[][] exitLocations;
    private int exitCounter = 0;
    private Image roomImage;

    public Room(int exitLocations)
    {
        roomNeighbours = new HashMap<Integer, Room>();
        tasksInRoom = new HashMap<String, Task>();
        itemsInRoom = new HashMap<String, Item>();
        NPCsInRoom = new HashMap<String, NPC>();
        this.exitLocations = new int[exitLocations*2][2];
        boundaries = new int[37][2];
        extraBoundaries = new int[28][2];
        npcInRoom = null;
        setBoundaries();
    }

    private void setBoundaries()
    {
        int x = 0;
        int y = 0;

        for (int i = 0; i < boundaries.length; i++)
        {
            if(i < 9 && x < 1728 && y <= 0)
            {
                for (int j = 0; j < boundaries[i].length; j++)
                {
                    if(j == 0)
                    {
                        boundaries[i][j] = x;
                    }
                    else if(j == 1)
                    {
                        boundaries[i][j] = y;
                    }
                }
                x += 192;
            }
            if(i > 8 && i < 18 && x == 1728 && y < 972)
            {
                for (int j = 0; j < boundaries[i].length; j++)
                {
                    if(j == 0)
                    {
                        boundaries[i][j] = x;
                    }
                    else if(j == 1)
                    {
                        boundaries[i][j] = y;
                    }
                }
                y += 108;
            }
            if(i > 18 && i < 28 && x >= 0 && y == 972)
            {
                for (int j = 0; j < boundaries[i].length; j++)
                {
                    if(j == 0)
                    {
                        boundaries[i][j] = x;
                    }
                    else if(j == 1)
                    {
                        boundaries[i][j] = y;
                    }
                }
                x -= 192;
            }
            if(i >= 28 && x == 0 && y >= 0)
            {
                for (int j = 0; j < boundaries[i].length; j++)
                {
                    if(j == 0)
                    {
                        boundaries[i][j] = x;
                    }
                    else if(j == 1)
                    {
                        boundaries[i][j] = y;
                    }
                }
                y -= 108;
            }
        }
    }

    public void addBoundary(int x, int y)
    {
        extraBoundaries[extraBoundaryCounter][0] = x;
        extraBoundaries[extraBoundaryCounter][1] = y;
        extraBoundaryCounter++;
    }

    public void addItem(String itemName, Item item)
    {
        itemsInRoom.put(itemName, item);
    }

    public Item removeItem(String name){
        return itemsInRoom.remove(name);
    }

    public String getItemStringsInRoom()
    {
        String temp = " ";
        if(isItemsInRoom())
        {
            temp = "\n" + "Items:";
            Set<String> items = itemsInRoom.keySet();
            for (String itemsInRoom: items)
            {
                temp += " " + itemsInRoom;
            }
        }
        return temp;
    }

    public boolean isItemsInRoom()
    {
        if(itemsInRoom.size() >= 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Item[] getAllItemsInRoom(){
        return itemsInRoom.values().toArray(new Item[0]);
    }

    public Item getItemByCoordinates(Coords coords){
        for (Item item : itemsInRoom.values()){
            if (!item.getCoords().equals(coords)){
                continue;
            }
            return item;
        }
        return null;
    }

    public String getTasksInRoom()
    {
        String temp = " ";
        if(isTasksInRoom())
        {
            temp = "\n" + "Tasks in the room:";
            Set<String> tasks = tasksInRoom.keySet();
            for(String tasksInRoom : tasks)
            {
                temp += " " + tasksInRoom;
            }
        }
        return temp;
    }

    public void removeTaskFromRoom(String task)
    {
        tasksInRoom.remove(task);
    }

    public void setRoomImage(String pathName)
    {
        File file = new File(pathName);
        roomImage = new Image(file.toURI().toString());
    }

    public Image getRoomImage()
    {
        return roomImage;
    }

    public void setRoomNeighbour(int exitNumber, Room room)
    {
        roomNeighbours.put(exitNumber,room);
    }

    public void setRoomExit(int x, int y)
    {
        exitLocations[exitCounter][0] = x;
        exitLocations[exitCounter][1] = y;
        exitCounter++;
        moveBoundary(x,y);
    }

    private void moveBoundary(int x, int y)
    {
        for (int i = 0; i < boundaries.length; i++)
        {
            if(boundaries[i][0] == x)
            {
                if(boundaries[i][1] == y)
                {
                    if(x == 0)
                    {
                        boundaries[i][0] -= 192;
                    }
                    else if(x == 1728)
                    {
                        boundaries[i][0] += 192;
                    }
                    else if(y == 0)
                    {
                        boundaries[i][1] -= 108;
                    }
                    else if(y == 972)
                    {
                        boundaries[i][1] += 108;
                    }
                }
            }
        }
    }

    public boolean isWall(int x, int y)
    {
        boolean result = false;

        for (int[] boundary : boundaries) {
            if (boundary[0] == x) {
                if (boundary[1] == y) {
                    result = true;
                    break;
                }
            }
        }
        for (int[] extraBoundary : extraBoundaries) {
            if (extraBoundary[0] == x) {
                if (extraBoundary[1] == y) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }
    
    public boolean isExit(int x, int y)
    {
        boolean result = false;

        for (int i = 0; i < exitCounter; i++)
        {
            if(exitLocations[i][0] == x)
            {
                if(exitLocations[i][1] == y)
                {
                    result = true;
                    break;
                }
            }
        }
        
        return result;
    }

    public int getExitNumber(int x,int y)
    {
        int returnInt = 0;

        for (int i = 0; i < exitCounter; i++)
        {
            if(exitLocations[i][0] == x)
            {
                if(exitLocations[i][1] == y)
                {
                    returnInt = i;
                    break;
                }
            }
        }

        return returnInt;
    }

    public Room getRoomFromExitNumber(int exitNumber)
    {
        Room result = null;

        result = roomNeighbours.get(exitNumber);

        return result;
    }

    public boolean isTasksInRoom()
    {
        return tasksInRoom.size() >= 1;
    }

    /*public String getNPCInRoom()
    {
        String temp = " ";
        if(isNPCInRoom())
        {
            temp = "\n";
            Set<String> NPCs = NPCsInRoom.keySet();
            for (String NPCsInRoom: NPCs)
            {
                temp += " " + NPCsInRoom + " is in the Room.";
            }

        }
        return temp;
    }*/

    public NPC getNPC()
    {
        return npcInRoom;
    }

    public boolean hasNPC()
    {
        return npcInRoom != null;
    }

    public Task getTask(String name)
    {
        return tasksInRoom.get(name);
    }

    public void addTask(String taskName,Task task)
    {
        tasksInRoom.put(taskName, task);
    }

    public void assignNPC(NPC npc)
    {
        npcInRoom = npc;
    }
}

