package Сommands;

import Builders.CoordinatesBuilder;
import Builders.LocationBuilder;
import Builders.RouteBuilder;
import Managers.CollectionManager;
import Managers.SystemManager;
import Models.Coordinates;
import Models.Location;
import Models.Route;

import java.io.IOException;

public class Update extends Command{
    public Update() {
        super("update", "updates element in collection");
    }

    public static void apply(String[] data) {
        if (data.length >= 2) {
            try {//TODO перекинуть это в консоль
                Integer id = Integer.valueOf(data[1]);
                if(!CollectionManager.checkItem(id)) {
                    System.out.println("No such element in collection");
                    return;
                }
                System.out.println("from: ");
                Location from = new LocationBuilder()
                        .setId()
                        .setX(SystemManager.ask("X", long.class))
                        .setY(SystemManager.ask("Y", Float.class))
                        .setZ(SystemManager.ask("Z", Double.class))
                        .build();
                System.out.println("to: ");
                Location to = new LocationBuilder()
                        .setId()
                        .setX(SystemManager.ask("X", long.class))
                        .setY(SystemManager.ask("Y", Float.class))
                        .setZ(SystemManager.ask("Z", Double.class))
                        .build();
                System.out.println("Coordinates: ");
                Coordinates coordinates = new CoordinatesBuilder()
                        .setId()
                        .setX(SystemManager.ask("X", int.class))
                        .setY(SystemManager.ask("Y", double.class))
                        .build();

                Route route = new RouteBuilder()
                        .setId()
                        .setName(SystemManager.ask("Route name ", String.class))
                        .setCoordinates(coordinates)
                        .setDate()
                        .setFrom(from)
                        .setTo(to)
                        .setDistance(SystemManager.ask("distance ", long.class))
                        .build();
                if (!route.validate())
                    System.out.println("New object have not passed validation test, check data conditions");
                else
                    CollectionManager.putItem(id, route);
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
