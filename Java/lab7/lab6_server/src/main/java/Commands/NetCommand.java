package Commands;

import Models.Route;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

public final class NetCommand implements Serializable {
    private Command command;
    private String[] line;
    private Route myRoute;

    public NetCommand (Command command, String[] line, Route myRoute) {
        this.command = command;
        this.line = line;
        this.myRoute = myRoute;
    }

    public Command getCommand() {
        return this.command;
    }

    public String[] getLine() {
        return line;
    }

    public Route getRoute() {
        return myRoute;
    }

    @Override
    public String toString(){
        return Arrays.toString(line);
    }
}
