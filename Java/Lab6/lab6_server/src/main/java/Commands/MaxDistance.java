package Commands;

import Managers.CollectionManager;
import Models.Route;

import java.io.BufferedReader;
import java.util.Comparator;
import java.util.Objects;

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
    public void apply(String[] data, BufferedReader console, Route route) {
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
