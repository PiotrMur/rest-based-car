package org.murpol.restcar.car;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Qualifier("postgresRepo")
@Component
public class CarPostgresRepository implements CarRepository {

    final CarCrudRepository carCrudRepository;

    public CarPostgresRepository(CarCrudRepository carCrudRepository) {
        this.carCrudRepository = carCrudRepository;
    }

    @Override
    public List<Car> findAll() {
        return null;
    }

    @Override
    public Optional<Car> findByVin(String vin) {
        return carCrudRepository.findById(vin);
    }

    @Override
    public void delete(Car car) {
        carCrudRepository.delete(car);
    }

    @Override
    public void store(Car car) {
        carCrudRepository.save(car);
    }
}
