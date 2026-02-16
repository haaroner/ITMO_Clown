package Сommands;

public class Exit extends Command{
    public Exit() {
        super("exit", "stops program");
    }

    public static void apply(String[] data) {
        System.out.println("Stopping the program...");
        System.exit(0);
    }
}
