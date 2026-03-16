package Commands;

import Managers.SystemManager;
import Models.Route;

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
    public void apply(String[] data, BufferedReader console, Route route) {
        SystemManager.getInstance().saveCollectionToFile();
        System.out.println("Collection saved");
    }
}
