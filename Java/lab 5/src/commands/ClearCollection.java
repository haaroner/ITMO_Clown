package commands;

import Managers.CollectionManager;
import Utility.Element;

import java.util.Map;

public class ClearCollection extends Command{
    public ClearCollection() {
        super("clearCollection", "clears all data");
    }

    public static void apply(String[] data) {
        for (Map.Entry<Integer, Element> entry : CollectionManager.getCollection().entrySet()) {
            CollectionManager.removeItem(entry.getKey());
        }
        System.out.println("Cleared");
    }
}