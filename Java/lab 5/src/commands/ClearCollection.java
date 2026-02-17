package Commands;

import Managers.CollectionManager;
import Models.Route;

import java.io.BufferedReader;
import java.util.Map;

/**
 * Class of clear function
 * @author Clown
 */
public final class ClearCollection extends Command{
    public ClearCollection() {

    }

    /**
     * Command apply method. Clears collection
     * @param data input from console
     * @param console which console to use
     */
    public static void apply(String[] data, BufferedReader console) {
        for (Map.Entry<Integer, Route> entry : CollectionManager.getCollection().entrySet()) {
            CollectionManager.removeItem(entry.getKey());
        }
        System.out.println("Cleared");
    }
}