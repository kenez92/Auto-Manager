package pl.car.automanager.persistence.entity.expanses;

import lombok.*;
import pl.car.automanager.persistence.entity.Expense;

import javax.persistence.*;

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

    private Double liters;

    @ManyToOne
    @JoinColumn(name = "EXPENSE_ID", nullable = false)
    private Expense expense;
}
