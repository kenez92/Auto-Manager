package pl.car.automanager.boundary.car;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import pl.car.automanager.commons.enums.FuelEnum;
import pl.car.automanager.persistence.entity.Car;
import pl.car.automanager.persistence.entity.Expense;
import pl.car.automanager.persistence.entity.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface CarUseCase {
    List<Car> findAll();

    Optional<Car> findById(Long id);

    Car addCar(CreateCarCommand command);

    UpdateCarResponse updateCar(UpdateCarCommand command);

    void removeById(Long id);

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
    class UpdateCarResponse{
        public static UpdateCarResponse SUCCESS = new UpdateCarResponse(true, Collections.emptyList());
        boolean success;
        List<String> errors;
    }
}
