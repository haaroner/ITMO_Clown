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
public final class ReplaceLower extends Command{

    /**
     * Command apply method. Compares Route in collection and new one by compareTo interface and keeps
     * in collection route which passed this comparison
     * @param data input from console
     * @param console which console to use
     */
    public synchronized void apply(String[] data, BufferedReader console, Route route) {
        try {
            Integer id = ConsoleManager.getInstance().ask("ID", Integer.class, console, false);
            Route newRoute = ConsoleManager.getInstance().getNewRoute(console);
            Route cur = CollectionManager.getInstance().getItem(id);
            if (Objects.nonNull(cur)) {
                if (cur.compareTo(newRoute) > 0) {
                    CollectionManager.getInstance().putItem(cur.getId(), newRoute);
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