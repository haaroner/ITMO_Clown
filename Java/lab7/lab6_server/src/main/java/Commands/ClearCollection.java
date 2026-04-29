package commands;

import managers.CollectionManager;
import models.Route;

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
            CollectionManager.getInstance().removeItem(entry.getKey(), data[data.length-2], data[data.length-1]);
        }
        System.out.println("Cleared");
    }
}