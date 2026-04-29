package commands;

import managers.ConsoleManager;
import models.Route;

import java.io.BufferedReader;

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
    public synchronized void apply(String[] data, BufferedReader console, Route route) {
        this.id = ConsoleManager.getInstance().ask("", Integer.class, console, false);
    }
}