package org.murpol.restcar.car;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryCarRepository implements CarRepository {

    private Map<String, Car> cars = new HashMap<>();

    @Override
    public List<Car> findAll() {
        return cars.values().stream().toList();
    }

    @Override
    public Optional<Car> findByVin(String vin) {
        return Optional.ofNullable(cars.get(vin));
    }

    @Override
    public void delete(Car car) {
        cars.remove(car.vin, car);
    }

    @Override
    public void store(Car car) {
        cars.put(car.vin, car);
    }
}
