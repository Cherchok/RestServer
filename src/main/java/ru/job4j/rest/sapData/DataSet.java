package ru.job4j.rest.sapData;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;

public class DataSet {

    @SerializedName("name")
    private String name;

    @SerializedName("values")
    private LinkedList<String> values;

    public DataSet(){}

    public DataSet(String name, LinkedList<String> values){
        this.name = name;
        this.values = values;
    }

    @Override
    public String toString() {
        return name +": " + values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<String> getValues() {
        return values;
    }

    public void setValues(LinkedList<String> values) {
        this.values = values;
    }
}
