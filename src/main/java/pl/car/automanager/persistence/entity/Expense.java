package pl.car.automanager.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import pl.car.automanager.persistence.entity.expanses.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "EXPENSE")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column
    private BigDecimal summaryCost;

    @OneToMany(
            cascade = CascadeType.ALL,
            targetEntity = Repair.class,
            mappedBy = "expense",
            fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnoreProperties("expense")
    private List<Repair> repairs = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            targetEntity = Insurance.class,
            mappedBy = "expense",
            fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnoreProperties("expense")
    private List<Insurance> insurances = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            targetEntity = Refueling.class,
            mappedBy = "expense",
            fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnoreProperties("expense")
    private List<Refueling> refueling = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            targetEntity = Registration.class,
            mappedBy = "expense",
            fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnoreProperties("expense")
    private List<Registration> registrations = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            targetEntity = Maintenance.class,
            mappedBy = "expense",
            fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnoreProperties("expense")
    private List<Maintenance> maintenances = new ArrayList<>();


    @OneToMany(
            cascade = CascadeType.ALL,
            targetEntity = Vulcanization.class,
            mappedBy = "expense",
            fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnoreProperties("expense")
    private List<Vulcanization> vulcanization = new ArrayList<>();

    public void addRepair(Repair repair) {
        repairs.add(repair);
        if(repair.getCost() != null){
            summaryCost = repair.getCost().add(getSummaryCost());
        }
    }

    public void addInsurance(Insurance insurance) {
        insurances.add(insurance);
        if (insurance.getCost() != null) {
            summaryCost = insurance.getCost().add(getSummaryCost());
        }
    }

    public void addRefueling(Refueling refueling) {
        this.refueling.add(refueling);
        if(refueling.getCost() != null){
            summaryCost = refueling.getCost().add(getSummaryCost());
        }
    }

    public void addRegistration(Registration registration) {
        registrations.add(registration);
        if(registration.getCost() != null) {
            summaryCost = registration.getCost().add(getSummaryCost());
        }
    }

    public void addMaintenance(Maintenance maintenance) {
        maintenances.add(maintenance);
        if(maintenance.getCost() != null) {
            summaryCost = maintenance.getCost().add(getSummaryCost());
        }
    }

    public void addVulcanization(Vulcanization vulcanization) {
        this.vulcanization.add(vulcanization);
        if(vulcanization.getCost() != null) {
            summaryCost = vulcanization.getCost().add(getSummaryCost());
        }
    }
}
