package Commands;

import Managers.CollectionManager;
import Managers.ConsoleManager;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Remove by key command class
 * @author Clown
 */
public class RemoveKey extends Command{
    public RemoveKey() {

    }

    /**
     * Command apply method. Removes by ID from collection
     * @param data input from console
     * @param console which console to use
     */
    public static void apply(String[] data, BufferedReader console) {

        try{
            Integer id = ConsoleManager.ask("ID: ", Integer.class, console);
            CollectionManager.removeItem(id);
        } catch (IOException e) {
        System.err.println("Cannot convert argument to id");
        }
    }
}