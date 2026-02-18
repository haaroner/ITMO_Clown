package Commands;

import java.io.BufferedReader;

/**
 * Info command class.
 */
public final class Info extends Command{
    public Info() {
    }
    /**
     * Command apply method. Display all command's names and descriptions
     * @param data input from console
     * @param console which console to use
     */
    public static void apply(String[] data, BufferedReader console) {
        if(data.length > 1)
            System.out.println("This command doesn't need any arguments,  they`ll be ignored");

        for(CommandType type: CommandType.values())
            System.out.println(type.getName() + ": " + type.getDescription()+";");

    }
}
