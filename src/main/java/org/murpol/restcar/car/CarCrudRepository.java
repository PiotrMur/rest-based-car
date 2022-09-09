package org.murpol.restcar.car;

import org.springframework.data.repository.CrudRepository;

public interface CarCrudRepository extends CrudRepository<Car, String> { //zmien na CRUD bo działa szybciej
}
