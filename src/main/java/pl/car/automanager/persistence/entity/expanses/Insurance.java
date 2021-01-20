package pl.car.automanager.persistence.entity.expanses;

import lombok.*;
import pl.car.automanager.persistence.entity.Expense;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "INSURANCE")
public class Insurance extends BaseExpanse {

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal cost;

    private String description;

    @ManyToOne
    @JoinColumn(name = "expense_id")
    private Expense expense;

}
