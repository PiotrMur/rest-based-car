package org.murpol.restcar.car;

import org.murpol.restcar.auxiliary.NewCarCreation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }


    public List<Car> getCars() {
        return StreamSupport.stream(carRepository.findAll().spliterator(), false).toList();
    }

    public void validateInput(CarDTO carDTO) {
        NewCarCreation.validateInput(carDTO);
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

    public void checkVinLength(String vin) {
        if (vin.length() != 17) {
            throw new InvalidVINLengthException(vin.length());
        }
    }

    static class NoCarWithThisVinInDbException extends RuntimeException {
        public NoCarWithThisVinInDbException(String vinID) {
            super("There is no car with this VIN number [" + vinID + "] in the database");
        }
    }

    static class InvalidVINLengthException extends RuntimeException {
        public InvalidVINLengthException(Integer length) {
            super("Invalid VIN length. There are [" + length + "] characters instead of 17");
        }
    }
}
