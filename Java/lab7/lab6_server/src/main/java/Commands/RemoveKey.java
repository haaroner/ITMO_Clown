package Commands;

import Managers.CollectionManager;
import Managers.ConsoleManager;
import Models.Route;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Remove by key command class
 * @author Clown
 */
public final class RemoveKey extends Command{

    /**
     * Command apply method. Removes by ID from collection
     * @param data input from console
     * @param console which console to use
     */
    public synchronized void apply(String[] data, BufferedReader console, Route route) {
            Integer id = ConsoleManager.getInstance().ask("ID: ", Integer.class, console, false);
            CollectionManager.getInstance().removeItem(id);
    }
}