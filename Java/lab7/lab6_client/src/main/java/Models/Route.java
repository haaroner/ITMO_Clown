package Models;

import Interfaces.Validatable;
import com.opencsv.bean.*;
import Managers.CollectionManager;
import Utility.Element;

import java.io.Serializable;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

public final class Route extends Element implements Comparable<Route>, Validatable, Serializable {
    private static Integer maxId = 0;
    @CsvBindByName private Integer id = 0; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @CsvBindByName private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    @CsvBindByName private Integer coordinatesId;
    @CsvBindByName(column = "date")
    @CsvDate(value = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Location from; //Поле может быть null
    private Location to; //Поле не может быть null
    @CsvBindByName private Integer fromId;
    @CsvBindByName private Integer toId;
    @CsvBindByName private long distance; //Значение поля должно быть больше 1
    //TODO сделать сеттеры и забиндить подклассы через спомог переменные и в спомог классы добавить нужные сеттеры, вызываемые в конструкторе?
    public Route (Integer id, String name, Coordinates coordinates,  Location from, Location to, Long distance) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.creationDate = new java.util.Date();
        this.coordinatesId = this.coordinates.getId();
        if(Objects.nonNull(this.from))
            this.fromId = this.from.getId();
        this.toId = this.to.getId();
        this.updateMaxId();
    }

    public Route(Integer id, String name, Integer coordinatesId,  Integer fromId, Integer toId, Long distance) {
        this.id = id;
        this.name = name;
        this.coordinatesId = coordinatesId;
        this.fromId = fromId;
        this.toId = toId;
        this.distance = distance;
    }

    public void updateMaxId() {
        maxId = Math.max(this.id, maxId);
    }

    public Route() {

    }

    public void setId(CollectionManager manager){
        this.id = manager.getCollection().values().stream().filter(Objects::nonNull).mapToInt(value -> value.getId()).max().orElse(-1) + 1;
    }

    public long getDistance(){
        return this.distance;
    }

    public static Integer getMaxId() {
        return maxId;
    }

    public void setFrom(Location from) {
        this.from = from;
    }

    public void setTo(Location to) {
        this.to = to;
    }

    public void setDate(java.util.Date date){
        this.creationDate = date;
    }

    public void setCoordinates(Coordinates coordinates) {

        this.coordinates = coordinates;
    }

    @Override
    public boolean validate() {
        //System.out.println(id + " " + name + " " + coordinates + " " + creationDate + " " +
        //      from + " " + to + " " + distance);
        //if(id == null || id <= 0) return false;
        if(name == null || name.isEmpty()) return false;

        if(coordinates == null) return false;
        if(!coordinates.validate()) return false;

        if(creationDate == null) return false;

        if(Objects.nonNull(from) && !from.validate()) return false;

        if(to == null) return false;
        if(!to.validate()) return false;
        if(distance < 1) return false;

        return true;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer newId) {
        this.id = newId;
    }

    public Integer getToId() {
        return this.toId;
    }

    public Integer getFromId() {
        return this.fromId;
    }

    public Integer getCoordinatesId(){
        return this.coordinatesId;
    }

    public Location getFrom() {
        return from;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Location getTo() {
        return to;
    }

    public void updateLinks() {
        this.toId = this.to.getId();
        if(Objects.nonNull(from))
            this.fromId = this.from.getId();
        else
            this.fromId = null;
        this.coordinatesId = this.coordinates.getId();
    }

    @Override
    public String toString() {
        if(this.validate()) {
            if(Objects.nonNull(from))
                return "id: " + id.toString() + " name: " + name + " coordinates: " + coordinates.toString() + " creationDate: " + creationDate.toString() +
                        " from: " + from.toString() + " to: " + to.toString() + " distance: " + distance;
            else
                return "id: " + id.toString() + " name: " + name + " coordinates: " + coordinates.toString() + " creationDate: " + creationDate.toString() +
                        " from: " + "null" + " to: " + to.toString() + " distance: " + distance;
        }
        return "Route have not passed validation test, may be some data is missing";
    }

    @Override
    public int compareTo(Route other) {
        return Integer.parseInt(""+(this.distance - other.getDistance()));
    }

    @Override
    public String getSaveQuery() {
        return "INSERT INTO ROUTES (COORDINTAES_ID, FROM_ID, TO_ID, ROUTE_DATE, DISTANCE, NAME) VALUES (?,?,?,?,?,?)";
    }

    @Override
    public PreparedStatement formSaveQuery(PreparedStatement statement) throws SQLException {
        statement.setInt(1, this.coordinatesId);
        if(Objects.nonNull(this.from))
            statement.setInt(2, this.fromId);
        else
            statement.setNull(2, Types.BIGINT);
        statement.setInt(3, this.toId);
        statement.setDate(4, java.sql.Date.valueOf("2024-01-01"));
        statement.setLong(5, this.distance);
        statement.setString(6, this.name);
        return statement;
    }

    public static String getRemoveQuery() {
        return "DELETE FROM ROUTES WHERE ROUTE_ID = ?";
    }
}