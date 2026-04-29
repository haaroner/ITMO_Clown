package commands;

import managers.CollectionManager;
import managers.ConsoleManager;
import models.Route;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

/**
 * Insert command class
 * @author Clown
 */
public final class Insert extends Command{

    /**
     * Command apply method. Inserts new Route to the Collection
     * @param data input from console
     * @param console which console to use
     */
    public synchronized void apply(String[] data, BufferedReader console, Route route) {
        try {//TODO перекинуть это в консоль
            if(Objects.isNull(route))
                route = ConsoleManager.getInstance().getNewRoute(console);
            if(route.validate())
                CollectionManager.getInstance().putItem(route.getId(), route);
            else
                System.out.println("New object have not passed validation test, check data conditions");
            //TODO сделать РУТ(тоже автоматически id), связать их, валидация, закинуть в коллекцию =>
            // победа
        }
        catch (IOException e) {
            System.err.println("Input exception");
        }
    }
}
