package Commands;

import Managers.ConsoleManager;

import java.io.*;

/**
 * Command class for executing file script
 * @author Clown
 */
public final class ExecuteScript extends Command{
    public ExecuteScript() {
    }

    /**
     * Command apply method. Executes script
     * @param data input from console
     * @param console which console to use
     */
    public static void apply(String[] data, BufferedReader console) {
        if(data.length < 2) {
            System.out.println("not enough arguments");
            return;
        }
        else{
            System.out.println("Executing script...\nSerial output will be disabled, only errors will appear");
            try{
                PrintStream origin = System.out;
                /*System.setOut(new PrintStream(new java.io.OutputStream() {
                    public void write(int b){}
                }));*/
                ConsoleManager.startScan(new BufferedReader(new FileReader(data[1])));
                System.setOut(origin);
                System.out.println("File executed");
            } catch (FileNotFoundException e) {
                System.err.println("File not found");
            } catch (IOException e) {
                System.err.println("IO error");
            }
        }
    }
}
