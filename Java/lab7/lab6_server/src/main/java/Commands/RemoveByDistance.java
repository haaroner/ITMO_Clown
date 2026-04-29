package commands;

import managers.CollectionManager;
import models.Route;

import java.io.BufferedReader;
import java.util.Map;
import java.util.Optional;

/**
 * Remove by distance class
 * @author Clown
 */
public final class RemoveByDistance extends Command{

    /**
     * Command apply method. Removes only one Route with specified distance
     * @param data input from console
     * @param console which console to use
     */
    public synchronized void apply(String[] data, BufferedReader console, Route route) {
        if (data.length >= 2) {
            try {
                long distance = Long.parseLong(data[1]);

                Optional<Map.Entry<Integer, Route>> obj = CollectionManager.
                        getInstance().
                        getCollection().
                        entrySet().
                        stream().
                        filter(entry -> entry.getValue().getDistance() == distance).
                        findFirst();
                if (obj.isEmpty()){
                    System.out.println("No such Route");
                }
                else {
                    CollectionManager.getInstance().removeItem(obj.get().getKey(), data[data.length-2], data[data.length-1]);
                }
            } catch (NumberFormatException e) {
                System.out.println("Cannot convert to dist");
            }
        }
        else{
            System.out.println("Not enough args");
        }
    }
}