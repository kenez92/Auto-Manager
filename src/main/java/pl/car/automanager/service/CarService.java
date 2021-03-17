package pl.car.automanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.car.automanager.boundary.car.CarUseCase;
import pl.car.automanager.persistence.entity.Car;
import pl.car.automanager.persistence.entity.Expense;
import pl.car.automanager.persistence.entity.expanses.*;
import pl.car.automanager.persistence.repository.CarRepository;

import javax.transaction.Transactional;
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
    @Transactional
    public Car addCar(CreateCarCommand command) {
        return carRepository.save(toCar(command));
    }

    @Override
    @Transactional
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
    @Transactional
    public UpdateCarResponse addInsuranceExpense(CreateExpenseInsuranceCommand command) {
        return carRepository.findById(command.getId())
                .map(car -> {
                    car.getExpense().addInsurance(toInsurance(command,car.getExpense()));
                    return UpdateCarResponse.SUCCESS;
                })
                .orElseGet(() -> new UpdateCarResponse(false, Collections.singletonList("Car not found")));
    }

    @Override
    @Transactional
    public UpdateCarResponse addMaintenanceExpense(CreateExpenseMaintenanceCommand command) {
        return carRepository.findById(command.getId())
                .map(car -> {
                    car.getExpense().addMaintenance(toMaintenance(command,car.getExpense()));
                    return UpdateCarResponse.SUCCESS;
                })
                .orElseGet(() -> new UpdateCarResponse(false, Collections.singletonList("Car not found")));
    }

    @Override
    @Transactional
    public UpdateCarResponse addRefuelingExpense(CreateExpenseRefuelingCommand command) {
        return carRepository
                .findById(command.getId())
                .map(car -> {
                    car.getExpense().addRefueling(toRefueling(command, car.getExpense()));
                    return UpdateCarResponse.SUCCESS;
                })
                .orElseGet(() -> new UpdateCarResponse(
                        false, Collections.singletonList("Car not found")));
    }

    @Override
    @Transactional
    public UpdateCarResponse addRegisterExpense(CreateExpenseRegisterCommand command) {
        return carRepository
                .findById(command.getId())
                .map(car -> {
                    car.getExpense().addRegistration(toRegister(command, car.getExpense()));
                    return UpdateCarResponse.SUCCESS;
                })
                .orElseGet(() -> new UpdateCarResponse(
                        false, Collections.singletonList("Car not found")));
    }

    @Override
    @Transactional
    public UpdateCarResponse addVulcanizationExpense(CreateExpenseVulcanizationCommand command) {
        return carRepository
                .findById(command.getId())
                .map(car -> {
                    car.getExpense().addVulcanization(toVulcanization(command, car.getExpense()));
                    return UpdateCarResponse.SUCCESS;
                })
                .orElseGet(() -> new UpdateCarResponse(
                        false, Collections.singletonList("Car not found")));
    }

    @Override
    @Transactional
    public UpdateCarResponse addRepairExpense(CreateExpenseRepairCommand command) {
        return carRepository.findById(command.getId())
                .map(car -> {
                    car.getExpense().addRepair(toRepair(command,car.getExpense()));
                    return UpdateCarResponse.SUCCESS;
                })
                .orElseGet(() -> new UpdateCarResponse(false, Collections.singletonList("Car not found")));
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
                .user(command.getUser())
                .build();
    }

    private Insurance toInsurance(CreateExpenseInsuranceCommand command, Expense expense) {
        return Insurance.builder()
                .date(command.getDate())
                .cost(command.getCost())
                .startDate(command.getStart())
                .endDate(command.getEnd())
                .description(command.getDescription())
                .expense(expense)
                .build();
    }

    private Maintenance toMaintenance(CreateExpenseMaintenanceCommand command, Expense expense) {
        return Maintenance.builder()
                .date(command.getDate())
                .cost(command.getCost())
                .distance(command.getDistance())
                .nextServiceDate(command.getNextServiceDate())
                .expense(expense)
                .build();
    }

    private Refueling toRefueling(CreateExpenseRefuelingCommand command, Expense expense) {
        return Refueling.builder()
                .date(command.getDate())
                .cost(command.getCost())
                .liters(command.getLiters())
                .expense(expense)
                .build();
    }

    private Registration toRegister(CreateExpenseRegisterCommand command, Expense expense) {
        return Registration.builder()
                .date(command.getDate())
                .cost(command.getCost())
                .nextRegDate(command.getDate())
                .faults(command.getFaults())
                .expense(expense)
                .build();
    }

    private Repair toRepair(CreateExpenseRepairCommand command, Expense expense) {
        return Repair.builder()
                .date(command.getDate())
                .cost(command.getCost())
                .repairDescription(command.getDescription())
                .expense(expense)
                .build();
    }

    private Vulcanization toVulcanization(CreateExpenseVulcanizationCommand command, Expense expense) {
        return Vulcanization.builder()
                .date(command.getDate())
                .cost(command.getCost())
                .description(command.getDescription())
                .expense(expense)
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
