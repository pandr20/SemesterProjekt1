package TaskSystem;

import Room.Room;

public class Task
{
    private int rewardPoints;
    private int completedStepsCounter;
    private int completedBadStepsCounter;
    private String[] goodSteps;
    private String[] badSteps;
    private Room[] roomSteps;
    private Room[] badRoomSteps;
    private String[] completedSteps;
    private String taskDescription;
    private String taskName;
    private boolean isCompleted = false;
    private boolean isCompletedBad = false;
    private boolean rewardType = false;

    public Task(int rewardPoints, int goodSteps, int badSteps, String taskName, String taskDescription)
    {
        this.rewardPoints = rewardPoints;
        this.goodSteps = new String[goodSteps];
        this.badSteps = new String[badSteps];
        this.roomSteps = new Room[goodSteps];
        this.badRoomSteps = new Room[badSteps];
        this.taskDescription = taskDescription;
        this.taskName = taskName;
        this.completedSteps = new String[goodSteps];
        completedStepsCounter = 0;
    }

    public Task(int rewardPoints, int goodSteps, String taskName, String taskDescription)
    {
        this.rewardPoints = rewardPoints;
        this.goodSteps = new String[goodSteps];
        this.roomSteps = new Room[goodSteps];
        this.taskDescription = taskDescription;
        this.taskName = taskName;
        this.completedSteps = new String[goodSteps];
        completedStepsCounter = 0;
    }

    //Methods
    public void completedStep()
    {
        completedSteps[completedStepsCounter] = goodSteps[completedStepsCounter];
        completedStepsCounter++;
        System.out.println("Step completed");
        if(completedStepsCounter == goodSteps.length)
        {
            isCompleted = true;
        }
        if(!isCompleted)
        {
            System.out.println(getStep());
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

    public String taskStart()
    {
        String taskStartDescription = "";

        taskStartDescription += getTaskDescription() + "\n" + getStep();

        return taskStartDescription;
    }

    //Getters
    public int getRewardPoints()
    {
        if(isCompletedBad)
        {
            return -rewardPoints;
        }
        return rewardPoints;
    }

    public String getTaskDescription()
    {
        return taskDescription;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getStep()
    {
        String getStepString = "";
        getStepString += goodSteps[completedStepsCounter];
        if(badSteps != null)
        {
            getStepString += "\nBad step: " + badSteps[completedBadStepsCounter];
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

    public Room getRoomStep() { return roomSteps[completedStepsCounter]; }

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

    public void setRoomSteps(int stepNumber, Room room)
    {
        roomSteps[stepNumber] = room;
    }

    public void setRoomBadStep(int stepNumber, Room room)
    {
        badRoomSteps[stepNumber] = room;
    }

    @Override
    public String toString()
    {
        return taskName;
    }
}
