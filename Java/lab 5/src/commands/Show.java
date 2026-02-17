package Commands;

import Managers.CollectionManager;
import Models.Route;

import java.io.BufferedReader;
import java.util.Map;

/**
 * Show command class
 * @author Clown
 */
public class Show extends Command{

    public Show() {

    }

    /**
     * Command apply method. Displays all Routes in collection
     * @param data input from console
     * @param console which console to use
     */
    public static void apply(String[] data, BufferedReader console) {
        System.out.println("Collection: ");
        for (Map.Entry<Integer, Route> entry : CollectionManager.getCollection().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().toString());
        }
        if(CollectionManager.getCollection().isEmpty())
            System.out.println("Collection is empty");
    }

}
