package commands;

import Managers.CollectionManager;
import Managers.SystemManager;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;

public class Save extends Command{
    public Save() {
        super("save", "saves current collection");
    }

    public static void apply(String[] data) {
        SystemManager.saveCollectionToFile();
        System.out.println("Collection saved");
    }
}
