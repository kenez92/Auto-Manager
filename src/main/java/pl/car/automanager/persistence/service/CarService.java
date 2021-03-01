package pl.car.automanager.persistence.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.car.automanager.persistence.entity.Car;
import pl.car.automanager.persistence.entity.Expense;
import pl.car.automanager.persistence.entity.expanses.*;
import pl.car.automanager.persistence.repository.CarRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CarService {

    @Autowired
    private final CarRepository carRepository;

    public Car addCar(Car car){
        return carRepository.save(car);
    }

    public Car findCarById(Long carId){
        return carRepository.getOne(carId);
    }

    public List<Car> findAllCars(){
        return carRepository.findAll();
    }

    public List<Repair> getRepairs(Long carId){
        return findCarById(carId).getExpense().getRepairs();
    }

    public List<Insurance> getInsurance(Long carId){
        return findCarById(carId).getExpense().getInsurances();
    }

    public List<Refueling> getRefueling(Long carId){
        return findCarById(carId).getExpense().getRefueling();
    }

    public List<Registration> getRegistration(Long carId){
        return findCarById(carId).getExpense().getRegistrations();
    }

    public List<ServiceCar> getServices(Long carId){
        return findCarById(carId).getExpense().getServiceCars();
    }

    public List<Vulcanization> getVulcanization(Long carId){
        return findCarById(carId).getExpense().getVulcanization();
    }

    public String getSummaryCost(Long carId){
        return findCarById(carId).getExpense().getSummaryCost().toString();
    }

    public void addExpense(Long carId) {
        carRepository.findById(carId).ifPresent(car ->
                car.setExpense(createExpense()));
    }

    public void addRefuelingExpense(Long carId, LocalDate date, BigDecimal cost, Double liters) {
        carRepository.findById(carId).ifPresent(
                car -> car.getExpense().addRefueling(createRefueling(date, cost, liters))
        );
    }

    public void addServiceExpense(Long carId, LocalDate date, BigDecimal cost,
                                  String distance, LocalDate nextService) {
        carRepository.findById(carId).ifPresent(
                car -> car.getExpense().addRService(createService(date, cost, distance, nextService))
        );
    }

    public void addInsuranceExpense(Long carId, LocalDate date, BigDecimal cost,
                                    LocalDate start, LocalDate end, String description) {
        carRepository.findById(carId).ifPresent(
                car -> car.getExpense().addInsurance(createInsurance(date, cost, start, end, description))
        );
    }

    public void addRegistrationExpense(Long carId, LocalDate date, BigDecimal cost,
                                       LocalDate next, String faults) {
        carRepository.findById(carId).ifPresent(
                car -> car.getExpense().addRegistration(createRegistration(date, cost, next, faults))
        );
    }

    public void addRepairExpense(Long carId, LocalDate date, BigDecimal cost,
                                 String description) {
        carRepository.findById(carId).ifPresent(
                car -> car.getExpense().addRepair(createRepair(date, cost, description))
        );
    }

    public void addVulcanizationExpense(Long carId, LocalDate date, BigDecimal cost,
                                        String description) {
        carRepository.findById(carId).ifPresent(
                car -> car.getExpense().addVulcanization(createVulcanization(date, cost, description))
        );
    }

    private Expense createExpense() {
        return Expense.builder()
                .summaryCost(BigDecimal.ZERO)
                .insurances(new ArrayList<>())
                .serviceCars(new ArrayList<>())
                .registrations(new ArrayList<>())
                .vulcanization(new ArrayList<>())
                .repairs(new ArrayList<>())
                .refueling(new ArrayList<>())
                .build();
    }

    private Insurance createInsurance(LocalDate date, BigDecimal cost, LocalDate start, LocalDate end, String description) {
        return Insurance.builder()
                .date(date)
                .cost(cost)
                .startDate(start)
                .endDate(end)
                .description(description)
                .build();
    }

    private Refueling createRefueling(LocalDate date, BigDecimal cost, Double liters) {
        return Refueling.builder()
                .date(date)
                .cost(cost)
                .liters(liters)
                .build();
    }

    private ServiceCar createService(
            LocalDate date, BigDecimal cost, String distance, LocalDate nextService) {
        return ServiceCar.builder()
                .date(date)
                .cost(cost)
                .distance(distance)
                .nextServiceDate(nextService)
                .build();
    }

    private Registration createRegistration(LocalDate date, BigDecimal cost, LocalDate next, String faults) {
        return Registration.builder()
                .date(date)
                .cost(cost)
                .nextRegDate(next)
                .faults(faults)
                .build();
    }

    private Repair createRepair(LocalDate date, BigDecimal cost, String description) {
        return Repair.builder()
                .date(date)
                .cost(cost)
                .repairDescription(description)
                .build();
    }

    private Vulcanization createVulcanization(LocalDate date, BigDecimal cost, String description) {
        return Vulcanization.builder()
                .date(date)
                .cost(cost)
                .description(description)
                .build();
    }
}
