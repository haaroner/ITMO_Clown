package commands;

public class Info extends Command{
    public Info() {
        super("info", "prints information about all commands");
    }

    public static void apply(String[] data) {
        if(data.length > 1)
            System.out.println("This command doesn't need any arguments,  they`ll be ignored");

        System.out.println("Придумать как выводить каждый дескрипшн");

    }
}
