package Commands;

import Managers.CollectionManager;
import Managers.ConsoleManager;
import Models.Route;
import Utility.Element;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Remove lower command class
 * @author Clown
 */
public final class RemoveLower extends Command{
    private Integer id;
    /**
     * Command apply method. Similarly to Remove greater command removes every item with lower ID
     * @param data input from console
     * @param console which console to use
     */
    public void apply(String[] data, BufferedReader console, Route route) {
        this.id = ConsoleManager.getInstance().ask("", Integer.class, console, false);
    }
}