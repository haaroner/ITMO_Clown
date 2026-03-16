package Main;

import Commands.NetCommand;
import Managers.NetManager;
import Managers.SystemManager;

import java.io.IOException;

/**
 * This is the main class. Program's entry point
 * @author Clown
 */
public class Main {
    public static void main(String[] args) throws IOException {
        NetManager.getInstance().initServer(12345, 1000);
        SystemManager.getInstance().init(args);
    }
}