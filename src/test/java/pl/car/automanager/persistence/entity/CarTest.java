package pl.car.automanager.persistence.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import pl.car.automanager.commons.enums.FuelEnum;
import pl.car.automanager.persistence.repository.CarRepository;

@SpringBootTest
public class CarTest {

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
        try {
            Assertions.assertNotNull(dbCar);
        } finally {
            carRepository.deleteById(carId);
        }
    }

    @Test
    public void testFindByIdShouldThrowException() {
        //Given
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Car car = carRepository.findById(0L).orElseThrow(IllegalArgumentException::new);
        });
    }

    @Test
    public void testAddCar() {
        //Given
        Car car = createCar();
        //When
        Car savedCar = carRepository.save(car);
        //Then
        try {
            Assertions.assertNotNull(savedCar);
        } finally {
            carRepository.deleteById(savedCar.getId());
        }
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
        car.setFuelEnum(FuelEnum.DIESEL);
        //When
        Car updatedCar = carRepository.save(car);
        //Then
        try {
            Assertions.assertEquals("Ford", updatedCar.getBrand());
            Assertions.assertEquals("Mondeo", updatedCar.getModel());
            Assertions.assertEquals("TESTVIN", updatedCar.getVin());
            Assertions.assertEquals("1.6 TEST", updatedCar.getEngine());
            Assertions.assertEquals(3, updatedCar.getAmountOfDoors());
            Assertions.assertEquals(FuelEnum.DIESEL, updatedCar.getFuelEnum());
        } finally {
            carRepository.deleteById(car.getId());
        }
    }


    private Car createCar() {
        return Car.builder()
                .brand("OPEL")
                .model("VECTRA C")
                .vin("VINTESTVINTESTVINTEST")
                .engine("2.0 TDCI")
                .amountOfDoors(5)
                .fuelEnum(FuelEnum.PETROL)
                .build();
    }
}
