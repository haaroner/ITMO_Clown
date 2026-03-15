package Commands;

import Managers.SystemManager;

import java.io.BufferedReader;

/**
 * Save command class
 * @author Clown
 */
public final class Save extends Command{
    public Save() {

    }

    /**
     * Command apply method. Saves current collection to the file
     * @param data input from console
     * @param console which console to use
     */
    public static void apply(String[] data, BufferedReader console) {
        SystemManager.getInstance().saveCollectionToFile();
        System.out.println("Collection saved");
    }
}
