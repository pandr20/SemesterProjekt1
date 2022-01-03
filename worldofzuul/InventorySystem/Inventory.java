package InventorySystem;

import java.util.ArrayList;

public class Inventory {

    public Inventory(int maxSize){
        this.maxSize = maxSize;
    //This.maxSize(Field maxSize) = maxSize(Parameter) FieldmaxSize tilhoerer vores objekt.
    }

    private int maxSize; 
    private int totalWeight = 0;
    
    private final ArrayList<Item> items = new ArrayList<Item>(maxSize);
    //maxSize saettes til 10, og der laves en arraylist istedet for et array.
    //Arraylist manager det normale array for os.

    public boolean addItem (Item item){
        if (totalWeight + item.getSize() > maxSize){

            return false;
    //Når stoerrelsen af items>maengden af maxitems returneres ingenting(Går ud af metoden).
        }
        
        totalWeight += item.getSize();
        items.add(item);
        return true;
    //Vi laver en additem metode, som kan tilfoeje items til inventaret.
    }

    public Item removeItem(String name){
        for (Item item : items){ //For alle Item's af typen item i klassen items..
            if (!item.getName().equals(name)) { //If statement i "forloop" med not-operator
                continue;
            }
            items.remove(item); //Er if-statement sandt bliver det paagaeldende item fjernet
            totalWeight -= item.getSize(); //Derefter bliver stoerrelsen af det item trukket fra "totalWeight"
            return item; //det er typen item der bliver returneret
        }
        return null; //Hvis vores if-statement er falsk, returner intet.
    }

    public Item[] getInventory(){
        Item[] inventory = new Item[items.size()];
        inventory = items.toArray(inventory);
        return inventory;
    }

    public int getTotalWeight(){
        return totalWeight;
    }

    public Item getItem (int index){
        return items.get(index);
    //Tager et item fra vores inventar og returnerer det.
    }
}