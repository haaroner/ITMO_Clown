package commands;

import managers.CollectionManager;
import models.Route;

import java.io.BufferedReader;
import java.util.List;

/**
 * Remove with greater ID Routes command class
 * @author Clown
 */
public final class RemoveGreater extends Command{
    private Integer id;
    /**
     * Command apply method. Removes all Routes with Higher ID
     * @param data input from console
     * @param console which console to use
     */
    public synchronized void apply(String[] data, BufferedReader console, Route route) {
        try {
            List<Route> toRemove= CollectionManager.
                    getInstance().
                    getCollection().
                    values().
                    stream().
                    filter(entry -> entry.getId() >= this.id).toList();

            toRemove.stream().forEach(entry -> CollectionManager.getInstance().removeItem(entry.getId(), data[data.length-2], data[data.length-1]));
        }
        catch (NumberFormatException e){
            System.err.println("Cannot get ID from argument");
        }
    }
}