package Commands;

import Managers.CollectionManager;
import Managers.SystemManager;
import Models.Route;

import java.io.BufferedReader;
import java.nio.file.Path;
import java.util.Map;

/**
 * Show command class
 * @author Clown
 */
public final class Info extends Command{

    /**
     * Command apply method. Displays all Routes in collection
     * @param data input from console
     * @param console which console to use
     */
    public void apply(String[] data, BufferedReader console, Route route) {
        System.out.println("Routes collection info: ");
        System.out.println("size: "+CollectionManager.getInstance().getLength());
        System.out.println("creation time: "+SystemManager.getInstance().getInitTime());
    }

}
