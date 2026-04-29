package commands;

import managers.CollectionManager;
import managers.SystemManager;
import models.Route;

import java.io.BufferedReader;

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
    public synchronized void apply(String[] data, BufferedReader console, Route route) {
        System.out.println("Routes collection info: ");
        System.out.println("size: "+CollectionManager.getInstance().getLength());
        System.out.println("creation time: "+SystemManager.getInstance().getInitTime());
    }

}
