package pl.car.automanager.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.car.automanager.persistence.entity.expanses.Repair;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "EXPENSE")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    private UUID uuid = UUID.randomUUID();

    private LocalDate date;

    private BigDecimal cost;

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "repairDescription", column  = @Column(name = "repair_description")))
    private Repair repair;

    @Override
    public boolean equals(Object that) {
        return this == that ||
                that instanceof Expense && Objects.equals(uuid, ((Expense) that).uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
