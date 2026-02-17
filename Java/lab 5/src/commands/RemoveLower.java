package Commands;

import Managers.CollectionManager;
import Managers.ConsoleManager;
import Utility.Element;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Remove lower command class
 * @author Clown
 */
public class RemoveLower extends Command{
    public RemoveLower() {

    }

    /**
     * Command apply method. Similarly to Remove greater command removes every item with lower ID
     * @param data input from console
     * @param console which console to use
     */
    public static void apply(String[] data, BufferedReader console) {
        try {
            String input = ConsoleManager.ask("", String.class, console);
            Integer id = Integer.valueOf(input);
            for (Element element : CollectionManager.getCollection().values())
                if (element.getId() <= id)
                    CollectionManager.removeItem(element.getId());
        }
        catch (IOException e) {
            System.err.println("IOException occurred");
        }
        catch (NumberFormatException e){
            System.err.println("Cannot get ID from argument");
        }
    }
}