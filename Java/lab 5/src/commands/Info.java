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

    public Info() {

    }

    /**
     * Command apply method. Displays all Routes in collection
     * @param data input from console
     * @param console which console to use
     */
    public static void apply(String[] data, BufferedReader console) {
        System.out.println("Routes collection info: ");
        System.out.println("size: "+CollectionManager.getLength());
        System.out.println("creation time: "+SystemManager.getInitTime());
    }

}
