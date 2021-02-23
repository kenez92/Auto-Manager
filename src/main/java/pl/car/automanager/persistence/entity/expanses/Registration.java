package pl.car.automanager.persistence.entity.expanses;

import lombok.*;
import pl.car.automanager.persistence.entity.Expense;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "REGISTRATION")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    private LocalDate nextRegDate;

    private String faults;

    @ManyToOne
    @JoinColumn(name = "EXPENSE_ID", nullable = false)
    private Expense expense;
}
