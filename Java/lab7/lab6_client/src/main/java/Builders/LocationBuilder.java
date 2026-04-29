package builders;

import models.Location;

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
