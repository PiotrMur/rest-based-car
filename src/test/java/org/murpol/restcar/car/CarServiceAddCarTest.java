package org.murpol.restcar.car;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CarServiceAddCarTest {

    @Mock
    private CarCrudRepository carCrudRepository;
    @InjectMocks
    private CarService carService;

/*    @BeforeEach
    void setUp() {
        carService = new CarService(carRepository);
    }*/

    @Test
    @Disabled
    void validateInput() {
    }

    /*@Test
    void createVinAndAddNewCar() {
        //given
        CarDTO carDTO = new CarDTO("", "Ferrari", "LaFerrari", "2019");

        //when
        Car newCar = carService.createVinAndAddNewCar(carDTO);

        //then
        ArgumentCaptor<Car> carArgumentCaptor = ArgumentCaptor.forClass(Car.class);
        verify(carRepository).save(carArgumentCaptor.capture());
        Car carCaptured = carArgumentCaptor.getValue();
        assertThat(carCaptured).isEqualTo(newCar);
    }*/

/*    @Test
    void addNewCar() {
        //given
        CarDTO carDTO = new CarDTO("W4TJB5ALUBGKV3V6W", "Ferrari", "LaFerrari", "2019");

        //when
        Car newlyAddedCar = carService.addNewCar(carDTO);

        //then
        ArgumentCaptor<Car> carArgumentCaptor = ArgumentCaptor.forClass(Car.class);
        verify(carRepository).save(carArgumentCaptor.capture());
        Car carCaptured = carArgumentCaptor.getValue();
        assertThat(carCaptured).isEqualTo(newlyAddedCar);
    }*/
}