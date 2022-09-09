package org.murpol.restcar.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CarServiceTest {

    private CarRepository carRepository = new InMemoryCarRepository();
    // @InjectMocks
    private CarService carService;


    @BeforeEach
    void setUp() {
        carService = new CarService(carRepository);
    }

    @Test
    void getEmptyCars() {
        //when
        var cars = carService.getCars();
        //then
        assertThat(cars).isEmpty();
    }

    @Test
    void getCars() {
        //given
        Car car = new Car();
        carRepository.store(car);

        //when
        var cars = carService.getCars();
        //then
        assertThat(cars).hasSize(1);
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

    @Test
    void shouldDeleteCarFromDb() {
        //given
        Car car = new Car("Ferrari", "LaFerrari", "2019");
        String vin = car.getVin();
        carRepository.store(car);

        var beforeCount = carRepository.findAll().size();

        //when
        carService.deleteCar(vin);

        //then
        var afterCount = carRepository.findAll().size();

        assertThat(beforeCount).isEqualTo( afterCount + 1);

        var result = carRepository.findByVin(vin);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldThrowExceptionIfVinIsNotInDb() {
        //given
        String vin = "N9EG5HTCJHSV01HE7";

        //then
        assertThatThrownBy(() -> carService.deleteCar(vin))
                .isInstanceOf(CarService.NoCarWithThisVinInDbException.class)
                .hasMessageContaining("There is no car with this VIN number");
    }

    @Test
    void updateCar() {
        //given
        Car car = new Car("Ferrari", "LaFerrari", "2019");
        String vin = car.getVin();
        CarDTO carDTO = new CarDTO(vin, "Ferrari", "F450", "2009");

        carRepository.store(car);

        //when
        carService.updateCar(vin, carDTO);
        //then
        var expectedCar = carRepository.findByVin(vin);
        assertThat(expectedCar).get().extracting(Car::getModel).isEqualTo("F450");
    }

    @Test
    void shouldGetCarFromDb() {
        //given
        Car car = new Car("Ferrari", "LaFerrari", "2019");
        String vin = car.getVin();

        carRepository.store(car);
        //when

        carService.getCar(vin);

        //then
        var expectedCar = carRepository.findByVin(vin);
        assertThat(expectedCar).hasValue(car);
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
        assertThatCode(() -> carService.checkVin(vin)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"N9EG5HTCJHSV01HE7", "00000000000000000"})
    void checkVinLength(String vin) {
        assertThatCode(() -> carService.checkVin(vin)).doesNotThrowAnyException();
    }


    @ParameterizedTest
    @ValueSource(strings = {"N9EG5HTCJHSV0", "N9EG5HTCJHSV0JDLAINCOA", ""})
    void checkIfInappropriateVinLengthThrowsException(String vin) {
        assertThatThrownBy(() -> carService.checkVin(vin))
                .isInstanceOf(CarService.InvalidVINLengthException.class)
                .hasMessageContaining("Invalid VIN length. There are");
    }
}