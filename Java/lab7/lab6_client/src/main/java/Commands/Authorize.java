package Commands;

import Managers.NetManager;
import Managers.SystemManager;
import Models.Route;

import java.io.BufferedReader;

/**
 * Register command class.
 */
public final class Authorize extends Command{
    private String[] data;
    /**
     * Command apply method. Display all command's names and descriptions
     * @param data input from console
     * @param console which console to use
     */
    public synchronized void apply(String[] data, BufferedReader console, Route route) {
        this.data = data;
        if(data.length >= 3)
            NetManager.getInstance().setUser(data[1], data[2]);
    }
}
