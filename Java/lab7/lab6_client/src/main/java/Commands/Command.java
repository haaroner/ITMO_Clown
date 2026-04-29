package commands;

import models.Route;

import java.io.BufferedReader;
import java.io.Serializable;

/**
 * Abstract command class
 * @author Clown
 */
public class Command implements Serializable {
    private final static Command INSTANCE = new Command();
    protected Command () {
    }

    public static Command getInstance() {
        return INSTANCE;
    }
    /**
     * Command apply method
     * @param data input from console
     * @param console which console to use
     */
    public synchronized void apply(String[] data, BufferedReader console, Route route) {
        System.out.println("Command applied");
    }
}
