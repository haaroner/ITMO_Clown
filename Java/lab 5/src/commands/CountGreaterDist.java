package Commands;

import Managers.CollectionManager;
import Models.Route;

import java.io.BufferedReader;

/**
 * count_greater_dist Command class
 * @author Clown
 */
public final class CountGreaterDist extends Command{
    public CountGreaterDist() {

    }
    /**
     * Command apply method. Counts all objects in collection with greater distance than given
     * @param data input from console
     * @param console which console to use
     */
    public static void apply(String[] data, BufferedReader console) {
        int cnt = 0;
        if (data.length >= 2) {
            try {
                long distance = Long.parseLong(data[1]);
                for (Route route : CollectionManager.getCollection().values())
                    if (route.getDistance() > distance) {
                        cnt++;
                    }
                System.out.println(cnt);
            } catch (NumberFormatException e) {
                System.out.println("Cannot convert input to dist");
            }
        }
        else{
            System.out.println("Not enough args");
        }
    }
}
