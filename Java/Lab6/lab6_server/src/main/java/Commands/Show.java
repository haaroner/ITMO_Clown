package Commands;

import Managers.CollectionManager;
import Models.Route;

import java.io.BufferedReader;
import java.util.Map;

/**
 * Show command class
 * @author Clown
 */
public final class Show extends Command{

    public Show() {

    }

    /**
     * Command apply method. Displays all Routes in collection
     * @param data input from console
     * @param console which console to use
     */
    public static void apply(String[] data, BufferedReader console) {
        System.out.println("Routes Collection: ");
        for (Map.Entry<Integer, Route> entry : CollectionManager.getInstance().getCollection().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().toString());
        }
        if(CollectionManager.getInstance().getCollection().isEmpty())
            System.out.println("Collection is empty");
    }

}
