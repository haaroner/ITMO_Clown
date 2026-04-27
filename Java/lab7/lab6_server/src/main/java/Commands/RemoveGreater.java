package Commands;

import Managers.CollectionManager;
import Managers.ConsoleManager;
import Models.Route;
import Utility.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Remove with greater ID Routes command class
 * @author Clown
 */
public final class RemoveGreater extends Command{
    private Integer id;
    /**
     * Command apply method. Removes all Routes with Higher ID
     * @param data input from console
     * @param console which console to use
     */
    public void apply(String[] data, BufferedReader console, Route route) {
        try {
            List<Route> toRemove= CollectionManager.
                    getInstance().
                    getCollection().
                    values().
                    stream().
                    filter(entry -> entry.getId() >= this.id).toList();

            toRemove.stream().forEach(entry -> CollectionManager.getInstance().removeItem(entry.getId()));
        }
        catch (NumberFormatException e){
            System.err.println("Cannot get ID from argument");
        }
    }
}