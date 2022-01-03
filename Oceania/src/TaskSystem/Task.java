package TaskSystem;

import Room.Room;
import NPC.*;

public class Task
{
    private int rewardPoints;
    private int completedStepsCounter;
    private int completedBadStepsCounter;
    private String[] goodSteps;
    private String[] badSteps;
    private NPC[] npcSteps;
    private Room[] badRoomSteps;
    private String[] completedSteps;
    private String taskDescription;
    private String taskName;
    private NPC taskGiver;
    private boolean isCompleted = false;
    private boolean isCompletedBad = false;
    private boolean rewardType = false;

    public Task(int rewardPoints, int goodSteps, int badSteps, String taskName, String taskDescription)
    {
        this(rewardPoints, goodSteps, taskName, taskDescription);
        this.badSteps = new String[badSteps];
        this.badRoomSteps = new Room[badSteps];
    }

    public Task(int rewardPoints, int goodSteps, String taskName, String taskDescription)
    {
        this.rewardPoints = rewardPoints;
        this.goodSteps = new String[goodSteps];
        this.npcSteps = new NPC[goodSteps];
        this.taskDescription = taskDescription;
        this.taskName = taskName;
        this.completedSteps = new String[goodSteps];
        completedStepsCounter = 0;
        taskGiver = null;
    }

    //Methods
    public void completedStep()
    {
        completedSteps[completedStepsCounter] = goodSteps[completedStepsCounter];
        completedStepsCounter++;
        System.out.println("Step completed");
        System.out.println(completedStepsCounter);
        if(completedStepsCounter == goodSteps.length - 1)
        {
            System.out.println("Completed task");
            isCompleted = true;
        }
    }

    public void completedBadStep()
    {
        completedSteps[completedStepsCounter] = badSteps[completedBadStepsCounter];
        completedBadStepsCounter++;
        System.out.println("Bad step completed");
        if(completedBadStepsCounter == badSteps.length)
        {
            isCompletedBad = true;
        }
    }

    public void completedTask()
    {

    }

    public String taskStart()
    {
        String taskStartDescription = "";

        taskStartDescription += getTaskDescription();

        return taskStartDescription;
    }

    //Getters
    public int getRewardPoints()
    {
        if(isCompletedBad)
        {
            return rewardPoints;
        }
        return -rewardPoints;
    }


    public String getTaskDescription()
    {
        return taskDescription;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getStep(NPC npc)
    {
        String getStepString = "";
        if(npc == npcSteps[completedStepsCounter])
        {
            getStepString += goodSteps[completedStepsCounter];
            if(badSteps != null)
            {
                getStepString += "\nBad step: " + badSteps[completedBadStepsCounter];
            }
        }
        else if(npc == npcSteps[completedStepsCounter - 1])
        {
            getStepString += goodSteps[completedStepsCounter - 1];
            if(badSteps != null)
            {
                getStepString += "\nBad step: " + badSteps[completedBadStepsCounter - 1];
            }
        }
        return getStepString;
    }

    public String[] getCompletedSteps()
    {
        return completedSteps;
    }

    public int getCompletedStepsCounter()
    {
        return completedStepsCounter;
    }

    public NPC getNPCSteps(NPC npc)
    {
        return npcSteps[completedStepsCounter];
    }

    public boolean isTaskGiver(NPC npc)
    {
        return npc == taskGiver;
    }

    public boolean isNPCLastStep(NPC npc)
    {
        return npc == npcSteps[completedStepsCounter - 1];
    }

    public boolean isNPCInTask(NPC npc)
    {
        boolean result = false;

        if(completedStepsCounter < 1)
        {
            if(npc == npcSteps[completedStepsCounter])
            {
                result = true;
            }
            else if(isTaskGiver(npc))
            {
                result = true;
            }
        }
        else {
            if(npc == npcSteps[completedStepsCounter])
            {
                result = true;
            }
            else if(npc == npcSteps[completedStepsCounter - 1])
            {
                result = true;
            }
        }

        return result;
    }

    public boolean isRewardType()
    {
        return rewardType;
    }

    public boolean isCompleted()
    {
        return isCompleted;
    }

    public boolean isCompletedBad()
    {
        return isCompletedBad;
    }

    //public Room getRoomStep() { return npcSteps[completedStepsCounter]; }

    public Room getRoomBadStep()
    {
        return badRoomSteps[completedBadStepsCounter];
    }

    //Setters
    public void setStep(int stepNumber, String stepDescription)
    {
        goodSteps[stepNumber] = stepDescription;
    }

    public void setBadStep(int stepNumber, String stepDescription)
    {
        badSteps[stepNumber] = stepDescription;
    }

    public void setRoomSteps(int stepNumber, NPC npc)
    {
        npcSteps[stepNumber] = npc;
    }

    public void setRoomBadStep(int stepNumber, Room room)
    {
        badRoomSteps[stepNumber] = room;
    }

    public void setTaskGiver(NPC npc)
    {
        taskGiver = npc;
    }

    /*@Override
    public String toString()
    {
        return taskName;
    }*/
}
