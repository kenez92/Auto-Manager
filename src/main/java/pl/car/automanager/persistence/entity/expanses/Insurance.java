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
@Table(name = "INSURANCE")
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private String description;

    @ManyToOne
    @JoinColumn(name = "EXPENSE_ID", nullable = false)
    private Expense expense;

}


