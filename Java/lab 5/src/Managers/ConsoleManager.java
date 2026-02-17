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

public class ConsoleManager {
    public ConsoleManager() {

    }

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

    static public void startScan(BufferedReader console) throws IOException {
        while(true) {
            if (!scanNewCommand(console)) break;
        }
    }
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
