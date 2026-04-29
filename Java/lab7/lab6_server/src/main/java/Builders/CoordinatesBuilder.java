package builders;

import models.Coordinates;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class CoordinatesBuilder {
    public CoordinatesBuilder CoordinatesBuilder(){
        return this;
    }

    public CoordinatesBuilder setId() {
        this.id = Coordinates.getMaxId()+1;
        return this;
    }

    public CoordinatesBuilder setX(int x) {
        this.x = x;
        return this;
    }

    public CoordinatesBuilder setY(double y) {
        this.y = y;
        return this;
    }

    public List<Coordinates> buildByResultSet(ResultSet resultSet) throws SQLException {
        List<Coordinates> result = new ArrayList<>();
        while(resultSet.next()) {
            Integer id = resultSet.getInt("COORDINATE_ID");
            int x = resultSet.getInt("X");
            double y = resultSet.getDouble("Y");
            result.add(new Coordinates(id, x, y));
        }
        return result;
    }

    public Coordinates build() {
        Coordinates newCoordinates = new Coordinates(this.id, this.x, this.y);
        if(newCoordinates.validate())
            return newCoordinates;
        else
            return null;
    }

    private Integer id;
    private int x;
    private double y;
}
