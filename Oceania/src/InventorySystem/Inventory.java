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
        item.disable();
        totalWeight += item.getSize();
        items.add(item);
        return true;
    //Vi laver en additem metode, som kan tilfoeje items til inventaret.
    }

    public Item removeItem(int index){
        if (items.size() < 1) return null;
        if (index > items.size()) return null;
        Item item = items.remove(index);
        totalWeight -= item.getSize();
        return item;
    }
    //

    public Item removeItem(String name){
        for (Item item : items){
            if (!item.getName().equals(name))
                continue;
            items.remove(item);
            totalWeight -= item.getSize();
            return item;
        }
        return null;
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