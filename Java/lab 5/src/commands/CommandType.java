package Commands;

/**
 * Enum with all commands
 * Needed to apply commands by making instance of class by name of certain enum
 * @author Clown
 */
public enum CommandType {
    info(Info.class, "info", "displays all commands"),
    exit(Exit.class, "exit", "stops program"),
    clear(ClearCollection.class, "clear", "clears collection"),
    show(Show.class, "show", "shows current collection"),
    save(Save.class, "save", "saves current collection"),
    insert(Insert.class, "insert", "inserts new item  to collection"),
    update(Update.class, "update", "updates item in collection"),
    remove_greater_key(RemoveGreater.class, "remove_greater_key", "removes elements with greater IDs"),
    remove_lower_key(RemoveLower.class, "remove_lower_key", "removes elements with lower IDs"),
    max_by_distance(MaxDistance.class, "max_by_distance", "max by distance"),
    remove_key(RemoveKey.class, "remove_key", "removes certain element"),
    remove_by_distance(RemoveByDistance.class, "remove_by_distance", "removes one element by it's dist"),
    count_greater_distance(CountGreaterDist.class, "count_greater_distance", "Prints number of elements with dist greater than given"),
    execute_script(ExecuteScript.class, "execute_script", "executes script from file"),
    replace_if_lower(ReplaceLower.class, "replace_if_lower", "replaces if comparator show lower number");

    private final Class<? extends Command> clazz;
    private final String name, description;

    /**
     * Enum constructor
     * @param clazz Certain command class attached to this @name
     * @param name name of the command in console, by this name clazz.apply is invoked
     * @param description description of the command. Displayed by info command
     */
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
