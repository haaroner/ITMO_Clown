package managers;

import models.Route;

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
    public void putItem(Integer id, Route newElement){
        if(newElement.validate())//TODO проверить что координаты и локация не совпадают с пред-ми
            collection.put(id, newElement);
    }

    /**
     * Checks is there element in collection with specified ID
     * @param id
     * @return boolean
     */
    public boolean checkItem(Integer id) {
        return collection.containsKey(id);
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
    public void removeItem(Integer id) {
        collection.remove(id);
    }

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
