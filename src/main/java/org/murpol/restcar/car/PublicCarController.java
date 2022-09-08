package org.murpol.restcar.car;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/cars")
public class PublicCarController {

    private final CarService carService;

    public PublicCarController(CarService carService) {
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


}
