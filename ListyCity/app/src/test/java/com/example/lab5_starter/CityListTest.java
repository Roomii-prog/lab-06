package com.example.lab5_starter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CityListTest {

    // ─── Helper methods ───────────────────────────────────────────────────────

    private CityList mockCityList() {
        CityList cityList = new CityList();
        cityList.add(mockCity());
        return cityList;
    }

    private City mockCity() {
        return new City("Edmonton", "Alberta");
    }

    // ─── Tests for add() ──────────────────────────────────────────────────────

    @Test
    void testAdd() {
        CityList cityList = mockCityList();
        assertEquals(1, cityList.getCities().size());

        City city = new City("Regina", "Saskatchewan");
        cityList.add(city);

        assertEquals(2, cityList.getCities().size());
        assertTrue(cityList.getCities().contains(city));
    }

    @Test
    void testAddException() {
        CityList cityList = mockCityList();
        City city = new City("Yellowknife", "Northwest Territories");
        cityList.add(city);

        assertThrows(IllegalArgumentException.class, () -> {
            cityList.add(city);
        });
    }

    // ─── Tests for getCities() ────────────────────────────────────────────────

    @Test
    void testGetCities() {
        CityList cityList = mockCityList();

        // Edmonton is the only city, should be at position 0
        assertEquals(0, mockCity().compareTo(cityList.getCities().get(0)));

        // Charlottetown (C) comes before Edmonton (E), pushes Edmonton to position 1
        City city = new City("Charlottetown", "Prince Edward Island");
        cityList.add(city);

        assertEquals(0, city.compareTo(cityList.getCities().get(0)));
        assertEquals(0, mockCity().compareTo(cityList.getCities().get(1)));
    }

    // ─── Tests for hasCity() ──────────────────────────────────────────────────

    @Test
    void testHasCityTrue() {
        CityList cityList = mockCityList();
        assertTrue(cityList.hasCity(mockCity()));
    }

    @Test
    void testHasCityFalse() {
        CityList cityList = mockCityList();
        City city = new City("Toronto", "Ontario");
        assertFalse(cityList.hasCity(city));
    }

    @Test
    void testHasCityUsesEquals() {
        CityList cityList = mockCityList();
        // Different object but same values — equals() should make this true
        City sameCity = new City("Edmonton", "Alberta");
        assertTrue(cityList.hasCity(sameCity));
    }

    // ─── Tests for delete() ───────────────────────────────────────────────────

    @Test
    void testDeleteRemovesCity() {
        CityList cityList = mockCityList();
        assertEquals(1, cityList.countCities());

        cityList.delete(mockCity());

        assertEquals(0, cityList.countCities());
        assertFalse(cityList.hasCity(mockCity()));
    }

    @Test
    void testDeleteException() {
        CityList cityList = mockCityList();
        City city = new City("Vancouver", "British Columbia");

        // City not in list — should throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            cityList.delete(city);
        });
    }

    // ─── Tests for countCities() ──────────────────────────────────────────────

    @Test
    void testCountCitiesEmpty() {
        CityList cityList = new CityList();
        assertEquals(0, cityList.countCities());
    }

    @Test
    void testCountCitiesAfterAdd() {
        CityList cityList = mockCityList();
        assertEquals(1, cityList.countCities());

        cityList.add(new City("Calgary", "Alberta"));
        assertEquals(2, cityList.countCities());
    }

    @Test
    void testCountCitiesAfterDelete() {
        CityList cityList = mockCityList();
        cityList.add(new City("Calgary", "Alberta"));
        assertEquals(2, cityList.countCities());

        cityList.delete(mockCity());
        assertEquals(1, cityList.countCities());
    }
}