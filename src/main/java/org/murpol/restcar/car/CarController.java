package org.murpol.restcar.car;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Car>> getCars(){
        return new ResponseEntity<>(carService.getCars(), HttpStatus.OK);
    }

    @GetMapping(path = "{vin}")
    public ResponseEntity<Car> getCar(@PathVariable String vin){
        return new ResponseEntity<>(carService.getCar(vin), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addNewCar(@RequestBody CarDTO carDTO){
        carService.addNewCar(carDTO);
    }

    @DeleteMapping(path = "{vin}")
    public void deleteCar(@PathVariable(name = "vin") String carVIN){
        carService.deleteCar(carVIN);
    }

    @PutMapping(path = "{vin}")
    public void updateCar(@PathVariable(name = "vin") String carVIN,
                          @RequestBody CarDTO carDTO)/*,
                          @RequestParam(name = "model", required = false) String carModel,
                          @RequestParam(name = "yop", required = false) String yearOfProduction)*/ {
        carService.updateCar(carVIN, carDTO);
    }

}
