package TaskSystem;

import Room.Room;

public class TaskSystem
{
    private Task[] activeTasks;
    private int activeTaskCounter;
    private Task[] completedTask;
    private int completedTaskCounter;
    public Task mainTask;
    public Task testTrack2;

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
        mainTask = new Task(100,5,"main","Main task");
        mainTask.setStep(0, "First, speak with Victor"
                + "\n" + " Then go to the fishers market, which is east-north-east of here, and speak with Kenneth, to take back his subsidies");
        mainTask.setStep(1,"Next step: First, speak with Kenneth"
                + "\n" + " Then go to pier 2, to get a fishing rod from Sigurd");
        mainTask.setStep(2,"Next step: First, speak with Sigurd"
                + "\n" + " Then return to Kenneth in fish store, and give him the fishing rod");
        mainTask.setStep(3, "Kenneth: Thank you for this fishing rod, now I'm able to catch less fish,"
                + "\n" + " which is more in line with the needs of this fishing village"
                + "\n" + " here; take my subsidies, and give them back to Victor the Statesman"
                + "\n" + "Next step: Return to the west harbour");
        mainTask.setStep(4,"Victor the Statesman: \"Thanks for bringing back the subsidies,"
                + "\n" + " you've done the world a great service, by reducing overfishing in our village!\""
                + "\n" + "Next step: Go to pier 2, which is south-east of here, to take a boat ride out to the coral reef, to see how it fares");


        // Side Task; plastic waste
        testTrack2 = new Task(100,2, 1,"side","Test task 2");
        testTrack2.setStep(0,"Go to the beach");
        testTrack2.setStep(1,"Return to Bridge 1");
        testTrack2.setBadStep(0,"Test bad step");

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
        System.out.println(task.taskStart());
    }

    public void assignStepRoom(Task task, int stepNumber,Room room)//Kan på sigt ændres til assignStepNPC således man skal snakke med en NPC i stedet for, men da det ikke er lagt i spillet endnu bliver det rummet
    {
        task.setRoomSteps(stepNumber, room);
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

    public String showTaskStep(Task task)
    {
        String temp = "";
        Task tempTask = null;
        for (int i = 0; i < activeTaskCounter ; i++)
        {
            if(activeTasks[i] == task)
            {
                tempTask = activeTasks[i];
                break;
            }
        }
        temp += tempTask.getStep();
        return temp;
    }

    public int getActiveTaskCounter()
    {
        return activeTaskCounter;
    }
}
