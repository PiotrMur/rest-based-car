package org.murpol.restcar.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class CarServiceTest {


    private CarRepositoryImplementationXX carRepositoryImplementationXX;
    private CarService carService;


    @BeforeEach
    void setUp() {
        carService = new CarService(carRepositoryImplementationXX);
    }

    @Test
    void getCars() {
        //when
        carService.getCars();
        //then
        verify(carRepositoryImplementationXX).getAllCars();
    }

/*    @Test
    void shouldAddNewCarToDb() {
        //given
        CarDTO carDTO = new CarDTO("", "Ferrari", "LaFerrari", "2019");

        //when
        Car carModified = carService.addNewCar(carDTO);
        //then
        ArgumentCaptor<Car> carArgumentCaptor = ArgumentCaptor.forClass(Car.class);
        verify(carRepository).save(carArgumentCaptor.capture());
        Car carCaptured = carArgumentCaptor.getValue();
        assertThat(carCaptured).isEqualTo(carModified);
    }*/

/*    @Test
    void shouldDeleteCarFromDb() {
        //given
        Car car = new Car("Ferrari", "LaFerrari", "2019");
        String vin = car.getVin();
        //given(carRepository.findById(vin)).willReturn(Optional.of(car)); //sprawdz tez metode when, given to BDD. To jest STUB
        when(carCrudRepository.findById(vin)).thenReturn(Optional.of(car)); //wykonuje metode findby zeby zmokowac dane
        //doReturn(Optional.of(car)).when(carRepository).findById(vin); //NIe wykonuje metody findby zeby zmokowac dane
        //when
        carService.deleteCar(vin);
        //verify(carRepository, times(1)).delete(car);
        then(carCrudRepository).should().delete(car); //MockitoBDD
    }*/

    @Test
    void shouldThrowExceptionIfVinIsNotInDb() {
        //given
        String vin = "N9EG5HTCJHSV01HE7";

        //then
        assertThatThrownBy(() -> carService.deleteCar(vin))
                .isInstanceOf(CarService.NoCarWithThisVinInDbException.class)
                .hasMessageContaining("There is no car with this VIN number");
    }

/*    @Test
    @Disabled
    void updateCar() {
        //given
        Car car = new Car("Ferrari", "LaFerrari", "2019");
        String vin = car.getVin();
        CarDTO carDTO = new CarDTO(vin, "Ferrari", "F450", "2009");
        when(carCrudRepository.findById(vin)).thenReturn(Optional.of(car));
        //when
        carService.updateCar(vin, carDTO);
        //then
        verify(carCrudRepository).save(car);
    }*/

    @Test
    void shouldGetCarFromDb() {
        //given
        Car car = new Car("Ferrari", "LaFerrari", "2019");
        String vin = car.getVin();

        //when
        Car car1 = carService.getCar(vin);

        //then
        assertThat(car1).isNotNull();
    }

    @Test
    void shouldThrowNoCarInDBException() {
        String vin = "N9EG5HTCJHSV01HE7";
        assertThatThrownBy(() -> carService.getCar(vin))
                .isInstanceOf(CarService.NoCarWithThisVinInDbException.class)
                .hasMessageContaining("There is no car with this VIN number");
    }

    @Test
    void checkVinLength() {
        //given
        String vin = "N9EG5HTCJHSV01HE7";
        //then
        assertThatCode(() -> carService.checkVinLength(vin)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"N9EG5HTCJHSV01HE7", "00000000000000000"})
    void checkVinLength(String vin) {
        assertThatCode(() -> carService.checkVinLength(vin)).doesNotThrowAnyException();
    }


    @ParameterizedTest
    @ValueSource(strings = {"N9EG5HTCJHSV0", "N9EG5HTCJHSV0JDLAINCOA", ""})
    void checkIfInappropriateVinLengthThrowsException(String vin) {
        assertThatThrownBy(() -> carService.checkVinLength(vin))
                .isInstanceOf(CarService.InvalidVINLengthException.class)
                .hasMessageContaining("Invalid VIN length. There are");
    }
}