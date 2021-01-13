package pl.car.automanager.persistence.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.car.automanager.commons.enums.FuelEnum;
import pl.car.automanager.persistence.repository.CarRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CarTestSuite {

    @Autowired
    private CarRepository carRepository;

    @Test
    public void testFindById() {
        //Given
        Car car = carRepository.save(createCar());
        Long carId = car.getId();
        //When
        Car dbCar = carRepository.findById(carId).orElse(null);
        //Then
        Assertions.assertNotNull(dbCar);
    }

    @Test
    public void testFindByIdShouldThrowException() {
        //Given
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            carRepository.findById(0L).orElseThrow(IllegalArgumentException::new);
        });
    }

    @Test
    public void testAddCar() {
        //Given
        Car car = createCar();
        //When
        Car savedCar = carRepository.save(car);
        //Then
        Assertions.assertNotNull(savedCar);
    }

    @Test
    public void testAddCarShouldThrowException() {
        //Given
        Car car = createCar();
        car.setBrand(null);
        //When & Then
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            carRepository.save(car);
        });
    }

    @Test
    public void testUpdateCar() {
        //Given
        Car car = carRepository.save(createCar());
        car.setBrand("Ford");
        car.setModel("Mondeo");
        car.setVin("TESTVIN");
        car.setEngine("1.6 TEST");
        car.setAmountOfDoors(3);
        car.setFuel(FuelEnum.DIESEL);
        //When
        Car updatedCar = carRepository.save(car);
        //Then
        Assertions.assertEquals("Ford", updatedCar.getBrand());
        Assertions.assertEquals("Mondeo", updatedCar.getModel());
        Assertions.assertEquals("TESTVIN", updatedCar.getVin());
        Assertions.assertEquals("1.6 TEST", updatedCar.getEngine());
        Assertions.assertEquals(3, updatedCar.getAmountOfDoors());
        Assertions.assertEquals(FuelEnum.DIESEL, updatedCar.getFuel());
        Assertions.assertNotNull(updatedCar.getUser());
    }

    @Test
    public void testDeleteCar() {
        //Given
        Car car = carRepository.save(createCar());
        //When
        carRepository.delete(car);
        //Then
        Assertions.assertFalse(carRepository.existsById(car.getId()));
    }


    private Car createCar() {
        return Car.builder()
                .brand("OPEL")
                .model("VECTRA C")
                .vin("VINTESTVINTESTVINTEST")
                .engine("2.0 TDCI")
                .amountOfDoors(5)
                .fuel(FuelEnum.PETROL)
                .user(createUser())
                .build();
    }

    private User createUser() {
        return User.builder()
                .firstName("Test Name")
                .email("test@test.pl")
                .login("Test_Login")
                .password("password")
                .build();
    }
}
