package InventorySystem;

public enum ItemType {

    PLASTIC("plastic"), BIOLOGICAL("biological"), METAL("metal");

    public String typeString;

    ItemType(String typeString)
    {
        this.typeString = typeString;
    }

    public String toString()
    {
        return typeString;
    }

}

