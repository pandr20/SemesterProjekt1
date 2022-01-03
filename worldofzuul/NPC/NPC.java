package NPC;

public class NPC {

    private String name;
    private boolean forced;


    public NPC (String name, boolean forced)
    {
        this.name = name;
        this.forced = forced;
    }

    public NPC()
    {}

    public String victor()
    {
        String temp = "\"Hey, You! I am so glad you could make it down here. We need your help!"
                + "\n" + " Subsidies, which is support provided to the fishing industry to offset the costs of doing business,"
                + "\n" + " are a key driver of overfishing. "
                + "\n" + " Subsidies can lead to overcapacity of fishing vessels and skews the production "
                + "\n" + "  costs, so that fishing operations continue when they would otherwise not make economic sense. "
                + "\n" + " Today’s worldwide fishing fleet is estimated to be up to two-and-a-half times "
                + "\n" + "  the capacity needed to catch what we actually need. "
                + "\n" + " The United Nations 2030 Agenda for Sustainable Development has called for an end to harmful subsidies."
                + "\n" + " We need your help. We have been given fishermen subsidies and need it back, so we can stop overfishing"
                + "\n" + " Kenneth, the village fisherman, has received some of these subsidies. Please bring them back to me!";
        return temp;
    }

    public String sigurd()
    {
        String temp = "Golly, you're one pretty lady/man or everything in between or around or what ever.."
                + "\n" + " you know, doesn't matter, I don't judge either way - oh gosh, now I'm just embarrassed"
                + "\n" + "here; take my fishing rod, I don't even like fishing anyway ...";
        return temp;
    }

    public String kenneth() {
        String temp = "Kenneth: \"Hello there!\""
                + "\n" + "You: \"General Kenobi!!\""
                + "\n" + "Kenneth: \"Who's General Kenobi? You know what, it doesn't matter,"
                + "\n" + " I know who you are, and I know why you're here! You want my subsidies don't you?"
                + "\n" + " Well, if you want them back, you're gonna have to go get me a new fishing rod"
                + "\n" + " I've heard that Sigurd, whom is south-south-west of here, has one\"";
        return temp;
    }
    public boolean getForced() { return forced; }

    public String getName() { return name; }
}
