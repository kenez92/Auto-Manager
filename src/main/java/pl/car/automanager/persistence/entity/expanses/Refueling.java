package pl.car.automanager.persistence.entity.expanses;

import lombok.*;
import pl.car.automanager.persistence.entity.Expense;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "REGISTRATION")
public class Refueling {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column
    private LocalDate date;

    @Column
    private BigDecimal cost;

    @Column
    private Double liters;

    @ManyToOne
    @JoinColumn(name = "EXPENSE_ID", nullable = false)
    private Expense expense;
}
