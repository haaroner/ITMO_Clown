package Commands;

import Managers.DbManager;
import Models.Route;

import java.io.BufferedReader;

/**
 * Register command class.
 */
public final class Register extends Command{
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
            if(DbManager.getInstance().checkIsUserRegistered(data[1]))
                System.out.println("Username is already used registered");
            else {
                DbManager.getInstance().registerUser(data[1], data[2]);
                System.out.println("Registration complete!");
            }

    }
}
