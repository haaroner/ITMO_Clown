package models;

import Managers.SystemManager;
import com.opencsv.bean.*;
import Managers.CollectionManager;
import Utility.Element;

import java.util.Objects;

public class Route extends Element{
    @CsvBindByName private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
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
    public Route (String name, Coordinates coordinates, Location from, Location to, long distance) {
        this.name = name;
        this.coordinates = coordinates;
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.creationDate = new java.util.Date();
    }

    public Route() {

    }

    public void setId(CollectionManager manager){
        this.id = manager.getCollection().values().stream().filter(Objects::nonNull).mapToInt(value -> value.getId()).max().orElse(-1) + 1;
    }

    public void setFrom(Location from) {
        this.from = from;
    }

    public void setTo(Location to) {
        this.to = to;
    }

    public void setCoordinates(Coordinates coordinates) {

        this.coordinates = coordinates;
    }

    @Override
    public boolean validate() {
        if(id == null || id <= 0) return false;
        if(name == null || name.isEmpty()) return false;
        if(coordinates == null) return false;
        if(creationDate == null) return false;
        if(from == null) return false;
        if(to == null) return false;
        if(distance > 1) return false;

        return true;
    }

    @Override
    public Integer getId() {
        return this.id;
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

    @Override
    public String toString() {
        return "id: " + id.toString() + " name: " + name + " coordinates: " + coordinates.toString() + " creationDate: " + creationDate.toString() +
                " from: " + from.toString() + " to: " + to.toString();
    }
}