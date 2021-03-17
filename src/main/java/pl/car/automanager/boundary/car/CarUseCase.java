package pl.car.automanager.boundary.car;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import pl.car.automanager.commons.enums.FuelEnum;
import pl.car.automanager.persistence.entity.Car;
import pl.car.automanager.persistence.entity.Expense;
import pl.car.automanager.persistence.entity.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface CarUseCase {
    List<Car> findAll();

    Optional<Car> findById(Long id);

    Car addCar(CreateCarCommand command);

    UpdateCarResponse updateCar(UpdateCarCommand command);

    UpdateCarResponse addRefuelingExpense(CreateExpenseRefuelingCommand command);

    void removeById(Long id);

    UpdateCarResponse addRepairExpense(CreateExpenseRepairCommand command);

    UpdateCarResponse addInsuranceExpense(CreateExpenseInsuranceCommand command);

    UpdateCarResponse addMaintenanceExpense(CreateExpenseMaintenanceCommand command);

    UpdateCarResponse addRegisterExpense(CreateExpenseRegisterCommand command);

    UpdateCarResponse addVulcanizationExpense(CreateExpenseVulcanizationCommand command);

    @Value
    class CreateCarCommand {
        String brand;
        String model;
        String vin;
        String engine;
        Integer amountOfDoors;
        FuelEnum fuel;
        User user;
        Expense expense;
    }

    @Value
    @Builder
    @AllArgsConstructor
    class UpdateCarCommand {
        Long id;
        String brand;
        String model;
        String vin;
        String engine;
        Integer amountOfDoors;
        FuelEnum fuel;
    }

    @Value
    @Builder
    @AllArgsConstructor
    class CreateExpenseRefuelingCommand {
        Long id;
        LocalDate date;
        BigDecimal cost;
        Double liters;
    }

    @Value
    @Builder
    @AllArgsConstructor
    class CreateExpenseMaintenanceCommand{
        Long id;
        LocalDate date;
        BigDecimal cost;
        String distance;
        LocalDate nextServiceDate;
    }

    @Value
    @Builder
    @AllArgsConstructor
    class CreateExpenseRepairCommand{
        Long id;
        LocalDate date;
        BigDecimal cost;
        String description;
    }

    @Value
    @Builder
    @AllArgsConstructor
    class CreateExpenseInsuranceCommand{
        Long id;
        LocalDate date;
        BigDecimal cost;
        LocalDate start;
        LocalDate end;
        String description;
    }

    @Value
    @Builder
    @AllArgsConstructor
    class CreateExpenseRegisterCommand{
        Long id;
        LocalDate date;
        BigDecimal cost;
        LocalDate next;
        String faults;
    }

    @Value
    @Builder
    @AllArgsConstructor
    class CreateExpenseVulcanizationCommand{
        Long id;
        LocalDate date;
        BigDecimal cost;
        String description;
    }

    @Value
    class UpdateCarResponse{
        public static UpdateCarResponse SUCCESS = new UpdateCarResponse(true, Collections.emptyList());
        boolean success;
        List<String> errors;
    }
}
