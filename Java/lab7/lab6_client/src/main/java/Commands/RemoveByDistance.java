package commands;

import managers.CollectionManager;
import models.Route;

import java.io.BufferedReader;

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
                for (Route _route : CollectionManager.getInstance().getCollection().values())
                    if (_route.getDistance() == distance) {
                        CollectionManager.getInstance().removeItem(_route.getId());
                        return;
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