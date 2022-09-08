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

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(String yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
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
