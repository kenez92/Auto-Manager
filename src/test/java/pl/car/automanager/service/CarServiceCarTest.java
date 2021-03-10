package pl.car.automanager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.car.automanager.persistence.entity.Car;
import pl.car.automanager.persistence.entity.Expense;
import pl.car.automanager.persistence.entity.User;
import pl.car.automanager.persistence.entity.expanses.*;
import pl.car.automanager.persistence.repository.CarRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static pl.car.automanager.boundary.car.CarUseCase.CreateCarCommand;
import static pl.car.automanager.commons.enums.FuelEnum.DIESEL;
import static pl.car.automanager.commons.enums.FuelEnum.PETROL;

@DataJpaTest
class CarServiceCarTest {

    private CarService carService;
    @Autowired
    @Mock
    private CarRepository carRepository;
    private CreateCarCommand firstCar;
    private CreateCarCommand secondCar;
    private User user;

    @BeforeEach
    public void setup() {
        carService = new CarService(carRepository);
        user = createUser();
        firstCar = createCarOne();
        secondCar = createCarTwo();
    }

    @Test
    public void shouldAddNewCar() {
        //given
        //when
        Car savedCar = carService.addCar(firstCar);
        //then
        assertNotNull(savedCar);
    }

    @Test
    public void shouldReturnCarById() {
        //given
        Car savedCar = carService.addCar(createCarOne());
        //when
        Optional<Car> carById = carService.findById(savedCar.getId());
        //then
        assertTrue(carById.isPresent());
    }


    @Test
    public void shouldReturnCarsOfUser() {
        //given
        Car carOne = carService.addCar(createCarOne());
        Car carTwo = carService.addCar(createCarTwo());
        //when
        user.addCar(carOne);
        user.addCar(carTwo);
        //then
        assertEquals(2, user.getCars().size());
    }

    @Test
    public void shouldAddExpense() {
        //given
        Car savedCar = carService.addCar(firstCar);
        //when
        //carService.addExpense(savedCar.getId());
        //then
        assertNotNull(savedCar.getExpense());
    }

    @Test
    public void shouldGetVulcanizationExpense() {
        //given
        Car savedCar = carService.addCar(secondCar);
        //when
        savedCar.getExpense().addVulcanization(Vulcanization.builder().build());
        //then
        assertTrue(savedCar.getExpense().getVulcanization().size() > 0);
    }

    @Test
    public void shouldGetInsuranceExpense() {
        //given
        Car savedCar = carService.addCar(firstCar);
        //when
        savedCar.getExpense().addInsurance(Insurance.builder().build());
        //then
        assertTrue(savedCar.getExpense().getInsurances().size() > 0);
    }

    @Test
    public void shouldGetRefuelingExpense() {
        //given
        Car savedCar = carService.addCar(secondCar);
        //when
        savedCar.getExpense().addRefueling(Refueling.builder().build());
        //then
        assertTrue(savedCar.getExpense().getRefueling().size() > 0);
    }

    @Test
    public void shouldGetRegistrationExpense() {
        //given
        Car savedCar = carService.addCar(firstCar);
        //when
        savedCar.getExpense().addRegistration(Registration.builder().build());
        //then
        assertTrue(savedCar.getExpense().getRegistrations().size() > 0);
    }

    @Test
    public void shouldGetServiceExpense() {
        //given
        Car savedCar = carService.addCar(secondCar);
        //when
        savedCar.getExpense().addRService(Maintenance.builder().build());
        //then
        assertTrue(savedCar.getExpense().getMaintenances().size() > 0);
    }

    @Test
    public void shouldGetRepairExpense() {
        //given
        Car savedCar = carService.addCar(firstCar);
        //when
        savedCar.getExpense().addRepair(Repair.builder().build());
        //then
        assertTrue(savedCar.getExpense().getRepairs().size() > 0);
    }

    @Test
    public void shouldGetSummaryCostFromExpense() {
        //given
        firstCar.getExpense().addRepair(Repair.builder().cost(new BigDecimal("250")).build());
        firstCar.getExpense().addInsurance(Insurance.builder().cost(new BigDecimal("500")).build());
        firstCar.getExpense().addRService(Maintenance.builder().cost(new BigDecimal("650")).build());
        firstCar.getExpense().addVulcanization(Vulcanization.builder().cost(new BigDecimal("40")).build());
        firstCar.getExpense().addRegistration(Registration.builder().cost(new BigDecimal("150")).build());
        firstCar.getExpense().addRefueling(Refueling.builder().cost(new BigDecimal("250")).build());
        //when
        String summary = firstCar.getExpense().getSummaryCost().toString();
        //then
        assertTrue(summary.contains("1840"));
    }

    private CreateCarCommand createCarOne() {
        return new CreateCarCommand("Opel", "Astra", "DSSAD556654", "1450", 4, PETROL, createUser(), createExpense());
    }

    private CreateCarCommand createCarTwo() {
        return new CreateCarCommand("Ford", "Ka", "DFFAD556654", "1540", 3, DIESEL, createUser(), createExpense());
    }

    private Expense createExpense() {
        return Expense.builder()
                .summaryCost(BigDecimal.ZERO)
                .refueling(new ArrayList<>())
                .repairs(new ArrayList<>())
                .vulcanization(new ArrayList<>())
                .registrations(new ArrayList<>())
                .insurances(new ArrayList<>())
                .maintenances(new ArrayList<>())
                .build();
    }

    private User createUser() {
        return User.builder()
                .email("user@com.pl")
                .firstName("Jan")
                .login("jan")
                .password("password")
                .cars(new ArrayList<>())
                .build();
    }
}