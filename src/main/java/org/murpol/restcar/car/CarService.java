package org.murpol.restcar.car;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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

    public CarDTO addNewCar(CarDTO carDTO) {
        Optional<Car> vin = carRepository.findById(carDTO.getVin());
        if (vin.isPresent()) {
            throw new CarIsAlreadyInDB(carDTO.getVin());
        }
        Car car = new Car(carDTO.getBrand(), carDTO.getModel(), carDTO.yearOfProduction);
        carRepository.save(car);
        return carDTO;
    }

    public void deleteCar(String vin) {
        checkVinLength(vin);
        Optional<Car> carRetrieved = carRepository.findById(vin);
        if (carRetrieved.isEmpty()) {
            throw new NoCarWithThisVinInDB(vin);
        }
        carRepository.delete(carRetrieved.get());
    }

    @Transactional
    public void updateCar(String vin, CarDTO carDTO) {
        checkVinLength(vin);
        Car car = carRepository.findById(vin)
                .orElseThrow(() -> new NoCarWithThisVinInDB(vin));
        /*if (brand != null && brand.length() > 0 && Objects.equals(brand, car.getBrand())) {
            car.setBrand(brand);
        }*/
        car.setBrand(car.getBrand());
        car.setModel(car.getModel());
        car.setYearOfProduction(car.getYearOfProduction());
        carRepository.save(car);
    }

    public Car getCar(String vin) {
        checkVinLength(vin);
        return carRepository.findById(vin)
                .orElseThrow(() -> new NoCarWithThisVinInDB(vin));
    }

    private void checkVinLength(String vin) {
        if (vin.length() != 17) {
            throw new InvalidVINLenghtException(vin.length());
        }
    }

    private static class CarIsAlreadyInDB extends RuntimeException {
        public CarIsAlreadyInDB(String vinID) {
            super("Car with this VIN number [" + vinID + "] is already in database");
        }
    }

    private static class NoCarWithThisVinInDB extends RuntimeException {
        public NoCarWithThisVinInDB(String vinID) {
            super("There is no car with this VIN number [" + vinID + "] in database");
        }
    }

    private static class InvalidVINLenghtException extends RuntimeException {
        public InvalidVINLenghtException(Integer length) {
            super("Invalid VIN length. It's [" + length + "] characters instead of 17");
        }
    }
}
