package commands;

import managers.CollectionManager;
import models.Route;

import java.io.BufferedReader;

/**
 * count_greater_dist Command class
 * @author Clown
 */
public final class CountGreaterDist extends Command{
    /**
     * Command apply method. Counts all objects in collection with greater distance than given
     * @param data input from console
     * @param console which console to use
     */
    public synchronized void apply(String[] data, BufferedReader console, Route route) {
        int cnt = 0;
        if (data.length >= 2) {
            try {
                long distance = Long.parseLong(data[1]);

                System.out.println(CollectionManager.
                        getInstance().
                        getCollection().
                        values().
                        stream().
                        filter(_route -> route.getDistance() > distance).
                        count());


            } catch (NumberFormatException e) {
                System.out.println("Cannot convert input to dist");
            }
        }
        else{
            System.out.println("Not enough args");
        }
    }
}
