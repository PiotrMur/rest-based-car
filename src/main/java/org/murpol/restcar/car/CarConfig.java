package org.murpol.restcar.car;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CarConfig {

    @Bean
    CommandLineRunner commandLineRunner(CarCrudRepository repository){
        return args -> {
            Car x5 = new Car("BMW", "X5", "2021");
            Car i5 = new Car("BMW", "i5", "2022");
            Car S63 = new Car("Mercedes", "S63", "2019");

            repository.saveAll(List.of(x5,i5,S63));
        };
    }
}
