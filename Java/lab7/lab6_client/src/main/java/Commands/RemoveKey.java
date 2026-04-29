package commands;

import managers.ConsoleManager;
import models.Route;

import java.io.BufferedReader;

/**
 * Remove by key command class
 * @author Clown
 */
public final class RemoveKey extends Command{
    private Integer id;
    /**
     * Command apply method. Removes by ID from collection
     * @param data input from console
     * @param console which console to use
     */
    public synchronized void apply(String[] data, BufferedReader console, Route route) {
            this.id = ConsoleManager.getInstance().ask("ID: ", Integer.class, console, false);
            //CollectionManager.getInstance().removeItem(id);
    }
}