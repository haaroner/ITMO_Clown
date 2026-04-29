package managers;

import models.Route;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;


/**
 * Collection Manager class.
 * It is used to interact with collection of Routes
 * Collection is based on java.util.LinkedHashMap
 * @author Clown
 */
public final class CollectionManager {
    private final static CollectionManager INSTANCE = new CollectionManager();

    private CollectionManager() {

    }

    public static CollectionManager getInstance() {
        return INSTANCE;
    }

    private ConcurrentSkipListMap<Integer, Route> collection = new ConcurrentSkipListMap<>();

    /**
     *
     * @return LinkedHashMap<Integer, Route>
     */
    public ConcurrentSkipListMap<Integer, Route> getCollection() {
        return collection;
    }

    public int getLength() {
        return collection.size();
    }

    /**
     *
     * @param newCollection
     */
    public void setCollection(ConcurrentSkipListMap<Integer, Route> newCollection) {
        collection = validateCollection(newCollection);
    }

    /**
     * Puts new element to the collection if it passes validation
     * @param id of element
     * @param newElement new Route in collection
     */
    public synchronized void putItem(Integer id, Route newElement, String user, String pswd){
        if(newElement.validate()) {
            if(DbManager.getInstance().addWholeRoute(newElement, user, pswd))
                collection.put(newElement.getId(), newElement);
            else
                System.out.println("Error during adding to DB");
        }
    }

    public synchronized void removeItem(Integer id, String user, String pswd) {
        if(DbManager.getInstance().removeWholeRoute(id, user, pswd)) {
            System.out.println("removing object :" + id);
            collection.remove(id);
        }
        else
            System.out.println("Error while deleting from DB");
    }

    public synchronized void updateItem(Integer id, Route newElement, String user, String pswd) {
        if(newElement.validate()) {
            if(DbManager.getInstance().updateWholeRoute(id, newElement, user, pswd)) {
                collection.remove(id);
                collection.put(newElement.getId(), newElement);
            }
            else
                System.out.println("Error while updating");
        }
    }

    /**
     * Checks is there element in collection with specified ID
     * @param id
     * @return boolean
     */
    public boolean checkItem(Integer id) {
        return collection.containsKey(id);
    }

    public Set<Integer> getKeys(){
        return collection.keySet();
    }

    /**
     *
     * @param id
     * @return Route
     */
    public Route getItem(Integer id) {
        return collection.get(id);
    }

    /**
     * Removes item from collection with specified ID
     * @param id Element's ID
     */


    /**
     * Validates the whole collection by validating it's every element
     * @param newCollection
     * @return Collection with validated elements
     */
    private ConcurrentSkipListMap<Integer, Route> validateCollection(ConcurrentSkipListMap<Integer, Route> newCollection){
        for (Integer id: newCollection.keySet()) {
            if(!newCollection.get(id).validate())
                newCollection.remove(id);
        }
        return newCollection;
    }
}
