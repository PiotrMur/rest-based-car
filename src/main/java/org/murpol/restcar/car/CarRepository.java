package org.murpol.restcar.car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
    List<Car> findAll();

    Optional<Car> findByVin(String vin);

    void delete(Car car);

    void store(Car car);
}
