package org.murpol.restcar.car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {

    public List<Car> getAllCars();
    public Optional<Car> findCarByVIN(String vin);

    public void deleteCar(Car car);
    public void storeCar(Car car);
}
