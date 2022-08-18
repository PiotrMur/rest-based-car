package org.murpol.restcar.car;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }


    public List<Car> getCars() {
        return carRepository.findAll();
    }

    public Car addNewCar(Car car) {
        Optional<Car> vin = carRepository.findById(car.getVin());
        if (vin.isPresent()) {
            throw new CarIsAlreadyInDB(car.getVin());
        }
        return carRepository.save(car);
    }

    public void deleteCar(String vin){
        Optional<Car> carRetrieved = carRepository.findById(vin);
        if(carRetrieved.isEmpty()){
            throw new NoCarWithThisVinInDB(vin);
        }
        carRepository.delete(carRetrieved.get());
    }

    public void updateCar(Car car){

    }

    private static class CarIsAlreadyInDB extends RuntimeException {
        public CarIsAlreadyInDB(String vinID) {
            super("Car with this VIN number [" + vinID + "] is already in database");
        }
    }

    private static class NoCarWithThisVinInDB extends RuntimeException {
        public NoCarWithThisVinInDB(String vinID){
            super("There is no car with this VIN number [" + vinID + "] in database");
        }
    }
}
