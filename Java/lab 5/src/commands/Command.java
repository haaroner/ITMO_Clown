package commands;

public abstract class Command {
    private String name, description;

    public Command (String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static void apply(String[] data){

    }

}
