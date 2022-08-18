package org.murpol.restcar.car;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/cars")
public class CarController {

    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<Car> getCars(){
        return carService.getCars();
    }

    @PostMapping
    public void addNewCar(@RequestBody Car car){
        carService.addNewCar(car);
    }

    @DeleteMapping(path = "{vin}")
    public void deleteCar(@PathVariable(name = "vin") String carVIN){
        carService.deleteCar(carVIN);
    }

    @PutMapping(path = "{vin}")
    public void updateCar(@PathVariable(name = "vin") String carVIN,
                          @RequestParam(name = "brand", required = false) String carBrand,
                          @RequestParam(name = "model", required = false) String carModel,
                          @RequestParam(name = "yop", required = false) String yearOfProduction) {

    }

}
