package org.murpol.restcar.car;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Qualifier("fileRepo")
@Component
public class CarFileRepository implements CarRepository{
    @Override
    public List<Car> findAll() {
        return null;
    }

    @Override
    public Optional<Car> findByVin(String vin) {
        return Optional.empty();
    }

    @Override
    public void delete(Car car) {

    }

    @Override
    public void store(Car car) {

    }
}
