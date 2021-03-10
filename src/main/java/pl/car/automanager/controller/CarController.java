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

import java.math.BigDecimal;
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
    public ResponseEntity<Car> addCar(@RequestBody RestCarCommand command) {
        Car car = carUseCase.addCar(command.toCreateCommand());
        return ResponseEntity.status(CREATED).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(ACCEPTED)
    public void updateCar(@PathVariable Long id, @RequestBody RestCarCommand command) {
        UpdateCarResponse response = carUseCase.updateCar(command.toUpdateCommand(id));
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
        private String brand;

        private String model;

        private String vin;

        private String engine;

        private Integer amountOfDoors;

        private FuelEnum fuel;

        UpdateCarCommand toUpdateCommand(Long id) {
            return new UpdateCarCommand(id, brand, model, vin, engine, amountOfDoors, fuel);
        }

        /*
         * this method has createUser() as parameter, necessary only for correct compilation
         * we will use CurrentUser which will be with current session
         * */
        CreateCarCommand toCreateCommand() {
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
}
