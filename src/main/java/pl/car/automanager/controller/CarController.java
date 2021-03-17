package pl.car.automanager.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.car.automanager.boundary.car.CarUseCase;
import pl.car.automanager.commons.enums.FuelEnum;
import pl.car.automanager.persistence.entity.Car;
import pl.car.automanager.persistence.entity.Expense;
import pl.car.automanager.persistence.entity.User;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static pl.car.automanager.boundary.car.CarUseCase.*;
import static pl.car.automanager.commons.enums.FuelEnum.PETROL;

@RequestMapping("/car")
@RestController
@AllArgsConstructor
public class CarController {
    private final CarUseCase carUseCase;

    @GetMapping
    @ResponseStatus(OK)
    public List<Car> getAllCars() {
        return carUseCase.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        return carUseCase
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ResponseEntity<Car> addCar(@Valid @RequestBody RestCarCommand command) {
        Car car = carUseCase.addCar(command.toCreateCarCommand());
        return ResponseEntity.status(CREATED).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(ACCEPTED)
    public void updateCar(@PathVariable Long id, @RequestBody RestCarCommand command) {
        UpdateCarResponse response = carUseCase.updateCar(command.toUpdateCarCommand(id));
        if (!response.isSuccess()) {
            String msg = String.join(", ", response.getErrors());
            throw new ResponseStatusException(BAD_REQUEST, msg);
        }
    }

    @PutMapping("/{id}/insurance")
    @ResponseStatus(ACCEPTED)
    public void insuranceExpense(@PathVariable Long id, @Valid @RequestBody RestInsuranceCommand command) {
        UpdateCarResponse response = carUseCase.addInsuranceExpense(command.toCreateInsurance(id));
        if (!response.isSuccess()) {
            String msg = String.join(", ", response.getErrors());
            throw new ResponseStatusException(BAD_REQUEST, msg);
        }
    }

    @PutMapping("/{id}/maintenance")
    @ResponseStatus(ACCEPTED)
    public void maintenanceExpense(@PathVariable Long id, @Valid @RequestBody RestMaintenanceCommand command) {
        UpdateCarResponse response = carUseCase.addMaintenanceExpense(command.toCreateMaintenance(id));
        if (!response.isSuccess()) {
            String msg = String.join(", ", response.getErrors());
            throw new ResponseStatusException(BAD_REQUEST, msg);
        }
    }

    @PutMapping("/{id}/refuel")
    @ResponseStatus(ACCEPTED)
    public void refuelingExpense(@PathVariable Long id, @Valid @RequestBody RestRefuelingCommand command) {
        UpdateCarResponse response = carUseCase.addRefuelingExpense(command.toCreateRefueling(id));
        if (!response.isSuccess()) {
            String msg = String.join(", ", response.getErrors());
            throw new ResponseStatusException(BAD_REQUEST, msg);
        }
    }

    @PutMapping("/{id}/register")
    @ResponseStatus(ACCEPTED)
    public void registerExpense(@PathVariable Long id, @Valid @RequestBody RestRegisterCommand command) {
        UpdateCarResponse response = carUseCase.addRegisterExpense(command.toCreateRegister(id));
        if (!response.isSuccess()) {
            String msg = String.join(", ", response.getErrors());
            throw new ResponseStatusException(BAD_REQUEST, msg);
        }
    }

    @PutMapping("/{id}/repair")
    @ResponseStatus(ACCEPTED)
    public void repairExpense(@PathVariable Long id, @Valid @RequestBody RestRepairCommand command) {
        UpdateCarResponse response = carUseCase.addRepairExpense(command.toCreateRepair(id));
        if (!response.isSuccess()) {
            String msg = String.join(", ", response.getErrors());
            throw new ResponseStatusException(BAD_REQUEST, msg);
        }
    }

    @PutMapping("/{id}/vulcanization")
    @ResponseStatus(ACCEPTED)
    public void vulcanizationExpense(@PathVariable Long id, @Valid @RequestBody RestVulcanizationCommand command) {
        UpdateCarResponse response = carUseCase.addVulcanizationExpense(command.toCreateVulcanization(id));
        if (!response.isSuccess()) {
            String msg = String.join(", ", response.getErrors());
            throw new ResponseStatusException(BAD_REQUEST, msg);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteCar(@PathVariable Long id) {
        carUseCase.removeById(id);
    }


    @Data
    private static class RestCarCommand {
        @NotBlank
        private String brand;

        @NotBlank
        private String model;

        @NotBlank
        private String vin;

        @NotBlank
        private String engine;

        @NotNull
        @Positive
        private Integer amountOfDoors;

        @NotNull
        private FuelEnum fuel;

        UpdateCarCommand toUpdateCarCommand(Long id) {
            return new UpdateCarCommand(id, brand, model, vin, engine, amountOfDoors, fuel);
        }

        /*
         * this method has createUser() as parameter, necessary only for correct compilation
         * we will use CurrentUser which will be with current session
         * */
        CreateCarCommand toCreateCarCommand() {
            return new CreateCarCommand(brand, model, vin, engine, amountOfDoors, PETROL, createUser(), createExpense());
        }

        private Expense createExpense() {
            return Expense.builder()
                    .summaryCost(BigDecimal.ZERO)
                    .build();
        }

        /*
         * this method is necessary only for correct compilation
         * we will use CurrentUser which will be with current session
         * */
        private User createUser() {
            return User.builder()
                    .email("user@com.pl")
                    .firstName("Jan")
                    .login("jan")
                    .password("password")
                    .build();
        }
    }

    @Data
    private static class RestInsuranceCommand {
        @NotNull @PastOrPresent
        LocalDate date;

        @DecimalMin("0.00") @Positive
        BigDecimal cost;

        @NotNull @PastOrPresent
        LocalDate start;

        @NotNull @Future
        LocalDate end;

        @NotBlank
        String description;

        CreateExpenseInsuranceCommand toCreateInsurance(Long id) {
            return new CreateExpenseInsuranceCommand(id, date, cost, start, end, description);
        }
    }

    @Data
    private static class RestMaintenanceCommand {

        @NotNull @PastOrPresent
        LocalDate date;

        @DecimalMin("0.00") @Positive
        BigDecimal cost;

        @NotBlank
        String distance;

        @NotNull @Future
        LocalDate nextServiceDate;

        CreateExpenseMaintenanceCommand toCreateMaintenance(Long id) {
            return new CreateExpenseMaintenanceCommand(id, date, cost, distance, nextServiceDate);
        }

    }

    @Data
    private static class RestRefuelingCommand {

        @NotNull @PastOrPresent
        LocalDate date;

        @DecimalMin("0.00") @Positive
        BigDecimal cost;

        @NotNull
        Double liters;

        CreateExpenseRefuelingCommand toCreateRefueling(Long id) {
            return new CreateExpenseRefuelingCommand(id, date, cost, liters);
        }

    }

    @Data
    private static class RestRegisterCommand {

        @NotNull @PastOrPresent
        LocalDate date;

        @DecimalMin("0.00") @Positive
        BigDecimal cost;

        @NotNull @Future
        LocalDate next;

        @NotBlank
        String faults;

        CreateExpenseRegisterCommand toCreateRegister(Long id) {
            return new CreateExpenseRegisterCommand(id, date, cost, next, faults);
        }

    }

    @Data
    private static class RestRepairCommand {

        @NotNull @PastOrPresent
        LocalDate date;

        @DecimalMin("0.00") @Positive
        BigDecimal cost;

        @NotBlank
        String description;

        CreateExpenseRepairCommand toCreateRepair(Long id) {
            return new CreateExpenseRepairCommand(id, date, cost, description);
        }

    }

    @Data
    private static class RestVulcanizationCommand {

        @NotNull @PastOrPresent
        LocalDate date;

        @DecimalMin("0.00") @Positive
        BigDecimal cost;

        @NotBlank
        String description;

        CreateExpenseVulcanizationCommand toCreateVulcanization(Long id) {
            return new CreateExpenseVulcanizationCommand(id, date, cost, description);
        }

    }

}











































