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
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.*;

/**
 * System manager class.
 * Operates with all system (core) functions
 * There is otsylka in author section
 * @author GPT
 */
public final class SystemManager {
    private static BufferedReader defaultConsole = new BufferedReader(new InputStreamReader(System.in));
    private static CollectionManager collectionManager = new CollectionManager();
    private static String routesFile, locationFile, coordinatesFile;
    public SystemManager () {

    }

    /**
     * Inits program and gets data from files
     * @param fileNames of data
     */
    static public void init(String[] fileNames) {
            if(fileNames.length < 3) {
                System.out.println("3 file`s names should be given \n exiting...");
                System.exit(1);
            }
            else {
                try {
                    routesFile = fileNames[0];
                    coordinatesFile = fileNames[1];
                    locationFile = fileNames[2];
                    LinkedHashMap<Integer, Route> data = getNewData(readFromFile(Route.class, routesFile),
                            readFromFile(Coordinates.class, coordinatesFile),
                            readFromFile(Location.class, locationFile));

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

    static private <T extends Element>  List<T> readFromFile(Class<T> tClass, String file) {
        try(BufferedReader fileReader = Files.newBufferedReader(Paths.get(file))) {//TODO выкинуть в отдельную папку
            try {
                List<T> rawData = new CsvToBeanBuilder<T>(fileReader)
                        .withType(tClass)
                        .withIgnoreLeadingWhiteSpace(true)
                        .withSeparator(',')
                        .build()
                        .parse();

                for(T t: rawData)
                    t.updateMaxId();
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

    /**
     * Linkes all parsed data to one collection by IDs of elements
     * @param routes - all routes to link with loc-ns and coord-s
     * @param coordinates coordinates to link with their routes by ID
     * @param locations locations to link with their routes by ID
     * @return
     */
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

    /**
     * Returns element by its ID
     * @param id
     * @param data
     * @return Route
     */
    static private Element getById(Integer id, List<? extends Element> data) {
            for(Element element: data) {
                if(element.getId().equals(id))
                    return element;
            }
            return null;
    }

    static public <T extends Element> void saveToFile (List<T> list, String file) {
        try {
            Writer writer = new FileWriter(file);
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer).build();
            beanToCsv.write(list);
            writer.close();

        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            System.err.println("Error occured during file saving :(");
        }
    }

    public static ArrayList<Location> getLocations() {
        ArrayList<Location> data = new ArrayList<>();
        for(Route route: CollectionManager.getCollection().values()) {
            data.add(route.getTo());
            data.add(route.getFrom());
        }
        return data;
    }

    public static List<Coordinates> getCoordinates() {
        List<Coordinates> data = new ArrayList<>();
        for(Route route: CollectionManager.getCollection().values()) {
            data.add(route.getCoordinates());
        }
        return data;
    }
    /**
     * Saves collection to the file
     * Saving is made with similar file structure and column naming
     */
    static public void saveCollectionToFile() {
        try {
            String[] files = {routesFile, coordinatesFile, locationFile};
            saveToFile((List<Route>) new ArrayList(CollectionManager.getCollection().values()), routesFile);
            saveToFile((List<Location>) new ArrayList(getLocations()), locationFile);
            saveToFile((List<Location>) new ArrayList(getCoordinates()), coordinatesFile);
        }
        catch (Exception e) {
            System.err.println("Error occured during file saving :(");
        }
    }
}
