package Managers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.bean.ColumnPositionMappingStrategy;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import java.io.Writer;
import java.io.FileWriter;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.CsvToBean;

import Utility.Element;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
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

    static public void init(String[] fileName) {
           // if(fileName.length > 0)
             //   scanFile(fileName[0]);
            // for(Coordinates coordinate: readCoordinates())
            //System.out.println(coordinate.toString());

            //for(Location location: readLocation())
              //  System.out.println(location.toString());

           // for(Route route: readRoutes())
            //    System.out.println(route.toString());
            LinkedHashMap<Integer, Element> data = getNewData(readRoutes(), readCoordinates(), readLocation());
            for (Map.Entry<Integer, Element> entry : data.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue().toString());

            }
            collectionManager.setCollection(data);
            //TODO вставить парсинг Route и распихать по id-шникам распаршенные подразделы
        }
    static private List<Coordinates> readCoordinates() {
            try(BufferedReader fileReader = Files.newBufferedReader(Paths.get("src/coordinates.csv"))) {//TODO выкинуть в отдельную папку
                List<Coordinates> rawData= new CsvToBeanBuilder<Coordinates>(fileReader)
                        .withType(Coordinates.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .withSeparator(',')
                        .build()
                        .parse();
                return rawData;
            }
            catch (FileNotFoundException e) {
                System.err.println("file not found");
                return null;//TODO check nonull in init!!
            }
            catch (IOException e) {
                System.err.println("file not found");
                return null;//TODO check nonull in init!!
            }
        }

    static private List<Location> readLocation() {
        try(BufferedReader fileReader = Files.newBufferedReader(Paths.get("src/location.csv"))) {//TODO выкинуть в отдельную папку
            List<Location> rawData= new CsvToBeanBuilder<Location>(fileReader)
                    .withType(Location.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSeparator(',')
                    .build()
                    .parse();
            return rawData;
        }
        catch (FileNotFoundException e) {
            System.err.println("file not found");
            return null;//TODO check nonull in init!!
        }
        catch (IOException e) {
            System.err.println("file not found");
            return null;//TODO check nonull in init!!
        }
    }

    static private List<Route> readRoutes() {
        try(BufferedReader fileReader = Files.newBufferedReader(Paths.get("src/data.csv"))) {//TODO выкинуть в отдельную папку
            List<Route> rawData= new CsvToBeanBuilder<Route>(fileReader)
                    .withType(Route.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSeparator(',')
                    .build()
                    .parse();
            return rawData;
        }
        catch (FileNotFoundException e) {
            System.err.println("file not found");
            return null;//TODO check nonull in init!!
        }
        catch (IOException e) {
            System.err.println("file not found");
            return null;//TODO check nonull in init!!
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
                    if(command.equals(CommandType.getCollection))
                        GetCollection.apply(line);
                    if(command.equals(CommandType.save))
                        Save.apply(line);
                    //TODO Если у команды не те типы ключей - просто System.print и return;
                } catch (IllegalArgumentException e) {
                    System.out.println("command not found, try again");
                    System.out.println(line[0]);
                }
            } else
                System.out.println("command not found, try again");

    }
}
