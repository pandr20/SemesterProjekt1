package TaskSystem;

import Room.Room;
import NPC.NPC;

public class TaskSystem
{
    private Task[] activeTasks;
    private int activeTaskCounter;
    private Task[] completedTask;
    private int completedTaskCounter;
    public Task mainTask;

    public TaskSystem(int amountOfQuests)
    {
        activeTasks = new Task[amountOfQuests];
        completedTask = new Task[amountOfQuests];
        activeTaskCounter = 0;
        completedTaskCounter = 0;
        createTasks();
    }

    private void createTasks()
    {
        // Main Task:
        mainTask = new Task(49,5,"main","\"Hey, You! I am so glad you could make it down here. We need your help!"
                + "\n" + " Subsidies, which is support provided to the fishing industry to offset the costs of doing business, are a key driver of overfishing. "
                + "\n" + " Subsidies can lead to overcapacity of fishing vessels and skews the production " +
                "costs, so that fishing operations continue when they would otherwise not make economic sense. "
                + "\n" + " Today’s worldwide fishing fleet is estimated to be up to two-and-a-half times "  +
                "the capacity needed to catch what we actually need. "
                + "\n" + " The United Nations 2030 Agenda for Sustainable Development has called for an end to harmful subsidies."
                + "\n" + " We need your help. We have been giving fishermen subsidies and need it back, so we can stop overfishing"
                + "\n" + " Kenneth, the village fisherman, has received some of these subsidies. Please bring them back to me!"
                + "\n" + " He has a little shop up on the east side of the shopping street."
                + "\n" + "\n" + "By the way, there are a lot of trash around the city - Could you go pick it up, and throw it in the garbage disposal please?");
        mainTask.setStep(0, "Kenneth: \"Hello there!\""
                + "\n" + "You: \n General Kenobi!!\""
                + "\n" + "Kenneth: \n Who's General Kenobi? You know what, it doesn't matter,"
                + "\n" + " I know who you are, and I know why you're here! You want my subsidies don't you?"
                + "\n" + " Well, if you want them back, you're gonna have to go get me a new fishing rod"
                + "\n" + " I've heard that Sigurd, whom is on Pier 2, has one\"");
        mainTask.setStep(1,"Golly, you're one pretty lady/man or everything in between or around or what ever.."
                + "\n" + " you know, doesn't matter, I don't judge either way - oh gosh, now I'm just embarrassed"
                + "\n" + "here; take my fishing rod, I don't even like fishing anyway ...");
        mainTask.setStep(2,"Kenneth: Thank you for this fishing rod, now I'm able to catch less fish,"
                + "\n" + " which is more in line with the needs of this fishing village"
                + "\n" + " here; take my subsidies, and give them back to Victor the Statesman");
        mainTask.setStep(3,"Victor the Statesman: \"Thanks for bringing back the subsidies,"
                + "\n" + " you've done the world a great service, by reducing overfishing in our village!\""
                + "\n" + "Next step: Go to pier 2, which is south-east of here, to take a boat ride out to the coral reef, to see how it fares");


    }

    public void moveCompletedTask(Task task)
    {
        for (int i = 0; i < activeTaskCounter; i++)
        {
            if(activeTasks[i] == task)
            {
                completedTask[completedTaskCounter] = activeTasks[i];
                completedTaskCounter++;
                for (int j = activeTaskCounter<1 ? i + 1 : i; j < activeTaskCounter; j++)
                {
                    activeTasks[i] = activeTasks[j];
                    activeTasks[j] = null;
                }
                activeTaskCounter--;
            }
        }
    }

    public void addTask(Task task)
    {
        activeTasks[activeTaskCounter] = task;
        activeTaskCounter++;
    }

    public void assignStepNPC(Task task, int stepNumber,NPC npc)//Kan på sigt ændres til assignStepNPC således man skal snakke med en NPC i stedet for, men da det ikke er lagt i spillet endnu bliver det rummet
    {
        task.setRoomSteps(stepNumber, npc);
    }

    public void assignBadStepRoom(Task task, int stepNumber, Room room)
    {
        task.setRoomBadStep(stepNumber, room);
    }

    public String getCompletedTask()
    {
        String completedTasksPrint = "";

        for (int i = 0; i < completedTaskCounter; i++)
        {
            if(completedTask[i] != null)
            {
                completedTasksPrint += completedTask[i];
            }
        }
        return completedTasksPrint;
    }

    @Override
    public String toString()
    {
        String temp = "";
        if(activeTaskCounter != 0)
        {
            temp = "Active tasks: ";
            for(Task task: activeTasks)
            {
                if(task != null)
                {
                    temp += task + " ";
                }
            }
        }
        else
        {
            temp = "No active tasks right now";
        }
        return temp;
    }

    public Task getActiveTask()
    {
        return activeTasks[0];
    }

    public String getActiveTaskName()
    {
        String temp = "";

        return activeTasks[completedTaskCounter].getTaskName();
    }

    public Task getTask(String taskName)
    {
        Task temp = null;

        for (int i = 0; i < activeTaskCounter; i++)
        {
            if(activeTasks[i].getTaskName().equals(taskName))
            {
                temp = activeTasks[i];
                break;
            }
        }
        return temp;
    }

    public boolean isATask(String task)
    {
        for (int i = 0; i < activeTasks.length; i++)
        {
            if(activeTasks[i].getTaskName() == null)
            {
                return false;
            }
            else if (task.equals(activeTasks[i].getTaskName()))
            {
                return true;
            }
        }
        return false;
    }


    public int getActiveTaskCounter()
    {
        return activeTaskCounter;
    }

}
