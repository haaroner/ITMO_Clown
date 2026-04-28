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
    /**
     * Command apply method. Clears collection
     * @param data input from console
     * @param console which console to use
     */
    public synchronized void apply(String[] data, BufferedReader console, Route route) {
        for (Map.Entry<Integer, Route> entry : CollectionManager.getInstance().getCollection().entrySet()) {
            CollectionManager.getInstance().removeItem(entry.getKey());
        }
        System.out.println("Cleared");
    }
}