package com.example.lab5_starter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CityList {


    private List<City> cities = new ArrayList<>();


    public void add(City city) {
        if (cities.contains(city)) {
            throw new IllegalArgumentException();
        }
        cities.add(city);
    }


    public List<City> getCities() {
        List<City> list = new ArrayList<>(cities);
        Collections.sort(list);
        return list;
    }


    public boolean hasCity(City city) {
        return cities.contains(city);
    }


    public void delete(City city) {
        if (!cities.contains(city)) {
            throw new IllegalArgumentException();
        }
        cities.remove(city);
    }


    public int countCities() {
        return cities.size();
    }
}