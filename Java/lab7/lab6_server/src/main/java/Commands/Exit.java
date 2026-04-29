package commands;

import models.Route;

import java.io.BufferedReader;

/**
 * Exit command class.
 * @author Clown
 */
public final class Exit extends Command{
    /**
     * Command apply method.This command stops the program
     * @param data input from console
     * @param console which console to use
     */
    public synchronized void apply(String[] data, BufferedReader console, Route route) {
        System.out.println("Stopping the program...");
        System.exit(0);
    }
}
