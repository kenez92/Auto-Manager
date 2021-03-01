package pl.car.automanager.persistence.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.car.automanager.commons.enums.FuelEnum;
import pl.car.automanager.persistence.entity.Car;
import pl.car.automanager.persistence.entity.Expense;
import pl.car.automanager.persistence.entity.expanses.*;
import pl.car.automanager.persistence.repository.CarRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CarServiceCarTest {

    private CarService carService;
    @Autowired @Mock
    private CarRepository carRepository;

    private LocalDate expenseDate;
    private LocalDate insuranceStart;
    private LocalDate insuranceEnd;
    private BigDecimal cost;
    private String expenseDescription;
    private Double liters;
    private String distance;
    private LocalDate nextExpenseDate;
    private String faults;
    private Car car;

    @BeforeEach
    public void setup() {
        carService = new CarService(carRepository);
        expenseDate = LocalDate.now();
        insuranceStart = LocalDate.now();
        insuranceEnd = LocalDate.now().plusYears(1L);
        cost = new BigDecimal("500");
        expenseDescription = "Default value";
        liters = 45.00D;
        distance = "115000";
        nextExpenseDate = LocalDate.now().plusYears(1L);
        faults = "Default faults in car";
        car = createCarOne();
    }

    @Test
    public void shouldAddNewCar() {
        //given
        //when
        Car savedCar = carService.addCar(car);
        //then
        assertNotNull(savedCar);
    }

    @Test
    public void shouldReturnCarById() {
        //given
        Car savedCar = carService.addCar(createCarOne());
        //when
        Car carById = carService.findCarById(savedCar.getId());
        //then
        assertNotNull(carById);
    }

    @Test
    public void shouldReturnCarsOfUser() {
        //given
        Car carOne = carService.addCar(createCarOne());
        Car carTwo = carService.addCar(createCarTwo());
        //when
        List<Car> cars = new ArrayList<>();
        cars.add(carOne);
        cars.add(carTwo);
        List<Car> dbCars = carService.findAllCars();
        //then
        assertTrue(dbCars.containsAll(cars));
    }

    @Test
    public void shouldAddExpense() {
        //given
        Car savedCar = carService.addCar(car);
        //when
        carService.addExpense(savedCar.getId());
        //then
        assertNotNull(savedCar.getExpense());
    }

    @Test
    public void shouldGetVulcanizationExpense() {
        //given
        Car savedCar = carService.addCar(car);
        //when
        savedCar.setExpense(new Expense());
        savedCar.getExpense().addVulcanization(Vulcanization.builder().build());
        //then
        assertTrue(carService.getVulcanization(savedCar.getId()).size() > 0);
    }

    @Test
    public void shouldGetInsuranceExpense() {
        //given
        Car savedCar = carService.addCar(car);

        //when
        savedCar.setExpense(new Expense());
        savedCar.getExpense().addInsurance(Insurance.builder().build());
        //then
        assertTrue(carService.getInsurance(savedCar.getId()).size() > 0);
    }

    @Test
    public void shouldGetRefuelingExpense() {
        //given
        Car savedCar = carService.addCar(car);
        //when
        savedCar.setExpense(new Expense());
        savedCar.getExpense().addRefueling(Refueling.builder().build());
        //then
        assertTrue(carService.getRefueling(savedCar.getId()).size() > 0);
    }

    @Test
    public void shouldGetRegistrationExpense() {
        //given
        Car savedCar = carService.addCar(car);
        //when
        savedCar.setExpense(new Expense());
        savedCar.getExpense().addRegistration(Registration.builder().build());
        //then
        assertTrue(carService.getRegistration(savedCar.getId()).size() > 0);
    }

    @Test
    public void shouldGetServiceExpense() {
        //given
        Car savedCar = carService.addCar(car);
        //when
        savedCar.setExpense(new Expense());
        savedCar.getExpense().addRService(ServiceCar.builder().build());
        //then
        assertTrue(carService.getServices(savedCar.getId()).size() > 0);
    }

    @Test
    public void shouldGetRepairExpense() {
        //given
        Car savedCar = carService.addCar(car);
        //when
        savedCar.setExpense(new Expense());
        savedCar.getExpense().addRepair(Repair.builder().build());
        //then
        assertTrue(carService.getRepairs(savedCar.getId()).size() > 0);
    }

    @Test
    public void shouldGetSummaryCostFromExpense() {
        //given
        Car prepareCar = createCarOne();
        prepareCar.setExpense(new Expense());
        prepareCar.getExpense().addRepair(Repair.builder().cost(new BigDecimal("250")).build());
        prepareCar.getExpense().addInsurance(Insurance.builder().cost(new BigDecimal("500")).build());
        prepareCar.getExpense().addRService(ServiceCar.builder().cost(new BigDecimal("650")).build());
        prepareCar.getExpense().addVulcanization(Vulcanization.builder().cost(new BigDecimal("40")).build());
        prepareCar.getExpense().addRegistration(Registration.builder().cost(new BigDecimal("150")).build());
        prepareCar.getExpense().addRefueling(Refueling.builder().cost(new BigDecimal("250")).build());
        //when
        Car savedCar = carService.addCar(prepareCar);
        //then
        assertTrue(carService.getSummaryCost(savedCar.getId()).contains("1840"));

    }

    @Test
    public void shouldAddRefuelingExpense() {
        //given
        Car savedCar = carService.addCar(car);
        //when
        savedCar.setExpense(new Expense());
        carService.addRefuelingExpense(savedCar.getId(), expenseDate, cost,
                liters);
        //then
        assertFalse(savedCar.getExpense().getRefueling().isEmpty());
    }

    @Test
    public void shouldServiceExpense() {
        //given
        Car savedCar = carService.addCar(car);
        //when
        savedCar.setExpense(new Expense());
        carService.addServiceExpense(savedCar.getId(), expenseDate, cost, distance, nextExpenseDate);
        //then
        assertFalse(savedCar.getExpense().getServiceCars().isEmpty());
    }

    @Test
    public void shouldAddInsuranceExpense() {
        //given
        Car savedCar = carService.addCar(car);
        //when
        savedCar.setExpense(new Expense());
        carService.addInsuranceExpense(savedCar.getId(), expenseDate, cost,
                insuranceStart, insuranceEnd, expenseDescription);
        //then
        assertFalse(savedCar.getExpense().getInsurances().isEmpty());
    }

    @Test
    public void shouldAddRegistrationExpense() {
        //given
        Car savedCar = carService.addCar(car);
        //when
        savedCar.setExpense(new Expense());
        carService.addRegistrationExpense(savedCar.getId(), expenseDate, cost,
                nextExpenseDate, faults);
        //then
        assertFalse(savedCar.getExpense().getRegistrations().isEmpty());
    }

    @Test
    public void shouldAddRepairExpense() {
        //given
        Car savedCar = carService.addCar(car);
        //when
        savedCar.setExpense(new Expense());
        carService.addRepairExpense(savedCar.getId(), expenseDate, cost, expenseDescription);
        //then
        assertFalse(savedCar.getExpense().getRepairs().isEmpty());
    }

    @Test
    public void shouldAddVulcanizationExpense() {
        //given
        Car savedCar = carService.addCar(car);
        //when
        savedCar.setExpense(new Expense());
        carService.addVulcanizationExpense(savedCar.getId(), expenseDate, cost, expenseDescription);
        //then
        assertFalse(savedCar.getExpense().getVulcanization().isEmpty());
    }

    private Car createCarOne() {
        return Car.builder()
                .amountOfDoors(4)
                .brand("Opel")
                .fuel(FuelEnum.PETROL)
                .model("Astra")
                .vin("DSSAD556654")
                .engine("1450")
                .build();
    }

    private Car createCarTwo() {
        return Car.builder()
                .amountOfDoors(3)
                .brand("Ford")
                .fuel(FuelEnum.DIESEL)
                .model("Ka")
                .vin("DFFAD556654")
                .engine("1540")
                .build();
    }
}