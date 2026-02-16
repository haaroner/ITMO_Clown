package Сommands;

public enum CommandType {
    info(Info.class, "info", "displays all commands"),
    exit(Exit.class, "exit", "stops programm"),
    clear(ClearCollection.class, "clear", "clears collection"),
    show(Show.class, "show", "shows current collection"),
    save(Save.class, "save", "saves current collection"),
    insert(Insert.class, "insert", "inserts new item  to collection"),
    update(Update.class, "update", "updates item in collection"),
    remove_greater_key(RemoveGreater.class, "remove_greater_key", "removes elements with greater IDs"),
    remove_lower_key(RemoveLower.class, "remove_lower_key", "removes elements with lower IDs"),
    max_by_distance(MaxDistance.class, "max_by_distance", "...");

    private final Class<? extends Command> clazz;
    private final String name, description;

    CommandType(Class<? extends Command> clazz, String name, String description) {
        this.clazz = clazz;
        this.name = name;
        this.description = description;
    }

    public Class<? extends Command> getClazz() {
        return clazz;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
}
