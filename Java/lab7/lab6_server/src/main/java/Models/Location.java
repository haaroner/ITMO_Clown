package models;

import utility.Element;
import com.opencsv.bean.CsvBindByName;
import interfaces.Validatable;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class Location extends Element implements Validatable, Serializable {
    private static Integer maxId = 0;
    @CsvBindByName private Integer id = 0;
    @CsvBindByName private long x;
    @CsvBindByName private Float y; //Поле не может быть null
    @CsvBindByName private Double z; //Поле не может быть null

    public Location(Integer id, long x, Float y, Double z) {
        this.id = id;
        maxId = Math.max(this.id, maxId);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void setId(Integer newId) {
        this.id = newId;
    }

    public Location() {}

    public long getX() {
        return x;
    }

    @Override
    public boolean validate(){
        return id != null && id > 0 && y != null && z != null;
    }

    public static Integer getMaxId() {
        return maxId;
    }


    @Override
    public String toString() {
        if(this.validate())
            return " x: " + String.valueOf(x) + " y: " + String.valueOf(y) + " z: " + String.valueOf(z);
        else
            return "unvalidatable";
    }

    public void updateMaxId() {
        maxId = Math.max(this.id, maxId);
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getSaveQuery () {
        return "INSERT INTO LOCATIONS (X, Y, Z) VALUES (?,?,?)";
    }

    @Override
    public PreparedStatement formSaveQuery(PreparedStatement statement) throws SQLException {
        statement.setLong(1, this.x);
        statement.setFloat(2, this.y);
        statement.setDouble(3, this.z);
        return statement;
    }
}
