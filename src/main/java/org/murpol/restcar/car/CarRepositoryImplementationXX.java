package org.murpol.restcar.car;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
public class CarRepositoryImplementationXX implements CarRepository{

    private final CarCrudRepository carCrudRepository;

    public CarRepositoryImplementationXX(CarCrudRepository carCrudRepository) {
        this.carCrudRepository = carCrudRepository;
    }


    @Override
    public List<Car> getAllCars() {
        return StreamSupport.stream(carCrudRepository.findAll().spliterator(), false).toList();
    }

    @Override
    public Optional<Car> findCarByVIN(String vin) {
        return carCrudRepository.findById(vin);
    }

    @Override
    public void deleteCar(Car car) {
        carCrudRepository.delete(car);

    }

    @Override
    public void storeCar(Car car) {
        carCrudRepository.save(car);
    }
}
