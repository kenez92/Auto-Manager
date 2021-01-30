package pl.car.automanager.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import pl.car.automanager.persistence.entity.expanses.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
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

    private LocalDate date;

    private BigDecimal cost;

    @OneToMany(
            cascade = CascadeType.ALL,
            targetEntity = Repair.class,
            mappedBy = "expense")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Repair> repairs = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            targetEntity = Insurance.class,
            mappedBy = "expense")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Insurance> insurances = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            targetEntity = Refueling.class,
            mappedBy = "expense")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Refueling> refueling = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            targetEntity = Registration.class,
            mappedBy = "expense")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Registration> registrations = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            targetEntity = Service.class,
            mappedBy = "expense")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Service> services = new ArrayList<>();


    @OneToMany(
            cascade = CascadeType.ALL,
            targetEntity = Vulcanization.class,
            mappedBy = "expense")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Vulcanization> vulcanization = new ArrayList<>();

    public void addRepair(Repair repair){
        repairs.add(repair);
    }

    public void addInsurance(Insurance insurance){
        insurances.add(insurance);
    }

    public void addRefueling(Refueling refueling){
        this.refueling.add(refueling);
    }

    public void addRegistration(Registration registration){
        registrations.add(registration);
    }

    public void addRService(Service service){
        services.add(service);
    }

    public void addVulcanization(Vulcanization vulcanization){
        this.vulcanization.add(vulcanization);
    }
}
