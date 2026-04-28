package Commands;

import Managers.CollectionManager;
import Managers.ConsoleManager;
import Models.Route;
import Utility.Element;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Remove with greater ID Routes command class
 * @author Clown
 */
public final class RemoveGreater extends Command{
    private Integer id;

    /**
     * Command apply method. Removes all Routes with Higher ID
     * @param data input from console
     * @param console which console to use
     */
    public synchronized void apply(String[] data, BufferedReader console, Route route) {
            this.id = ConsoleManager.getInstance().ask("", Integer.class, console, false);
    }
}