package Commands;

public enum CommandWord
{
    GO("go"), QUIT("quit"), HELP("help"),UNKNOWN("?"),
    SHOW("show"), ACCEPT("accept"), TAKE("take"),
    DROP("drop"), INV("inventory"), INSPECT("inspect"), HELLO("hello"), MAP("map");

    private String commandString;
    
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    public String toString()
    {
        return commandString;
    }
}
