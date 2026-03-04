package com.example.lab5_starter;

import java.io.Serializable;


public class City implements Comparable<City>, Serializable {


    private String name;


    private String province;


    public City(String name, String province) {
        this.name = name;
        this.province = province;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getProvince() {
        return province;
    }


    public void setProvince(String province) {
        this.province = province;
    }


    @Override
    public int compareTo(City other) {
        return this.name.compareTo(other.getName());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return this.name.equals(city.name) && this.province.equals(city.province);
    }


    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + province.hashCode();
        return result;
    }
}