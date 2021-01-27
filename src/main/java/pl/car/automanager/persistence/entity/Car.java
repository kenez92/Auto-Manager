package pl.car.automanager.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.car.automanager.commons.enums.FuelEnum;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CAR")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "BRAND", nullable = false)
    private String brand;

    @Column(name = "MODEL", nullable = false)
    private String model;

    @Column(name = "VIN")
    private String vin;

    @Column(name = "ENGINE", nullable = false)
    private String engine;

    @Column(name = "AMOUNT_OF_DOORS")
    private Integer amountOfDoors;

    @Enumerated(EnumType.STRING)
    @Column(name = "FUEL", nullable = false)
    private FuelEnum fuel;

    @ManyToOne
    private User user;

    @OneToOne
    private Expense expense;
}
