package org.murpol.restcar.auxiliary;

import org.murpol.restcar.car.Car;
import org.murpol.restcar.car.CarDTO;
import org.murpol.restcar.car.CarRepository;

import java.util.Optional;

public class NewCarCreation {
    private final CarRepository carRepository;

    public NewCarCreation(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public void validateInput(CarDTO carDTO) {
        if (carDTO.vin() == null || carDTO.vin().isEmpty()) {
            createVinAndAddNewCar(carDTO);
        } else {
            addNewCar(carDTO);
        }
    }

    private Car createVinAndAddNewCar(CarDTO carDTO) {

        Car car = new Car(carDTO.brand(), carDTO.model(), carDTO.yearOfProduction());

        Optional<Car> newCarsId = carRepository.findById(carDTO.vin());
        if (newCarsId.isPresent()) {
            throw new CarIsAlreadyInDB(car.getVin());
        }

        carRepository.save(car);
        return car;
    }

    private Car addNewCar(CarDTO carDTO) {
        Car car = new Car(carDTO.brand(), carDTO.model(), carDTO.yearOfProduction());
        car.setVin(carDTO.vin());

        Optional<Car> carsId = carRepository.findById(car.getVin());
        if (carsId.isPresent()) {
            throw new CarIsAlreadyInDB(car.getVin());
        }

        carRepository.save(car);
        return car;
    }

    private static class CarIsAlreadyInDB extends RuntimeException {
        public CarIsAlreadyInDB(String vinID) {
            super("Car with this VIN number [" + vinID + "] is already in the database");
        }
    }
}