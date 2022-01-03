package InventorySystem;

public enum ItemType {

    UNSPECIFIED("unspecified"), PLASTIC("plastic"), BIOLOGICAL("biological"), METAL("metal");

    private String typeString;

    ItemType(String typeString)
    {
        this.typeString = typeString;
    }

    public String toString()
    {
        return typeString;
    }

}

