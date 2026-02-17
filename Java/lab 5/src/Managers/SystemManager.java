package Managers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import java.io.Writer;
import java.io.FileWriter;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.opencsv.bean.CsvToBeanBuilder;

import Utility.Element;
import Commands.*;
import Models.Coordinates;
import Models.Location;
import Models.Route;

import java.io.*;

public class SystemManager {
    private static BufferedReader defaultConsole = new BufferedReader(new InputStreamReader(System.in));
    private static Map<String, Class<? extends Command>> commands = new HashMap<>();
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
            try {
                ConsoleManager.startScan(defaultConsole);
            } catch (IOException e) {
                System.err.println("Error occurred while running program\nexiting...");
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
        try(Writer writer = new FileWriter("output.csv")) {
            StatefulBeanToCsv<Route> beanToCsv = new StatefulBeanToCsvBuilder<Route>(writer).build();
            beanToCsv.write((List<Route>) new ArrayList(CollectionManager.getCollection().values()));
        }
        catch (Exception e) {
            System.err.println("Error occured during file saving :(");
        }
    }
}
