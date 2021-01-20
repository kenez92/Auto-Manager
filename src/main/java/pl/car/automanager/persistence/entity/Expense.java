package pl.car.automanager.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.car.automanager.persistence.entity.expanses.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "EXPENSE")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long expenseId;

    @OneToMany(mappedBy = "expense")
    Collection<Insurance> insurances;

    @OneToMany(mappedBy = "expense")
    Collection<Refueling> refueling;

    @OneToMany(mappedBy = "expense")
    Collection<Registration> regReviews;

    @OneToMany(mappedBy = "expense")
    Collection<Service> servReviews;

    @OneToMany(mappedBy = "expense")
    Collection<Repair> repairs;

    @OneToMany(mappedBy = "expense")
    Collection<Vulcanization> vulcanization;

    public void addInsurance(Insurance insurance) {
        if (insurances == null) {
            insurances = new HashSet<>();
        }
        insurances.add(insurance);
    }

    public void addRefuel(Refueling refuel) {
        if (refueling == null) {
            refueling = new HashSet<>();
        }
        refueling.add(refuel);
    }

    public void addRegistration(Registration registration) {
        if (regReviews == null) {
            regReviews = new HashSet<>();
        }
        regReviews.add(registration);
    }

    public void addServiceReview(Service service) {
        if (servReviews == null) {
            servReviews = new HashSet<>();
        }
        servReviews.add(service);
    }

    public void addRepairs(Repair repair) {
        if (repairs == null) {
            repairs = new HashSet<>();
        }
        repairs.add(repair);
    }

    public void addVulcanization(Vulcanization vulcanization) {
        if (this.vulcanization == null) {
            this.vulcanization = new HashSet<>();
        }
        this.vulcanization.add(vulcanization);
    }
}
