package builders;

import models.Coordinates;
import models.Location;
import models.Route;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public final class RouteBuilder {
    public RouteBuilder RouteBuilder(){
        return this;
    }

    public RouteBuilder setId() {
        this.id = Route.getMaxId()+1;
        return this;
    }

    public RouteBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public RouteBuilder setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        this.coordinatesId = coordinates.getId();
        return this;
    }

    public RouteBuilder setDate(java.util.Date creationDate){
        this.creationDate = new Date();
        return this;
    }

    public RouteBuilder setDate(){
        this.creationDate = new Date();
        return this;
    }

    public RouteBuilder setFrom (Location location) {
        this.from = location;
        if(Objects.nonNull(location))
            this.fromId = location.getId();
        return this;
    }

    public RouteBuilder setTo (Location location) {
        this.to = location;
        this.toId = location.getId();
        return this;
    }

    public RouteBuilder setDistance(Long distance) {
        this.distance = distance;
        return this;
    }

    public List<Route> buildByResultSet(ResultSet resultSet) throws SQLException {
        List<Route> result = new ArrayList<>();
        while(resultSet.next()) {
            Integer id = resultSet.getInt("ROUTE_ID");
            String name = resultSet.getString("NAME");
            Integer coordinatesId = resultSet.getInt("COORDINTAES_ID");
            Integer fromId = resultSet.getInt("FROM_ID");
            Integer toId = resultSet.getInt("TO_ID");
            java.util.Date creationDate = resultSet.getDate("ROUTE_DATE");
            Long distance = resultSet.getLong("DISTANCE");
            Route route = new Route(id, name, coordinatesId, fromId, toId, distance);
            route.setDate(creationDate);
            result.add(route);
        }
        return result;
    }

    public Route build() {
        Route newRoute = new Route(this.id, this.name, this.coordinates, this.from, this.to, this.distance);
       // System.out.println(this.id.toString() + this.name + this.coordinates.toString() + this.from.toString() + this.to.toString() + this.distance);
        System.out.println(newRoute);
        if(newRoute.validate())
            return newRoute;
        else
            return null;
    }

    private Integer id;
    private String name;
    private Coordinates coordinates;
    private Integer coordinatesId;
    private java.util.Date creationDate;
    private Location from;
    private Location to;
    private Integer fromId;
    private Integer toId;
    private Long distance;
}
