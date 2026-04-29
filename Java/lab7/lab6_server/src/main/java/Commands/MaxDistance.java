package commands;

import managers.CollectionManager;
import models.Route;

import java.io.BufferedReader;
import java.util.Comparator;

/**
 * Get Route with max distance command
 * @author Clown
 */
public final class MaxDistance extends Command{

    /**
     * Command apply method. Returns one of the Routes from collection with max distance
     * @param data input from console
     * @param console which console to use
     */
    public synchronized void apply(String[] data, BufferedReader console, Route route) {
        if(data.length >= 1) {
            try{
                if(CollectionManager.getInstance().getLength() > 0)
                    System.out.println(CollectionManager.getInstance().getCollection().values().stream().max(Comparator.naturalOrder()).get());
                else
                    System.out.println("Empty collection");
            }
            catch (NumberFormatException e){
                System.err.println("Cant get distance from argument");
            }
        }
    }
}
