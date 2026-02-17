package Commands;

import java.io.BufferedReader;

/**
 * Abstract command class
 * @author Clown
 */
public abstract class Command {

    public Command () {
    }

    /**
     * Command apply method
     * @param data input from console
     * @param console which console to use
     */
    public static void apply(String[] data, BufferedReader console){
        System.out.println("Command applied");
    }
}
