package Commands;

import Managers.CollectionManager;
import Models.Route;

import java.io.BufferedReader;
import java.util.Objects;

/**
 * Get Route with max distance command
 * @author Clown
 */
public final class MaxDistance extends Command{
    public MaxDistance() {

    }

    /**
     * Command apply method. Returns one of the Routes from collection with max distance
     * @param data input from console
     * @param console which console to use
     */
    public static void apply(String[] data, BufferedReader console) {
        if(data.length >= 1) {
            try{
                double maxDistance = 0;
                Route maxRoute = null;
                for(Route element: CollectionManager.getInstance().getCollection().values()){
                    if(element.getDistance() > maxDistance) {
                        maxDistance = element.getDistance();
                        maxRoute = element;
                    }
                }
                if(!Objects.isNull(maxRoute))
                    System.out.println(maxRoute);
                else
                    System.out.println("Empty collection");
            }
            catch (NumberFormatException e){
                System.err.println("Cant get distance from argument");
            }
        }
    }
}
