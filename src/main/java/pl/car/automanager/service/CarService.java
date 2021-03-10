package pl.car.automanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.car.automanager.boundary.car.CarUseCase;
import pl.car.automanager.persistence.entity.Car;
import pl.car.automanager.persistence.repository.CarRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class CarService implements CarUseCase {

    @Autowired
    private final CarRepository carRepository;

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Optional<Car> findById(Long id) {
        return carRepository.findById(id);
    }

    @Override
    public Car addCar(CreateCarCommand command) {
        return carRepository.save(toCar(command));
    }

    @Override
    public UpdateCarResponse updateCar(UpdateCarCommand command) {
        return carRepository
                .findById(command.getId())
                .map(car -> {
                    updateFields(command, car);
                    return UpdateCarResponse.SUCCESS;
                })
                .orElseGet(() -> new UpdateCarResponse(
                        false, Collections.singletonList("Car not found")));
    }

    @Override
    public void removeById(Long id) {
        carRepository.findById(id).ifPresent(
                carRepository::delete);
    }

    private Car toCar(CreateCarCommand command) {
        return Car.builder()
                .brand(command.getBrand())
                .model(command.getModel())
                .vin(command.getVin())
                .engine(command.getEngine())
                .amountOfDoors(command.getAmountOfDoors())
                .fuel(command.getFuel())
                .expense(command.getExpense())
                .build();
    }

    private void updateFields(UpdateCarCommand command, Car car) {
        if (command.getBrand() != null) {
            car.setBrand(command.getBrand());
        }
        if (command.getModel() != null) {
            car.setModel(command.getModel());
        }
        if (command.getVin() != null) {
            car.setVin(command.getVin());
        }
        if (command.getEngine() != null) {
            car.setEngine(command.getEngine());
        }
        if (command.getAmountOfDoors() != null) {
            car.setAmountOfDoors(command.getAmountOfDoors());
        }
        if (command.getFuel() != null) {
            car.setFuel(command.getFuel());
        }
    }
}
