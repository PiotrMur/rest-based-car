package org.murpol.restcar.car;

import org.murpol.restcar.auxiliary.NewCarCreation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CarService {


    private final CarRepositoryImplementationXX carRepositoryImplementationXX;

    public CarService(CarRepositoryImplementationXX carRepositoryImplementationXX) {
        this.carRepositoryImplementationXX = carRepositoryImplementationXX;
    }


    public List<Car> getCars() {
        return carRepositoryImplementationXX.getAllCars();
    }

    public void validateInput(CarDTO carDTO) {
        new NewCarCreation(carRepositoryImplementationXX).validateInput(carDTO);
    }


    public void deleteCar(String vin) {
        checkVinLength(vin);
        Optional<Car> carRetrieved = carRepositoryImplementationXX.findCarByVIN(vin);
        if (carRetrieved.isEmpty()) {
            throw new NoCarWithThisVinInDbException(vin);
        }
        carRepositoryImplementationXX.deleteCar(carRetrieved.get());
    }

    @Transactional
    public void updateCar(String vin, CarDTO carDTO) {
        checkVinLength(vin);
        Car car = carRepositoryImplementationXX.findCarByVIN(vin)
                .orElseThrow(() -> new NoCarWithThisVinInDbException(vin));
        /*if (brand != null && brand.length() > 0 && Objects.equals(brand, car.getBrand())) {
            car.setBrand(brand);
        }*/
        car.setBrand(carDTO.brand());
        car.setModel(carDTO.model());
        car.setYearOfProduction(carDTO.yearOfProduction());
        carRepositoryImplementationXX.storeCar(car);
    }

    public Car getCar(String vin) {
        checkVinLength(vin);
        return carRepositoryImplementationXX.findCarByVIN(vin)
                .orElseThrow(() -> new NoCarWithThisVinInDbException(vin));
    }

    private static final Pattern VIN_PATTERN = Pattern.compile("[A-Z0-9]+");

    public void checkVinLength(String vin) {
        Matcher matcher = VIN_PATTERN.matcher(vin);
        if (vin.length() != 17 && matcher.find()) {
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
