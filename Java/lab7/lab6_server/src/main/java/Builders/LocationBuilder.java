package builders;

import models.Location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public final class LocationBuilder {
    public LocationBuilder LocationBuilder(){
        return this;
    }

    public LocationBuilder setId() {
        this.id = Location.getMaxId()+1;
        return this;
    }

    public LocationBuilder setX(long x) {
        this.x = x;
        return this;
    }

    public LocationBuilder setX(Supplier<Long> x) {
        this.x = x.get();
        return this;
    }

    public LocationBuilder setY(Float y) {
        this.y = y;
        return this;
    }

    public LocationBuilder setY(Supplier<Float> y) {
        while(Objects.isNull(this.y)) {
            this.y = y.get();
        }
        return this;
    }

    public LocationBuilder setZ(Double z) {
        this.z = z;
        return this;
    }

    public LocationBuilder setZ(Supplier<Double> z) {
        while(Objects.isNull(this.z)) {
            this.z = z.get();
        }
        return this;
    }

    public List<Location> buildByResultSet(ResultSet resultSet) throws SQLException {
        List<Location> result = new ArrayList<>();
        while(resultSet.next()) {
            Integer id = resultSet.getInt("LOCATION_ID");
            int x = resultSet.getInt("X");
            Float y = resultSet.getFloat("Y");
            Double z = resultSet.getDouble("Z");
            result.add(new Location(id, x, y, z));
        }
        return result;
    }

    public Location build() {
        Location newLocation = new Location(this.id, this.x, this.y, this.z);
        if(newLocation.validate())
            return newLocation;
        else
            return null;
    }

    private Integer id;
    private long x;
    private Float y;
    private Double z;
}
