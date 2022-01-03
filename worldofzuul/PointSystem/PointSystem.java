package PointSystem;

public class PointSystem {

    private int point = 0;

    public int addPoint(int value)
    {
        point += value;
        return point;
    }

    @Override
    public String toString()
    {
        return "Your current points: " + point;
    }

    public int getPoint() { return point; }

    public void setPoint(int point) { this.point = point; }
}
