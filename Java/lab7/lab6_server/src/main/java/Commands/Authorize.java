package commands;

import managers.DbManager;
import models.Route;

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
        if(data.length < 3)
            System.out.println("Not enough arg-s");
        else
        if(DbManager.getInstance().checkIsUserRegistered(data[1])) {
            if (DbManager.getInstance().checkIsUserAuth(data[1], data[2]))
                System.out.println("Authorized!");
            else
                System.out.println("Not authorized - check username and pswd");
        } else {
            System.out.println("No such user!");
        }

    }
}
