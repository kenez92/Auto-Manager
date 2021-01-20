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
@Table(name = "SERVICE")
public class Service extends BaseExpanse {

    private LocalDate date;

    private LocalDate nextSerDate;

    private BigDecimal cost;

    private String distance;

    @ManyToOne
    @JoinColumn(name = "expense_id")
    private Expense expense;
}
