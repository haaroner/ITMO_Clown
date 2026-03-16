package Models;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class ServerResponse implements Serializable {
    private final static ServerResponse INSTANCE = new ServerResponse();
    private LinkedHashMap<Integer, Route> routes;
    private String data;

    private ServerResponse(){}

    public static ServerResponse getInstance() {
        return INSTANCE;
    }

    public void setRoutes(LinkedHashMap<Integer, Route> routes) {
        this.routes = routes;
    }
    public void setData(String data) {
        this.data = data;
    }

    public LinkedHashMap<Integer, Route> getRoutes () {
        return this.routes;
    }
    public String getData(){
        return this.data;
    }
}
