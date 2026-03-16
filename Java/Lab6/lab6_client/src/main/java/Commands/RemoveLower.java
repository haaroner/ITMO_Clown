package Commands;

import Managers.CollectionManager;
import Managers.ConsoleManager;
import Models.Route;
import Utility.Element;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Remove lower command class
 * @author Clown
 */
public final class RemoveLower extends Command{

    /**
     * Command apply method. Similarly to Remove greater command removes every item with lower ID
     * @param data input from console
     * @param console which console to use
     */
    public void apply(String[] data, BufferedReader console, Route route) {
        try {
            String input = ConsoleManager.getInstance().ask("", String.class, console, false);
            Integer id = Integer.valueOf(input);
            for (Element element : CollectionManager.getInstance().getCollection().values())
                if (element.getId() <= id)
                    CollectionManager.getInstance().removeItem(element.getId());
        }
        catch (NumberFormatException e){
            System.err.println("Cannot get ID from argument");
        }
    }
}