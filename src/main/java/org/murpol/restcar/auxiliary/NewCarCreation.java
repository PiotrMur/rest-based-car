package org.murpol.restcar.auxiliary;

import org.murpol.restcar.car.Car;
import org.murpol.restcar.car.CarDTO;
import org.murpol.restcar.car.CarRepositoryImplementationXX;
import org.springframework.stereotype.Component;

import java.util.Optional;


public class NewCarCreation {


    private final CarRepositoryImplementationXX carRepositoryImplementationXX;

    public NewCarCreation(CarRepositoryImplementationXX carRepositoryImplementationXX) {
        this.carRepositoryImplementationXX = carRepositoryImplementationXX;
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

        Optional<Car> newCarsId = carRepositoryImplementationXX.findCarByVIN(carDTO.vin());
        if (newCarsId.isPresent()) {
            throw new CarIsAlreadyInDB(car.getVin());
        }

        carRepositoryImplementationXX.storeCar(car);
        return car;
    }

    private Car addNewCar(CarDTO carDTO) {
        Car car = new Car(carDTO.brand(), carDTO.model(), carDTO.yearOfProduction());
        car.setVin(carDTO.vin());

        Optional<Car> carsId = carRepositoryImplementationXX.findCarByVIN(car.getVin());
        if (carsId.isPresent()) {
            throw new CarIsAlreadyInDB(car.getVin());
        }

        carRepositoryImplementationXX.storeCar(car);
        return car;
    }

    private static class CarIsAlreadyInDB extends RuntimeException {
        public CarIsAlreadyInDB(String vinID) {
            super("Car with this VIN number [" + vinID + "] is already in the database");
        }
    }
}