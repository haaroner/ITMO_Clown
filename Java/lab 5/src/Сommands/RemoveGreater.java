package Сommands;

import Managers.CollectionManager;
import Managers.SystemManager;
import Utility.Element;

import java.io.IOException;
import java.util.Map;

public class RemoveGreater extends Command{
    public RemoveGreater() {
        super("", "");
    }

    public static void apply(String[] data) {

        try {
            String input = SystemManager.ask("", String.class);
            Integer id = Integer.valueOf(input);
            for (Element element : CollectionManager.getCollection().values())
                if (element.getId() >= id)
                    CollectionManager.removeItem(element.getId());
        }
        catch (IOException e) {
            System.err.println("IOException occured");
        }
        catch (NumberFormatException e){
            System.err.println("Cannot get ID from argument");
        }
    }
}