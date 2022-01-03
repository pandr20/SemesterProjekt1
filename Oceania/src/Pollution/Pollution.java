package Pollution;

public class Pollution {


    private double pollution;

    public Pollution(double pollution) {
        this.pollution = pollution;
    }

    public void addPollution(int value)
    {
        pollution += value;
    }

    @Override
    public String toString()
    {
        return "Your current points: " + pollution;
    }

    public double getPollution() { return pollution; }

}
