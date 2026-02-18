package Managers;

import Builders.CoordinatesBuilder;
import Builders.LocationBuilder;
import Builders.RouteBuilder;
import Models.Coordinates;
import Models.Location;
import Models.Route;
import Commands.Command;
import Commands.CommandType;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Console and file interact class.
 * Based on java.io.BufferReader
 * @author Clown
 */
public final class ConsoleManager {
    public ConsoleManager() {

    }

    /**
     * Function for commands to ask certain parameter with certain prompt line and certain response type
     * If input type is not convertible to needed type prompt would be remade
     * @param line - prompt for user
     * @param type - Needed type
     * @param console - Which console to use (script file input or console)
     * @return data of needed type
     * @param <T> Needed type
     * @throws IOException In case of problems with console input
     */
    public static <T> T ask(String line, Class<T> type, BufferedReader console) throws IOException {
        System.out.println(line);
        while (true) {
            String data = console.readLine();
            if(!data.isEmpty()) {
                try {
                    if (type == Integer.class) return (T) Integer.valueOf(data);
                    else if (type == long.class) return (T) Long.valueOf(data);
                    else if (type == Float.class) return (T) Float.valueOf(data);
                    else if (type == Double.class) return (T) Double.valueOf(data);
                    else if (type == int.class) return (T) Integer.valueOf(data);
                    else if (type == double.class) return (T) Double.valueOf(data);
                    else if (type == String.class) return (T) data;
                    else {
                        System.err.println("Unknown type used");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Cannot convert input to type[" + type.toString() + "]. Try again");
                } catch (Throwable e) {
                    System.out.println("try again");
                }
            }
            else {
                System.out.println("Empty argument is not allowed here");
            }
        }
    }

    /**
     * Starts scanning specified console for new commands until null (occurs in the end of files)
     * @param console which console to use
     * @throws IOException in case of IO problems
     */
    static public void startScan(BufferedReader console) throws IOException {
        while(true) {
            if (!scanNewCommand(console)) break;
        }
    }

    /**
     * Scans new command from specified console and invokes it's apply method
     * It is based on making new .class from commands name written in {@link Commands.CommandType}
     * After it makes new instance of this class and invokes method apply
     * @param console which to use
     * @return true/false. True - command invoked, false - No commands remaining (EOF)
     * @throws IOException in case of IO problems
     */
    private static boolean scanNewCommand(BufferedReader console) throws IOException {
        System.out.println("type new command:");
        String input = console.readLine();
        if(Objects.nonNull(input)) {
            String[] line = input.split(" ");
            if (line.length > 0) {
                try {
                    Class<? extends Command> newCommand = CommandType.valueOf(line[0]).getClazz();
                    Command instance = newCommand.getDeclaredConstructor().newInstance();
                    Method method = instance.getClass().getMethod("apply", String[].class, BufferedReader.class);
                    Object[] args = {line, console};
                    method.invoke(instance, args);

                    //TODO Если у команды не те типы ключей - просто System.print и return;
                } catch (IllegalArgumentException e) {
                    System.out.println("Illegal argument");
                    System.out.println(line[0]);
                } catch (InstantiationException e) {
                    System.out.println("command not found, try again");
                    System.out.println(line[0]);
                } catch (Throwable e) {
                    System.out.println("Unexpected error occurred, try again");
                    System.out.println(line[0]);
                }
            } else
                System.out.println("command not found, try again");
        return true;
        }
        else {
            return false;
        }
    }

    /**
     * Asks all necessary data and forms new Route for collection
     * @param console which to use
     * @return new Route
     * @throws IOException in case of IO problems
     */
    public static Route getNewRoute(BufferedReader console) throws IOException {
        System.out.println("from: ");
        Location from = new LocationBuilder()
                .setId()
                .setX(ConsoleManager.ask("X", long.class, console))
                .setY(ConsoleManager.ask("Y", Float.class, console))
                .setZ(ConsoleManager.ask("Z", Double.class, console))
                .build();
        System.out.println("to: ");
        Location to = new LocationBuilder()
                .setId()
                .setX(ConsoleManager.ask("X", long.class, console))
                .setY(ConsoleManager.ask("Y", Float.class, console))
                .setZ(ConsoleManager.ask("Z", Double.class, console))
                .build();
        System.out.println("Coordinates: ");
        Coordinates coordinates = new CoordinatesBuilder()
                .setId()
                .setX(ConsoleManager.ask("X", int.class, console))
                .setY(ConsoleManager.ask("Y", double.class, console))
                .build();

        return new RouteBuilder()
                .setId()
                .setName(ConsoleManager.ask("Route name ", String.class, console))
                .setCoordinates(coordinates)
                .setDate()
                .setFrom(from)
                .setTo(to)
                .setDistance(ConsoleManager.ask("distance ", long.class, console))
                .build();
    }
}
