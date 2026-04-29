package managers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.*;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import java.io.Writer;
import java.io.FileWriter;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.opencsv.bean.CsvToBeanBuilder;

import utility.Element;
import models.Coordinates;
import models.Location;
import models.Route;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.*;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * System manager class.
 * Operates with all system (core) functions
 * There is otsylka in author section
 * @author GPT
 */
public final class SystemManager {
    private final static SystemManager INSTANCE = new SystemManager();
    private BufferedReader defaultConsole = new BufferedReader(new InputStreamReader(System.in));
    private String routesFile, locationFile, coordinatesFile;
    private ArrayList<Path> openFiles = new ArrayList<>();
    private String pepper;
    private SystemManager () {

    }

    public static SystemManager getInstance() {
        return INSTANCE;
    }

    /**
     * Inits program and gets data from files
     * @param fileNames of data
     */
    public void init(String[] fileNames) throws IOException {
        pepper = ConsoleManager.getInstance().ask("PSWD pepper: ", String.class, defaultConsole, false);
                try {
                    List<List<?>> fromDb = DbManager.getInstance().loadCollectionFromDb();
                    ConcurrentSkipListMap<Integer, Route> data = getNewData((List<Route>) fromDb.get(0), (List<Coordinates>) fromDb.get(1), (List<Location>) fromDb.get(2));

                    for (Map.Entry<Integer, Route> entry : data.entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue().toString());

                    }
                    CollectionManager.getInstance().setCollection(data);
                }
                catch (NullPointerException e) {
                    System.err.println("Some of data might be damaged. Starting with empty data base\n");
                    e.printStackTrace();
                }
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!");
        //DbManager.getInstance().addItem(CollectionManager.getInstance().getItem(1).getCoordinates(), DbTableType.coordinates);
            NetManager.getInstance().initServer(12345);

            try {
                ConsoleManager.getInstance().startScan(defaultConsole);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error occurred while running program\nexiting...");
            }

            //TODO вставить парсинг Route и распихать по id-шникам распаршенные подразделы
        }

    private <T extends Element>  List<T> readFromFile(Class<T> tClass, String file) {
        try(BufferedReader fileReader = Files.newBufferedReader(Paths.get(file))) {//TODO выкинуть в отдельную папку
            try {
                List<T> rawData = new CsvToBeanBuilder<T>(fileReader)
                        .withType(tClass)
                        .withIgnoreLeadingWhiteSpace(true)
                        .withSeparator(',')
                        .build()
                        .parse();
                ArrayList<Integer> idList = new ArrayList<>();
                for(T t: rawData) {
                    if(idList.contains(t.getId()))
                        System.err.println("File ["+file+"] contains elements with similar ID, previous elements with this ID: " + t.getId() + " will be replaced by last one");
                    idList.add(t.getId());
                    t.updateMaxId();
                }


                return rawData;
            }
            catch (RuntimeException e){
                System.out.println("Problem in parsing. File :" + file + ", Line: ");
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

    public String getPepper() {
        return pepper;
    }

    /**
     * Linkes all parsed data to one collection by IDs of elements
     * @param routes - all routes to link with loc-ns and coord-s
     * @param coordinates coordinates to link with their routes by ID
     * @param locations locations to link with their routes by ID
     * @return
     */
    private ConcurrentSkipListMap<Integer, Route> getNewData(List<Route> routes, List<Coordinates> coordinates, List<Location> locations) {
        ConcurrentSkipListMap<Integer, Route> newData = new ConcurrentSkipListMap<>();
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
    private Element getById(Integer id, List<? extends Element> data) {
            for(Element element: data) {
                if(element.getId().equals(id))
                    return element;
            }
            return null;
    }

    public <T extends Element> void saveToFile (List<T> list, String file) {
        try {
            Path path = Paths.get(file);
            if((!Files.exists(path)) || SystemManager.getInstance().checkWritable(file)) {
                Writer writer = new FileWriter(file);
                StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer).build();
                beanToCsv.write(list);
                writer.close();
            }
            else{
                System.err.println("Cannot write to this file: " + file);
            }

        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            System.err.println("Error occured during file saving :(");
        }
    }

    public ArrayList<Location> getLocations() {
        ArrayList<Location> data = new ArrayList<>();
        for(Route route: CollectionManager.getInstance().getCollection().values()) {
            data.add(route.getTo());
            data.add(route.getFrom());
        }
        return data;
    }

    public List<Coordinates> getCoordinates() {
        List<Coordinates> data = new ArrayList<>();
        for(Route route: CollectionManager.getInstance().getCollection().values()) {
            data.add(route.getCoordinates());
        }
        return data;
    }
    /**
     * Saves collection to the file
     * Saving is made with similar file structure and column naming
     */
    public void saveCollectionToFile() {
        try {
            String[] files = {routesFile, coordinatesFile, locationFile};
            saveToFile((List<Route>) new ArrayList(CollectionManager.getInstance().getCollection().values()), routesFile);
            saveToFile((List<Location>) new ArrayList(getLocations()), locationFile);
            saveToFile((List<Location>) new ArrayList(getCoordinates()), coordinatesFile);
        }
        catch (Exception e) {
            System.err.println("Error occured during file saving :(");
        }
    }

    public FileTime getInitTime() {
        try {
            Path path = Paths.get(routesFile);
            return (FileTime) Files.getAttribute(path, "creationTime");
        } catch (IOException e) {
            System.out.println("file reading exception occurred");
            return null;
        }
    }

    public BufferedReader openFile(String file) {
        try{
            Path newPath = Paths.get(file);
            if(!Files.exists(newPath)){
                System.err.println("No such file: " + file);
                System.err.flush();
                return null;
            }

            if(!SystemManager.getInstance().checkReadable(file)){
                System.err.println("File is not readable: " + file);
                System.err.flush();
                return null;
            }

            for(Path filePath: openFiles) {
                if(Files.isSameFile(filePath, newPath))
                {
                    System.err.println("This file has already been opened: " + file );
                    System.err.flush();
                    return null;
                }
            }
            openFiles.add(newPath);
            return new BufferedReader(new FileReader(file));
        } catch (IOException e) {
            System.err.println("no such file found");
            System.err.flush();
            return null;
        }
    }

    public boolean checkReadable(String file) {
        Path path = Paths.get(file);
        if(!Files.exists(path))
            return false;
        return Files.isReadable(path);
    }

    public boolean checkWritable(String file) {
        Path path = Paths.get(file);
        if(!Files.exists(path))
            return true;
        return Files.isWritable(path);
    }

    public void closeFile(String file) {
        Path newPath = Paths.get(file);
        if(!Files.exists(newPath)){
            return ;
        }
        Iterator<Path> iterator = openFiles.iterator();
        while(iterator.hasNext()){
            Path curPath = iterator.next();
            try {
                if(Files.isSameFile(curPath, newPath)) {
                    iterator.remove();
                    return;
                    }

            } catch (IOException e) {
                System.err.println("unexpected err");
            }
        }
    }

    public boolean isFileOpen() {
        return !openFiles.isEmpty();
    }
}
