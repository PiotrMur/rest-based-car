package org.murpol.restcar.car;

import java.util.Objects;

public record CarDTO(String vin, String brand, String model, String yearOfProduction) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDTO carDTO = (CarDTO) o;
        return vin.equals(carDTO.vin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vin);
    }
}
