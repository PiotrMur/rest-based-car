package org.murpol.restcar.car;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public CarDTO addNewCar(CarDTO carDTO) {
        Optional<Car> vin = carRepository.findById(carDTO.vin());
        if (vin.isPresent()) {
            throw new CarIsAlreadyInDB(carDTO.vin());
        }
        Car car = new Car(carDTO.brand(), carDTO.model(), carDTO.yearOfProduction());
        carRepository.save(car);
        return carDTO;
    }

    public void deleteCar(String vin) {
        checkVinLength(vin);
        Optional<Car> carRetrieved = carRepository.findById(vin);
        if (carRetrieved.isEmpty()) {
            throw new NoCarWithThisVinInDbException(vin);
        }
        carRepository.delete(carRetrieved.get());
    }

    @Transactional
    public void updateCar(String vin, CarDTO carDTO) {
        checkVinLength(vin);
        Car car = carRepository.findById(vin)
                .orElseThrow(() -> new NoCarWithThisVinInDbException(vin));
        /*if (brand != null && brand.length() > 0 && Objects.equals(brand, car.getBrand())) {
            car.setBrand(brand);
        }*/
        car.setBrand(carDTO.brand());
        car.setModel(carDTO.model());
        car.setYearOfProduction(carDTO.yearOfProduction());
        carRepository.save(car);
    }

    public Car getCar(String vin) {
        checkVinLength(vin);
        return carRepository.findById(vin)
                .orElseThrow(() -> new NoCarWithThisVinInDbException(vin));
    }

    private void checkVinLength(String vin) {
        if (vin.length() != 17) {
            throw new InvalidVINLengthException(vin.length());
        }
    }

    private static class CarIsAlreadyInDB extends RuntimeException {
        public CarIsAlreadyInDB(String vinID) {
            super("Car with this VIN number [" + vinID + "] is already in database");
        }
    }

    private static class NoCarWithThisVinInDbException extends RuntimeException {
        public NoCarWithThisVinInDbException(String vinID) {
            super("There is no car with this VIN number [" + vinID + "] in database");
        }
    }

    private static class InvalidVINLengthException extends RuntimeException {
        public InvalidVINLengthException(Integer length) {
            super("Invalid VIN length. It's [" + length + "] characters instead of 17");
        }
    }
}
