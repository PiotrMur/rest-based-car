package org.murpol.restcar.car;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "management/api/v1/cars")
public class ManagementCarController {

    private final CarService carService;

    public ManagementCarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<Car> getCars(){
       return carService.getCars();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createCar(@RequestBody CarDTO carDTO){
        carService.validateInput(carDTO);
    }

    @DeleteMapping(path = "{vin}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
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
