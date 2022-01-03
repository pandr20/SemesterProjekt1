package NPC;

import TaskSystem.*;

public class NPC {

    private String name;
    private String speech;
    private int[][] position;
    private int positionCounter;
    private Task npcTask;

    public NPC (String name)
    {
        this.name = name;
        position = new int[2][2];
        positionCounter = 0;
        npcTask = null;
    }

    public void setSpeech(String speech)
    {
        this.speech = speech;
    }

    public void setPosition(int x, int y)
    {
        position[positionCounter][0] = x;
        position[positionCounter][1] = y;
        positionCounter++;
    }

    public void assignTask(Task task)
    {
        npcTask = task;
        task.setTaskGiver(this);
    }

    public void removeTask()
    {
        npcTask = null;
    }

    public boolean isPlayerInRange(int x, int y)
    {
        boolean result = false;

        for (int i = 0; i < positionCounter; i++)
        {
            if(position[i][0] == x)
            {
                if(position[i][1] == y)
                {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    public boolean hasTask()
    {
        return npcTask != null;
    }

    public String getName() { return name; }

    public String getSpeech() { return speech;}

    @Override
    public String toString() {
        return name;
    }
}
