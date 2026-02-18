package Commands;

import Managers.CollectionManager;
import Models.Route;

import java.io.BufferedReader;

/**
 * Remove by distance class
 * @author Clown
 */
public final class RemoveByDistance extends Command{
    public RemoveByDistance() {

    }

    /**
     * Command apply method. Removes only one Route with specified distance
     * @param data input from console
     * @param console which console to use
     */
    public static void apply(String[] data, BufferedReader console) {
        if (data.length >= 2) {
            try {
                long distance = Long.parseLong(data[1]);
                for (Route route : CollectionManager.getCollection().values())
                    if (route.getDistance() == distance) {
                        CollectionManager.removeItem(route.getId());
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