package pl.car.automanager.persistence.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.car.automanager.persistence.entity.Expense;
import pl.car.automanager.persistence.entity.expanses.*;
import pl.car.automanager.persistence.repository.ExpenseRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    @Autowired
    private final ExpenseRepository expenseRepository;

    public Expense addRefuelingExpense(LocalDate date, BigDecimal cost, Double liters) {
        Expense expense = createExpense(date, cost);
        Refueling refueling = createRefueling(liters);
        expense.addRefueling(refueling);
        refueling.setExpense(expense);
        return expenseRepository.save(expense);
    }

    public Expense addServiceExpense(LocalDate date, BigDecimal cost,
                                     String distance, LocalDate nextService) {
        Expense expense = createExpense(date, cost);
        pl.car.automanager.persistence.entity.expanses.Service service = createService(distance, nextService);
        expense.addRService(service);
        service.setExpense(expense);
        return expenseRepository.save(expense);
    }

    public Expense addInsuranceExpense(LocalDate date, BigDecimal cost,
                                       LocalDate start, LocalDate end, String description) {
        Expense expense = createExpense(date, cost);
        Insurance insurance = createInsurance(start,end,description);
        expense.addInsurance(insurance);
        insurance.setExpense(expense);
        return expenseRepository.save(expense);
    }

    public Expense addRegistrationExpense(LocalDate date, BigDecimal cost,
                                          LocalDate next, String faults) {
        Expense expense = createExpense(date, cost);
        Registration registration = createRegistration(next,faults);
        expense.addRegistration(registration);
        registration.setExpense(expense);
        return expenseRepository.save(expense);
    }

    public Expense addRepairExpense(LocalDate date, BigDecimal cost,
                                 String description) {
        Expense expense = createExpense(date, cost);
        Repair repair = createRepair(description);
        expense.addRepair(repair);
        repair.setExpense(expense);
        return expenseRepository.save(expense);
    }

    public Expense addVulcanizationExpense(LocalDate date, BigDecimal cost,
                                        String description) {
        Expense expense = createExpense(date, cost);
        Vulcanization vulcanization = createVulcanization(description);
        expense.addVulcanization(vulcanization);
        vulcanization.setExpense(expense);
        return expenseRepository.save(expense);

    }

    private Expense createExpense(LocalDate date, BigDecimal cost) {
        return Expense.builder()
                .date(date)
                .cost(cost)
                .insurances(new ArrayList<>())
                .services(new ArrayList<>())
                .registrations(new ArrayList<>())
                .vulcanization(new ArrayList<>())
                .repairs(new ArrayList<>())
                .refueling(new ArrayList<>())
                .build();
    }

    private Insurance createInsurance(LocalDate start, LocalDate end, String description) {
        return Insurance.builder()
                .startDate(start)
                .endDate(end)
                .description(description)
                .build();
    }

    private Refueling createRefueling(Double liters) {
        return Refueling.builder()
                .liters(liters)
                .build();
    }

    private pl.car.automanager.persistence.entity.expanses.Service createService(String distance, LocalDate nextService) {
        return pl.car.automanager.persistence.entity.expanses.Service.builder()
                .distance(distance)
                .nextServiceDate(nextService)
                .build();
    }

    private Registration createRegistration(LocalDate next, String faults) {
        return Registration.builder()
                .nextRegDate(next)
                .faults(faults)
                .build();
    }

    private Repair createRepair(String description) {
        return Repair.builder()
                .repairDescription(description)
                .build();
    }

    private Vulcanization createVulcanization(String description) {
        return Vulcanization.builder()
                .description(description)
                .build();
    }
}
