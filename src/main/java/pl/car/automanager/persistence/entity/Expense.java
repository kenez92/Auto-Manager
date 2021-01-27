package pl.car.automanager.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.car.automanager.persistence.entity.expanses.Repair;

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
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            targetEntity = Repair.class,
            mappedBy = "expense")
    private List<Repair> repairs = new ArrayList<>();

}
