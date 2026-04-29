package commands;

import models.Route;

import java.io.BufferedReader;

/**
 * Save command class
 * @author Clown
 */
public final class Save extends Command{
    private final static Save INSTANCE = new Save();

    private Save() {

    }

    public static Save getInstance() {
        return INSTANCE;
    }

    /**
     * Command apply method. Saves current collection to the file
     * @param data input from console
     * @param console which console to use
     */
    public synchronized void apply(String[] data, BufferedReader console, Route route) {
        System.out.println("Save command is deprecated since lab7");
    }
}
