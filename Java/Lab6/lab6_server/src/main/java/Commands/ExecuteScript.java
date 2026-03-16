package Commands;

import Managers.ConsoleManager;
import Managers.SystemManager;
import Models.Route;

import java.io.*;
import java.util.Objects;

/**
 * Command class for executing file script
 * @author Clown
 */
public final class ExecuteScript extends Command{

    /**
     * Command apply method. Executes script
     * @param data input from console
     * @param console which console to use
     */
    public void apply(String[] data, BufferedReader console, Route route) {
        if(data.length < 2) {
            System.out.println("not enough arguments");
            return;
        }
        else{
            System.out.println("Executing script...\nSerial output will be disabled, only errors will appear");
            try{
                PrintStream origin = System.out;
                System.setOut(new PrintStream(new java.io.OutputStream() {
                    public void write(int b){}
                }));
                BufferedReader scriptReader = SystemManager.getInstance().openFile(data[1]);
                if(Objects.nonNull(scriptReader)) {
                    ConsoleManager.getInstance().startScan(scriptReader);
                    System.out.println("File executed");
                    scriptReader.close();
                }
                System.setOut(origin);
                SystemManager.getInstance().closeFile(data[1]);
            } catch (FileNotFoundException e) {
                System.err.println("File not found");
            } catch (IOException e) {
                System.err.println("IO error");
            } catch (ClassNotFoundException e) {
                System.err.println("aboba");
            }
        }
    }
}
