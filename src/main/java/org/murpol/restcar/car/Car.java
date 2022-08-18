package org.murpol.restcar.car;

import org.murpol.restcar.auxiliary.VINGenerator;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    String vin;
    String brand;
    String model;
    String yearOfProduction;

    public Car() {
    }

    public Car(String brand, String model, String yearOfProduction) {
        this.brand = brand;
        this.model = model;
        this.yearOfProduction = yearOfProduction;
        this.vin = VINGenerator.generateVIN();
    }


    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getYearOfProduction() {
        return yearOfProduction;
    }

    public String getVin() {
        return vin;
    }

    @Override
    public String toString() {
        return "Car{" +
                "VIN=" + vin +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", yearOfProduction='" + yearOfProduction + '\'' +
                '}';
    }
}
