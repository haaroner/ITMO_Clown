package Commands;

import java.io.BufferedReader;

/**
 * Exit command class.
 * @author Clown
 */
public final class Exit extends Command{
    public Exit() {
    }
    /**
     * Command apply method.This command stops the program
     * @param data input from console
     * @param console which console to use
     */
    public static void apply(String[] data, BufferedReader console) {
        System.out.println("Stopping the program...");
        System.exit(0);
    }
}
