package Сommands;

import Managers.SystemManager;

public class Save extends Command{
    public Save() {
        super("save", "saves current collection");
    }

    public static void apply(String[] data) {
        SystemManager.saveCollectionToFile();
        System.out.println("Collection saved");
    }
}
