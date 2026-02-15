package Managers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import java.io.Writer;
import java.io.FileWriter;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.opencsv.bean.CsvToBeanBuilder;

import Utility.Element;
import commands.*;
import models.Coordinates;
import models.Location;
import models.Route;

import java.io.*;

public class SystemManager {
    static BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
    static CollectionManager collectionManager = new CollectionManager();
        public SystemManager () {

        }

    static public void init(String[] fileNames) {
            if(fileNames.length < 3) {
                System.out.println("3 file`s names should be given \n exiting...");
                System.exit(1);
            }
            else {
                try {
                    LinkedHashMap<Integer, Element> data = getNewData(readRoutes(fileNames[0]), readCoordinates(fileNames[1]), readLocation(fileNames[2]));
                    for (Map.Entry<Integer, Element> entry : data.entrySet()) {
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

    static private LinkedHashMap<Integer, Element> getNewData(List<Route> routes, List<Coordinates> coordinates, List<Location> locations) {
            LinkedHashMap<Integer, Element> newData = new LinkedHashMap<Integer, Element>();
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
            try {
                if(type == Integer.class) return (T)Integer.valueOf(data);
                else if(type == long.class) return (T)Long.valueOf(data);
                else if(type == Float.class) return (T)Float.valueOf(data);
                else if(type == Double.class) return (T)Double.valueOf(data);
                else if(type == int.class) return (T)Integer.valueOf(data);
                else if(type == double.class) return (T)Double.valueOf(data);
                else if(type == String.class) return (T)data;
                else {
                    System.err.println("Unknown type used");
                }
            } catch (NumberFormatException e) {
                System.out.println("Cannot convert input to type[" + type.toString() + "]. Try again");
            }
            catch(Throwable e) {
                System.out.println("try again");
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
                    CommandType command = CommandType.valueOf(line[0]);

                    if(command.equals(CommandType.info))
                        Info.apply(line);
                    if(command.equals(CommandType.exit))
                        Exit.apply(line);
                    if(command.equals(CommandType.clear))
                        ClearCollection.apply(line);
                    if(command.equals(CommandType.show))
                        Show.apply(line);
                    if(command.equals(CommandType.save))
                        Save.apply(line);
                    if(command.equals(CommandType.insert))
                        Insert.apply(line);
                    //TODO Если у команды не те типы ключей - просто System.print и return;
                } catch (IllegalArgumentException e) {
                    System.out.println("command not found, try again");
                    System.out.println(line[0]);
                }
            } else
                System.out.println("command not found, try again");

    }
}
