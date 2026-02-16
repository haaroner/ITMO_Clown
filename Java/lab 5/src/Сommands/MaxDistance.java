package Сommands;

import Managers.CollectionManager;
import Models.Route;

import java.util.Objects;

public class MaxDistance extends Command{
    public MaxDistance() {
        super("max_by_distance", "...");
    }

    public static void apply(String[] data) {
        if(data.length >= 1) {
            try{
                Route route = CollectionManager.getMaxByDistance();
                if(!Objects.isNull(route))
                    System.out.println(route);
                else
                    System.out.println("Empty collection");
            }
            catch (NumberFormatException e){
                System.err.println("Cant get distance from argument");
            }
        }
    }
}
