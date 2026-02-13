package models;

import Utility.Element;
import com.opencsv.bean.CsvBindByName;
import interfaces.Validatable;

public class Location extends Element implements Validatable {
    @CsvBindByName private Integer id;
    @CsvBindByName private long x;
    @CsvBindByName private Float y; //Поле не может быть null
    @CsvBindByName private Double z; //Поле не может быть null

    @Override
    public boolean validate(){
        return y != null && z != null;
    }

    @Override
    public String toString() {
        return "id: " + String.valueOf(id) + " x: " + String.valueOf(x) + " y: " + String.valueOf(y) + " z: " + String.valueOf(z);
    }

    @Override
    public Integer getId() {
        return id;
    }
}
