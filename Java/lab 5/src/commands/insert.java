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
    public Insert(){
    }

    /**
     * Command apply method. Inserts new Route to the Collection
     * @param data input from console
     * @param console which console to use
     */
    public static void apply(String[] data, BufferedReader console) {
        try {//TODO перекинуть это в консоль
            System.out.println("from: ");
            Route route = ConsoleManager.getNewRoute(console);
            if(route.validate())
                CollectionManager.putItem(route.getId(), route);
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
