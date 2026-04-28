package Commands;

import Managers.SystemManager;
import Models.Route;

import java.io.BufferedReader;

/**
 * Save command class
 * @author Clown
 */
public final class Save extends Command{

    /**
     * Command apply method. Saves current collection to the file
     * @param data input from console
     * @param console which console to use
     */
    public synchronized void apply(String[] data, BufferedReader console, Route route) {
        SystemManager.getInstance().saveCollectionToFile();
        System.out.println("Collection saved");
    }
}
