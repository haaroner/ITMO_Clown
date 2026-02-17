package Commands;

import Managers.CollectionManager;
import Managers.ConsoleManager;
import Models.Route;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Update collection element command class
 * @author Clown
 */
public class Update extends Command{
    public Update() {

    }

    /**
     * Command apply method. If there is Route in Collection with specified ID updates element
     * @param data input from console
     * @param console which console to use
     */
    public static void apply(String[] data, BufferedReader console) {
        if (data.length >= 2) {
            try {//TODO перекинуть это в консоль
                Integer id = Integer.valueOf(data[1]);
                if(!CollectionManager.checkItem(id)) {
                    System.out.println("No such element in collection");
                    return;
                }
                Route route = ConsoleManager.getNewRoute(console);
                if (!route.validate())
                    System.out.println("New object have not passed validation test, check data conditions");
                else {
                    CollectionManager.removeItem(id);
                    CollectionManager.putItem(route.getId(), route);
                }
                //TODO сделать РУТ(тоже автоматически id), связать их, валидация, закинуть в коллекцию =>
                // победа
            } catch (IOException e) {
                System.err.println("Input exception");
            }
            catch (NumberFormatException e) {
                System.err.println("Cannot get id from the argument: " + data[1]);
            }
        }
        else {
            System.out.println("ID arguement should be given");
        }
    }
}
