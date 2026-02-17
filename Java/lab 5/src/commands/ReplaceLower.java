package Commands;

import Managers.CollectionManager;
import Managers.ConsoleManager;
import Models.Route;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

/**
 * replace if lower command class
 * @author Clown
 */
public class ReplaceLower extends Command{
    public ReplaceLower() {
    }

    /**
     * Command apply method. Compares Route in collection and new one by compareTo interface and keeps
     * in collection route which passed this comparison
     * @param data input from console
     * @param console which console to use
     */
    public static void apply(String[] data, BufferedReader console) {
        try {
            Integer id = ConsoleManager.ask("ID", Integer.class, console);
            Route newRoute = ConsoleManager.getNewRoute(console);
            Route cur = CollectionManager.getItem(id);
            if (Objects.nonNull(cur)) {
                if (cur.compareTo(newRoute) > 0) {
                    CollectionManager.putItem(cur.getId(), newRoute);
                    System.out.println("Route added");
                }
                else {
                    System.out.println("Collection didn't change");
                }
            }
        }
        catch (IOException e) {
            System.err.println("IOException occurred");
        }
        catch (NumberFormatException e){
            System.err.println("Cannot get ID from argument");
        }
    }
}