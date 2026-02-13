package models;

import Utility.Element;
import interfaces.Validatable;
import com.opencsv.bean.CsvBindByName;

public class Coordinates extends Element implements Validatable{
    @CsvBindByName private Integer id;
    @CsvBindByName private int x;
    @CsvBindByName private double y;

    @Override
    public String toString() {
        return "id: " + String.valueOf(id) + " x: " + String.valueOf(x) + " y: " + String.valueOf(y);
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public boolean validate() {//TODO подумать
        return true;
    }
}
