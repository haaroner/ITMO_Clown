package Managers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.*;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import java.io.Writer;
import java.io.FileWriter;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.opencsv.bean.CsvToBeanBuilder;

import Utility.Element;
import Сommands.*;
import Models.Coordinates;
import Models.Location;
import Models.Route;

import java.io.*;

public class SystemManager {
    private static BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
    private static Map<String, Class<? extends Command>> commands = new HashMap<>();
    static CollectionManager collectionManager = new CollectionManager();
    public SystemManager () {

    }


    static public void init(String[] fileNames) {

//        commands.put(Info.getName(), Info.class);
//        commands.put(Insert.getName(), Insert.class);
//        commands.put(Save.getName(), Exit.class);
//        commands.put(Show.getName(), Show.class);
//        commands.put(Exit.getName(), Exit.class);
//        commands.put(ClearCollection.getName(), ClearCollection.class);
            if(fileNames.length < 3) {
                System.out.println("3 file`s names should be given \n exiting...");
                System.exit(1);
            }
            else {
                try {
                    LinkedHashMap<Integer, Route> data = getNewData(readRoutes(fileNames[0]), readCoordinates(fileNames[1]), readLocation(fileNames[2]));
                    for (Map.Entry<Integer, Route> entry : data.entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue().toString());

                    }
                    collectionManager.setCollection(data);
                }
                catch (NullPointerException e) {
                    System.err.println("Some of data might be damaged.");
                }
            }
            //TODO вставить парсинг Route и распихать по id-шникам распаршенные подразделы
        }
    static private List<Coordinates> readCoordinates(String file) {
            try(BufferedReader fileReader = Files.newBufferedReader(Paths.get(file))) {//TODO выкинуть в отдельную папку
                try {
                    List<Coordinates> rawData = new CsvToBeanBuilder<Coordinates>(fileReader)
                            .withType(Coordinates.class)
                            .withIgnoreLeadingWhiteSpace(true)
                            .withSeparator(',')
                            .build()
                            .parse();
                    for(Coordinates coordinates: rawData)
                        coordinates.updateMaxId();
                    return rawData;
                }
                catch (RuntimeException e){
                    System.err.println("Problem in parsing. File :" + file + ", Line: ");
                    return null;
                }
            }
            catch (FileNotFoundException e) {
                System.err.println("file not found");
                return null;//TODO check nonull in init!!
            }
            catch (IOException e) {
                System.err.println("file not found");
                return null;//TODO check nonull in init!!
            }
            catch (Throwable e) {
                System.err.println("unexpected exception occured");
                return null;
            }
        }

    static private List<Location> readLocation(String file) {
        try(BufferedReader fileReader = Files.newBufferedReader(Paths.get(file))) {//TODO выкинуть в отдельную папку
            try {
                List<Location> rawData= new CsvToBeanBuilder<Location>(fileReader)
                        .withType(Location.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .withSeparator(',')
                        .build()
                        .parse();
                for(Location location: rawData)
                    location.updateMaxId();
                return rawData;
            }
             catch (RuntimeException e){
                System.err.println("Problem in parsing. File :" + file + ", Line: ");
                return null;
            }
        }
        catch (FileNotFoundException e) {
            System.err.println("file not found");
            return null;//TODO check nonull in init!!
        }
        catch (IOException e) {
            System.err.println("file not found");
            return null;//TODO check nonull in init!!
        }
        catch (Throwable e) {
            System.err.println("unexpected exception occured");
            return null;
        }
    }

    static private List<Route> readRoutes(String file) {
        try(BufferedReader fileReader = Files.newBufferedReader(Paths.get(file))) {//TODO выкинуть в отдельную папку
           try {
               List<Route> rawData = new CsvToBeanBuilder<Route>(fileReader)
                       .withType(Route.class)
                       .withIgnoreLeadingWhiteSpace(true)
                       .withSeparator(',')
                       .build()
                       .parse();

               for(Route route: rawData)
                   route.updateMaxId();
               return rawData;
           }
           catch (RuntimeException e){
               System.err.println("Problem in parsing. File :" + file + ", Line: ");
               return null;
           }
        }
        catch (FileNotFoundException e) {
            System.err.println("file not found");
            return null;//TODO check nonull in init!!
        }
        catch (IOException e) {
            System.err.println("file not found");
            return null;//TODO check nonull in init!!
        }
        catch (Throwable e) {
            System.err.println("unexpected exception occured");
            return null;
        }
    }

    static private LinkedHashMap<Integer, Route> getNewData(List<Route> routes, List<Coordinates> coordinates, List<Location> locations) {
            LinkedHashMap<Integer, Route> newData = new LinkedHashMap<Integer, Route>();
            for(Route route: routes) {
                route.setFrom((Location) getById(route.getFromId(), locations));
                route.setTo((Location) getById(route.getToId(), locations));
                route.setCoordinates((Coordinates) getById(route.getCoordinatesId(), coordinates));
                newData.put(route.getId(), route);
            }
            return newData;//TODO объеденить с текущей коллекцией
    }

    static private Element getById(Integer id, List<? extends Element> data) {
            for(Element element: data) {
                if(element.getId().equals(id))
                    return element;
            }
            return null;
    }
        /*private LinkedHashMap<Integer, Element> scanFile(String fileName) {

        }*/

    static public void saveCollectionToFile() {
        try(Writer writer = new FileWriter("src/output.csv")) {
            StatefulBeanToCsv<Route> beanToCsv = new StatefulBeanToCsvBuilder<Route>(writer).build();
            beanToCsv.write((List<Route>) new ArrayList(CollectionManager.getCollection().values()));
        }
        catch (Exception e) {
            System.err.println("Error occured during file saving :(");
        }

    }

    public static <T> T ask(String line, Class<T> type) throws IOException {
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

    static public void startScan () throws IOException {
        while(true) {
            scanNewCommand();
        }
    }
    static private /*CommandType*/void scanNewCommand() throws IOException {
            System.out.println("type new command:");
            String[] line = console.readLine().split(" ");
            if (line.length > 0) {
                try {
                    Class<? extends Command> newCommand = CommandType.valueOf(line[0]).getClazz();
                    Command instance = newCommand.getDeclaredConstructor().newInstance();
                    Method method = instance.getClass().getMethod("apply", String[].class);
                    Object[] args = {line};
                    method.invoke(instance, args);

                    //TODO Если у команды не те типы ключей - просто System.print и return;
                } catch (IllegalArgumentException e) {
                    System.out.println("command not found, try again");
                    System.out.println(line[0]);
                } catch (InstantiationException e) {
                    System.out.println("command not found, try again");
                    System.out.println(line[0]);
                } catch (Throwable e) {
                    System.out.println("command not found, try again");
                    System.out.println(line[0]);
                }
            } else
                System.out.println("command not found, try again");

    }
}
