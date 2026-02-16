package Сommands;

public abstract class Command {
    private static String name, description;

    public Command (String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static void apply(String[] data){
        System.out.println("123");
    }

    public static String getName() {
        return name;
    }

    public static String getDescription() {
        return description;
    }
}
