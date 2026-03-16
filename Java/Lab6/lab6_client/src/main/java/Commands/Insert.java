package Commands;

import Managers.CollectionManager;
import Managers.ConsoleManager;
import Models.Route;

import java.io.BufferedReader;
import java.io.IOException;

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
    public void apply(String[] data, BufferedReader console) {
        Route route;
        try {//TODO перекинуть это в консоль
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
