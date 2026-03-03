package Commands;

import Managers.CollectionManager;
import Managers.ConsoleManager;
import Utility.Element;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Remove with greater ID Routes command class
 * @author Clown
 */
public final class RemoveGreater extends Command{
    public RemoveGreater() {

    }

    /**
     * Command apply method. Removes all Routes with Higher ID
     * @param data input from console
     * @param console which console to use
     */
    public static void apply(String[] data, BufferedReader console) {
        try {
            String input = ConsoleManager.ask("", String.class, console, false);
            Integer id = Integer.valueOf(input);
            for (Element element : CollectionManager.getCollection().values())
                if (element.getId() >= id)
                    CollectionManager.removeItem(element.getId());
        }
        catch (NumberFormatException e){
            System.err.println("Cannot get ID from argument");
        }
    }
}