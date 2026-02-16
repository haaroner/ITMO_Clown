package Managers;

import Models.Route;
import Utility.Element;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class CollectionManager {
    private static ArrayList<Integer> ids = new ArrayList<Integer>();
    private static LinkedHashMap<Integer, Route> collection = new LinkedHashMap<>();
    public static ArrayList<Integer> getIds() {
        return ids;
    }

    public static LinkedHashMap<Integer, Route> getCollection() {
        return collection;
    }

    public static void setCollection(LinkedHashMap<Integer, Route> newCollection) {
        collection = validateCollection(newCollection);
    }

    public static Route getMaxByDistance() {
        double maxDistance = 0;
        Route maxRoute = null;
        for(Route element: collection.values()){
            if(element.getDistance() > maxDistance) {
                maxDistance = element.getDistance();
                maxRoute = element;
            }
        }
        return maxRoute;
    }

    public static void putItem(Integer id, Route newElement){
        if(newElement.validate())//TODO проверить что координаты и локация не совпадают с пред-ми
            collection.put(id, newElement);
    }

    public static boolean checkItem(Integer id) {
        return collection.containsKey(id);
    }

    public static Element getItem(Integer id) {
        return collection.get(id);
    }

    public static void removeItem(Integer id) {
        collection.remove(id);
    }

    private static LinkedHashMap<Integer, Route> validateCollection(LinkedHashMap<Integer, Route> newCollection){
        for (Integer id: newCollection.keySet()) {
            if(!newCollection.get(id).validate())
                newCollection.remove(id);
        }
        return newCollection;
    }
}
