package Models;

import Utility.Element;
import Interfaces.Validatable;
import com.opencsv.bean.CsvBindByName;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class Coordinates extends Element implements Validatable, Serializable {
    private static Integer maxId = 0;
    @CsvBindByName private Integer id = 0;
    @CsvBindByName private int x;
    @CsvBindByName private double y;

    public Coordinates(Integer id, int x, double y) {
        this.id = id;
        maxId = Math.max(this.id, maxId);
        this.x = x;
        this.y = y;
    }

    @Override
    public void setId(Integer newId) {
        this.id = newId;
    }

    public Coordinates() {}

    @Override
    public boolean validate() {
        return this.id != null && this.id >= 1;
    }

    public static Integer getMaxId() {
        return maxId;
    }

    public void updateMaxId() {
        maxId = Math.max(this.id, maxId);
    }

    @Override
    public String toString() {
        if(this.validate())
            return "x: " + String.valueOf(x) + " y: " + String.valueOf(y);
        else
            return "unvalidatable";
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String getSaveQuery() {
        return "INSERT INTO COORDINATES (X, Y) VALUES (?,?)";
    }

    @Override
    public PreparedStatement formSaveQuery(PreparedStatement statement) throws SQLException {
        statement.setInt(1, this.x);
        statement.setDouble(2, this.y);
        return statement;
    }
}
