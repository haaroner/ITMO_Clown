package Managers;

import Utility.Element;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class CollectionManager {
    private static ArrayList<Integer> ids = new ArrayList<Integer>();
    private static LinkedHashMap<Integer, Element> collection = new LinkedHashMap<>();
    public static ArrayList<Integer> getIds() {
        return ids;
    }

    public static LinkedHashMap<Integer, Element> getCollection() {
        return collection;
    }

    public static void setCollection(LinkedHashMap<Integer, Element> newCollection) {
        collection = validateCollection(newCollection);
    }

    public static void putItem(Integer id, Element newElement){
        if(newElement.validate())//TODO проверить что координаты и локация не совпадают с пред-ми
            collection.put(id, newElement);
    }

    public static Element getItem(Integer id) {
        return collection.get(id);
    }

    public static void removeItem(Integer id) {
        collection.remove(id);
    }

    private static LinkedHashMap<Integer, Element> validateCollection(LinkedHashMap<Integer, Element> newCollection){
        for (Integer id: newCollection.keySet()) {
            if(!newCollection.get(id).validate())
                newCollection.remove(id);
        }
        return newCollection;
    }
}
