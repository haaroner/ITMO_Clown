package commands;

import Managers.CollectionManager;
import Utility.Element;

import java.util.Map;

public class Show extends Command{
    public Show() {
        super("show", "displays whole collection");
    }

    public static void apply(String[] data) {
        System.out.println("Collection: ");
        for (Map.Entry<Integer, Element> entry : CollectionManager.getCollection().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().toString());
        }
        if(CollectionManager.getCollection().isEmpty())
            System.out.println("Collection is empty");
    }
}
