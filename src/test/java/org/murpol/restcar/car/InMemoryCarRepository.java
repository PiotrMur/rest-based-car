package org.murpol.restcar.car;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryCarRepository implements CarRepository{

    private Map<String, Car> cars = new HashMap<>();

    @Override
    public List<Car> getAllCars() {
        return cars.values().stream().toList();
    }

    @Override
    public Optional<Car> findCarByVIN(String vin) {
        return Optional.ofNullable(cars.get(vin));
    }

    @Override
    public void deleteCar(Car car) {

    }

    @Override
    public void storeCar(Car car) {

    }
}
